package com.lcydream.project.partation;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * KafkaPartation
 *
 * @author Luo Chun Yun
 * @email 1475653689@qq.com
 * @date 2019/1/14 14:46
 */
public class KafkaPartition implements Partitioner {

	@Override
	public int partition(String topic, Object key, byte[] keyBytes,
	                     Object value, byte[] valueBytes, Cluster cluster) {
		//获取所有分区信息
		//List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
		//获取分区数量
		//int partitionNo = partitionInfos.size();
		int partitionNo = cluster.partitionCountForTopic(topic);
		//获取key的hashCode
		int hashCode = key.hashCode();
		return Math.abs(hashCode%partitionNo);
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> configs) {

	}
}
