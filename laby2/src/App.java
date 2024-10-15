import java.util.ArrayList;

public class App { 
    final static double MAX = 200;
    
    static void AddRLTask(int num, ArrayList<Task> Tasks,ArrayList<Task> Tasks1,ArrayList<Task> Tasks2,ArrayList<Task> Tasks3,ArrayList<Task> Tasks4,ArrayList<Task> Tasks5,ArrayList<Task> Tasks6,ArrayList<Task> Tasks7){

        int ArrivalTimeR;
        int currentCilinder;
        int Deadline;

        for(int i=0;i<num;i++){
            ArrivalTimeR = (int)(Math.random()*200);
            currentCilinder = (int)((Math.random()*MAX)+1);
            Deadline = (int)((Math.random() * MAX/2) + MAX/4);

            Tasks.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks1.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks2.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks3.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks4.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks5.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks6.add(new Task(currentCilinder, ArrivalTimeR, Deadline));
            Tasks7.add(new Task(currentCilinder, ArrivalTimeR, Deadline));

        }

    }

    static void AddTask(int num, ArrayList<Task> Tasks, ArrayList<Task> Tasks1, ArrayList<Task> Tasks2, ArrayList<Task> Tasks3,ArrayList<Task> Tasks4,ArrayList<Task> Tasks5,ArrayList<Task> Tasks6,ArrayList<Task> Tasks7){

        int ArrivalTimeR;
        int currentCilinder;

        for(int i=0;i<num;i++){
            ArrivalTimeR = (int)(Math.random()*200);
            currentCilinder = (int)((Math.random()*MAX)+1);

            Tasks.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks1.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks2.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks3.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks4.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks5.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks6.add(new Task(currentCilinder, ArrivalTimeR));
            Tasks7.add(new Task(currentCilinder, ArrivalTimeR));

        }
    }
    
