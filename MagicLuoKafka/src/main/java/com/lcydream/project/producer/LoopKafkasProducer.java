package com.lcydream.project.producer;

import com.lcydream.project.config.KafkasProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * KafkasProducer
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/11 16:55
 */
public class LoopKafkasProducer implements Runnable{

	private final KafkaProducer<Integer,String> producer;


	public LoopKafkasProducer() {
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", KafkasProperties.KAFKA_BROKER_LIST);
		properties.setProperty("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
		properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		//properties.setProperty("partitioner.class","com.lcydream.project.partation.KafkaPartition");
		properties.setProperty("client.id","producerClient");
		this.producer = new KafkaProducer<Integer, String>(properties);
	}

	public void sendMsg(){
		producer.send(new ProducerRecord<Integer, String>(KafkasProperties.THREE_CONSUMER_TOPIC, 2, "magic message"+System.currentTimeMillis()),
				(metadata,exception)->
					System.out.println("message send to:["+metadata.partition()+"],[offset:["
					+metadata.offset()+"]")
				);
	}

	@Override
	public void run() {
		int messageNo=0;
		while(true){
			String messageStr="messages-"+messageNo;
			producer.send(new ProducerRecord<Integer, String>(KafkasProperties.THREE_CONSUMER_TOPIC, messageNo, messageStr), (recordMetadata,e)->
					System.out.println("message send to:["+recordMetadata.partition()+"],offset:["+recordMetadata.offset()+"]")
			);
			++messageNo;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		LoopKafkasProducer kafkasProducer = new LoopKafkasProducer();
		//kafkasProducer.sendMsg();
		//System.in.read();
		//TimeUnit.SECONDS.sleep(3);
		new Thread(kafkasProducer).start();
	}
}
