package Main;

import Methods.SimulationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class QueueController implements Initializable {
    public QueueController() {}

    ///         Blocks
    @FXML
    private TextField numberOfTasks = new TextField();
    @FXML
    private TextField numberOfServers = new TextField();
    @FXML
    private TextField simulationTime = new TextField();
    @FXML
    private TextField minimumArrivalTime = new TextField();
    @FXML
    private TextField maximumArrivalTime = new TextField();
    @FXML
    private TextField minimumServiceTime = new TextField();
    @FXML
    private TextField maximumServiceTime = new TextField();
    @FXML
    ChoiceBox selectTheStrategy = new ChoiceBox();
    @FXML
    private Button start = new Button();
    @FXML
    TextArea textArea;
    static TextArea logOfEvents;

    @FXML
    private Button back = new Button();
    ///         Actions
    @FXML
    public void onClickSelectTheStrategy(){
        selectTheStrategy.setItems(FXCollections.observableArrayList("SHORTEST_TIME","SHORTEST_QUEUE"));
    }
    @FXML
    public void onClickStart() throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader logEvents = new FXMLLoader(QueueController.class.getResource("LogEvent.fxml"));
        Scene scene1 = new Scene(logEvents.load(), 630, 500);
        stage1.setTitle("Log Of Events ");
        stage1.setScene(scene1);
        stage1.show();
        try {
            File printResultInFile = new File("Testing.txt");
            if (printResultInFile.createNewFile()) {
                System.out.println("File created: " + printResultInFile.getName());
            } else {
                System.out.println("Now you are writing in an existed file.");
                FileWriter print = new FileWriter("Testing.txt",false);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        SimulationManager simulation = new SimulationManager(
                Integer.parseInt(numberOfTasks.getText()),Integer.parseInt(numberOfServers.getText()),
                Integer.parseInt(simulationTime.getText()),Integer.parseInt(minimumArrivalTime.getText()),
                Integer.parseInt(maximumArrivalTime.getText()),Integer.parseInt(minimumServiceTime.getText()),
                Integer.parseInt(maximumServiceTime.getText()),selectTheStrategy.getValue().toString());

        Thread t = new Thread(simulation);
        t.start();
    }
    @FXML
    public void clickOnBack(){
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }
    public void initialize(URL url, ResourceBundle rb){
        logOfEvents = textArea;
    }
    public static String logOfEventsWindow(int currentTime, String taskList, String queueList){
        //System.out.println("-------------------------------------------------------------------------------");
        logOfEvents.appendText("Current time is: " + currentTime + "\n" + taskList + queueList );
        //logOfEvents.setText(logOfEvents.getText());
        //System.out.println(logOfEvents.getText() + " <========================================");
        return logOfEvents.getText();
    }
    //getters and setters

    public TextField getNumberOfTasks() {return numberOfTasks;}
    public void setNumberOfTasks(TextField numberOfTasks) {this.numberOfTasks = numberOfTasks;}
    public TextField getNumberOfServers() {return numberOfServers;}
    public void setNumberOfServers(TextField numberOfServers) {this.numberOfServers = numberOfServers;}
    public TextField getSimulationTime() {return simulationTime;}
    public void setSimulationTime(TextField simulationTime) {this.simulationTime = simulationTime;}
    public TextField getMinimumArrivalTime() {return minimumArrivalTime;}
    public void setMinimumArrivalTime(TextField minimumArrivalTime) {this.minimumArrivalTime = minimumArrivalTime;}
    public TextField getMaximumArrivalTime() {return maximumArrivalTime;}
    public void setMaximumArrivalTime(TextField maximumArrivalTime) {this.maximumArrivalTime = maximumArrivalTime;}
    public TextField getMinimumServiceTime() {return minimumServiceTime;}
    public void setMinimumServiceTime(TextField minimumServiceTime) {this.minimumServiceTime = minimumServiceTime;}
    public TextField getMaximumServiceTime() {return maximumServiceTime;}
    public void setMaximumServiceTime(TextField maximumServiceTime) {this.maximumServiceTime = maximumServiceTime;}
    public ChoiceBox getSelectTheStrategy() {return selectTheStrategy;}
    public void setSelectTheStrategy(ChoiceBox selectTheStrategy) {this.selectTheStrategy = selectTheStrategy;}
    public Button getStart() {return start;}
    public void setStart(Button start) {this.start = start;}
    public TextArea getLogOfEvents() {return logOfEvents;}
    public void setLogOfEvents(TextArea logOfEvents) {this.logOfEvents = logOfEvents;}
    public Button getBack() {return back;}
    public void setBack(Button back) {this.back = back;}
}