package Model;

import java.util.ArrayList;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private LinkedBlockingQueue<Task> tasks;
    private Task firstTask = new Task();
    private AtomicInteger waitingPeriodOfSimulation = new AtomicInteger();
    public static ArrayList<Integer> averageTimeArray = new ArrayList<>();
    private int averageTimeInt = 0;
    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriodOfSimulation.set(0);
    }
    public synchronized void addTask(Task newTask){
        tasks.add(newTask);
        averageTimeInt = averageTimePerTask();
        averageTimeArray.add(averageTimeInt);
        waitingPeriodOfSimulation.incrementAndGet();
    }
    @Override
    public void run() {
        while(true){
            try {
                Task task = tasks.peek();
                if(tasks.peek() != null)
                    firstTask = tasks.peek();
                else
                    firstTask = new Task();
                if (task != null) {
                    Thread.sleep(1000L * task.getServiceTime());
                    waitingPeriodOfSimulation.decrementAndGet();
                    removeFirstElement(tasks);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public int averageTimePerTask(){
        int time = 0;
        for(Task it: tasks){time += it.getServiceTime();}
        return time;
    }
    public void removeFirstElement(LinkedBlockingQueue<Task> tasks){
        Task task = tasks.peek();
        tasks.remove(task);
        this.tasks = tasks;
    }
    public synchronized String showTaskList(boolean testing){
        String result = "";
        for (Task value : tasks) {
            result = result + "(" + value.getID() + "," + value.getArrivalTime() + "," + value.getServiceTime() + "); ";
            if(testing)
                System.out.print("(" + value.getID() + "," + value.getArrivalTime() + "," + value.getServiceTime() + "); ");
        }

        if(testing)
            System.out.println();
        if (testing)
            if(firstTask.getServiceTime() > 0) {
                firstTask.setServiceTime(firstTask.getServiceTime() - 1);
            }
        else
            if(firstTask.getServiceTime() > 0) {
                firstTask.setServiceTime(firstTask.getServiceTime() - 1);
            }
        result = result + "\n";
        return result;
    }


    public synchronized LinkedBlockingQueue<Task> getTasks() {return tasks;}
    public synchronized void setTasks(LinkedBlockingQueue<Task> tasks) {this.tasks = tasks;}
    public Task getFirstTask() {return firstTask;}
    public void setFirstTask(Task firstTask) {this.firstTask = firstTask;}
    public AtomicInteger getWaitingPeriodOfSimulation() {return waitingPeriodOfSimulation;}
    public void setWaitingPeriodOfSimulation(AtomicInteger waitingPeriodOfSimulation) {this.waitingPeriodOfSimulation = waitingPeriodOfSimulation;}
    public ArrayList<Integer> getAverageTimeArray() {return averageTimeArray;}
    public void setAverageTimeArray(ArrayList<Integer> averageTimeArray) {this.averageTimeArray = averageTimeArray;}
    public int getAverageTimeInt() {return averageTimeInt;}
    public void setAverageTimeInt(int averageTime) {this.averageTimeInt = averageTime;}


}
