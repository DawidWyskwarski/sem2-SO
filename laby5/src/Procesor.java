import java.util.ArrayList;

public class Procesor {
    
    private int id;
    private ArrayList<Process> load;
    private int currentLoad; 

    public Procesor(int id){
        this.id = id;
        this.load = new ArrayList<>();
        this.currentLoad = 0;
    }

    public int getId() {
        return id;
    }

    public void resetProcesor(){
        load.clear();
        currentLoad = 0;
    }

    public void addProcess(Process p){
        currentLoad += p.getNeededPower();
        load.add(p);
    }

    public void addMulitple(ArrayList<Process> p){

        for(int i=0;i<p.size();i++){
            Process pro = p.remove(0);
            addProcess(pro); //WOOOOOO
        }
    }

    public ArrayList<Process> removeProcess(){
        ArrayList<Process> list = new ArrayList<>();

        for(int i=0;i<load.size()/3;i++){
            Process p = load.remove(load.size() - 1);
            currentLoad -= p.getNeededPower();
            list.add(p);
        }

        return list;
    }

    public void updateLoadedProcesses(){

        for(int i=0;i<load.size();i++){
            Process p = load.get(i);
            
            p.decrementRemainingTime();

            if(p.getRemainingTime() == 0){
                currentLoad -= p.getNeededPower();
                load.remove(p);
                i--;
            }
        }
    } 

    public int getCurrentLoad() {
        return currentLoad;
    }
}
