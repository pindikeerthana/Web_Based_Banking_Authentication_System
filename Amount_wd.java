package org.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Amount")
public class Amount_wd extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tamount =req.getParameter("amount");
		double uamount =Double.parseDouble(tamount);
		PrintWriter writer=resp.getWriter();
		resp.setContentType("text/html");
		HttpSession session=req.getSession();
		Double damount=(Double)session.getAttribute("damount");
		String mb=(String) session.getAttribute("mb");
		String pin=(String) session.getAttribute("pin");
		if (damount!=null) {
			
		}
		if (uamount>0)
		{
			if (damount>=uamount) 
			{
				double sub=damount-uamount;
				String update="update bank set amount=? where mobileNo=? and pin=?";
				String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection=DriverManager.getConnection(url);
					PreparedStatement ps=connection.prepareStatement(update);
					ps.setDouble(1, sub);
					ps.setString(2, mb);
					ps.setString(3, pin);
					int num=ps.executeUpdate();
					if (num>0) 
					{
						RequestDispatcher rd=req.getRequestDispatcher("WithDraw.html");
						rd.include(req, resp);
						writer.println("<center><h1>Withdraw Successfull.....</h1></center>");
					}
					else 
					{
						RequestDispatcher rd=req.getRequestDispatcher("WithDraw.html");
						rd.include(req, resp);
						writer.println("<center><h1>404</h1></center>");
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else {
				RequestDispatcher rd=req.getRequestDispatcher("Amount.html");
				rd.include(req, resp);
				writer.println("<center><h1>Insufficient Amount</h1></center>");
			}
			
			
		}
		else 
		{
			RequestDispatcher rd=req.getRequestDispatcher("Amount.html");
			rd.include(req, resp);
			writer.println("<center><h1>Invalid Amount</h1></center>");
		}
	}
}
