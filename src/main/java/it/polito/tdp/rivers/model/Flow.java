package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow 
{
	private LocalDate day;
	private double flow;
	private River river;

	public Flow(LocalDate day, double flow, River river) 
	{
		this.day = day;
		this.flow = flow;
		this.river = river;
	}

	public LocalDate getDay() 
	{
		return this.day;
	}

	public double getFlow() 
	{
		return this.flow;
	}
	
	public River getRiver()
	{
		return this.river;
	}

	@Override
	public String toString() 
	{
		return "Flow [day=" + day + ", flow=" + flow + ", river=" + river + "]";
	}

}
