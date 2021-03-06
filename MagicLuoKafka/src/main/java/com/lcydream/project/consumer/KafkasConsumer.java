package com.lcydream.project.consumer;

import com.lcydream.project.config.KafkasProperties;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * KafkasConsumer
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/11 20:59
 */
public class KafkasConsumer extends ShutdownableThread {

	//High level consumer
	//low level consumer

	private final KafkaConsumer<String,Object> consumer;

	public KafkasConsumer() {
		super("KafkasConsumer",false);
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkasProperties.KAFKA_BROKER_LIST);
		//GroupId 消息所属的分组
		properties.put(ConsumerConfig.GROUP_ID_CONFIG,"GroupOne");
		//是否自动提交消息:offset
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
		//自动提交的间隔时间
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
		//设置使用最开始的offset偏移量为当前group.id的最早消息
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		//设置心跳时间
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
		//设置key和value的反序列化对象
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
		this.consumer = new KafkaConsumer<>(properties);
	}

	@Override
	public void doWork() {
		consumer.subscribe(Collections.singletonList(KafkasProperties.TOPIC));
		ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(1000,0));
		for(ConsumerRecord record : records){
			System.out.println("["+record.partition()+"]receiver message:["
					+record.key()+"->"+record.value()+"],offset:["
					+record.offset()+"]");
		}
	}

	public static void main(String[] args) throws IOException {
		KafkasConsumer kafkasConsumer = new KafkasConsumer();
		kafkasConsumer.start();
	}

}
