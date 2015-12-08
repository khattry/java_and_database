package JavaHibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author khattry
 * 
 */
public class HibernateUtil
{

	private static SessionFactory sessionFactory;
	private static HibernateUtil instance;

	private HibernateUtil()
	{
		sessionFactory = null;
		instance=null;
	}

	public static HibernateUtil getInstance()
	{
		if (instance == null)
		{
			instance = new HibernateUtil();
		}
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	private static SessionFactory buildSessionFactory()
	{

		SessionFactory factory = null;
		try
		{
			 Configuration cfg= new Configuration();
			 cfg.configure();
			factory =cfg.buildSessionFactory();
		} catch (HibernateException e)
		{

			e.printStackTrace();
		}
		return factory;
	}

	public SessionFactory getSessionFactory()
	{
		sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

}
