package JavaJDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnectionManager
{

	// instance of Driver Manager
	private static JDBCConnectionManager instance = null;

	private Connection conn;
	private String driverName = "org.postgresql.Driver";
	private String propName;

	/**
	 * Erzeugt eine Datenbank-Verbindung
	 */
	private JDBCConnectionManager(String dbName)
	{
		dbName.trim().toLowerCase();
		if (dbName.contains("mysql"))
		{
			this.propName = "mysql.properties";
			System.out.println(dbName);

		} else if (dbName.contains(":postgresql:"))
		{
			this.propName = "postgres.properties";
			System.out.println(dbName);

		} else
		{
			throw new IllegalArgumentException("Unknown Database of ");

		}
		try
		{
			Properties properties = new Properties();
			URL url = ClassLoader.getSystemResource(propName);
			FileInputStream stream = new FileInputStream(new File(url.toURI()));
			properties.load(stream);
			stream.close();

			String jdbcUser = properties.getProperty("jdbc_user");
			String jdbcPass = properties.getProperty("jdbc_pass");
			String jdbcUrl = properties.getProperty("jdbc_url");

			// Verbindung zur DB herstellen
			Class.forName(this.getDriverName());
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
			System.out.println("Connected database successfully...");
		} catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Liefert Instanz des Managers
	 * 
	 * @return ConnectionManager
	 */
	public static JDBCConnectionManager getInstance(String dbName)
	{
		if (instance == null)
		{
			instance = new JDBCConnectionManager(dbName);
		}
		return instance;
	}

	/**
	 * Liefert eine Verbindung zur DB zurück
	 * 
	 * @return Connection
	 */
	public Connection getConnection()
	{
		return conn;
	}

	private String getDriverName()
	{
		return driverName;
	}

}