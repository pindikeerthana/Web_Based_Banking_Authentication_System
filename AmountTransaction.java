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

@WebServlet("/AmountTransaction")
public class AmountTransaction extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tramount = req.getParameter("ramount");
		double ramount = Double.parseDouble(tramount);
		HttpSession session = req.getSession();
		Double sdamount = (Double) session.getAttribute("sdamount");
		Double rdamount = (Double) session.getAttribute("rdamount");
		String smb = (String) session.getAttribute("smb");
		String rmb = (String) session.getAttribute("rmb");
	
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		if (ramount > 0)
		{
			if (sdamount>=ramount) 
			{
				double sub=sdamount-ramount;
				double add=rdamount+ramount;
				String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
				String updates="update bank set amount=? where mobileNo=?";
				String updater="update bank set amount=? where mobileNo=?";
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement ps = connection.prepareStatement(updates);
					ps.setDouble(1, sub);
					ps.setString(2, smb);
					int num = ps.executeUpdate();
					if (num!=0)
					{
						PreparedStatement ps1 = connection.prepareStatement(updater);
						ps1.setDouble(1, add);
						ps1.setString(2, rmb);
						int num1 = ps1.executeUpdate();
						if (num1!=0)
						{
							RequestDispatcher rd = req.getRequestDispatcher("WelcomePage.html");
							rd.include(req, resp);
							writer.println("<center><h1>Amount received successfull...</h1></center>");
						}
						else 
						{
							RequestDispatcher rd = req.getRequestDispatcher("WelcomePage.html");
							rd.include(req, resp);
							writer.println("<center><h1>network issue...</h1></center>");
						}
						RequestDispatcher rd = req.getRequestDispatcher("WelcomePage.html");
						rd.include(req, resp);
						writer.println("<center><h1>Transaction successfull....</h1></center>");
					}
					else
					{
						RequestDispatcher rd = req.getRequestDispatcher("AmountTransaction.html");
						rd.include(req, resp);
						writer.println("<center><h1>404</h1></center>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("MobileTransaction.html");
				rd.include(req, resp);
				writer.println("<center><h1>Insufficient fund</h1></center>");
			}
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("AmountTransaction.html");
			rd.include(req, resp);
			writer.println("<center><h1>Invalid amount</h1></center");
		}
	}
}
