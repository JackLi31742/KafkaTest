package org.cripac.isee.vpe.entities;

import java.util.List;
import java.util.Map;

import org.cripac.isee.vpe.entities.Report.ServerInfo.DevInfo.ProcessesDevInfo;

/**
 * Report是一台服务器级，
 * @author LANG
 *
 */
public class Report {

	public ClusterInfo clusterInfo;
	/**
	 * key 是hostname，value是每个节点的信息
	 */
	public Map <String,ServerInfo> serverInfosMap;
	/**
	 * 每一台服务器的硬件信息
	 * @author LANG
	 *
	 */
	public static class ServerInfo {
		//主机名
		public String nodeName;
		public String ip;
		
		// 已使用的内存
		public long usedMem;
		 // 最大可使用内存
		public long jvmMaxMem;
		 // 可使用内存 
		public long jvmTotalMem;
		// 总的物理内存
		public long physicTotalMem;
		//jvm最近的CPU使用率
		public int procCpuLoad;
		//整个系统的最近CPU使用率
		public int sysCpuLoad;
		//物理CPU个数 
		public int cpuNum;
		//每个物理CPU中core的个数(即核数) 
		public int cpuCore;
		//逻辑CPU的个数 
		public int cpuVirtualNum;
		 //gpu个数
		public int deviceCount;
		
		//gpu 编号list
		public List<Integer> devNumList;
		//gpu 正在运行的程序使用的gpu编号list,一台服务器上总的
		public List<Integer> processNumAllList;
		//一台服务器上所有的正在运行的gpu 程序信息
		public List<ProcessesDevInfo> processAllList;
		
		/**
		 * 硬件gpu的信息
		 */
		public List<DevInfo> devInfosList;
		
		/**
		 * DevInfo是一台服务器里的gpu，可能有多个
		 * @author LANG
		 *
		 */
	    public static class DevInfo {
	    	//gpu编号
	    	public int index;
	    	public int fanSpeed;
	        //GPU使用率
	    	public int utilRate;
	    	public long usedMem;
	    	public long totalMem;
	    	public int temp;
	    	public int slowDownTemp;
	    	public int shutdownTemp;
	    	public int powerUsage;
	    	public int powerLimit;
	    	
	        //正在运行的gpu的程序个数
	    	public int infoCount;
	    	//每个gpu 正在运行的程序使用的gpu编号list
	    	public List<Integer> processNumList;
	    	
	    	public List<ProcessesDevInfo> processesDevInfosList;
	    	/**
	    	 * ProcessesDevInfo是每个gpu上的正在运行的程序
	    	 */
	    	
	        public static class ProcessesDevInfo {
	        	public int index;
	        	//正在使用的gpu的程序的pid
	        	public int pid;
	        	//正在使用的gpu的程序的pid 对应的 内存
	        	public long usedGpuMemory;
	        }
	    }
	    
	    
	}
	
	/**
	 * 从yarn上读取
	 */
	public static class ClusterInfo{
		public List<Nodes> nodeInfosList;
		public List<ApplicationInfos> applicationInfosList;
		
		/**
		 * 集群节点的信息
		 * @author LANG
		 *
		 */
		public static class Nodes{
			//名字
			public String name;
			//容量cpu
			public int capabilityCpu;
			//容量内存
			public int capabilityMemory;
			//已使用的cpu
			public int usedCpu;
			//已使用的内存
			public int usedMemory;
		}
		/**
		 * 每个application的信息
		 */
		public static class ApplicationInfos{
	    	public String applicationId;
	    	public int neededResourceMemory;
	    	public int neededResourceVcore;
	    	public int usedResourceMemory;
	    	public int usedResourceVcore;
	    	public int reservedResourceMemory;
	    	public int reservedResourceVcore;
	    	//cpu以及内存的信息，通过yarn去拿container的
	    	public List<ContarinerInfos> contarinerInfosList;
	    	//这个是为了拿到gpu的信息
	    	public Map<String,EachAppNode> eachAppNodeMap;
	    	/**
	    	 * 每台节点的信息，这些信息的数量是集群pc的数量，但由于是分布式，每个只能得到一台节点的，主要是为了得到gpu信息
	    	 */
	    	public static class EachAppNode{
		    	public String nodeName;
		    	public String ip;
		    	
		    	public int pid;
		    	//gpu 编号
		    	public int index;
		    	public long usedGpuMemory;
	    	}
	    	
	    	/**
	    	 * container的信息，分配的资源
	    	 * @author LANG
	    	 *
	    	 */
	    	public static class ContarinerInfos{
	    		public String containerId;
	    		public String state;
	    		//内存
	    		public int allocatedMemory;
	    		//cpu
	    		public int allocatedCpu;
	    	}
	    	
	    }
	}
	
	

	
    
    


}
