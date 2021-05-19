package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class River 
{
	public static final int SECS_IN_A_MINUTE = 60;
	public static final int MINUTES_IN_A_HOUR = 60;
	public static final int HOURS_IN_A_DAY = 24;
	public static final int DAYS_IN_A_MONTH = 60;
	
	/***********************/
	private final int id;
	private String name;
	private LocalDate firstDate;
	private LocalDate lastDate;
	private Integer numMeasurements;
	private Double avgFlowPerSec;
	private List<Flow> flows;
	
	
	public River(int id) 
	{
		this.id = id;
	}

	public River(int id, String name) 
	{
		this.id = id;
		this.name = name;
	}

	public int getId() 
	{
		return this.id;
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public LocalDate getFirstDate()
	{
		return this.firstDate;
	}
	
	public void setFirstDate(LocalDate firstDate)
	{
		this.firstDate = firstDate;
	}
	
	public LocalDate getLastDate()
	{
		return this.lastDate;
	}
	
	public void setLastDate(LocalDate lastDate)
	{
		this.lastDate = lastDate;
	}

	public Integer getNumMeasurements()
	{
		return this.numMeasurements;
	}
	
	public void setNumMeasurements(Integer numMeasurements)
	{
		this.numMeasurements = numMeasurements;
	}

	public Double getAvgFlowPerSec() 
	{
		return this.avgFlowPerSec;
	}
	
	public Double getAvgFlowPerDay()
	{
		if(this.avgFlowPerSec == null)
			return null;
		
		int secondsInADay = SECS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;
		double avgFlowPerDay = this.avgFlowPerSec * secondsInADay;
		
		return avgFlowPerDay;
	}
	
	public Double getAvgFlowPerMonth()
	{
		if(this.avgFlowPerSec == null)
			return null;
		
		int secondsInAMonth = SECS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY * DAYS_IN_A_MONTH;
		double avgFlowPerMonth = this.avgFlowPerSec * secondsInAMonth;
		
		return avgFlowPerMonth;
	}

	public void setAvgFlowPerSec(Double avgFlowPerSec) 
	{
		this.avgFlowPerSec = avgFlowPerSec;
	}

	public void setFlows(List<Flow> flows) 
	{
		this.flows = flows;
	}

	public List<Flow> getFlows() 
	{
		if (this.flows == null)
			this.flows = new ArrayList<Flow>();
		
		return this.flows;
	}

	@Override
	public String toString() 
	{
		return this.name;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.id;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (this.id != other.id)
			return false;
		return true;
	}
}
