<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="kr.ac.sungshin.w13.DBConnection" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Delete Employee</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String employee_id = request.getParameter("employee_id");
	
	Connection conn = null;	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	String sql = "delete from employees where EMPLOYEE_ID = ?";	 
	
	int n = 0;
	
	try {
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);	
		pstmt.setString(1, employee_id);
		n = pstmt.executeUpdate();		
	} catch (SQLException e){
		System.out.println(e.getMessage());
	} finally {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
%>
<script type="text/javascript">
	if(<%=n%> > 0){
		alert("정상적으로 삭제되었습니다.");
		location.href="../index.html";
	}else{
		alert("입력이 실패했습니다.");	
		history.go(-1);
	}
</script>
</body>
</html>