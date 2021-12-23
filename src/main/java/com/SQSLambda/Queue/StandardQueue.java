package com.SQSLambda.Queue;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import javax.jms.*;

public class StandardQueue {

    public static void main(String[] args) throws JMSException {

        // Create a new connection factory with all defaults (credentials and region) set automatically
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.defaultClient()
        );

// Create the connection.
        SQSConnection connection = null;
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        // Get the wrapped client
        AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

// Create an SQS queue named MyStandardQueue, if it doesn't already exist
        try {
            if (!client.queueExists("MyStandardQueue")) {
                client.createQueue("MyStandardQueue");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

        // Create the nontransacted session with AUTO_ACKNOWLEDGE mode
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a queue identity and specify the queue name to the session
        Queue queue = session.createQueue("MyStandardQueue");

        // Create a producer for the 'MyStandardQueue'
        MessageProducer producer = session.createProducer(queue);

        // Create the text message
        TextMessage message = session.createTextMessage("Standard Queue Working");

        // Send the message
        producer.send(message);
        System.out.println("JMS Message " + message.getJMSMessageID());
    }
}
