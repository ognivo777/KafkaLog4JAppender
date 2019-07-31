# Kafka Log4J Appender changed for Pega PRPC 7

## A Pega log4j Appender that streams log events to a Kafka topic. 

### Configuration

```xml
	<appender name="KafkaStream" class="net.johnpage.kafka.KafkaLog4JAppender">
		<param name="Topic" value="pega-logs"/>
		<param name="kafkaProducerProperties" value="bootstrap.servers=127.0.0.1:9092&#10;value.serializer=org.apache.kafka.common.serialization.StringSerializer&#10;key.serializer=org.apache.kafka.common.serialization.StringSerializer&#10;compression.type=snappy&#10;client.id=pega-pe-722"/>
		<layout class="com.pega.pegarules.priv.LogLayoutJSON">
			<param name="userFields" value="sourceLabel:pega-live,ls_type:pega-rules,src-vm:${hostName},src-node:nodename,src-env:${sys:pega.appservername}"/>
		</layout>
	</appender>
```
This is a Pega Log4J v1 Appender integrated with a Kafka Producer. It streams events as they occur to a remote Kafka queue. 


### Building
```
mvn clean install
```

### Usage

1. Place the *kafka-log4j-appender-1.0.jar* in the application *"lib"* directory.
2. Download and place the *kafka-clients-0.10.0.0.jar* and the *json-simple-1.1.1.jar* into the application *"lib"* directory.
3. Edit the log4j configuration file.
4. Ensure a Kafka Producer properties file is available at the location configured in the lo4j configuration file.

### Kafka Producer Properties

A typical Kafka Producer properties can be specified via file that might read as follows:
```properties
bootstrap.servers=a.domain.com:9092
value.serializer=org.apache.kafka.common.serialization.StringSerializer
key.serializer=org.apache.kafka.common.serialization.StringSerializer
security.protocol=SSL
ssl.truststore.location=a.kafka.client.truststore.jks
ssl.truststore.password=apassword
```
A complete reference to the producer properties is [here](https://kafka.apache.org/documentation.html#producerconfigs).

### Built using:
* [prlogging-1.2.14.jar](https://community1.pega.com/community/product-support/question/how-i-can-get-prpublicjar-and-prlogging-1214jar-include-my)
* [Apache Kafka Producer 0.10](https://kafka.apache.org/)

### Kafka Version
Tested with Kafka 0.10. Should be backwards compatible with 0.90 and 0.82. These 3 versions rely on the following initialization of the Producer:
```java
new KafkaProducer(Properties properties) 
```
To use a different version of Kafka, include the desired version on the classpath. Version-appropriate properties will need to be used.
