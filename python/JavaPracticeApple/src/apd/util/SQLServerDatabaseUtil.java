package initiate.apd.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.intuit.taf.logging.Logger;

/**  
* Oracle Database Utility Class<br>
* 
* @author  Jeffrey Walker
* @version 1.0.0 
*/ 
public class SQLServerDatabaseUtil 
{
	private static Logger sLog;
	private Connection connection;

	/**
	 * Initializes an Oracle Database Connection.<br>
	 * 
	 * @param username Username for the Oracle Database
	 * @param password Password for the Oracle Database
	 * @param url Oracle Database Connection URL
	 * 
	 * @return The connection object to the database
	 */
	public Connection createDatabaseConnection(String username, String password, String url)
	{
		try
		{
			sLog = Logger.getInstance();
			String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		    Class.forName(driverName);
		    connection = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e)
		{
			sLog.error(e.getMessage());
		}
		
		return connection;
	}
}
