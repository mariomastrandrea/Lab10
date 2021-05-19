package it.polito.tdp.rivers.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBConnect 
{
	private static final String jdbcURL = "jdbc:mariadb://127.0.0.1/rivers";
	private static final HikariDataSource dataSource;
	private static final String username = "root";
	private static final String password = "root";
	
	static 
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setUsername(username);
		config.setPassword(password);
		
		// MySQL configuration
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		dataSource = new HikariDataSource(config);
	}
	
	public static Connection getConnection() 
	{
		try 
		{
			Connection connection = dataSource.getConnection();
			return connection;
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("DB connection error at: " + jdbcURL, sqle);
		}
	}

	public static void close(AutoCloseable... resources)
	{
		for(var res : resources)
		{
			try
			{
				res.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException("Error closing resource: " + res.toString(), e);
			}
		}
	}
}