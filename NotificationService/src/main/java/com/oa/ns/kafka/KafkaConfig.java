package com.oa.ns.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.events.parser.JSONParser;

@Configuration
public class KafkaConfig {

	@Autowired
	Environment env;

	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {

		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.broker"));
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, env.getProperty("enable.auto.commit"));
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, env.getProperty("auto.commit.interval.ms"));
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("group.id"));
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("kafka.auto.offset.reset"));
		return propsMap;

	}
	
	@Bean
	public JSONParser createJSONParser() {
		return new JSONParser();
	}

	@Bean
	public OrderNotificationConsumer orderListener() {
		return new OrderNotificationConsumer();
	}
	
	@Bean
	public OrderErrNotificationConsumer orderErrListener() {
		return new OrderErrNotificationConsumer();
	}
	
	@Bean
	public PaymentNotificationConsumer paymentListener() {
		return new PaymentNotificationConsumer();
	}
	
	@Bean
	public PaymentErrNotificationConsumer paymentErrListener() {
		return new PaymentErrNotificationConsumer();
	}
	
	@Bean
	public InventoryNotificationConsumer inventoryListener() {
		return new InventoryNotificationConsumer();
	}
	
	@Bean
	public InventoryErrNotificationConsumer inventoryErrListener() {
		return new InventoryErrNotificationConsumer();
	}
	
	@Bean
	public FulfillmentNotificationConsumer fulfillmentListener() {
		return new FulfillmentNotificationConsumer();
	}
	
	@Bean
	public FulfillmentErrNotificationConsumer fulfillmentErrListener() {
		return new FulfillmentErrNotificationConsumer();
	}
}
