import java.util.ArrayList;

public class App {
    
    static void AddProcess(int num, ArrayList<Process> Processes,ArrayList<Process> Processes1,ArrayList<Process> Processes2, ArrayList<Process> Processes3){

        int ArrivalTimeR;
        int CompletionTimeR;

        for(int i=0;i<num/4;i++){
            ArrivalTimeR = (int)(Math.random()*25);
            CompletionTimeR = (int)((Math.random()*10)+6);

            Processes.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes1.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes2.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes3.add(new Process(i, CompletionTimeR, ArrivalTimeR));
        }

        for(int i=num/4;i<num;i++){
            ArrivalTimeR = (int)(Math.random()*25);
            CompletionTimeR = (int)((Math.random()*5)+1);

            Processes.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes1.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes2.add(new Process(i, CompletionTimeR, ArrivalTimeR));
            Processes3.add(new Process(i, CompletionTimeR, ArrivalTimeR));
        }


    }

    static void FCFS(ArrayList<Process> Processes){
        
        ArrayList<Process> queue = new ArrayList<>();

        Double Time = 0.0;
        double overallWaiting = 0;
        double maxWaiting = 0;

        //FCFS
        while(Time <= 25 || !queue.isEmpty()){
            
            for(int i=0;i<Processes.size();i++){
                if(Time==Processes.get(i).getTimeOfAppearing()){
                    queue.add(Processes.get(i));
                }
            }
            
            for(int i=0;i<queue.size();i++){
                if(i==0){
                    queue.get(i).setTimeToComplete(queue.get(i).getTimeToComplete() - 1);
                }
                else{
                    queue.get(i).setWaitingTime(queue.get(i).getWaitingTime() + 1);
                }
            }

            if(!queue.isEmpty() && queue.get(0).getTimeToComplete()==0){
                //System.out.println("Proces " + queue.get(0).getIndex() + " ukończony");
                overallWaiting+= queue.get(0).getWaitingTime();    
                
                if(queue.get(0).getWaitingTime() > maxWaiting){
                    maxWaiting = queue.get(0).getWaitingTime();
                }
                
                queue.remove(0);
            }

            Time++;

        }

        System.out.println("FCFS: avgwaitingTime " + overallWaiting/Processes.size());
        System.out.println("FCFS: maxWaiting " + maxWaiting);
    }

    static void SJF(ArrayList<Process> Processes){
        
        ArrayList<Process> queue = new ArrayList<>();

        Double Time = 0.0;
        double overallWaiting = 0;
        double maxWaiting = 0;
        boolean active = false;
        int indexMin = 0;

        //SJF
        while(Time <= 25 || !queue.isEmpty()){
            
            for(int i=0;i<Processes.size();i++){
                if(Time==Processes.get(i).getTimeOfAppearing()){
                    queue.add(Processes.get(i));
                }
            }
            
            if(!active){
                for(int i=0;i<queue.size();i++){
                    if(i==0){
                        indexMin = 0;
                    }
                    else if(queue.get(indexMin).getTimeToComplete() > queue.get(i).getTimeToComplete()){
                        indexMin = i;
                    }
                }

                active = true;
            }
            

            for(int i=0;i<queue.size();i++){
                if(i==indexMin){
                    queue.get(i).setTimeToComplete(queue.get(i).getTimeToComplete() - 1);
                }
                else{
                    queue.get(i).setWaitingTime(queue.get(i).getWaitingTime() + 1);
                }
            }
                
            

            if(!queue.isEmpty() && queue.get(indexMin).getTimeToComplete()==0){
                active = false;

                //System.out.println("Proces " + queue.get(indexMin).getIndex() + " ukończony");
                overallWaiting+= queue.get(indexMin).getWaitingTime(); 

                if(queue.get(indexMin).getWaitingTime() > maxWaiting){
                    maxWaiting = queue.get(indexMin).getWaitingTime();
                }
                
                queue.remove(indexMin);

            }            

            Time++;

        }

        System.out.println("SJF: avgwaitingTime " + overallWaiting/Processes.size());
        System.out.println("SJF: maxWaiting " + maxWaiting);
    } 

