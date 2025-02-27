package org.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithDraw_res")
public class WithDraw_res extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mobile = req.getParameter("mb");
		String password = req.getParameter("pss");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/hmtl");
		
		String url= "jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		
		String select="select * from bank where mobileNo=? and pin=?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobile);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			HttpSession session = req.getSession();
			
			if (rs.next())
			{
				Random r = new Random();
				int otp = r.nextInt(10000);
				if (otp<1000)
				{
					otp+=1000;
				}
				
				session.setAttribute("otp", otp);
				
				double damount=rs.getDouble("amount");
				session.setAttribute("otp", otp);
				session.setAttribute("damount", damount);
				session.setAttribute("mb", mobile);
				session.setAttribute("pin", password);
				session.setMaxInactiveInterval(15);
				writer.println("<center><h1>"+otp+"</h1></center>");

				
				RequestDispatcher rd = req.getRequestDispatcher("Otp.html");
				rd.include(req, resp);
				
				
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("WithDraw.html");
				rd.include(req, resp);
				writer.println("<center><h2>Invalid details</h2></center>");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
