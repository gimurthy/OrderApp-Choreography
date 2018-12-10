# OrderApp-Choreography
Sample app to demo the Event Driven Choreography pattern using Spingboot &amp; Kafka

<b>Kafka Topics setup</b> - Run the following commands to setup the various topics
 
- kafka-topics.bat --create --topic g-order-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-order-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-payment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-payment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-inventory-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-inventory-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-fulfillment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

- kafka-topics.bat --create --topic g-fulfillment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
