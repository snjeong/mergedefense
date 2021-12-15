package com.fruttidino.api.component;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmazonSQSSender {

	SendMessageResult sendMessage(Object message) throws JsonProcessingException;

}
