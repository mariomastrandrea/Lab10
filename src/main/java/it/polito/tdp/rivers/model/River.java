package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class River 
{
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
