package com.lcydream.project.producer;

import com.lcydream.project.config.KafkasProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * KafkasProducer
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/11 16:55
 */
public class KafkasProducer {

	private final KafkaProducer<Integer,String> producer;


	public KafkasProducer() {
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", KafkasProperties.KAFKA_BROKER_LIST);
		properties.setProperty("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
		properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("client.id","producerClient");
		this.producer = new KafkaProducer<Integer, String>(properties);
	}

	public void sendMsg(){
		producer.send(new ProducerRecord<Integer, String>(KafkasProperties.TOPIC, 2, "magic message"+System.currentTimeMillis()),
				(metadata,exception)->
					System.out.println("message send to:["+metadata.partition()+"],[offset:["
					+metadata.offset()+"]")
				);
	}

	public static void main(String[] args) throws Exception {
		KafkasProducer kafkasProducer = new KafkasProducer();
		kafkasProducer.sendMsg();
		//System.in.read();
		TimeUnit.SECONDS.sleep(3);
	}
}
