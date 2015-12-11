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

	/**
	 * Erzeugt eine Datenbank-Verbindung
	 */
	private JDBCConnectionManager()
	{
		try
		{
			// Holen der Einstellungen aus der db2.properties Datei
			Properties properties = new Properties();
			URL url = ClassLoader.getSystemResource("mysql.properties");
			FileInputStream stream = new FileInputStream(new File(url.toURI()));
			properties.load(stream);
			stream.close();

			String jdbcUser = properties.getProperty("jdbc_user");
			String jdbcPass = properties.getProperty("jdbc_pass");
			String jdbcUrl = properties.getProperty("jdbc_url");

			// Verbindung zur DB2 herstellen
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
	public static JDBCConnectionManager getInstance()
	{
		if (instance == null)
		{
			instance = new JDBCConnectionManager();
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

	public String getDriverName()
	{
		return driverName;
	}

}