package JavaJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import JavaHibernate.Employee;

public class DBConnection
{

	public DBConnection()
	{

	}

	public Connection connectToDB(String dbServer)
	{
		Connection conn = JDBCConnectionManager.getInstance(dbServer)
				.getConnection();

		return conn;
	}

	public void insertEmployee(String firstName, String lastName, int salary)
			throws SQLException
	{
		String sql = "INSERT INTO employee VALUES" + "(" + firstName + ","
				+ lastName + "," + salary + ");";
		Statement stmt = null;
		Connection conn = this.connectToDB("postgres");
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);

	}

	public void insertEmployee(Employee em) throws SQLException
	{
		String sql = "INSERT INTO employee "
				+ "(id,first_name,last_name,salary)" + "VALUES (" + em.getId()
				+ ",'" + em.getFirstName() + "'" + "," + "'" + em.getLastName()
				+ "'" + "," + em.getSalary() + ");";
		Connection conn = this.connectToDB("postgres");
		Statement stmt = null;
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);

	}

	public void select(String sql)
	{

		Connection conn = this.connectToDB("postgres");
		Statement stmt = null;
		try
		{
			stmt = conn.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				System.out.println(rs);
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println(rs.getRow());
			System.out.println(rsmd.getColumnCount());

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(String sql)
	{

	}

	public void delete(String sql)
	{

	}

	public List<Employee> getAllEmployee(String tableName) throws SQLException
	{
		String sql = "select * from " + tableName + ";";

		List<Employee> list = new ArrayList<Employee>();
		Connection conn = this.connectToDB("postgres");

		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			// Retrieve by column name
			int id = rs.getInt("id");
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			int salary = rs.getInt("salary");
			// create Employee-Object
			Employee em = new Employee(id, first_name, last_name, salary);
			list.add(em);
		}

		return list;
	}

	public Employee getEmployee(int id) throws SQLException
	{
		String sql = "select * from employee where id=" + id + ";";
		Employee em = null;
		Connection conn = this.connectToDB("postgres");

		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			// Retrieve by column name
			int empId = rs.getInt("id");
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			int salary = rs.getInt("salary");
			// create Employee-Object
			em = new Employee(empId, first_name, last_name, salary);
			// System.out.println(em.toString());
		}
		return em;
	}

}
