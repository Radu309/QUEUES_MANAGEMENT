package Methods;

import Model.Server;
import Model.Task;

import java.util.List;

public class Strategy {
    public synchronized void addTask(List<Server> servers, Task t){
    }
}

class ConcreteStrategyTime extends Strategy {
    public synchronized void addTask(List<Server> servers, Task t){
        int minim = 100;
        int finalTime=0;
        Server server = new Server();
        for(Server it : servers){
            finalTime = 0;
            for(Task task: it.getTasks())
                if(it.getTasks().size() != 0)
                    finalTime += task.getServiceTime();
            if(finalTime < minim){
                minim = finalTime;
                server = it;
            }
        }
        for(Server it : servers){
            if(it.getTasks().size() == 0) {
                server = it;
                break;
            }
        }
        server.addTask(t);
    }
}

class ConcreteStrategyQueue extends Strategy {
    public synchronized void addTask(List<Server> servers,Task t){
        int minim = 100;
        Server server = new Server();
        for (Server it : servers) {
            if (it.getTasks().size() < minim) {
                minim = it.getTasks().size();
                server = it;
            }
        }
        server.addTask(t);
    }
}

enum SelectionPolicy {
    SHORTEST_QUEUE, SHORTEST_TIME
}


