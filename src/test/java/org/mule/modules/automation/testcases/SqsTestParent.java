/**
 * Mule Amazon SQS Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.automation.testcases;

import java.util.Map;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MessagingException;
import org.mule.modules.tests.ConnectorTestCase;

import com.amazonaws.services.sqs.model.QueueDeletedRecentlyException;

public class SqsTestParent extends ConnectorTestCase {
	
	@Rule
	// Increase timeout to allow some retrying
	public Timeout globalTimeout = new Timeout(120 * 1000);
	
	private static final int MAX_RETRIES = 10;
	private static final long RECREATE_QUEUE_DELAY = 61 * 1000;
	private static final long RETRY_DELAY = RECREATE_QUEUE_DELAY / MAX_RETRIES;
	private static final String CONNECTION_ERROR_MSG = "Failed to connect after "
			+ MAX_RETRIES + " retries";

	protected String getQueueAttribute(String attribute)
			throws Exception {
		upsertOnTestRunMessage("attribute", attribute);
		Map<String, String> attributes = runFlowAndGetPayload("get-queue-attributes");
		return attributes.get(attribute);
	}
	
	protected void setQueueAttribute(String attribute, String value)
			throws Exception {
		upsertOnTestRunMessage("attribute", attribute);
		upsertOnTestRunMessage("value", value);
		runFlowAndGetPayload("set-queue-attribute");
	}

	protected void deleteQueue() throws Exception {
		initializeTestRunMessage("testDeleteQueueTestData");
		runFlowAndGetPayload("delete-queue");
	}
	
	protected int getApproximateNumberOfMessages() throws Exception {
		return runFlowAndGetPayload("get-approximate-number-of-messages");
	}

	protected void sendMessage(String message) throws Exception {
		initializeTestRunMessage("testSendMessageTestData");
		upsertOnTestRunMessage("message", message);
		runFlowAndGetPayload("send-message-custom-message");
	}

	private void sleepOnException(MessagingException e,
			Class<? extends Exception> expectedClass)
			throws InterruptedException, MessagingException {
		if (e.causedBy(expectedClass)) {
			Thread.sleep(RETRY_DELAY);
		} else {
			throw e;
		}
	}
	
	@Override
	protected <T> T runFlowAndGetPayload(String flowName) throws Exception {
		for (int retries = 0; retries < MAX_RETRIES; retries++) {
			try {
				return super.runFlowAndGetPayload(flowName);
			} catch (MessagingException e) {
				sleepOnException(e, QueueDeletedRecentlyException.class);
			}
		}
		throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null,
				CONNECTION_ERROR_MSG);
	}
	
	@Override
	protected <T> T runFlowAndGetPayload(String flowName, String beanId)
			throws Exception {
		for (int retries = 0; retries < MAX_RETRIES; retries++) {
			try {
				return super.runFlowAndGetPayload(flowName, beanId);
			} catch (MessagingException e) {
				sleepOnException(e, QueueDeletedRecentlyException.class);
			}
		}
		throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null,
				CONNECTION_ERROR_MSG);
	}

}