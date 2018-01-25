import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/sus")

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	java.sql.PreparedStatement ps;
	int x=0;
	
	public void init() throws ServletException {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User", "root", "123456");
			ps=con.prepareStatement("insert into info values(?,?,?,?,?,?,?)");   
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user=request.getParameter("username");
		String password=request.getParameter("password");
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String gender=request.getParameter("gender");
		String city=request.getParameter("city");
		Integer age=Integer.parseInt(request.getParameter("age"));
		try{
			ps.setString(1,firstname);
			ps.setString(2,lastname);
			ps.setString(3,user);
			ps.setString(4,password);
			ps.setString(6,gender);
			ps.setString(7,city);
			ps.setInt(5, age);
			x=ps.executeUpdate();
			if(x!=0){
				response.sendRedirect("index.html");
			}
			else{
				response.sendRedirect("signup.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}