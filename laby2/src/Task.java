public class Task {
    
    private int cilinder;
    private int deadline;
    private int arrivalTime;

    public Task(int cilinder, int arrivalTime){
        this.cilinder = cilinder;
        this.arrivalTime = arrivalTime;
    }

    public Task(int cilinder, int arrivalTime, int deadline){
        this.arrivalTime = arrivalTime;
        this.cilinder = cilinder;
        this.deadline = deadline;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getCilinder() {
        return cilinder;
    }
    public int getDeadline() {
        return deadline;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setCilinder(int cilinder) {
        this.cilinder = cilinder;
    }
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

}
