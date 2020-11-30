<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.sql.SQLException" %>
<%@ page language="java" import="kr.ac.sungshin.w13.DBConnection" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>List of employees</title>
</head>
<body>
	<ul>
		<li><a href="../index.html">index</a>
	</ul>

	<h3>직원 목록</h3>
<%
	Connection conn = null;	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	String sql = "select emp.first_name, emp.last_name, emp.salary, dep.department_name, job.job_title from employees emp, departments dep, jobs job where emp.department_id = dep.department_id and emp.job_id = job.job_id";
	
	try {
		conn = DBConnection.getConnection();				
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<th>"+"first_name"+"</th>");
		out.println("<th>"+"last_name"+"</th>");
		out.println("<th>"+"salary"+"</th>");
		out.println("<th>"+"department_name"+"</th>");
		out.println("<th>"+"job_title"+"</th>");
		out.println("</tr>");
		while(rs.next()){
			out.println("<tr>");			
			out.println("<td>" + rs.getString("first_name") + "</td>");
			out.println("<td>" + rs.getString("last_name") + "</td>");
			out.println("<td>" + rs.getString("salary") + "</td>");
			out.println("<td>" + rs.getString("department_name") + "</td>");
			out.println("<td>" + rs.getString("job_title") + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");		
	} catch (SQLException e){
		System.out.println(e.getMessage());
	} finally {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
	
%>
</body>
</html>