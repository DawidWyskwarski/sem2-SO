import java.text.DecimalFormat;
import java.util.ArrayList;

public class App {
    
    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    public static void alg_1(ArrayList<Process> requests_main, int p, int z, ArrayList<Procesor> procesors){        
        
        ArrayList<Process> requests = new ArrayList<>(requests_main);
        int querry = 0;
        int migrations = 0;
        ArrayList<Double> avgArray = new ArrayList<>();
        double avg = 0;
        double sumOfLoad = 0;
        double dev = 0;
        int time = 0;
        boolean found = false;;

        while(!requests.isEmpty()){
            found = false;
            Process proces = requests.remove(0);
            Procesor currentCPU = procesors.get( (int) (Math.random() * procesors.size()) );

            for(int i=0;i<z && !found;i++){
                Procesor randomCPU = procesors.get( (int) (Math.random() * procesors.size()) );
                querry++;
                
                for(int j=0;j<procesors.size();j++){
                    procesors.get(j).updateLoadedProcesses();   
                }

                if(randomCPU.getCurrentLoad() < p && !randomCPU.equals(currentCPU)){
                    randomCPU.addProcess(proces);
                    migrations++;
                    found = true;
                }
                time++;

                if(time%5 == 0){
                    sumOfLoad=0;
                    for(int j=0;j<procesors.size();j++){
                        sumOfLoad+=procesors.get(j).getCurrentLoad();
                    }
                    avgArray.add( (double) (sumOfLoad / procesors.size()) );
                }
            }

            if(!found){
                if(currentCPU.getCurrentLoad() + proces.getNeededPower() <= 100){
                    currentCPU.addProcess(proces);
                }
                else{
                    requests.add(proces);
                }
            }
            time++;

            if(time%5 == 0){
                sumOfLoad=0;
                for(int j=0;j<procesors.size();j++){
                    sumOfLoad+=procesors.get(j).getCurrentLoad();
                }
                avgArray.add( (double) (sumOfLoad / procesors.size()) );
            }

            for(int j=0;j<procesors.size();j++){
                procesors.get(j).updateLoadedProcesses();   
            }
        }

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += avgArray.get(i);
        }
        avg = (double) ( sumOfLoad / avgArray.size() );

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += (double) Math.pow( (avgArray.get(i) - avg) , 2);
        }

        dev = (double) Math.sqrt( sumOfLoad / avgArray.size() );


        System.out.println("Algorytm 1:");
        System.out.println("Srednie obciążenie: " + df2.format(avg) + "%");
        System.out.println("Odchylenie: " + df2.format(dev) + "%");
        System.out.println("Zapytania: " + querry);
        System.out.println("Migracje: " + migrations + "\n");
        System.out.println("=======");
    }

    public static void alg_2(ArrayList<Process> requests_main, int p, ArrayList<Procesor> procesors){        
        
        ArrayList<Process> requests = new ArrayList<>(requests_main);
        int querry = 0;
        int migrations = 0;
        ArrayList<Double> avgArray = new ArrayList<>();
        double avg = 0;
        double sumOfLoad = 0;
        double dev = 0;
        int time = 0;
        boolean found = false;

        while(!requests.isEmpty()){
            found = false;
            Process proces = requests.remove(0);
            Procesor currentCPU = procesors.get( (int) (Math.random() * procesors.size()) );

            if(currentCPU.getCurrentLoad() < p){
                
                if(currentCPU.getCurrentLoad() + proces.getNeededPower() <= 100){
                    currentCPU.addProcess(proces);
                    found = true;
                }

            }else{
                
                while(!found ){

                    Procesor randomCPU = procesors.get( (int) (Math.random() * procesors.size()) );
                    querry++;
                    
                    for(int j=0;j<procesors.size();j++){
                        procesors.get(j).updateLoadedProcesses();   
                    }
    
                    if(randomCPU.getCurrentLoad() < p && !randomCPU.equals(currentCPU)){
                        randomCPU.addProcess(proces);
                        migrations++;
                        found = true;
                    }
                    time++;
    
                    if(time%5 == 0){
                        sumOfLoad=0;
                        for(int j=0;j<procesors.size();j++){
                            sumOfLoad+=procesors.get(j).getCurrentLoad();
                        }
                        avgArray.add( (double) (sumOfLoad / procesors.size()) );
                    }
                }                
            }

            if(!found){
                requests.add(proces);
            }

            time++;

            if(time%5 == 0){
                sumOfLoad=0;
                for(int j=0;j<procesors.size();j++){
                    sumOfLoad+=procesors.get(j).getCurrentLoad();
                }
                avgArray.add( (double) (sumOfLoad / procesors.size()) );
            }

            for(int j=0;j<procesors.size();j++){
                procesors.get(j).updateLoadedProcesses();   
            }
        }

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += avgArray.get(i);
        }
        avg = (double) ( sumOfLoad / avgArray.size() );

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += (double) Math.pow( (avgArray.get(i) - avg) , 2);
        }

        dev = (double) Math.sqrt( sumOfLoad / avgArray.size() );


        System.out.println("Algorytm 2:");
        System.out.println("Srednie obciążenie: " + df2.format(avg) + "%");
        System.out.println("Odchylenie: " + df2.format(dev) + "%");
        System.out.println("Zapytania: " + querry);
        System.out.println("Migracje: " + migrations + "\n");
        System.out.println("=======");
    }
    
    public static void alg_3(ArrayList<Process> requests_main, int p, int r, ArrayList<Procesor> procesors){        
        
        ArrayList<Process> requests = new ArrayList<>(requests_main);
        int querry = 0;
        int migrations = 0;
        ArrayList<Double> avgArray = new ArrayList<>();
        double avg = 0;
        double sumOfLoad = 0;
        double dev = 0;
        int time = 0;
        boolean found = false;

        while(!requests.isEmpty()){
            found = false;
            Process proces = requests.remove(0);
            Procesor currentCPU = procesors.get( (int) (Math.random() * procesors.size()) );

            if(currentCPU.getCurrentLoad() < p){
                
                if(currentCPU.getCurrentLoad() + proces.getNeededPower() <= 100){
                    currentCPU.addProcess(proces);
                    found = true;
                }

            }else{
                
                while(!found ){

                    Procesor randomCPU = procesors.get( (int) (Math.random() * procesors.size()) );
                    querry++;
                    
                    for(int j=0;j<procesors.size();j++){
                        procesors.get(j).updateLoadedProcesses();   
                    }
    
                    if(randomCPU.getCurrentLoad() < p && !randomCPU.equals(currentCPU)){
                        randomCPU.addProcess(proces);
                        migrations++;
                        found = true;
                    }
                    time++;
    
                    if(time%5 == 0){
                        sumOfLoad=0;
                        for(int j=0;j<procesors.size();j++){
                            sumOfLoad+=procesors.get(j).getCurrentLoad();
                        }
                        avgArray.add( (double) (sumOfLoad / procesors.size()) );
                    }
                }                
            }

            if(!found){
                requests.add(proces);
            }

            for(int i=0;i<procesors.size();i++){

                found = false;
                
                if(procesors.get(i).getCurrentLoad() < r){
                
                    for(int j=0;j<procesors.size() && !found;j++){
                        querry++;
                        if(j!=i && procesors.get(j).getCurrentLoad() > p){
                            migrations++;
                            procesors.get(j).addMulitple(procesors.get(i).removeProcess());
                            found = true;
                        }
                    }
                }
            }
            
            time++;

            if(time%5 == 0){
                sumOfLoad=0;
                for(int j=0;j<procesors.size();j++){
                    sumOfLoad+=procesors.get(j).getCurrentLoad();
                }
                avgArray.add( (double) (sumOfLoad / procesors.size()) );
            }

            for(int j=0;j<procesors.size();j++){
                procesors.get(j).updateLoadedProcesses();   
            }
        }

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += avgArray.get(i);
        }
        avg = (double) ( sumOfLoad / avgArray.size() );

        sumOfLoad = 0;
        for(int i=0;i<avgArray.size();i++){
            sumOfLoad += (double) Math.pow( (avgArray.get(i) - avg) , 2);
        }

        dev = (double) Math.sqrt( sumOfLoad / avgArray.size() );


        System.out.println("Algorytm 3:");
        System.out.println("Srednie obciążenie: " + df2.format(avg) + "%");
        System.out.println("Odchylenie: " + df2.format(dev) + "%");
        System.out.println("Zapytania: " + querry);
        System.out.println("Migracje: " + migrations + "\n");
        System.out.println("=======");

    }

    public static void main(String[] args) throws Exception {
        ArrayList<Procesor> procesors = new ArrayList<>();
        
        //liczba procesorów
        int N = 50;
        for(int i=0;i<N;i++){
            procesors.add(new Procesor(i));
        }

        ArrayList<Process> requests = new ArrayList<>();
        //request size
        int rs = 5000;

        for(int i=0;i<rs;i++){
            int time = (int) ((Math.random()* 80 ) + 300);
            int power = (int) ((Math.random()* 39 ) + 1);
            requests.add(new Process(power, time, time));
        }

        int p = 80;
        int z = 20;
        int r = 20;

        alg_1(requests,p,z,procesors);

        for(int i=0;i<requests.size();i++){
            requests.get(i).resetRemainingTime();
        }
        for(int i=0;i<procesors.size();i++){
            procesors.get(i).resetProcesor();
        }

        alg_2(requests,p, procesors);

        for(int i=0;i<requests.size();i++){
            requests.get(i).resetRemainingTime();
        }
        for(int i=0;i<procesors.size();i++){
            procesors.get(i).resetProcesor();
        }

        alg_3(requests, p, r, procesors);
    }
}
