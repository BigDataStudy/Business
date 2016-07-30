package com.study.bigdata.web;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.study.bigdata.simulation.DidiOrderRequestor;

public class DidiRequestServlet extends HttpServlet { 
	static Logger logger = Logger.getLogger(DidiRequestServlet.class.getName());
	
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
	      DidiOrderRequestor requester = new DidiOrderRequestor();
	      PrintWriter out = response.getWriter(); 
	      try {
	    	  requester.send();
	    	  response.setContentType("text/html");  
	          out.println("<html>");  
	          out.println("<head>");  
	          out.println("<title>Successful Page</title>");  
	          out.println("</head>");  
	          out.println("<body>");  
	          out.println("<h1>The Request is sent successfully!!!</h1>");  
	          out.println("</body>");  
	          out.println("</html>"); 
	          logger.info("request successfully");
		} catch (Exception e) {
			logger.error("fail to request", e);
			  response.setContentType("text/html");  
	          out.println("<html>");  
	          out.println("<head>");  
	          out.println("<title>Fail Page</title>");  
	          out.println("</head>");  
	          out.println("<body>");  
	          out.println("<h1>The Request Failed to sent</h1>");  
	          out.println("</body>");  
	          out.println("</html>"); 
			e.printStackTrace();
		}
	      
	}

}  