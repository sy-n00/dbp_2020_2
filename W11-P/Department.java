package w11_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Department {

	private static String className = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "hr";
	private static String password = "1234";
	
	public Connection getConnection() {
		Connection conn = null;

		// jdbc 드라이버 로드 및 오라클 접속
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("connection success");
		} catch (ClassNotFoundException cnfe) {
			// ClassNotFoundException은 드라이버 설정할 때 문제발생 시
			System.out.println("failed db driver loading\n" + cnfe.toString());
		} catch (SQLException sqle) {
			// SQLException은 db연결에 문제발생 시
			System.out.println("failed db connection\n" + sqle.toString());		
		} catch (Exception e) {
			// 그 밖의 에러 발생 시
			System.out.println("Unknown error");
			e.printStackTrace();
		}
		return conn;
	}
	
	// 연결하는 동안 리소스 차지하고 있기때문에 사용 완료 후 메모리 반환 해줘야 함.
	public void closeAll(Connection conn, PreparedStatement pstm, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (pstm != null) pstm.close();
			if (conn != null) conn.close();
			System.out.println("All closed");
		} catch (SQLException sqle) {
			System.out.println("Error!!");
			sqle.printStackTrace();
		}
	}

	private void select() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from ( select * from departments order by rownum desc ) where department_id > 200";
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			// 연결
			conn = getConnection();
			// PreparedStatement 객체 생성
			pstm = conn.prepareStatement(sql);
			// 쿼리 동작시키는 메서드 : executeQuery(sql문자열)
			// 동작한 쿼리는 ResultSet 객체 변수에 저장.
			rs = pstm.executeQuery(sql);
			// ResultSet 객체는 한 줄 씩 읽어올 수 있음
			while(rs.next()) {
				System.out.print("department_id: " + rs.getInt("department_id"));
				System.out.print("\tdepartment_name: " + rs.getString("department_name"));
				System.out.println("\tlocation: " + rs.getInt("location_id"));	
			}	
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstm, rs);
		}
	}
	
	private void update() {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update departments set location_id = 1100 where department_id = 280";		
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// update 시, 몇 개의 row가 업데이트 되었는지 확인하는 변수 count
			int count = pstm.executeUpdate();
			System.out.println(count + " row updated");

			pstm.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
            this.closeAll(conn, pstm, null);
        }
	}
	
	private void insert() {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into departments values (280, 'Temporary Salesman', null, 1000)";		
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// insert 시, 몇 개의 row가 삽입되었는지 확인하는 변수 count
			int count = pstm.executeUpdate(sql);
			System.out.println(count + " row inserted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// ResultSet이 없으니까 null값으로 넣어줌.
			this.closeAll(conn, pstm, null);
		}
	}
	
	private void delete() {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete from departments where department_id = 280";
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// delete 시, 몇 개의 row가 삭제되었는지 확인하는 변수 count
			int count = pstm.executeUpdate();
			System.out.println(count + " row deleted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, pstm, null);
		}
	}

	public static void main(String[] args) {		
		
		Department di = new Department();
		// 부서번호 200번대 이후의 부서 출력
		System.out.println("show departments that over 200 department_id");
		di.select();
		// 새로운 부서 삽입
		System.out.println("\ninsert new department");
		di.insert();
		// 새로운 부서 삽입 확인
		System.out.println("\ncheck insert");
		di.select();
		// 새로 삽입했던 부서의 지역 변경 (1000-로마 에서 1100-베니스로)
		System.out.println("\nchange location Rome(1000) to Venice(1100)");
		di.update();
		// 수정이 잘 되었는지 확인
		System.out.println("\ncheck update");
		di.select();
		// 새로 삽입했던 부서 삭제
		System.out.println("\ndelete department that lastest insert");
		di.delete();
		// 삭제가 잘 되었는지 확인
		System.out.println("\ncheck delete");
		di.select();
	}

}
