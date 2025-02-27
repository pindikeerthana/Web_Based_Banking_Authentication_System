package org.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/OtpValidation")
public class OtpValidation extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String totp = req.getParameter("otp");
		int userotp = Integer.parseInt(totp);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		HttpSession session = req.getSession();
		Integer otp = (Integer)session.getAttribute("otp");
		
		if (otp!=null)
		{
			if (userotp==otp) 
			{
				RequestDispatcher rd = req.getRequestDispatcher("Amount.html");
				rd.include(req, resp);
			}
			else
			{
				writer.println("<center><h2>"+otp+"</h2></center>");
				RequestDispatcher rd = req.getRequestDispatcher("Otp.html");
				rd.include(req, resp);
				writer.println("<center><h2>Invalid otp</h2></center>");
			}
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("WithDraw.html");
			rd.include(req, resp);
			//writer.println("<center><h2>"+otp+"</h2></center>");
			writer.println("<center><h2>session time out</h2></center>");
		}
	}

}
