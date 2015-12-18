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

	private Connection conn = null;
	private String driverName = null;
	private String propName = null;

	/**
	 * Erzeugt eine Datenbank-Verbindung
	 */
	private JDBCConnectionManager(String dbServer)
	{
		this.register(dbServer);

	}

	private void register(String dbServer)
	{
		dbServer.trim().toLowerCase();
		if (dbServer.contains("mysql"))
		{
			this.propName = "mysql.properties";
			this.driverName = "com.mysql.jdbc.Driver";
			System.out.println(dbServer);

		} else if (dbServer.contains(":postgresql:"))
		{
			this.propName = "postgres.properties";
			this.driverName = "org.postgresql.Driver";
			System.out.println(dbServer);

		} else
		{
			throw new IllegalArgumentException("Unknown Database!");

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
			Class.forName(this.driverName);
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

}