    static void SJFW(ArrayList<Process> Processes){
        
        ArrayList<Process> queue = new ArrayList<>();

        Double Time = 0.0;
        double overallWaiting = 0;
        double maxWaiting = 0;
        int indexMin = 0;

        //SJF
        while(Time <= 25 || !queue.isEmpty()){
            
            for(int i=0;i<Processes.size();i++){
                if(Time==Processes.get(i).getTimeOfAppearing()){
                    queue.add(Processes.get(i));
                }
            }
            
            for(int i=0;i<queue.size();i++){
                if(i==0){
                    indexMin = 0;
                }
                else if(queue.get(indexMin).getTimeToComplete() > queue.get(i).getTimeToComplete()){
                    indexMin = i;
                }
            }


            for(int i=0;i<queue.size();i++){
                if(i==indexMin){
                    queue.get(i).setTimeToComplete(queue.get(i).getTimeToComplete() - 1);
                }
                else{
                    queue.get(i).setWaitingTime(queue.get(i).getWaitingTime() + 1);
                }
            }
                
            

            if(!queue.isEmpty() && queue.get(indexMin).getTimeToComplete()==0){

                //System.out.println("Proces " + queue.get(indexMin).getIndex() + " ukończony");
                overallWaiting+= queue.get(indexMin).getWaitingTime(); 

                if(queue.get(indexMin).getWaitingTime() > maxWaiting){
                    maxWaiting = queue.get(indexMin).getWaitingTime();
                }
                
                queue.remove(indexMin);

            }            

            Time++;

        }

        System.out.println("SJFw: avgwaitingTime " + overallWaiting/Processes.size());
        System.out.println("SJFw: maxWaiting " + maxWaiting);
    }

    static void RR(ArrayList<Process> Processes, double quant){
        
        ArrayList<Process> queue = new ArrayList<>();

        Double Time = 0.0;
        double processingTime = 0;
        double overallWaiting = 0;
        double maxWaiting = 0;
        

        //RR
        while(Time <= 25 || !queue.isEmpty()){
            
            for(int i=0;i<Processes.size();i++){
                if(Time==Processes.get(i).getTimeOfAppearing()){
                    queue.add(Processes.get(i));
                }
            }
            

            if(processingTime == quant && !queue.isEmpty()){
                
                Process tmp = queue.remove(0);
                
                queue.add(tmp);
                processingTime = 0;

            }
            
            if (processingTime < quant && !queue.isEmpty()){

                for(int i=0;i<queue.size();i++){
                    if(i==0){
                        queue.get(i).setTimeToComplete(queue.get(i).getTimeToComplete() - 1);
                        processingTime++;

                        if(queue.get(i).getTimeToComplete() == 0){
                            
                            //System.out.println("Proces " + queue.get(i).getIndex() + " ukończony");
                            overallWaiting+= queue.get(i).getWaitingTime();
                            processingTime = 0;

                            if(queue.get(i).getWaitingTime() > maxWaiting){
                                maxWaiting = queue.get(i).getWaitingTime();
                            }
                            
                            queue.remove(i);
                            
                            if(!queue.isEmpty()){
                                queue.get(0).setWaitingTime(queue.get(0).getWaitingTime() + 1);
                            }
                        }
                    }
                    else{
                        queue.get(i).setWaitingTime(queue.get(i).getWaitingTime() + 1);
                    }

                }
                
            }
            
            Time++;

        }

        System.out.println("RR: avgwaitingTime " + overallWaiting/Processes.size());
        System.out.println("RR: maxWaiting " + maxWaiting);
    }
    
    public static void main(String[] args) throws Exception {
        
        ArrayList<Process> Processes = new ArrayList<>();
        ArrayList<Process> Processes1 = new ArrayList<>();
        ArrayList<Process> Processes2 = new ArrayList<>();
        ArrayList<Process> Processes3 = new ArrayList<>();
        AddProcess(50,Processes,Processes1,Processes2,Processes3);

        FCFS(Processes);

        SJF(Processes1);

        SJFW(Processes2);

        RR(Processes3, 5);
    }
}
