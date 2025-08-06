package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EmployeeDao {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/InnoTech";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public LinkedList<Employee> getAllEmployee() {
		LinkedList<Employee> li = new LinkedList<Employee>();

		try (Connection conn = getConnection();
				PreparedStatement pst = conn.prepareStatement("select * from EmployeeData");
				ResultSet rs = pst.executeQuery();) {
			while (rs.next()) {
				li.add(new Employee(rs.getString("name"), rs.getInt("id"), rs.getString("dob"),
						rs.getString("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
}
