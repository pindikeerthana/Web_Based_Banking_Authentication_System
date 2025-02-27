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

@WebServlet("/ReCeiver_res")
public class ReCeiver_res extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String rmb = req.getParameter("rmb");
		
		String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select="select * from bank where mobileNo=?";
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, rmb);
			ResultSet rs = ps.executeQuery();
			
			HttpSession session = req.getSession();
			
			if (rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("AmountTransaction.html");
				rd.forward(req, resp);
				
				double rdamount = rs.getDouble(3);
				session.setAttribute("rdamount", rdamount);
				session.setAttribute("rmb", rmb);
			} 
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("ReceiverMobile.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid mobile number</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
