package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO 
{
	public List<River> getAllRivers() 
	{	
		final String sqlQuery = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try 
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet queryResult = statement.executeQuery();

			while (queryResult.next()) 
			{
				River newRiver = new River(queryResult.getInt("id"), queryResult.getString("name"));
				rivers.add(newRiver);
			}

			connection.close();
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao Error in getAllRivers()", sqle);
		}

		return rivers;
	}
}
