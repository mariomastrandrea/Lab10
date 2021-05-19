package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO 
{
	public Set<River> getAllRivers() 
	{	
		final String sqlQuery = "SELECT id, name FROM river";

		Set<River> rivers = new HashSet<River>();

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

			DBConnect.close(queryResult, statement, connection);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao Error in getAllRivers()", sqle);
		}

		return rivers;
	}

	public void setDataOf(River river)
	{
		final String sqlQuery = String.format("%s %s %s",
			"SELECT MIN(day) as firstDate, MAX(day) as lastDate, COUNT(*) AS num, AVG(flow) AS avgFlow",
			"FROM flow",
			"WHERE river = ?");
		
		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, river.getId());
			ResultSet queryResult = statement.executeQuery();
			
			if(queryResult.next())
			{
				LocalDate firstDate = queryResult.getDate("firstDate").toLocalDate();
				LocalDate lastDate = queryResult.getDate("lastDate").toLocalDate();
				int numMeasurements = queryResult.getInt("num");
				double avgFlowPerSec = queryResult.getDouble("avgFlow");
				
				river.setFirstDate(firstDate);
				river.setLastDate(lastDate);
				river.setNumMeasurements(numMeasurements);
				river.setAvgFlowPerSec(avgFlowPerSec);
			}
			
			DBConnect.close(queryResult, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getDataOf()", sqle);
		}
	}
	
	public void setFlowsOf(River river)
	{
		final String sqlQuery = String.format("%s %s %s %s",
												"SELECT id, day, flow",
												"FROM flow",
												"WHERE river = ?",
												"ORDER BY day ASC");
			
		List<Flow> flows = new ArrayList<>();

		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, river.getId());
			ResultSet queryResult = statement.executeQuery();

			while(queryResult.next())
			{
				int flowId = queryResult.getInt("id");
				LocalDate date = queryResult.getDate("day").toLocalDate();
				double flowPerSec = queryResult.getDouble("flow");

				Flow newFlow = new Flow(flowId, date, flowPerSec, river);
				flows.add(newFlow);
			}

			river.setFlows(flows);

			DBConnect.close(queryResult, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getFlowsOf()", sqle);
		}
	}
}










