import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class jobs {
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
	public void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (psmt != null) psmt.close();
			if (conn != null) conn.close();
			System.out.println("All closed");
		} catch (SQLException sqle) {
			System.out.println("Error!!");
			sqle.printStackTrace();
		}
	}

	private void select() {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from ( select * from jobs order by rownum desc ) where rownum = 1";
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			// 연결
			conn = getConnection();
			// PreparedStatement 객체 생성
			psmt = conn.prepareStatement(sql);
			// 쿼리 동작시키는 메서드 : executeQuery(sql문자열)
			// 동작한 쿼리는 ResultSet 객체 변수에 저장.
			rs = psmt.executeQuery(sql);
			// ResultSet 객체는 한 줄 씩 읽어올 수 있음
			while(rs.next()) {
				System.out.print("job_id: " + rs.getString("job_id"));
				System.out.print("\tjob_title: " + rs.getString("job_title"));
				System.out.print("\tmin_salary: " + rs.getInt("min_salary"));
				System.out.println("\tmax_salary: " + rs.getInt("max_salary"));	
			}	
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, rs);
		}
	}
	
	private void insert() {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "insert into jobs values ('TP_SALES', 'Temporary Salesman', 4000, 6000)";		
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// insert 시, 몇 개의 row가 삽입되었는지 확인하는 변수 count
			int count = psmt.executeUpdate(sql);
			System.out.println(count + " row inserted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// ResultSet이 없으니까 null값으로 넣어줌.
			this.closeAll(conn, psmt, null);
		}
	}
	
	private void update() {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "update jobs set min_salary = 5000 where job_id = 'TP_SALES'";		
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// update 시, 몇 개의 row가 업데이트 되었는지 확인하는 변수 count
			int count = psmt.executeUpdate();
			System.out.println(count + " row updated");

			psmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void delete() {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "delete from jobs where job_id = 'TP_SALES'";
		
		// 오라클에 쿼리 전송 및 결과값 반환
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// delete 시, 몇 개의 row가 삭제되었는지 확인하는 변수 count
			int count = psmt.executeUpdate();
			System.out.println(count + " row deleted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, null);
		}
	}

	public static void main(String[] args) {		
		jobs job = new jobs();
		// 마지막 행의 직군 정보 확인
		job.select();
		System.out.println("\ninsert job named temporary salesman");
		// 새로운 직군 삽입
		job.insert();
		System.out.println("\ncheck insert");
		// 새로운 직군이 잘 삽입되었는지 확인
		job.select();
		System.out.println("\nupdate min_salary of last row job");
		// 삽입한 행의 직군의 최저 연봉 수정
		job.update();
		System.out.println("\ncheck update");
		// 수정이 잘 되었는지 확인
		job.select();
		System.out.println("\ndelete job of last row");
		// 삽입한 행의 직군 삭제
		job.delete();
		System.out.println("\ncheck delete");
		// 삭제가 잘 되었는지 확인
		job.select();
	}
}
