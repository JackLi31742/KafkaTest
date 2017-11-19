package demo;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import KafkaDemo.KafkaTest.KafkaConsumer;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafKaComsumer extends Thread {
	private final Logger log = LoggerFactory.getLogger(KafKaComsumer.class);
	private String topic;
	
	public KafKaComsumer(String topic){
		super();
		this.topic = topic;
	}
	
	@Override
	public void run() {
		ConsumerConnector consumer = createConsumer();
									
		Map<String,Integer> topicCountMap = new HashMap<String,Integer>();//指定topic名称
		topicCountMap.put(topic, 1);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> retStreams = consumer.createMessageStreams(topicCountMap);//messageStreams

		KafkaStream<byte[], byte[]> kafkaStream  = retStreams.get(topic).get(0);//获取指定的topic
		log.info("接收---------------------");
//		System.out.println(retStreams.get(topic).size()+ "----"+kafkaStream.clientId());
		log.info(retStreams.get(topic).size()+ "----"+kafkaStream.clientId());
		
		ConsumerIterator<byte[],byte[]> it = kafkaStream.iterator();		
		while(it.hasNext()){
			try {
				byte[] message = it.next().message();
//				System.out.println("receive message is " + new String(message,"UTF-8"));
				log.info("receive message is " + new String(message,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	private ConsumerConnector createConsumer(){
	
		Properties consumerPros = new Properties();//替换linux上的默认Properties 配置
		consumerPros.setProperty("zookeeper.connect", "gpu-task-nod1:2181,gpu-task-nod2:2181,gpu1608:2181");
		consumerPros.setProperty("group.id", "test1");
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerPros));
	}
	
	public static void main(String[] args) {
		new KafKaComsumer("monitor-report").start();
	}

}
