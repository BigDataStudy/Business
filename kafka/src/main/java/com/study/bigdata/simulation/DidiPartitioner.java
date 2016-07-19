package com.study.bigdata.simulation;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class DidiPartitioner implements Partitioner {
    public DidiPartitioner (VerifiableProperties props) {
 
    }
 
    public int partition(Object key, int a_numPartitions) {
       int partition = 0;
      
       partition = Integer.parseInt(key.toString()) % a_numPartitions;
       return partition;
  }
 
}