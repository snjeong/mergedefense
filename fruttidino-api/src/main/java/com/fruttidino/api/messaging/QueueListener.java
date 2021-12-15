package com.fruttidino.api.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruttidino.api.component.AmazonSQSSender;
import com.fruttidino.api.entity.Mint;
import com.fruttidino.api.entity.Transfer;
import com.fruttidino.api.entity.nft.NftLog;
import com.fruttidino.api.repository.NftLogRepository;
import com.fruttidino.api.service.DinoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class QueueListener {

	private final DinoService dinoService;
	private final ObjectMapper objectMapper;
	private final AmazonSQSSender amazonSQSSender;
	private final NftLogRepository nftLogRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

	@Autowired
	public QueueListener(DinoService dinoService, ObjectMapper objectMapper, AmazonSQSSender amazonSQSSender, NftLogRepository nftLogRepository) {
		this.dinoService = dinoService;
		this.objectMapper = objectMapper;
		this.amazonSQSSender = amazonSQSSender;
		this.nftLogRepository = nftLogRepository;
	}

	@PostMapping("/send")
	public String send(@RequestBody Mint message) throws JsonProcessingException {
		amazonSQSSender.sendMessage(message);
		return "OK";
	}

	@SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	private void receiveMessage(@Headers Map<String, String> headers, @Payload String message) throws JsonProcessingException {

		log.info("payload : {}", message);
		log.info("hdeader : {}", headers);
		NftLog nftLog = new NftLog();

		if (headers.get("MessageGroupId") == null)
			return;

		if (headers.get("MessageGroupId").equals("Mint")) {
			Mint readValue = objectMapper.readValue(message, Mint.class);
			// creating UUID
			UUID uid = UUID.fromString(readValue.getDinoId());
			dinoService.addNftInfo(uid, readValue);
		}
		else {
			Transfer readValue = objectMapper.readValue(message, Transfer.class);
			dinoService.addUserTransfer(readValue.getTokenId(), readValue);
		}
	}
}
