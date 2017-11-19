package test;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * Created by silence on 2016/10/18.
 * Desc :
 */
public class MessageHandler extends Thread {
    private static final Logger logger = Logger.getLogger("messageHandler");
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MessageHandler.class);
    KafkaStream<byte[], byte[]> kafkaStreams;

    public MessageHandler(KafkaStream<byte[], byte[]> kafkaStreams) {
        super();
        this.kafkaStreams = kafkaStreams;
    }

    public void run() {
        //处理kafka数据
        ConsumerIterator<byte[], byte[]> streamIterator = kafkaStreams.iterator();
//        ClientRequestsModel model = new ClientRequestsModel();
        while (streamIterator.hasNext()) {
            log.info("开始处理kafka数据。。。");
            MessageAndMetadata<byte[], byte[]> record = streamIterator.next();
            String message = new String(record.message());
            log.info("{} topic 的数据，写入{}日志文件中。。。", record.topic(), record.topic());
            if (record.topic().equals("adPv")) {
//                model = (ClientRequestsModel) JsonUtils.json2Object(message, ClientRequestsModel.class);
                //保存数据到日志文件中
            } else if (record.topic().equals("adDevReg")) {
//                MyLogger.myInfo2(logger, message);
            }
        }
    }
}