package com.study.bigdata.simulation;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DidiWeatherProducer extends AbstractProducer {
	
    public static void main(String[] args) throws InterruptedException {
      
    }

    
    /*
    Time 	string 	时间戄1�7 	2016-01-15 00:35:11
	Weather 	int 	天气 	7
	temperature 	double 	温度 	-9
	PM2.5 	double 	pm25 	66*/

    //2016-01-01 00:00:28	1	4.0	177
    @Override
	protected Message prepareMsg() throws Exception{
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        StringBuilder builder= new StringBuilder();
        Date date= new Date();
        builder.append(sdf.format(date)).append("\t"); //Time
        builder.append("1").append("\t");//Weather
        builder.append("4.0").append("\t");//temperature
        builder.append("177").append("\t");//PM2.5
        
        int ss= Calendar.getInstance().get(Calendar.SECOND);
        
        return new Message(String.valueOf(ss), builder.toString());
	}

	@Override
	protected String getTopic() {
		return "didi-weather-topic";
	}

}