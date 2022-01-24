package com.fruttidino.api.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruttidino.api.component.AmazonSQSSender;
import com.fruttidino.api.entity.nft.Mint;
import com.fruttidino.api.entity.nft.Transfer;
import com.fruttidino.api.entity.nft.NftLog;
import com.fruttidino.api.repository.NftLogRepository;
import com.fruttidino.api.service.DinoNftQueueService;
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

	private final DinoNftQueueService dinoNftQueueService;
	private final ObjectMapper objectMapper;
	private final AmazonSQSSender amazonSQSSender;
	private final NftLogRepository nftLogRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

	@Autowired
	public QueueListener(DinoNftQueueService dinoNftQueueService, ObjectMapper objectMapper, AmazonSQSSender amazonSQSSender, NftLogRepository nftLogRepository) {
		this.dinoNftQueueService = dinoNftQueueService;
		this.objectMapper = objectMapper;
		this.amazonSQSSender = amazonSQSSender;
		this.nftLogRepository = nftLogRepository;
	}

	@PostMapping("/send/sqs/mint")
	public String send(@RequestBody Mint message) throws JsonProcessingException {
		amazonSQSSender.sendMessage(message);
		return "OK";
	}

	@SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	private void receiveMessage(@Headers Map<String, String> headers, @Payload String message) throws JsonProcessingException {

		log.info("payload : {}", message);
		log.info("hdeader : {}", headers);
		NftLog nftLog = new NftLog();

		if (headers.get("MessageGroupId") == null) {
			log.error("[receiveMessage][error] : not exist MessageGroupId, message id = {}", headers.get("MessageId"));
			return;
		}

		if (headers.get("MessageDeduplicationId") == null) {
			log.error("[receiveMessage][error] : not exist MessageDeduplicationId, message id = {}", headers.get("MessageId"));
			return;
		}

		String messageDeduplicationId = headers.get("MessageDeduplicationId");
		if (headers.get("MessageGroupId").equals("Mint")) {
			Mint mint = objectMapper.readValue(message, Mint.class);
			UUID uid = UUID.fromString(mint.getDinoId());
			dinoNftQueueService.addNftMintInfo(uid, messageDeduplicationId, mint);
		}
		else {
			Transfer transfer = objectMapper.readValue(message, Transfer.class);
			dinoNftQueueService.addNftOwnerTransfer(transfer.getTokenId(), messageDeduplicationId, transfer);
		}
	}
}
