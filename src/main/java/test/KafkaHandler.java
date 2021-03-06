package test;

import kafka.consumer.KafkaStream;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by silence on 2016/10/18.
 * Desc :
 */
public class KafkaHandler extends Thread {
    List<KafkaStream<byte[], byte[]>> kafkaStreams;

    public KafkaHandler(List<KafkaStream<byte[], byte[]>> kafkaStreams) {
        super();
        this.kafkaStreams = kafkaStreams;
    }

    ExecutorService service = Executors.newFixedThreadPool(2);

    public void run() {
        try {
            Iterator<KafkaStream<byte[], byte[]>> iterator = kafkaStreams.iterator();
            while (iterator.hasNext()) {
                KafkaStream<byte[], byte[]> next = iterator.next();
                service.submit(new MessageHandler(next));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
