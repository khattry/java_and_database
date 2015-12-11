package JavaJDBC;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import JavaHibernate.Employee;

public class DBConnection
{

	public DBConnection()
	{

	}

	public void insertEmployee(String firstName,String lastName,int salary) throws SQLException
	{
		 String sql = "INSERT INTO employee VALUES" +"("+firstName+","+lastName+","+salary+");";
		 Connection conn = JDBCConnectionManager.getInstance().getConnection();
		 Statement stmt = null;
		 stmt = conn.createStatement();
		 stmt.executeUpdate(sql);
			
	}
	public void insertEmployee(Employee em) throws SQLException
	{
		 String sql = "INSERT INTO employee " +"(id,first_name,last_name,salary)"+
		 "VALUES ("+em.getId()+",'"+em.getFirstName()+"'"+","+"'"+em.getLastName()+"'"+","+em.getSalary()+");";
		 Connection conn = JDBCConnectionManager.getInstance().getConnection();
		 Statement stmt = null;
		 stmt = conn.createStatement();
		 stmt.executeUpdate(sql);
			
	}
	public void select(String sql)
	{

		Connection conn = JDBCConnectionManager.getInstance().getConnection();
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
		Connection conn = JDBCConnectionManager.getInstance().getConnection();
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
		String sql= "select * from employee where id="+id+";";
		Employee em = null;
		Connection conn = JDBCConnectionManager.getInstance().getConnection();
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
		//System.out.println(em.toString());
		}
		return em;
	}
	// public void selectfrom(String sql) throws SQLException
	// {
	// Connection conn = JDBCConnectionManager.getInstance().getConnection();
	// Statement stmt = conn.createStatement();
	// ResultSet rs = stmt.executeQuery(sql);
	// ResultSetMetaData rsmd = rs.getMetaData();
	// int cc = rsmd.getColumnCount();
	// System.out.println(cc);
	// for (int i = 1; i <= cc; i++)
	// {
	// System.out.println(rsmd.getColumnName(i));
	// }
	// }

	public void generateXls(String tablename, String filename)
	    throws SQLException, FileNotFoundException, IOException {
	
	    // Create new Excel workbook and sheet
	    HSSFWorkbook xlsWorkbook = new HSSFWorkbook();
	    HSSFSheet xlsSheet = xlsWorkbook.createSheet();
	    short rowIndex = 0;
	
	    // Execute SQL query
	    Connection conn = JDBCConnectionManager.getInstance().getConnection();
	    PreparedStatement stmt =
	    conn.prepareStatement("select * from " + tablename);
	    ResultSet rs = stmt.executeQuery();
	
	    // Get the list of column names and store them as the first
	    // row of the spreadsheet.
	    ResultSetMetaData colInfo = rs.getMetaData();
	    List<String> colNames = new ArrayList<String>();
	    HSSFRow titleRow = xlsSheet.createRow(rowIndex++);
	
	    for (int i = 1; i <= colInfo.getColumnCount(); i++) {
	      colNames.add(colInfo.getColumnName(i));
	      titleRow.createCell((short) (i-1)).setCellValue(
	        new HSSFRichTextString(colInfo.getColumnName(i)));
	      xlsSheet.setColumnWidth((short) (i-1), (short) 4000);
	    }
	
	    // Save all the data from the database table rows
	    while (rs.next()) {
	      HSSFRow dataRow = xlsSheet.createRow(rowIndex++);
	      short colIndex = 0;
	      for (String colName : colNames) {
	        dataRow.createCell(colIndex++).setCellValue(
	          new HSSFRichTextString(rs.getString(colName)));
	      }
	    }
	
	    // Write to disk
	    xlsWorkbook.write(new FileOutputStream(filename));
	  }

}
