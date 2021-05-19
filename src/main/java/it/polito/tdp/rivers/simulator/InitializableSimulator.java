package it.polito.tdp.rivers.simulator;

import it.polito.tdp.rivers.model.River;

public interface InitializableSimulator
{
	RunnableSimulator initialize(River river, double scaleFactor);
}
