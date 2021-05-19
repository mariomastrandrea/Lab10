package it.polito.tdp.rivers.simulator;

public interface SimulatorResult
{
	double getWaterBasinCapacity();
	int getNumDaysOfDisruption();
	double getAvgBasinLevelPerSec();
}
