package net.johnpage.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerFactory{
  private static Producer producer;
  private static Properties properties;
  public static void setProperties(Properties properties) {
    ProducerFactory.properties = properties;
  }
  public static Producer getInstance() {
    if(producer==null){
      ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(null);
      producer = new KafkaProducer(ProducerFactory.properties);
      Thread.currentThread().setContextClassLoader(contextClassLoader);
    }
    return producer;
  }
  protected static void setInstance(Producer thisProducer) {
    producer = thisProducer;
  }
}