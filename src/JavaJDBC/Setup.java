package JavaJDBC;


import java.sql.SQLException;

import JavaHibernate.Employee;

public class Setup
{

	public static void main(String[] args) throws SQLException
	{
		DBConnection db=new DBConnection();
		//db.select(sql);
//		List<Employee> list=db.getAllEmployee("employee");
//		for(Employee em:list){
//		System.out.println(em.toString());
//		}
//	
		//System.out.println(db.getEmployee(137).toString());
		Employee em=new Employee(150,"khattry","ould",12376);
		db.insertEmployee(em);

}

}