    static void FCFS_EDF(ArrayList<Task> Tasks, ArrayList<Task> RLTasks) throws InterruptedException{
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();;

        int currentCilinder = 1;
        int sumOfcilinders = 0;

        int indexOfShortestDL = 0;
        boolean activeDL = false;

        int Time = 0;

        while(!queue.isEmpty() || Time<=200){
            //dodawanie zwykłych tasków
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    
                    queue.add(Tasks.get(i));
                }
            }
            
            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                   
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(priorityQueue.get(indexOfShortestDL).getDeadline() > priorityQueue.get(i).getDeadline() ){
                            indexOfShortestDL = i;
                        }
                        activeDL = true;
                    }
                    
                }
                
                //prezsuwanie cylindra
                if(currentCilinder == priorityQueue.get(indexOfShortestDL).getCilinder()){
                    priorityQueue.remove(indexOfShortestDL);
                    indexOfShortestDL = 0;
                    activeDL = false;
                }
                else if(currentCilinder < priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

               //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else if(!queue.isEmpty()){

                //przesuwanie cylindra
                if(currentCilinder == queue.get(0).getCilinder()){
                    queue.remove(0);
                }
                else if(currentCilinder < queue.get(0).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > queue.get(0).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }
                
                //System.out.println(currentCilinder + " N");
            }

            Time++;
        }

        System.out.println("FCFS_EDF: " + sumOfcilinders);
    
    }
    
    static void FCFS_FDS(ArrayList<Task> Tasks, ArrayList<Task> RLTasks) throws InterruptedException{
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();;
        Task currentPTask = new Task(0, 0, 0);


        int currentCilinder = 1;
        int sumOfcilinders = 0;

        int indexOfShortestDL = 0;
        boolean activeDL = false;

        int Time = 0;

        while(!queue.isEmpty() || Time<=200){
            //dodawanie zwykłych tasków
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }
            
            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(i==0){
                            currentPTask = priorityQueue.get(i);
                        }
                        else if(currentPTask.getDeadline() > priorityQueue.get(i).getDeadline()){
                            currentPTask = priorityQueue.get(i);
                        }

                        activeDL = true;
                    }
                    
                }
                     
                //prezsuwanie cylindra
                if(currentCilinder == currentPTask.getCilinder()){
                    priorityQueue.remove(currentPTask);
                    activeDL = false;
                }
                else if(currentCilinder < currentPTask.getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > currentPTask.getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //usuwanie napotknych po dordzę Real Time Tasków
                for(int i=0;i<priorityQueue.size();i++){
                    if(priorityQueue.get(i).getCilinder() == currentCilinder){
                        
                        priorityQueue.remove(i);
                    }
                }
                
                //usuwanie napotkanych zwykłych tasków
                for(int i=0;i<queue.size();i++){
                    if(queue.get(i).getCilinder() == currentCilinder){
                        
                        queue.remove(i);
                    }
                }

                //System.out.println(currentCilinder);

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else if(!queue.isEmpty()){

                //przesuwanie cylindra
                if(currentCilinder == queue.get(0).getCilinder()){
                    queue.remove(0);
                }
                else if(currentCilinder < queue.get(0).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > queue.get(0).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }
                
                //System.out.println(currentCilinder);
            }

            Time++;
        }

        System.out.println("FCFS_FDS: " + sumOfcilinders);
    
    }

    static void SSTF_EDF(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){

        ArrayList<Task> queue = new ArrayList<>();
        int currentCilinder = 1;
        int sumOfcilinders = 0;
        boolean active = false;
        int indexOfTask = 0;
        
        ArrayList<Task> priorityQueue = new ArrayList<>();
        boolean activeDL = false;
        int indexOfShortestDL = 0;

        int Time = 0;


        while(!queue.isEmpty() || Time<=200){
            
            //dodawanie ...
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(priorityQueue.get(indexOfShortestDL).getDeadline() > priorityQueue.get(i).getDeadline() ){
                            indexOfShortestDL = i;
                        }
                        activeDL = true;
                    }
                    
                }
                
                //prezsuwanie cylindra
                if(currentCilinder == priorityQueue.get(indexOfShortestDL).getCilinder()){
                    priorityQueue.remove(indexOfShortestDL);
                    indexOfShortestDL = 0;
                    activeDL = false;
                }
                else if(currentCilinder < priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //System.out.println(currentCilinder + " P");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else if(!queue.isEmpty()){

                //wyszukiwanie najbliższego taska
                if(!active){
                    for(int i=0;i<queue.size();i++){
                        if( Math.abs( queue.get(indexOfTask).getCilinder() - currentCilinder ) > Math.abs( queue.get(i).getCilinder() - currentCilinder ) ){
                            indexOfTask = i;
                        }
                    }
                    active = true;
                }

                //przesuwanie ...
                if(currentCilinder == queue.get(indexOfTask).getCilinder()){
                    //System.out.println("Ukończono " + queue.get(indexOfTask).getCilinder());
                    queue.remove(indexOfTask);
                    indexOfTask = 0;
                    active = false;
                }
                else if(currentCilinder < queue.get(indexOfTask).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > queue.get(indexOfTask).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //System.out.println(currentCilinder + " N");
            }

            

            Time++;
        }

        System.out.println("SSTF_EDF: " + sumOfcilinders);
    }
    
    static void SSTF_FDS(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){

        ArrayList<Task> queue = new ArrayList<>();
        int currentCilinder = 1;
        int sumOfcilinders = 0;
        boolean active = false;
        int indexOfTask = 0;
        
        ArrayList<Task> priorityQueue = new ArrayList<>();
        boolean activeDL = false;
        Task currentPTask = new Task(0, 0, 0);

        int Time = 0;


        while(!queue.isEmpty() || Time<=200){
            
            //dodawanie ...
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(i==0){
                            currentPTask = priorityQueue.get(i);
                        }
                        else if(currentPTask.getDeadline() > priorityQueue.get(i).getDeadline()){
                            currentPTask = priorityQueue.get(i);
                        }

                        activeDL = true;
                    }
                    
                }
                     
                //prezsuwanie cylindra
                if(currentCilinder == currentPTask.getCilinder()){
                    priorityQueue.remove(currentPTask);
                    activeDL = false;
                }
                else if(currentCilinder < currentPTask.getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > currentPTask.getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //usuwanie napotknych po dordzę Real Time Tasków
                for(int i=0;i<priorityQueue.size();i++){
                    if(priorityQueue.get(i).getCilinder() == currentCilinder){
                        priorityQueue.remove(i);
                    }
                }
                
                //usuwanie napotkanych zwykłych tasków
                for(int i=0;i<queue.size();i++){
                    if(queue.get(i).getCilinder() == currentCilinder){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }

                //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else if(!queue.isEmpty()){

                //wyszukiwanie najbliższego taska
                if(!active){
                    for(int i=0;i<queue.size();i++){
                        if( Math.abs( queue.get(indexOfTask).getCilinder() - currentCilinder ) > Math.abs( queue.get(i).getCilinder() - currentCilinder ) ){
                            indexOfTask = i;
                        }
                    }
                    active = true;
                }

                //przesuwanie ...
                if(currentCilinder == queue.get(indexOfTask).getCilinder()){
                    //System.out.println("Ukończono " + queue.get(indexOfTask).getCilinder());
                    queue.remove(indexOfTask);
                    indexOfTask = 0;
                    active = false;
                }
                else if(currentCilinder < queue.get(indexOfTask).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > queue.get(indexOfTask).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //System.out.println(currentCilinder + " N");
            }

            

            Time++;
        }

        System.out.println("SSTF_FDS: " + sumOfcilinders);
    }

    static void SCAN_EDF(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();

        boolean activeDL = false;
        int indexOfShortestDL = 0;

        int currentCilinder = 1;
        int sumOfcilinders = 0;

        int Time = 0;

        boolean left = true;

        while(!queue.isEmpty() || Time<=200){
            //dodawanie
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(priorityQueue.get(indexOfShortestDL).getDeadline() > priorityQueue.get(i).getDeadline() ){
                            indexOfShortestDL = i;
                        }
                        activeDL = true;
                    }
                    
                }
                
                //prezsuwanie cylindra
                if(currentCilinder == priorityQueue.get(indexOfShortestDL).getCilinder()){
                    priorityQueue.remove(indexOfShortestDL);
                    indexOfShortestDL = 0;
                    activeDL = false;
                }
                else if(currentCilinder < priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else{

                //kompletowanie tasków na które trafi się po drodzę
                for(int i=0;i<queue.size();i++){
                    if(currentCilinder == queue.get(i).getCilinder()){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }  

                if(!queue.isEmpty()){   

                    //przesuwanie ...
                    if(left){
                        currentCilinder--;
                        sumOfcilinders++;
                    }
                    else{
                        currentCilinder++;
                        sumOfcilinders++;
                    }

                    if(currentCilinder == 0){
                        currentCilinder = 1;
                        left = !left;
                    }

                    if(currentCilinder == MAX){
                        left = !left;
                    }

                    //System.out.println(currentCilinder + " N");
                    
                }
            }

            Time++;
        }

        System.out.println("SCAN_EDF: " + sumOfcilinders);
    
    
    }

    static void SCAN_FDS(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();

        Task currentPTask = new Task(0, 0, 0);

        boolean activeDL = false;

        int currentCilinder = 1;
        int sumOfcilinders = 0;

        int Time = 0;

        boolean left = true;

        while(!queue.isEmpty() || Time<=200){
            //dodawanie
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(i==0){
                            currentPTask = priorityQueue.get(i);
                        }
                        else if(currentPTask.getDeadline() > priorityQueue.get(i).getDeadline()){
                            currentPTask = priorityQueue.get(i);
                        }

                        activeDL = true;
                    }
                    
                }
                

                //prezsuwanie cylindra
                if(currentCilinder == currentPTask.getCilinder()){
                    priorityQueue.remove(currentPTask);
                    activeDL = false;
                }
                else if(currentCilinder < currentPTask.getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > currentPTask.getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //usuwanie napotknych po dordzę Real Time Tasków
                for(int i=0;i<priorityQueue.size();i++){
                    if(priorityQueue.get(i).getCilinder() == currentCilinder){
                        priorityQueue.remove(i);
                    }
                }
                
                //usuwanie napotkanych zwykłych tasków
                for(int i=0;i<queue.size();i++){
                    if(queue.get(i).getCilinder() == currentCilinder){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }

                //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else{

                //kompletowanie tasków na które trafi się po drodzę
                for(int i=0;i<queue.size();i++){
                    if(currentCilinder == queue.get(i).getCilinder()){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }  

                if(!queue.isEmpty()){   

                    //przesuwanie ...
                    if(left){
                        currentCilinder--;
                        sumOfcilinders++;
                    }
                    else{
                        currentCilinder++;
                        sumOfcilinders++;
                    }

                    if(currentCilinder == 0){
                        currentCilinder = 1;
                        left = !left;
                    }

                    if(currentCilinder == MAX){
                        left = !left;
                    }

                    //System.out.println(currentCilinder + " N");
                    
                }
            }

            Time++;
        }

        System.out.println("SCAN_FDS: " + sumOfcilinders);
    
    
    }

    static void C_SCAN_EDF(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();

        int currentCilinder = 1;
        int sumOfcilinders = 0;

        boolean activeDL = false;
        int indexOfShortestDL = 0;

        int Time = 0;

        while(!queue.isEmpty() || Time<=200){
            
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(priorityQueue.get(indexOfShortestDL).getDeadline() > priorityQueue.get(i).getDeadline() ){
                            indexOfShortestDL = i;
                        }
                        activeDL = true;
                    }
                    
                }
                
                //prezsuwanie cylindra
                if(currentCilinder == priorityQueue.get(indexOfShortestDL).getCilinder()){
                    priorityQueue.remove(indexOfShortestDL);
                    indexOfShortestDL = 0;
                    activeDL = false;
                }
                else if(currentCilinder < priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > priorityQueue.get(indexOfShortestDL).getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else{

                //usuwanie napotkanych tasków
                for(int i=0;i<queue.size();i++){
                    if(currentCilinder == queue.get(i).getCilinder()){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }

                if(!queue.isEmpty()){   
        
                    //System.out.println(currentCilinder + " N");

                    currentCilinder++;
                    sumOfcilinders++;

                    //zawrotka
                    if(currentCilinder == MAX+1){
                        currentCilinder = 1;
                    } 

                    
                }
            }
            Time++;
        }

        System.out.println("C_SCAN_EDF: " + sumOfcilinders);
    }

    static void C_SCAN_FDS(ArrayList<Task> Tasks, ArrayList<Task> RLTasks){
        
        ArrayList<Task> queue = new ArrayList<>();
        ArrayList<Task> priorityQueue = new ArrayList<>();

        Task currentPTask = new Task(0, 0, 0);

        int currentCilinder = 1;
        int sumOfcilinders = 0;

        boolean activeDL = false;
        int indexOfShortestDL = 0;

        int Time = 0;

        while(!queue.isEmpty() || Time<=200){
            
            for(int i=0;i<Tasks.size();i++){
                if(Tasks.get(i).getArrivalTime() == Time){
                    ///System.out.println("Pojawil sie N " + Tasks.get(i).getCilinder());
                    queue.add(Tasks.get(i));
                }
            }

            //dodawanie real time tasków
            for(int i=0;i<RLTasks.size();i++){
                if(RLTasks.get(i).getArrivalTime() == Time){
                    //System.out.println("Pojawil sie R " + RLTasks.get(i).getCilinder() + " " +RLTasks.get(i).getDeadline());
                    priorityQueue.add(RLTasks.get(i));
                }
            }

            //usówanie tasków na które sie nie zdąrzy 
            for(int i=0;i<priorityQueue.size();i++){
                    
                if(priorityQueue.get(i).getDeadline() < Math.abs(priorityQueue.get(i).getCilinder() - currentCilinder)){
                    
                    //System.out.println("Rejected " + priorityQueue.get(i).getCilinder());
                    priorityQueue.remove(i);
                    i=0;

                }
            }

            if(!priorityQueue.isEmpty()){
                
                //wyszukiwanie taska z najkrótszym deadlinem 
                if(!activeDL){
                    for(int i=0;i<priorityQueue.size();i++){
                    
                        if(i==0){
                            currentPTask = priorityQueue.get(i);
                        }
                        else if(currentPTask.getDeadline() > priorityQueue.get(i).getDeadline()){
                            currentPTask = priorityQueue.get(i);
                        }

                        activeDL = true;
                    }
                    
                }
                     
                //prezsuwanie cylindra
                if(currentCilinder == currentPTask.getCilinder()){
                    priorityQueue.remove(currentPTask);
                    activeDL = false;
                }
                else if(currentCilinder < currentPTask.getCilinder()){
                    currentCilinder++;
                    sumOfcilinders++;
                }
                else if(currentCilinder > currentPTask.getCilinder()){
                    currentCilinder--;
                    sumOfcilinders++;
                }

                //usuwanie napotknych po dordzę Real Time Tasków
                for(int i=0;i<priorityQueue.size();i++){
                    if(priorityQueue.get(i).getCilinder() == currentCilinder){
                        priorityQueue.remove(i);
                    }
                }
                
                //usuwanie napotkanych zwykłych tasków
                for(int i=0;i<queue.size();i++){
                    if(queue.get(i).getCilinder() == currentCilinder){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }

                //System.out.println(currentCilinder + " R");

                //zmniejszanie deadlinu na wykonanie real time tasków
                for(int i=0;i<priorityQueue.size();i++){
                    
                    priorityQueue.get(i).setDeadline(priorityQueue.get(i).getDeadline() - 1);
                
                }

            }else{

                //usuwanie napotkanych tasków
                for(int i=0;i<queue.size();i++){
                    if(currentCilinder == queue.get(i).getCilinder()){
                        //System.out.println("Ukończono " + queue.get(i).getCilinder());
                        queue.remove(i);
                    }
                }

                if(!queue.isEmpty()){   
        
                    //System.out.println(currentCilinder + " N");

                    currentCilinder++;
                    sumOfcilinders++;

                    //zawrotka
                    if(currentCilinder == MAX+1){
                        currentCilinder = 1;
                    } 

                    
                }
            }
            Time++;
        }

        System.out.println("C_SCAN_FDS: " + sumOfcilinders);
    }

    
    public static void main(String[] args) throws Exception {
        
        ArrayList<Task> Tasks = new ArrayList<>();
        ArrayList<Task> Tasks1 = new ArrayList<>();
        ArrayList<Task> Tasks2 = new ArrayList<>();
        ArrayList<Task> Tasks3 = new ArrayList<>();
        ArrayList<Task> Tasks4 = new ArrayList<>();
        ArrayList<Task> Tasks5 = new ArrayList<>();
        ArrayList<Task> Tasks6 = new ArrayList<>();
        ArrayList<Task> Tasks7 = new ArrayList<>();

        AddTask(50, Tasks, Tasks1, Tasks2, Tasks3, Tasks4, Tasks5, Tasks6, Tasks7);

        ArrayList<Task> pTask = new ArrayList<>();
        ArrayList<Task> pTask1 = new ArrayList<>();
        ArrayList<Task> pTask2 = new ArrayList<>();
        ArrayList<Task> pTask3 = new ArrayList<>();
        ArrayList<Task> pTask4 = new ArrayList<>();
        ArrayList<Task> pTask5 = new ArrayList<>();
        ArrayList<Task> pTask6 = new ArrayList<>();
        ArrayList<Task> pTask7 = new ArrayList<>();

        AddRLTask(10, pTask, pTask1, pTask2, pTask3, pTask4, pTask5, pTask6, pTask7);

        /*for(int i=0;i<Tasks.size();i++){
            System.out.println("Arrival: " + Tasks.get(i).getArrivalTime() + " Cilinder: " + Tasks.get(i).getCilinder());
        }

        for(int i=0;i<pTask.size();i++){
            System.out.println("Arrival: " + pTask.get(i).getArrivalTime() + " Cilinder: " + pTask.get(i).getCilinder() + " Deadline: " + pTask.get(i).getDeadline());
        }*/

        FCFS_EDF(Tasks, pTask);    
        FCFS_FDS(Tasks1, pTask1);

        SSTF_EDF(Tasks2, pTask2);
        SSTF_FDS(Tasks3, pTask3);

        SCAN_EDF(Tasks4, pTask4);
        SCAN_FDS(Tasks5, pTask5);

        C_SCAN_EDF(Tasks6, pTask6);
        C_SCAN_FDS(Tasks7, pTask7);
    }
}
