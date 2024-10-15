public class Process {
    
    private int neededPower;
    private int timeToComplete;
    private int remainingTime;

    public Process(int neededPower,int timeToComplete, int remainingTime){
        this.neededPower = neededPower;
        this.timeToComplete = timeToComplete;
        this.remainingTime = remainingTime;
    }

    public int getNeededPower() {
        return neededPower;
    }
    public int getRemainingTime() {
        return remainingTime;
    }

    public void decrementRemainingTime(){
        this.remainingTime--;
    }
    public void resetRemainingTime(){
        this.remainingTime = timeToComplete;
    }

}
