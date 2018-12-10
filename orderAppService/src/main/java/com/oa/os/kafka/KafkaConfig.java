package com.oa.os.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.events.parser.JSONParser;
import com.oa.os.processor.OrderProcessor;

@Configuration
public class KafkaConfig {

	@Autowired
	Environment env;

	@SuppressWarnings("rawtypes")
	@Bean
	public ProducerFactory producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate(producerFactory());
	}
	
	@Bean
	public JSONParser createJSONParser() {
		return new JSONParser();
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.broker"));
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

		return props;
	}
	
	@Bean
	public PaymentErrEventConsumer paymentErrEventListener() {
		return new PaymentErrEventConsumer();
	}
	
	@Bean
	public OrderProcessor createOrderProcessor() {
		return new OrderProcessor();
	}

}
