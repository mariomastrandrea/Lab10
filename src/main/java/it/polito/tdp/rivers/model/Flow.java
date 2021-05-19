package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow 
{
	private final int id;
	private final LocalDate day;
	private final double flow;
	private final River river;

	
	public Flow(int id, LocalDate day, double flow, River river) 
	{
		this.id = id;
		this.day = day;
		this.flow = flow;
		this.river = river;
	}
	
	public int getId()
	{
		return this.id;
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
		return "Flow Id: " + this.id + ", date: " + 
					this.day + ", flow: " + this.flow + ", river: " + this.river;
	}
}
