//package hadoop;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URISyntaxException;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//import org.cripac.isee.alg.pedestrian.tracking.Tracklet;
//import org.cripac.isee.vpe.util.hdfs.HadoopHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class HadoopTest {
//
//	private static final Logger log = LoggerFactory.getLogger(HadoopTest.class);
//	private	static Configuration conf;
//	private static FileSystem fs;
//	
//	public static FileSystem HDFSOperation() throws IOException{
//		conf = new Configuration();
//		conf.set("fs.default.name", "hdfs://172.18.33.84:8020");
//      String hadoopHome = System.getenv("HADOOP_HOME");
//      conf.addResource(new Path(hadoopHome + "/etc/hadoop/core-site.xml"));
//      conf.addResource(new Path(hadoopHome + "/etc/hadoop/yarn-site.xml"));
//      conf.setBoolean("dfs.support.append", true);
////      conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName(), "LaS-VPE-Platform-Web");
////      conf.set("fs.file.impl", LocalFileSystem.class.getName(), "LaS-VPE-Platform-Web");
//		fs = FileSystem.get(conf);
//		return fs;
//	}
//	public static boolean downLoadFromHdfs(String hdfsPath,String localPath ) throws IOException, URISyntaxException{
//		boolean bFlg = false;
////		FileSystem fs = FileSystem.get(conf);//这里的conf已经配置的ip地址和端口号
//		FileSystem fs=HDFSOperation();
//		InputStream in = null;
//		OutputStream out = null;
//        try {
//			in = fs.open(new Path(hdfsPath));  
//			out = new FileOutputStream(localPath);
//			IOUtils.copyBytes(in, out, 4096, true);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		}finally{
//			IOUtils.closeStream(in);
//			IOUtils.closeStream(out);
//			fs.close();
//		}
//		return bFlg;
//	}
//
//	public static void main(String[] args) throws Exception {
//		
////		String dsf = "/user/vpe.cripac/new2/20131223103919-20131223104515/46b64a7f-fbbb-406b-9892-f47c6c8d93b2/27/bbox.data";
////		downLoadFromHdfs(dsf, "/home/vpe.cripac/projects/jun.li/KafkaTest2/2.txt");
//		String storeDir = "/user/vpe.cripac/new2/20131223125419-20131223130015/bbb12b39-426e-4c99-9188-828a6ce1de0b/46";
////      storeDir = base + storeDir;
//
//      try {
//          Tracklet tracklet = HadoopHelper.retrieveTracklet(storeDir);
//          System.out.println("Test getTrackletInfo, the info is:");
//          System.out.println(tracklet.toString());
//      } catch(IOException e1) {
//      } catch(URISyntaxException e2) {
//      }
//		/*try {
//			String dsf = "/user/vpe.cripac/new2/20131223103919-20131223104515/46dddf58-38b8-4848-a243-48bfd9e8f270/6/bbox.data";
//			// Configuration conf = new Configuration();
//			// final InputStreamReader infoReader;
//			// FileSystem fs = FileSystem.get(URI.create(dsf),conf);
//			FileSystem hdfs = new HDFSFactory().produce();
//			// infoReader = new InputStreamReader(hdfs.open(new
//			// Path(dsf)),"utf-8");
//			FSDataInputStream hdfsInStream = hdfs.open(new Path(dsf));
//
//			InputStreamReader inputStreamReader = new InputStreamReader(hdfsInStream,"utf-8");
//			List<String> list = IOUtils.readLines(inputStreamReader);
//			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//				String string = (String) iterator.next();
//				log.info(string);
//			}
//			// byte[] ioBuffer = new byte[1024*200];
//			// int readLen = hdfsInStream.read();
//			// while(readLen!=-1)
//			// {
//			// System.out.write(ioBuffer, 0, readLen);
//			// readLen = hdfsInStream.read(ioBuffer);
//			// log.info(String.valueOf(readLen));
//			// }
//			hdfsInStream.close();
//			hdfs.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//
//	}
//}