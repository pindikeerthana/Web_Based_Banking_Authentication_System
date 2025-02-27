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

@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mobile = req.getParameter("mb");
		String password = req.getParameter("pss");
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select="select * from bank where mobileNo=? and pin=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobile);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			

			if (rs.next())
			{
				writer.println("<center><h1>User Details</h1></center>");
				writer.println("<center><h2 style=color:teal;>Id        : "+rs.getInt(1)+"</h2></center>");
				writer.println("<center><h2 style=color:teal;>Name      : "+rs.getString(2)+"</h2></center>");
				writer.println("<center><h2 style=color:teal;>Amount    : "+rs.getDouble(3)+"</h2></center>");
				writer.println("<center><h2 style=color:teal;>Mobile no : "+mobile+"</h2></center>");
				writer.println("<center><h2 style=color:teal;>Pin       : "+password+"</h2></center>");
			} 
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("CheckBalance.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid Details</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
