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

### Sample LogStach Configuration
```
input {
  kafka {
    bootstrap_servers => "127.0.0.1:9092"
    topics => ["pega-logs"]
    codec => json
  }	
}

output {
  stdout { codec => rubydebug }
}
```
Example of result events:
```ruby
{
    "@logtimestamp" => "2019-07-31T17:17:16.304Z",
            "class" => "?",
       "pegathread" => "STANDARD",
      "source_host" => "srv001",
             "file" => "?",
         "src-node" => "nodename",
       "@timestamp" => 2019-07-31T17:17:16.304Z,
      "logger_name" => "com.pega.pegarules.search.internal.PRSearchProviderImpl",
           "src-vm" => "",
           "method" => "?",
          "ls_type" => "pega-rules",
         "@version" => 1,
      "line_number" => "?",
          "src-env" => "",
      "sourceLabel" => "pega-live",
            "level" => "INFO",
          "message" => "Initialized full text search functionality for this node. Full-text search enabled for node F3962772FF1FEECF62815A313B27FC74",
      "thread_name" => "StartupTaskUtil INITIALIZE_SEARCH"
}
```

### Building
```
mvn clean install
```

### Usage

1. [Download](https://github.com/ognivo777/KafkaLog4JAppender/releases) the *kafka-pega-log4j-appender-1.1.jar* and import in pega using Application->Distribution->Import wizard.
2. Edit the *prlogging.xml* configuration file.
3. Restart pega application server.

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
