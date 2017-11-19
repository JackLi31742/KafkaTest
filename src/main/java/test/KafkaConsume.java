package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class KafkaConsume  extends Thread {
	private final Logger log = LoggerFactory.getLogger(KafkaConsume.class);
    private final static String TOPIC = "URL";

    private static Properties properties;

    static {
        properties = new Properties();
//        String path = KafkaConsume.class.getResource("/").getFile().toString()
//                + "kafka.properties";
        InputStream is = KafkaConsume.class.getClassLoader().getResourceAsStream("kafka.properties");
        try {
//            FileInputStream fis = new FileInputStream(new File(path));
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取消息
     * 
     * @throws Exception
     */
    public void getMsg() throws Exception {
        ConsumerConfig config = new ConsumerConfig(properties);

        ConsumerConnector consumer = kafka.consumer.Consumer
                .createJavaConsumerConnector(config);

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

        topicCountMap.put(TOPIC, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());

        StringDecoder valueDecoder = new StringDecoder(
                new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer
                .createMessageStreams(topicCountMap, keyDecoder, valueDecoder);

        KafkaStream<String, String> stream = consumerMap.get(TOPIC).get(0);

        ConsumerIterator<String, String> it = stream.iterator();

        while (it.hasNext()) {
            String json = it.next().message();
//            User user = (User) JsonUtils.JsonToObj(json, User.class);
//            System.out.println(user);
            log.info("接收："+json);
        }
    }
    public void run() {
    	try {
			getMsg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void main(String[] args) throws Exception {
		new KafkaConsume().start();
//    	InputStream is = KafkaConsume.class.getClassLoader().getResourceAsStream("kafka.properties");
//    	while(is.re)
//    	System.out.println(IOUtils.toString(is));
	}
}
