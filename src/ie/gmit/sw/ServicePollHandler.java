package ie.gmit.sw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/poll")
public class ServicePollHandler extends HttpServlet {
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html"); 
		PrintWriter out = resp.getWriter(); 
		
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		int counter = 1;
		if (req.getParameter("counter") != null){
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
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
		out.print("<td class=\"mui--text-title\">Measuring Document Similarity</td>");
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
		
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); //Refresh every 5 seconds
		out.print("</script>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}