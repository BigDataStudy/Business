package com.study.bigdata.jmeter;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.study.bigdata.simulation.DidiOrderRequestor;

public class RequesterAgent extends AbstractJavaSamplerClient{

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		DidiOrderRequestor requester = new DidiOrderRequestor();
		SampleResult sr = new SampleResult();
		sr.setSampleLabel("request: ");
	      try {
	    	  sr.sampleStart();// jmeter 开始统计响应时间标记
	    	  requester.send();
	    	  sr.setResponseData("请求发送成功！！", null);
              sr.setDataType(SampleResult.TEXT);
              sr.setSuccessful(true);
//			System.out.println(requester.getAvailablePassenger());
		} catch (Exception e) {
			sr.setResponseData("请求发送失败", null);
			sr.setSuccessful(false);
			e.printStackTrace();
		} finally {
            sr.sampleEnd();// jmeter 结束统计响应时间标记
        }
        return sr;
	}
	
	public static void main(String[] args){
		RequesterAgent ra = new RequesterAgent();
		ra.runTest(null);
	}

}
