package it.polito.tdp.rivers.simulator;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import static it.polito.tdp.rivers.model.River.*;

public class Simulator implements InitializableSimulator, RunnableSimulator, SimulatorResult
{
	public static final double BASIC_REQUIREMENT_FACTOR = 0.8; 
	public static final double EXTRA_REQUIREMENT_FACTOR = 10.0;
	public static final double EXTRA_REQUIREMENT_PROBABILITY = 0.05;
	
	// input
	private River river;
	private Double scaleFactor; // k
	
	// data
	private double basicDailyRequirement;
	private double extraordinaryDailyRequirement;
	private List<Flow> dailyFlows;	//events
	private double actualBasinLevel;	// C
	
	// output
	private Double basinCapacity;	// Q
	private int numDaysOfDisruption;
	private double avgBasinLevel;	// C_med
	
	
	private Simulator() { }
	
	public static InitializableSimulator buildSimulator() 
	{
		return new Simulator();
	}
	
	public RunnableSimulator initialize(River river, double scaleFactor)
	{
		if(river.getAvgFlowPerSec() == null)
			throw new RuntimeException("Errore: il fiume " + river + "non è stato inizializzato");

		this.river = river;
		this.scaleFactor = scaleFactor;
		
		this.basicDailyRequirement = BASIC_REQUIREMENT_FACTOR * this.river.getAvgFlowPerDay();
		this.extraordinaryDailyRequirement = EXTRA_REQUIREMENT_FACTOR * this.basicDailyRequirement;
		
		this.dailyFlows = new ArrayList<>(river.getFlows());
		this.basinCapacity = this.scaleFactor * this.river.getAvgFlowPerMonth();
		this.actualBasinLevel = this.basinCapacity / 2;
		
		this.numDaysOfDisruption = 0;
		this.avgBasinLevel = 0.0;
		
		return this;
	}
	
	public SimulatorResult run()
	{
		int daysCount = 0;
		
		for(Flow todayFlow : this.dailyFlows)
		{			
			double inputFlow = todayFlow.getFlowPerDay();
			double outFlow = this.getTodayRequirement();
			
			double dailyVariation = inputFlow - outFlow;
			
			//update basin level
			this.actualBasinLevel += dailyVariation;
			
			if(actualBasinLevel < 0)
			{
				this.actualBasinLevel = 0;
				this.numDaysOfDisruption++;
			}
			
			if(this.actualBasinLevel > this.basinCapacity) //overflow
				this.actualBasinLevel = this.basinCapacity;
			
			//update avg level
			double oldAvg = this.avgBasinLevel;
			double newAvg = ((oldAvg * daysCount) + this.actualBasinLevel) / ++daysCount;
			this.avgBasinLevel = newAvg;
		}
		
		return this;
	}
	
	private double getTodayRequirement()
	{
		double random = Math.random();
		
		if(random < EXTRA_REQUIREMENT_PROBABILITY)
			return this.extraordinaryDailyRequirement;
		
		return this.basicDailyRequirement;
	}
	
	public double getWaterBasinCapacity()
	{
		if(this.basinCapacity == null)
			return 0.0;
		
		return this.basinCapacity;
	}
	
	public int getNumDaysOfDisruption()
	{
		return this.numDaysOfDisruption;
	}
	
	public double getAvgBasinLevelPerSec()
	{
		double avgBasinLevelPerDay = this.avgBasinLevel;
		int numSecondsInADay = SECS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY;
		
		return avgBasinLevelPerDay / (double)numSecondsInADay;
	}
	
}
