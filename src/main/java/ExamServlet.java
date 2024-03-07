import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExamServlet
 */
@WebServlet("/ExamServlet")
public class ExamServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		String sqlquery = null;
		try {

			String username = "examuser";
			String password = "exampass";
			String url= "jdbc:mysql://mysql-webapp:3306/examdaw22";
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			
			sqlquery = "SELECT students.name, students.surname , projects.name as project \r\n"
								+ "from students \r\n"
								+ "join projects on students.studentId = projects.studentId\r\n"
								+ "join teachers on teachers.teacherId = projects.teacherId \r\n"
								+ "where teachers.Name =" + "'" + request.getParameter("teacherName") + "'";

			out.println("<html><head><title>DAW22 projects </title>");
		    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
		    out.println("</head><body>");
			out.println("<h1>These are the DAW2 selected projects ");
	        out.println(request.getParameter("teacherName"));
	        out.println("are:</h1>");

			ResultSet rset = stmt.executeQuery(sqlquery);
            int count = 0;

            out.println("<table>");
            out.println("<tr><th>Name</th><th>Surname</th><th>Project</th></tr>");

            while (rset.next()) {
                out.println("<tr>");
                out.println("<td>" + rset.getString("name") + "</td>");
                out.println("<td>" + rset.getString("surname") + "</td>");
                out.println("<td>" + rset.getString("project") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<p>====" + count + " records found ========</p>");

			out.println("</body></html>");
		} catch (Exception ex) {
			out.println(ex.toString());
			ex.printStackTrace();;
		} finally {
			out.println("<p>Your query was: " + sqlquery + "</p>");
			out.close();
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
