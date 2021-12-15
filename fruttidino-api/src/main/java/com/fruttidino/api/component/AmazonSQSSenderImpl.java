package com.fruttidino.api.component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Reference:
 * https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html
 * 2.2.3 Spring Boot auto-configuration
 */

@RequiredArgsConstructor
@Component
public class AmazonSQSSenderImpl implements AmazonSQSSender {
	private final ObjectMapper objectMapper;
	private final AmazonSQS amazonSQS;
	private final AmazonSQSProperties properties;

	@Override
	public SendMessageResult sendMessage(Object message) throws JsonProcessingException {
		SendMessageRequest sendMessageRequest =
				new SendMessageRequest(properties.getUrl(), objectMapper.writeValueAsString(message))
				.withMessageGroupId("ttcnc-sales")
				.withMessageDeduplicationId(UUID.randomUUID().toString());

		return amazonSQS.sendMessage(sendMessageRequest);
	}

}
