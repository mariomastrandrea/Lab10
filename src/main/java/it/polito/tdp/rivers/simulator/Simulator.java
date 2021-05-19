package it.polito.tdp.rivers.simulator;

import it.polito.tdp.rivers.model.River;

public class Simulator implements InitializableSimulator, RunnableSimulator, SimulatorResult
{
	public static final int SECS_IN_A_MINUTE = 60;
	public static final int MINUTES_IN_A_HOUR = 60;
	public static final int HOURS_IN_A_DAY = 24;
	public static final int DAYS_IN_A_MONTH = 60;

	// input
	private River river;
	private Double scaleFactor; // k
	
	private Double waterBasinCapacity;	// Q
	
	// output
	private int numDaysOfDisruption;
	private double avgBasinLevel;	// C_med
	
	
	private Simulator() { }
	
	public static InitializableSimulator buildSimulator() 
	{
		return new Simulator();
	}
	
	public RunnableSimulator initialize(River river, double scaleFactor)
	{
		this.river = river;
		this.scaleFactor = scaleFactor;
		
		int secondsInAMonth = SECS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY * DAYS_IN_A_MONTH;
		this.waterBasinCapacity = this.scaleFactor * this.river.getAvgFlowPerSec() * secondsInAMonth;
		//TODO: implement method; generate events
		
		return this;
	}
	
	public Simulator run()
	{
		//TODO: implement method
		
		return this;
	}
	
	public double getWaterBasinCapacity()
	{
		if(this.waterBasinCapacity == null)
			return 0.0;
		
		return this.waterBasinCapacity;
	}
	
	public int getNumDaysOfDisruption()
	{
		return this.numDaysOfDisruption;
	}
	
	public double getAvgBasinLevel()
	{
		return this.avgBasinLevel;
	}
	
}
