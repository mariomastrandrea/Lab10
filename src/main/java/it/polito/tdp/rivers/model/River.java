package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;

public class River 
{
	private final int id;
	private String name;
	private double flowAvg;
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

	public String getName() 
	{
		return this.name;
	}

	public int getId() 
	{
		return this.id;
	}

	public double getFlowAvg() 
	{
		return this.flowAvg;
	}

	public void setFlowAvg(double flowAvg) 
	{
		this.flowAvg = flowAvg;
	}

	public void setFlows(List<Flow> flows) 
	{
		this.flows = flows;
	}

	public List<Flow> getFlows() 
	{
		if (this.flows == null)
			this.flows = new ArrayList<Flow>();
		
		return flows;
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
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}
}
