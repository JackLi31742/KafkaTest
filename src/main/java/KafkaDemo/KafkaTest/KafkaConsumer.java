package KafkaDemo.KafkaTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.NodeState;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.cripac.isee.vpe.entities.Report;
import org.cripac.isee.vpe.entities.Report.ClusterInfo.ApplicationInfos;
import org.cripac.isee.vpe.entities.Report.ClusterInfo.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer 
{
	private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
	public static final String REPORT_TOPIC = "monitor-desc-";
	private String topic;

	public KafkaConsumer(String topic){  
        super();  
        this.topic = topic;  
    }

	public void report() {
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1); // 一次从主题中获取一个数据
		Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// 获取每次接收到的这个数据
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		while (iterator.hasNext()) {
			String message = new String(iterator.next().message());
			System.out.println("接收到的监控信息是: " + message);
			Report report=new Gson().fromJson(message,Report.class);
			List<ApplicationInfos> appList=report.clusterInfo.applicationInfosList;
			log.info("接收到的app编号是: " + appList.toString());
		}
	}

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "gpu-task-nod1:2181,gpu-task-nod2:2181,gpu1608:2181");// 声明zk
		properties.put("group.id", "kafkaCus");// 必须要使用别的组名称，
												// 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	}

	public static void main(String[] args) {
//		String ipString;
//        try {
//        	ipString=WebToolUtils.getLocalIP();
//		} catch (Exception e) {
//			// TODO: handle exception
//			ipString="Unknown ip";
//		}
		new KafkaConsumer(REPORT_TOPIC+"gpu1608").report();// 使用kafka集群中创建好的主题 test

	}
	
	/**
	 * 得到yarn
	 * LANG
	 * @return
	 */
	public YarnClient getYarnClient(){
		YarnConfiguration conf = new YarnConfiguration();
//		conf.set("fs.defaultFS", "hdfs://rtask-nod8:8020");
		conf.set("yarn.resourcemanager.scheduler.address", "rtask-nod8:8030");
		String hadoopHome = System.getenv("HADOOP_HOME");
		conf.addResource(new Path(hadoopHome + "/etc/hadoop/core-site.xml"));
		conf.addResource(new Path(hadoopHome + "/etc/hadoop/yarn-site.xml"));
		YarnClient yarnClient = YarnClient.createYarnClient();
		yarnClient.init(conf);
		yarnClient.start();
		return yarnClient;
	}
	
	public List<String> getNodesName(YarnClient yarnClient) throws YarnException, IOException{
		List<NodeReport> nodeList=yarnClient.getNodeReports(NodeState.RUNNING);
		List<String> nodeNamesList=new ArrayList<String>();
		for (int i = 0; i < nodeList.size(); i++) {
			
			NodeReport nodeReport=nodeList.get(i);
			nodeNamesList.add(nodeReport.getNodeId().getHost());
			
		}
		return nodeNamesList;
	}
}
