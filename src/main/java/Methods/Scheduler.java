package Methods;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> serverList = new ArrayList<>();
    private int maxNumberServers;
    private int maxTasksPerServer;
    private Strategy strategy = new Strategy();

    public Scheduler(int maxNumberServers) {
        for(int i = 0; i < maxNumberServers; i++){
            Server server = new Server();
            serverList.add(server);
            Thread t = new Thread(server);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        strategy.addTask(serverList,t);
    }

    public int getMaxNumberServers() {return maxNumberServers;}
    public void setMaxNumberServers(int maxNumberServers) {this.maxNumberServers = maxNumberServers;}
    public int getMaxTasksPerServer() {return maxTasksPerServer;}
    public void setMaxTasksPerServer(int maxTasksPerServer) {this.maxTasksPerServer = maxTasksPerServer;}
    public List<Server> getServerList() {return serverList;}
    public void setServerList(List<Server> serverList) {this.serverList = serverList;}
    public Strategy getStrategy() {return strategy;}
    public void setStrategy(Strategy strategy) {this.strategy = strategy;}
}