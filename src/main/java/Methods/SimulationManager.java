package Methods;

import Main.QueueController;
import Model.Server;
import Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;
import static javafx.application.Application.launch;

public class SimulationManager implements Runnable {
    private int numberOfClients = 30;
    private int numberOfServers = 4;
    private int maxSimulation = 60;
    private int minArrivalTime = 2;
    private int maxArrivalTime = 10;
    private int minServiceTime = 2;
    private int maxServiceTime = 5;
    public SelectionPolicy selectionPolicy;

    private Scheduler scheduler;
    private CopyOnWriteArrayList<Task> generateTasks = new CopyOnWriteArrayList<>();

    public SimulationManager(int numberOfClients, int numberOfServers, int maxSimulation, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, String policy) {
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.maxSimulation = maxSimulation;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;

        scheduler = new Scheduler(numberOfServers);

        System.out.println(policy);
        if(Objects.equals(policy, "SHORTEST_TIME")) {
            scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);
        }
        else {
            scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
        }
        scheduler.changeStrategy(selectionPolicy);
        Server server = new Server();
        generateRandomTasks();
    }
    public void generateRandomTasks() {
        Random rand = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            Task task = new Task();
            task.setID(i);
            task.setArrivalTime(rand.nextInt(minArrivalTime, maxArrivalTime));
            task.setServiceTime(rand.nextInt(minServiceTime, maxServiceTime));
            generateTasks.add(task);
        }
        sortedTasks(generateTasks);
        showList(generateTasks);
    }
    public void showList(List<Task> generateTasks){
        for(Task task: generateTasks){
            System.out.println("(" + task.getID() + ": " + task.getArrivalTime() + " - " + task.getServiceTime() + ")");
        }
    }
    public void sortedTasks(CopyOnWriteArrayList<Task> generateTasks){
        for(int i = 0; i < numberOfClients-1; i++)
            for(int j = i+1; j < numberOfClients; j++){
                Task task1 = generateTasks.get(i);
                Task task2 = generateTasks.get(j);
                if(task1.getArrivalTime() > task2.getArrivalTime())
                    Collections.swap(generateTasks,i,j);
            }
        for(int i = 0; i < numberOfClients; i++){
            Task task = generateTasks.get(i);
            task.setID(i);
        }
        this.generateTasks = generateTasks;
    }

    @Override
    public void run(){
        int currentTime = 0;
        while(currentTime <= maxSimulation) {
            for (Task it : generateTasks) {
                if (it.getArrivalTime() <= currentTime) {
                    scheduler.dispatchTask(it);
                    generateTasks.remove(it);
                }
            }
            System.out.println(currentTime);
            try {
                String string1 = showTasks(generateTasks);
                String string2 = showQueueList(scheduler.getServerList());
                FileWriter resultWriteInFile = new FileWriter("Testing.txt",true);
                resultWriteInFile.write( "Time: "+ currentTime + "\n" );
                resultWriteInFile.write(string1);
                resultWriteInFile.write(string2);
                QueueController queueController = new QueueController();
                queueController.logOfEventsWindow(currentTime,string1,string2);
                resultWriteInFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        float result = 0F;
        for(Integer it: Server.averageTimeArray){
            result += it;
        }
        result /= numberOfClients;
        System.out.println("AVERAGE TIME IS: " + result);
        try {
            FileWriter resultWriteInFile = new FileWriter("Testing.txt",true);
            resultWriteInFile.write("AVERAGE TIME IS: " + result);
            resultWriteInFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showTasks(List<Task> generatedList){
        String result = "Task: ";
        System.out.print("Task: ");
        for (Task value : generatedList) {
            System.out.print("(" + value.getID() + "," + value.getArrivalTime() + "," + value.getServiceTime() + "); ");
            result = result + "(" + value.getID() + "," + value.getArrivalTime() + "," + value.getServiceTime() + "); ";
        }
        result = result + '\n';
        System.out.println();
        return result;
    }
    public String showQueueList(List<Server> servers){
        String result = "";
        int index = 0;
        for(Server it: servers){
            System.out.print("Queue " + index + ":  ");
            it.showTaskList(true);
            result = result + "Queue " + index + ":  ";
            result = result + it.showTaskList(false);
            index++;
        }
        System.out.println();
        result = result + "\n";
        return result;
    }

    public List<Task> getGenerateTasks() {return generateTasks;}
    public void setGenerateTasks(CopyOnWriteArrayList<Task> generateTasks) {this.generateTasks = generateTasks;}
    public Scheduler getScheduler() {return scheduler;}
    public void setScheduler(Scheduler scheduler) {this.scheduler = scheduler;}

    public int getNumberOfClients() {return numberOfClients;}
    public void setNumberOfClients(int numberOfClients) {this.numberOfClients = numberOfClients;}
    public int getNumberOfServers() {return numberOfServers;}
    public void setNumberOfServers(int numberOfServers) {this.numberOfServers = numberOfServers;}
    public int getMaxSimulation() {return maxSimulation;}
    public void setMaxSimulation(int maxSimulation) {this.maxSimulation = maxSimulation;}
    public int getMinArrivalTime() {return minArrivalTime;}
    public void setMinArrivalTime(int minArrivalTime) {this.minArrivalTime = minArrivalTime;}
    public int getMaxArrivalTime() {return maxArrivalTime;}
    public void setMaxArrivalTime(int maxArrivalTime) {this.maxArrivalTime = maxArrivalTime;}
    public int getMinServiceTime() {return minServiceTime;}
    public void setMinServiceTime(int minServiceTime) {this.minServiceTime = minServiceTime;}
    public int getMaxServiceTime() {return maxServiceTime;}
    public void setMaxServiceTime(int maxServiceTime) {this.maxServiceTime = maxServiceTime;}
    public SelectionPolicy getSelectionPolicy() {return selectionPolicy;}
    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {this.selectionPolicy = selectionPolicy;}
}
