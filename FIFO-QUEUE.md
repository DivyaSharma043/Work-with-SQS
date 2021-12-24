# FIFO QUEUE

## Create connection
```java
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
```

## Create an amazon sqs fifo queue

```java
        // Create an Amazon SQS FIFO queue named MyFIFOQueue.fifo, if it doesn't already exist
        if (!client.queueExists("MyFIFOQueue.fifo")) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("FifoQueue", "true");
            attributes.put("ContentBasedDeduplication", "true");
            client.createQueue(new CreateQueueRequest().withQueueName("MyFIFOQueue.fifo").withAttributes(attributes));
        }
```
## Sending messages synchronously
```java
        // Create the nontransacted session with AUTO_ACKNOWLEDGE mode
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a queue identity and specify the queue name to the session
        Queue queue = session.createQueue("MyFIFOQueue.fifo");

        // Create a producer for the 'MyStandardQueue'
        MessageProducer producer = session.createProducer(queue);

        // Create the text message
        TextMessage message = session.createTextMessage("FIFO QUEUE");

        // Set the message group ID
        message.setStringProperty("JMSXGroupID", "Default");

        // Send the message
        producer.send(message);
        System.out.println("JMS Message " + message.getJMSMessageID());
        System.out.println("JMS Message Sequence Number " + message.getStringProperty("JMS_SQS_SequenceNumber"));
```

## Receiving messages synchronously
```java
        // Create a consumer for the 'MyFIFOQueue'
        MessageConsumer consumer = session.createConsumer(queue);
        
        // Start receiving incoming messages
        connection.start();

        // Receive a message from 'MyFIFOQueue' and wait up to 1 second
        Message receivedMessage = consumer.receive(1000);

        // Cast the received message as TextMessage and display the text
        if (receivedMessage != null) {
            System.out.println("Received: " + ((TextMessage) receivedMessage).getText());
            System.out.println("Group id: " + receivedMessage.getStringProperty("JMSXGroupID"));
            System.out.println("Message deduplication id: " + receivedMessage.getStringProperty("JMS_SQS_DeduplicationId"));
            System.out.println("Message sequence number: " + receivedMessage.getStringProperty("JMS_SQS_SequenceNumber"));
        }
```

## Close Connection
```java
        // Close the connection (and the session).
        connection.close();
        System.out.println("Connection close");
```
