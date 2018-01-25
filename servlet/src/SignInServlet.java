

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sis")

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	PreparedStatement ps;
	public SignInServlet(){
		
	}
	public void init() throws ServletException {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User", "root", "123456");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user=request.getParameter("user");
		String password=request.getParameter("password");
		try{
			ps=con.prepareStatement("select * from info where username=? and password=?");   
			ps.setString(1,user);
			ps.setString(2,password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				HttpSession hs=request.getSession();
				hs.setAttribute("key", user);
				response.sendRedirect("home.html");
			}
			else
			{
				response.sendRedirect("signin.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
