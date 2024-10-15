public class Process {
    
    private int index;
    private double waitingTime;
    private double timeToComplete;
    private double timeOfAppearing;

    public Process(int index, double TimeToComplete, double timeOfAppearing){
        this.index = index;
        this.waitingTime = 0;
        this.timeToComplete = TimeToComplete;
        this.timeOfAppearing = timeOfAppearing;
    }

    public int getIndex() {
        return index;
    }

    public double getTimeToComplete() {
        return timeToComplete;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public double getTimeOfAppearing() {
        return timeOfAppearing;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTimeToComplete(double timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTimeOfAppearing(double timeOfAppearing) {
        this.timeOfAppearing = timeOfAppearing;
    }

    public void ProcessState(){
        System.out.println( "Process:  " + getIndex() +  
                            "  Time to complete  " + getTimeToComplete() +
                            "  Waiting Time:  " + getWaitingTime() + 
                            "  Time of Arrival:  " + getTimeOfAppearing()
        );
    }
}
