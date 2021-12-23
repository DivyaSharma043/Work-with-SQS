## Dependencies for SQS

```java
<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>software.amazon.awssdk</groupId>
				<artifactId>bom</artifactId>
				<version>2.17.102</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
  
  <dependency>
			<groupId>com.amazonaws</groupId>
			<artifactId>amazon-sqs-java-messaging-lib</artifactId>
			<version>1.0.8</version>
			<type>jar</type>
		</dependency>
```
## Create Connection with AWS

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
# Create Standard Queue

```java
// Create an SQS queue named MyStandardQueue, if it doesn't already exist
        try {
            if (!client.queueExists("MyStandardQueue")) {
                client.createQueue("MyStandardQueue");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
```
