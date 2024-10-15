import java.util.ArrayList;

public class App {
    
    public static void Equal(Proces[] proceses, ArrayList<Page> requests,int allFrames){

        int framesPerProces = allFrames/proceses.length;
        for(int i=0;i<proceses.length;i++){
            proceses[i].setAssignedFrames(framesPerProces);
        }

        int pageFaults = 0;

        for(int i=0;i<requests.size();i++){
            Page current = requests.get(i);
            pageFaults += proceses[current.getProcesNumber()].LRU(current);
            proceses[current.getProcesNumber()].addRequest(current);
        }

        System.out.println("Equal: " + pageFaults);

    }

    public static void Proportional(Proces[] proceses, ArrayList<Page> requests,int allFrames){

        int sumOfPages = 0;
        int pageFaults = 0;

        for(int i=0;i<proceses.length;i++){
            sumOfPages += proceses[i].getUsingPages();
        }
        for(int i=0;i<proceses.length;i++){
            proceses[i].setAssignedFrames( (int) (( allFrames * proceses[i].getUsingPages() )/sumOfPages) );
        }

        for(int i=0;i<requests.size();i++){
            Page current = requests.get(i);
            pageFaults += proceses[current.getProcesNumber()].LRU(current);
            proceses[current.getProcesNumber()].addRequest(current);
        }

        System.out.println("Proportional: " + pageFaults);
    }
    
    public static void PageFaultFrequencyControl(Proces[] proceses, ArrayList<Page> requests, int allFrames, double l, double u) {
        int sumOfPages = 0;
        int pageFaults = 0;
        int freeFrames = 0;

        for(int i=0;i<proceses.length;i++){
            sumOfPages += proceses[i].getUsingPages();
        }
        for(int i=0;i<proceses.length;i++){
            proceses[i].setAssignedFrames((int) ((allFrames * proceses[i].getUsingPages())/sumOfPages));
        }

        for(int i=0;i<requests.size();i++){
            Page current = requests.get(i);
            Proces currentProcess = proceses[current.getProcesNumber()];

            pageFaults += currentProcess.LRU(current);
            currentProcess.addRequest(current);

            // Dynamiczne przydzielanie ramek na podstawie PPF
            if (currentProcess.getPageFaultCounter() >= currentProcess.getTimeWindow()) {
                double PPF = (double) currentProcess.getPageFaults() / currentProcess.getTimeWindow();
                
                if (PPF > u) {
                    if (freeFrames > 0) {
                        currentProcess.setAssignedFrames(currentProcess.getAssignedFrames() + 1);
                        freeFrames--;
                    }
                } else if (PPF < l) {
                    if (currentProcess.getAssignedFrames() > 1) {
                        currentProcess.removeFormFrames(currentProcess.toRemove(current));
                        currentProcess.setAssignedFrames(currentProcess.getAssignedFrames() - 1);
                        freeFrames++;
                    }
                }

                currentProcess.resetPageFaultCounter();
                currentProcess.resetPageFaults();
            }
        }

        System.out.println("PageFaultFrequencyControl: " + pageFaults);
    }

    public static void zoneModelAlgorithm(Proces[] proceses, ArrayList<Page> requests, int allFrames, int deltaT) {
        int sumOfPages = 0;
        int pageFaults = 0;

        // Początkowy przydział proporcjonalny
        for (Proces proces : proceses) {
            sumOfPages += proces.getUsingPages();
        }
        for (Proces proces : proceses) {
            proces.setAssignedFrames((allFrames * proces.getUsingPages()) / sumOfPages);
        }

        // Symulacja
        for (int i = 0; i < requests.size(); i++) {
            Page current = requests.get(i);
            Proces currentProcess = proceses[current.getProcesNumber()];

            pageFaults += currentProcess.LRU(current);
            currentProcess.addRequest(current);

            if ((i+1) % (deltaT / 2) == 0) {

                for (Proces proces : proceses) {
                    proces.updateWSS(i+1);
                }

                // Sprawdzenie, czy liczba wymaganych ramek przekracza dostępne
                int totalWSS = 0;
                for (Proces proces : proceses) {
                    totalWSS += proces.getWSS();
                    
                }

                if (totalWSS <= allFrames) {
                    for (Proces proces : proceses) {
                        proces.setAssignedFrames(proces.getWSS());
                        proces.updateFrames();
                    }
                } else {
                    int maxWSSIndex = 0;
                    for (int j = 1; j < proceses.length; j++) {
                        if (proceses[j].getWSS() > proceses[maxWSSIndex].getWSS()) {
                            maxWSSIndex = j;
                        }
                    }

                    proceses[maxWSSIndex].setAssignedFrames(proceses[maxWSSIndex].getWSS());

                    sumOfPages = 0;
                    for (int j = 0; j < proceses.length; j++) {
                        if (j != maxWSSIndex) {
                            sumOfPages += proceses[j].getUsingPages();
                        }
                    }
                    for (int j = 0; j < proceses.length; j++) {
                        if (j != maxWSSIndex) {
                            proceses[j].setAssignedFrames((allFrames * proceses[j].getUsingPages()) / sumOfPages);
                        }
                    }
                }
            }
        }
        System.out.println("ZoneModel: " + pageFaults);
    }

    public static ArrayList<Page> fill(Proces[] porceses, int requestsPerProces, int groups){

        ArrayList<Page> allRequests = new ArrayList<>();
        ArrayList<Page>[] reque = new ArrayList[porceses.length];
    
        for (int i = 0; i < reque.length; i++) {
            reque[i] = new ArrayList<>();
        }
    
        for(int i=0;i<porceses.length;i++){
            int numberOfPages = (int) ((Math.random()*2000) + 500);
            porceses[i].setUsingPages(numberOfPages);
    
            int[] tab = new int[(int) ((requestsPerProces/groups) * 0.5)];
    
            for(int j=0;j<requestsPerProces;j += (requestsPerProces/groups)){
    
                for(int k=0;k<tab.length;k++){
                    tab[k] = (int) (Math.random()*numberOfPages);
                }
    
                for(int k=j;k< j + (requestsPerProces/groups) && k <requestsPerProces;k++){
                    reque[i].add(new Page(tab[(int) (Math.random()*(tab.length-1))], i));   
                }
            }
        }
    
        while(true) {
            // Sprawdź, czy wszystkie listy są puste
            boolean allEmpty = true;
            for (ArrayList<Page> list : reque) {
                if (!list.isEmpty()) {
                    allEmpty = false;
                    break;
                }
            }
            // Jeśli wszystkie listy są puste, przerwij pętlę
            if (allEmpty) {
                break;
            }
    
            int index;
            do {
                index = (int) (Math.random() * porceses.length);
            } while (reque[index].isEmpty());
    
            allRequests.add(reque[index].remove(0));
        }
    
        return allRequests;
    }

    public static void reset(Proces[] proceses){
        for(int i=0;i<proceses.length;i++){
            proceses[i].resetProcess();
        }
    }

    public static void main(String[] args) throws Exception {
        Proces[] porceses = new Proces[10];
        for(int i=0;i<porceses.length;i++){
            porceses[i] = new Proces(i, 0);
        }
        ArrayList<Page> requests = fill(porceses, 1000, 16);

        Equal(porceses, requests, 1000);
        reset(porceses);
        
        Proportional(porceses, requests, 1000);
        reset(porceses);

        PageFaultFrequencyControl(porceses, requests, 1000, 0.1, 0.3);
        reset(porceses);

        zoneModelAlgorithm(porceses,requests,1000,2000);
    }
}
