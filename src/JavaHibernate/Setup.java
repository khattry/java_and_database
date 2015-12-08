package JavaHibernate;

import java.util.List;

public class Setup
{

	public static void main(String[] args)
	{
		HBConnection db = new HBConnection();

		Employee e1 = new Employee("Zara", "Ali", 1000);
		Employee e2 = new Employee("Daisy", "Das", 5000);
		Employee e3 = new Employee("John", "Paul", 10000);
		// Save
		// db.saveObject(e1);
		// db.saveObject(e2);
		// db.saveObject(e3);
		// Get
		// Employee em =(Employee)db.getObject(Employee.class, 137);
		// System.out.println(em.toString());
		// Update
		// Employee em =(Employee)db.getObject(Employee.class, 137);
		// em.setSalary(12345);
		// db.updateObject(em);
		// Delete
		// db.deleteObject(Employee.class, 136);
		List<Object> objetcs = db.createQuery("from Employee");
		for (Object ob : objetcs)
		{
			System.out.println(((Employee) ob).toString());
		}
//		List<Object> objetcs = db.createSQLQuery("select * from employee", Employee.class);
//		for (Object ob : objetcs)
//		{
//			System.out.println(((Employee) ob).toString());
//		}
	
	}

}
