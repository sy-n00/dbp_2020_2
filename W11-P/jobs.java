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

		// jdbc ����̹� �ε� �� ����Ŭ ����
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("connection success");
		} catch (ClassNotFoundException cnfe) {
			// ClassNotFoundException�� ����̹� ������ �� �����߻� ��
			System.out.println("failed db driver loading\n" + cnfe.toString());
		} catch (SQLException sqle) {
			// SQLException�� db���ῡ �����߻� ��
			System.out.println("failed db connection\n" + sqle.toString());		
		} catch (Exception e) {
			// �� ���� ���� �߻� ��
			System.out.println("Unknown error");
			e.printStackTrace();
		}
		return conn;
	}
	
	// �����ϴ� ���� ���ҽ� �����ϰ� �ֱ⶧���� ��� �Ϸ� �� �޸� ��ȯ ����� ��.
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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			// ����
			conn = getConnection();
			// PreparedStatement ��ü ����
			psmt = conn.prepareStatement(sql);
			// ���� ���۽�Ű�� �޼��� : executeQuery(sql���ڿ�)
			// ������ ������ ResultSet ��ü ������ ����.
			rs = psmt.executeQuery(sql);
			// ResultSet ��ü�� �� �� �� �о�� �� ����
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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// insert ��, �� ���� row�� ���ԵǾ����� Ȯ���ϴ� ���� count
			int count = psmt.executeUpdate(sql);
			System.out.println(count + " row inserted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// ResultSet�� �����ϱ� null������ �־���.
			this.closeAll(conn, psmt, null);
		}
	}
	
	private void update() {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "update jobs set min_salary = 5000 where job_id = 'TP_SALES'";		
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// update ��, �� ���� row�� ������Ʈ �Ǿ����� Ȯ���ϴ� ���� count
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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			// delete ��, �� ���� row�� �����Ǿ����� Ȯ���ϴ� ���� count
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
		// ������ ���� ���� ���� Ȯ��
		job.select();
		System.out.println("\ninsert job named temporary salesman");
		// ���ο� ���� ����
		job.insert();
		System.out.println("\ncheck insert");
		// ���ο� ������ �� ���ԵǾ����� Ȯ��
		job.select();
		System.out.println("\nupdate min_salary of last row job");
		// ������ ���� ������ ���� ���� ����
		job.update();
		System.out.println("\ncheck update");
		// ������ �� �Ǿ����� Ȯ��
		job.select();
		System.out.println("\ndelete job of last row");
		// ������ ���� ���� ����
		job.delete();
		System.out.println("\ncheck delete");
		// ������ �� �Ǿ����� Ȯ��
		job.select();
	}
}
