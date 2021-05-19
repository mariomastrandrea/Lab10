package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model 
{
	private final RiversDAO riversDao;
	private Set<River> allRivers;
	private River river;
	
	private Simulator simulator;
	
	
	public Model()
	{
		this.riversDao = new RiversDAO();
		this.allRivers = null;
		this.river = null;
		this.simulator = new Simulator();
	}
	
	public Collection<River> getAllRivers()
	{
		if(this.allRivers == null)
			this.allRivers = this.riversDao.getAllRivers();
		
		return this.allRivers;
	}

	public void computeDataOf(River selectedRiver)
	{
		this.river = selectedRiver;
		
		if(selectedRiver.getFirstDate() != null && selectedRiver.getLastDate() != null
			&& selectedRiver.getNumMeasurements() != null && selectedRiver.getAvgFlowPerSec() != null)
			return;	//already computed
		
		this.riversDao.getDataOf(this.river);
		
		return;
	}

	public LocalDate GetFirstDate()
	{
		return this.river.getFirstDate();
	}

	public LocalDate getLastDate()
	{
		return this.river.getLastDate();
	}

	public int getNumMeasurements()
	{
		Integer num = this.river.getNumMeasurements();
		
		if(num == null)
			return 0;
		
		return num;
	}

	public double getAvgFlowPerSec()
	{
		Double avgFlow = this.river.getAvgFlowPerSec();
		
		if(avgFlow == null)
			return 0.0;
		
		return avgFlow;
	}

	public void startSimulation(River selectedRiver, double scaleFactor)
	{
		if(!selectedRiver.equals(this.river))
			throw new RuntimeException("Error: new river is different from the previous selected");
		
		this.simulator.initialize(this.river, scaleFactor);
		this.simulator.run();
	}

	public double getWaterBasinCapacity()
	{
		return this.simulator.getWaterBasinCapacity();
	}

	public int getNumDaysOfDisruption()
	{
		return this.getNumDaysOfDisruption();
	}

	public double getAvgBasinLevel()
	{
		return this.simulator.getAvgBasinLevel();
	}

}
