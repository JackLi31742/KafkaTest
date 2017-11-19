package demo;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;


/**
 * @author     lanwei
 * @Date       2016.12.21
 * @fileName   KafkaProducer.java
 */
public class KafkaProducer extends Thread {
	private String topic;
	
	public KafkaProducer(String topic){
		this.topic = topic;	
	}

	@Override
	public void run() {
		Producer producer = creadProducer();
		try {
			for(int i = 0;i < 100;i++){
				producer.send(new KeyedMessage(topic,new String(("1111Demo2蓝"+i).getBytes(),"UTF-8")));				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private Producer creadProducer(){
		Properties props = new Properties();
		props.setProperty("zookeeper.connect", "192.168.157.129:2181");
		props.setProperty("serializer.class",StringEncoder.class.getName());
		props.setProperty("metadata.broker.list","192.168.157.129:9092");
		
		return new Producer( new ProducerConfig(props));
	}
	
		
	public static void main(String[] args) throws InterruptedException {
		new KafkaProducer("test").start();;
	}
}
