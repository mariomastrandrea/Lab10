package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.simulator.SimulatorResult;

public class FXMLController 
{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> riverComboBox;

    @FXML
    private TextField firstDateTextField;

    @FXML
    private TextField lastDateTextField;

    @FXML
    private TextField numMeasurementsTextField;

    @FXML
    private TextField averageFlowTextField;

    @FXML
    private TextField scaleFactorTextField;
    private static final int MAX_CHARS_SCALE_FACTOR = 6;

    @FXML
    private Button startSimulationButton;

    @FXML
    private TextArea outputTextArea;
    
	private Model model;
    

    @FXML
    void handleRiverSelection(ActionEvent event) 
    {
    	River selectedRiver = this.riverComboBox.getValue();
    	
    	if(selectedRiver == null)
    	{
    		this.setViewRiverSelected(false);
    		return;
    	}
    	// update view
    	this.setViewRiverSelected(true);
        	
    	//business logic
    	this.model.computeDataOf(selectedRiver);
    	
    	//retrieve data
    	LocalDate firstDate = this.model.GetFirstDate();
    	LocalDate lastDate = this.model.getLastDate();
    	int numMeasurements = this.model.getNumMeasurements();
    	double avgFlowPerSec = this.model.getAvgFlowPerSec();
    	
    	if(firstDate == null || lastDate == null || firstDate.isAfter(lastDate))
    	{
    		this.outputTextArea.setText("An error occured processing River data");
    		return;
    	}
    	
    	// show data
    	this.firstDateTextField.setText(firstDate.toString());
    	this.lastDateTextField.setText(lastDate.toString());
    	this.numMeasurementsTextField.setText(String.valueOf(numMeasurements));
    	
    	String avgFlowPerSecString = String.format("%.3f m^3/s", avgFlowPerSec);
    	this.averageFlowTextField.setText(avgFlowPerSecString);
    }
    
	private void setViewRiverSelected(boolean selected)
    {
    	boolean disable = !selected;
    	
    	this.firstDateTextField.setDisable(disable);
		this.lastDateTextField.setDisable(disable);
		this.averageFlowTextField.setDisable(disable);
		this.numMeasurementsTextField.setDisable(disable);
		this.scaleFactorTextField.setDisable(disable);
    }

    @FXML
    void handleScaleFactorTyping(KeyEvent event) 
    {
    	String input = this.scaleFactorTextField.getText();
    	
    	if(input == null || input.isBlank())
    		this.startSimulationButton.setDisable(true);
    	else
    		this.startSimulationButton.setDisable(false);
    }
    
    @FXML
    void handleStartSimulation(ActionEvent event) 
    {
    	River selectedRiver = this.riverComboBox.getValue();
    	
    	if(selectedRiver == null)
    	{
    		this.outputTextArea.setText("Input error: river has not been selected");
    		return;
    	}
    	
    	String scaleFactorInput = this.scaleFactorTextField.getText();
    	
    	if(scaleFactorInput == null || scaleFactorInput.isBlank())
    	{
    		this.outputTextArea.setText("Input error: no scale factor set");
    		return;
    	}
    	
    	double scaleFactor;
    	
    	try
		{
			scaleFactor = Double.parseDouble(scaleFactorInput);
		}
		catch(NumberFormatException nfe)
		{
			this.outputTextArea.setText("Errore: \"" + scaleFactorInput + "\" non e' un numero valido");
			return;
		}
    	
    	// run simulation
    	SimulatorResult result = this.model.startSimulation(selectedRiver, scaleFactor);
    	
    	//print output
    	String output = this.printOutput(selectedRiver, scaleFactor, result);
    	this.outputTextArea.setText(output);
    }

    private String printOutput(River river, double scaleFactor, SimulatorResult result)
	{
    	// get output
    	double Q = result.getWaterBasinCapacity();
    	int daysOfDisruption = result.getNumDaysOfDisruption();
    	double avgBasinLevelPerSec = result.getAvgBasinLevelPerSec();
    	
		StringBuilder sb = new StringBuilder();
		
		sb.append("*** Simulazione effetuata ***\n\n");
		sb.append("Input:\n");
		sb.append("\tFiume: ").append(river.toString()).append("\n");
		sb.append("\tFattore di scala (k): ").append(scaleFactor).append("\n");
		sb.append("\n");
		sb.append("Output:\n");
		sb.append("\tCapacità del bacino: ").append(String.format("%.3e", Q)).append(" m^3\n");
		sb.append("\tNumero di giorni di disservizio: ").append(daysOfDisruption).append("\n");
		sb.append("\tOccupazione media del bacino: ").append(String.format("%.3f", avgBasinLevelPerSec)).append(" m^3/s");
		
		return sb.toString();
	}

	@FXML
    void initialize() 
    {
        assert riverComboBox != null : "fx:id=\"riverComboBox\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert firstDateTextField != null : "fx:id=\"firstDateTextField\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert lastDateTextField != null : "fx:id=\"lastDateTextField\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert numMeasurementsTextField != null : "fx:id=\"numMeasurementsTextField\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert averageFlowTextField != null : "fx:id=\"averageFlowTextField\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert scaleFactorTextField != null : "fx:id=\"scaleFactorTextField\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert startSimulationButton != null : "fx:id=\"startSimulationButton\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
        assert outputTextArea != null : "fx:id=\"outputTextArea\" was not injected: check your FXML file 'Scene_lab10.fxml'.";
    
        this.scaleFactorTextField.setTextFormatter(new TextFormatter<>(change -> 
        {
        	String input = change.getText();
        	
        	if(input == null || input.isBlank())
        		return change;
        	
        	int insertedChars = this.scaleFactorTextField.getText().length();
        	int allowedChars = MAX_CHARS_SCALE_FACTOR - insertedChars;
        	
        	if(input.length() > allowedChars)
        		input = input.substring(0, allowedChars);
        	
        	if(!input.matches("[0-9.]+"))
        		input = input.replaceAll("[^0-9.]+", "");
        	
        	change.setText(input);
        	
        	return change;
        }));
    }
    
    public void setModel(Model model) 
    {
    	this.model = model;
    	
    	Collection<River> rivers = this.model.getAllRivers();
    	this.riverComboBox.getItems().addAll(rivers);
    }
}

