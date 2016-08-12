package com.study.bigdata.web;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.study.bigdata.simulation.AbstractProducer;
import com.study.bigdata.simulation.DidiOrderReplyer;
import com.study.bigdata.simulation.DidiOrderRequestor;

public class DidiReplyServlet extends HttpServlet { 
	static Logger logger = Logger.getLogger(DidiReplyServlet.class.getName());
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)  
        throws IOException, ServletException 

    {  
		doPost(request, response);
    }  
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws IOException, ServletException {  
		  DidiOrderReplyer reply = new DidiOrderReplyer();
	      PrintWriter out = response.getWriter(); 
	      try {
	    	  reply.send();
    		  response.setContentType("text/html");  
	          out.println("<html>");  
	          out.println("<head>");  
	          out.println("<title>Successful Page</title>");  
	          out.println("</head>");  
	          out.println("<body>");  
	          out.println("<h1>The Reply is sent successfully!!!</h1>");  
	          out.println("<h1>Test hot deploy - Reply</h1>");
	          out.println("</body>");  
	          out.println("</html>"); 
	          logger.info("reply successfully");
	    	  
		} catch (Exception e) {
			logger.error("fail to reply", e);
			  response.setContentType("text/html");  
	          out.println("<html>");  
	          out.println("<head>");  
	          out.println("<title>Fail Page</title>");  
	          out.println("</head>");  
	          out.println("<body>");  
	          out.println("<h1>The Reply Failed to sent</h1>");  
	          out.println("</body>");  
	          out.println("</html>"); 
			  
		}
	      
	}

}  