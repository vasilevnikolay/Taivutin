package taivutin;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class TaivutinServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		resp.setContentType("text/html;charset=UTF-8");
		String s = req.getParameter("word");
		System.out.println(s);
		req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
	}
}
