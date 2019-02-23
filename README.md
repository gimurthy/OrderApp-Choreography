# OrderApp-Choreography
Sample app to demo the Event Driven Choreography pattern using Spingboot &amp; Kafka

<b>Kafka Topics setup</b> - Run the following commands to setup the various topics
- kafka-topics.bat --create --topic g-order-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-order-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-payment-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-payment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-inventory-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-inventory-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-fulfillment-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1
- kafka-topics.bat --create --topic g-fulfillment-err-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 1

Sample Message - 
{
	"customerId": "gimu",
	"productSKU": "SKU0001",
	"qty": 2,
	"cost": 100,
	"deliveryAddress": "Blore"
}

URL to kickstart the application - http://localhost:8080/order/create
