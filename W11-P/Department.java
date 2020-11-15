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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			// ����
			conn = getConnection();
			// PreparedStatement ��ü ����
			pstm = conn.prepareStatement(sql);
			// ���� ���۽�Ű�� �޼��� : executeQuery(sql���ڿ�)
			// ������ ������ ResultSet ��ü ������ ����.
			rs = pstm.executeQuery(sql);
			// ResultSet ��ü�� �� �� �� �о�� �� ����
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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// update ��, �� ���� row�� ������Ʈ �Ǿ����� Ȯ���ϴ� ���� count
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
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// insert ��, �� ���� row�� ���ԵǾ����� Ȯ���ϴ� ���� count
			int count = pstm.executeUpdate(sql);
			System.out.println(count + " row inserted");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// ResultSet�� �����ϱ� null������ �־���.
			this.closeAll(conn, pstm, null);
		}
	}
	
	private void delete() {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete from departments where department_id = 280";
		
		// ����Ŭ�� ���� ���� �� ����� ��ȯ
		try {
			conn = this.getConnection();
			pstm = conn.prepareStatement(sql);
			// delete ��, �� ���� row�� �����Ǿ����� Ȯ���ϴ� ���� count
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
		// �μ���ȣ 200���� ������ �μ� ���
		System.out.println("show departments that over 200 department_id");
		di.select();
		// ���ο� �μ� ����
		System.out.println("\ninsert new department");
		di.insert();
		// ���ο� �μ� ���� Ȯ��
		System.out.println("\ncheck insert");
		di.select();
		// ���� �����ߴ� �μ��� ���� ���� (1000-�θ� ���� 1100-���Ͻ���)
		System.out.println("\nchange location Rome(1000) to Venice(1100)");
		di.update();
		// ������ �� �Ǿ����� Ȯ��
		System.out.println("\ncheck update");
		di.select();
		// ���� �����ߴ� �μ� ����
		System.out.println("\ndelete department that lastest insert");
		di.delete();
		// ������ �� �Ǿ����� Ȯ��
		System.out.println("\ncheck delete");
		di.select();
	}

}
