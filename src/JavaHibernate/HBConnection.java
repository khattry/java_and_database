package JavaHibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Die Klasse baut die Verbindung zu einer Datenbank mittels Hibernate-Interface
 * auf
 * 
 * @author khattry
 * 
 */
public class HBConnection
{

	public HBConnection()
	{

	}

	/**
	 * Speichert ein Objekt in die Datenbank.
	 * 
	 * @param object
	 */
	public void saveObject(Object object)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			// Objekt speichern
			sess.save(object);
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}

	}

	/**
	 * liefet ein Objet zurueck.
	 * 
	 * @param klasse
	 * @param id
	 * @return Objekt
	 */
	public Object getObject(Class<?> klasse, Serializable id)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();
		Object obj = null;
		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			obj = sess.get(klasse, id);
			
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}
		return obj;
	}

	/**
	 * entfernt ein Objet von der Datenbank.
	 * 
	 * @param klasse
	 * @param id
	 */
	public void deleteObject(Class<?> klasse, Serializable id)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			Object obj = this.getObject(klasse, id);
			sess.delete(obj);
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}

	}

	public void updateObject(Object obj)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			sess.update(obj);
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * gibt alle Daten von einer Tabelle zurueck.
	 * @param sql
	 * @return
	 */
	public List<Object> getObjects(String sql)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();

		List<Object> liste = null;

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			Query query = sess.createQuery(sql);
			liste = query.list();
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}
		return liste;
	}

	/**
	 * liefert die gespeicherten Objekten mittels einer HQL
	 * 
	 * @param hgl
	 * @return Liste der gespeicherten Objekten
	 */
	@SuppressWarnings("unchecked")
	public List<Object> createQuery(String hgl)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();
	
		List<Object> liste = null;

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
		
			Query query = sess.createQuery(hgl);
			liste = query.list();
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}
		return liste;
	}

	/**
	 * liefert die gespeicherten Objekten mittels einer SQL-Abfrage
	 * 
	 * @param sql
	 * @param klasse
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> createSQLQuery(String sql, Class<?> klasse)
	{
		SessionFactory factory = HibernateUtil.getInstance()
				.getSessionFactory();
		Session sess = factory.openSession();
		List<Object> liste = null;

		Transaction tx = null;
		try
		{
			tx = sess.beginTransaction();
			SQLQuery query = sess.createSQLQuery(sql);
			query.addEntity(klasse);
			liste = query.list();
			tx.commit();
		} catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally
		{
			sess.flush();
			sess.close();
		}
		return liste;
	}
}