package com.study.bigdata.simulation;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DidiTrafficProducer extends AbstractProducer {
	
    public static void main(String[] args) throws InterruptedException {
      
    }

    /*
     *  district_hash 	string 	区域哈希倄1�7 	1ecbb52d73c522f184a6fc53128b1ea1
		tj_level 	string 	不同拥堵程度的路段数 	1:231 2:33 3:13 4:10
		tj_time 	string 	时间戄1�7 	2016-01-15 00:35:11
     * */
    //1ecbb52d73c522f184a6fc53128b1ea1	1:231	2:33	3:13	4:10	2016-01-01 23:30:22
    @Override
	protected Message prepareMsg() throws Exception {
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        StringBuilder builder= new StringBuilder();
        builder.append("1ecbb52d73c522f184a6fc53128b1ea1").append("\t");//district_hash
        builder.append("1:231 2:33 3:13 4:10").append("\t");//tj_level
        
        Date date= new Date();
        builder.append(sdf.format(date));
        int ss= Calendar.getInstance().get(Calendar.SECOND);
        
        return new Message(String.valueOf(ss), builder.toString());
	}

	@Override
	protected String getTopic() {
		return "didi-traffic-topic";
	}

}