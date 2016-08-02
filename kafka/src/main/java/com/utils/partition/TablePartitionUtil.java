package com.utils.partition;


public class TablePartitionUtil {
	public static final int DEFAULT_PARTITION_NUM = 6;
	
	public static int UUIDPartitioner(String id, int numPartitions) {
		return Math.abs(id.hashCode()) % numPartitions;
	}
	
	public static String tableRouter(String table_name, String id) {
		return table_name + "_" + UUIDPartitioner(id, DEFAULT_PARTITION_NUM);
	}
}
