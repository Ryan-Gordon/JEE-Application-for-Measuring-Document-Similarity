package ie.gmit.sw.servletHandlers;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import ie.gmit.sw.Processor;
import ie.gmit.sw.Result;

/**
 * ServicePollHandler displays the result to the user. 
 * The results of similarity are displayed in a table.
 * @author ryangordon
 *
 */
@WebServlet("/poll")
public class ServicePollHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConcurrentHashMap<Integer, Result> outQueue;


	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		
		outQueue = Processor.getOutQueue();
	    }
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html"); 
		PrintWriter out = resp.getWriter(); 
		int jobNumber = 0;
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		int counter = 1;
		if (req.getParameter("counter") != null){
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
		}
		if(req.getParameter("jobNumber")!=null) {
		jobNumber = Integer.parseInt(req.getParameter("jobNumber"));
		}
	
		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");		
		out.print("<link rel=\"stylesheet\" href=\"includes/basic.css\">");
		out.print("");
		out.print("<!-- load MUI -->");
		out.print("<link href=\"//cdn.muicss.com/mui-0.9.35/css/mui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
		out.print("<link href=\" http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
		out.print("<script src=\"//cdn.muicss.com/mui-0.9.35/js/mui.min.js\"></script>");
		out.print("</head>");
		out.print("<body>");
		out.print("<!-- example content -->");
		out.print("<header class=\"mui-appbar mui--z1\">");
		out.print("<div class=\"mui-container\">");
		out.print(" <table>");
		out.print(" <tr class=\"mui--appbar-height\">");
		out.print("<td align=\"mui--text-right\">");
		out.print("<ul class=\"mui-list--inline mui--text-body2\">");
		out.print(" <li><a href=\"https://github.com/Ryan-Gordon/JEE-Application-for-Measuring-Document-Similarity\"><i class=\"icon ion-social-github\"></i>Github</a></li>");
		out.print("</ul>");
		out.print("<td class=\"mui--text-title\">Measuring Document Similarity - Result</td>");
		out.print(" </tr>");
		out.print("</table>");
		out.print("</div>");
		out.print("</header>");
		out.print("<div id=\"content-wrapper\" class=\"mui--text-center\">");
		out.print(" <div class=\"mui--appbar-height\"></div>");
		//End of the header
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		out.print("<b><font color=\"ff0000\">A total of " + counter + " polls have been made for this request.</font></b> ");
		out.print("Place the final response here... a nice table (or graphic!) of the document similarity...");
		
	
	    if (Processor.getOutQueue().size() > 0) {
	    	out.print("	<table class=\"mui-table\">");
	    	out.print("<thead>");
	    	out.print("<tr>");
	    	out.print("<th>Document title</th>");
	    	out.print(" <th>Similarity</th>");
	    	out.print("  </tr>");
	    	out.print("</thead>");
	    	out.print("<tbody>");
	    	out.print(" </tr>");
	    
		if (outQueue.containsKey(jobNumber)) {
			//Take our result from the outQueue
			Result results = outQueue.remove(jobNumber);
			
		//Print a table row for each document result we have.
		for (String docTitle : results.getDocuments()) {
			out.print("<tr><td>");
			out.print(docTitle);
			out.print("</td><td>");
			out.printf("%.0f %%", Double.valueOf(results.getResult(docTitle)));
			out.print("</td></tr>");
		}
		out.print("</tbody>");
		out.print(" 	</table>");
		out.print("</body>");	
		out.print("</html>");
		}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}