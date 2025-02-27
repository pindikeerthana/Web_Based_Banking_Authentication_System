package org.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Amount_Cr")
public class Amount_Cr extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tamount= req.getParameter("amount");
		double uamount = Double.parseDouble(tamount);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		HttpSession session = req.getSession();
		Double camount =(Double)session.getAttribute("camount");
		String mobile = (String)session.getAttribute("mobile");
		String pin =(String)session.getAttribute("password"); 
		
		double add=uamount+camount;
				
		String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		
		String update="update bank set amount=? where mobileNo=? and pin=?";
		
		try {
			getClass().forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(update);
			ps.setDouble(1, add);
			ps.setString(2, mobile);
			ps.setString(3, pin);
			int num = ps.executeUpdate();
			if (num!=0)
			{
				RequestDispatcher rd = req.getRequestDispatcher("Credit.html");
				rd.include(req, resp);
				writer.println("<center><h1>Amount credited</h1></center>");
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("Credit.html");
				rd.include(req, resp);
				writer.println("<center><h1>404 error</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
