package it.polito.tdp.rivers.model;

import static it.polito.tdp.rivers.model.River.*;

import java.time.LocalDate;

public class Flow 
{
	private final int id;
	private final LocalDate day;
	private final double flowPerSec;
	private final River river;

	
	public Flow(int id, LocalDate day, double flowPerSec, River river) 
	{
		this.id = id;
		this.day = day;
		this.flowPerSec = flowPerSec;
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

	public double getFlowPerSec() 
	{
		return this.flowPerSec;
	}
	
	public double getFlowPerDay()
	{
		int numSecondsInADay = SECS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;
		return this.flowPerSec * numSecondsInADay;
	}
	
	public River getRiver()
	{
		return this.river;
	}
}
