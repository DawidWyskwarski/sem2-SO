import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Proces {
    
    private int procesNumber;
    private int usingPages;
    private int assignedFrames;
    private ArrayList<Page> frames;
    private ArrayList<Page> requests;
    private double pageFaults;
    private double timeWindow;
    private double pageFaultCounter;
    private int wss;
    private int deltaT;

    public Proces(int ProcesNumber,int UsingPages){
        this.procesNumber = ProcesNumber;
        this.usingPages = UsingPages;
        this.assignedFrames = 0;
        this.requests = new ArrayList<>();
        this.frames = new ArrayList<>();
        this.pageFaults = 0;
        this.timeWindow = 3;  // Przykładowa wartość okna czasowego
        this.pageFaultCounter = 0;
        this.wss = 0;
        this.deltaT = 2000;
    }

    public void resetProcess(){
        assignedFrames = 0;
        requests.clear();
        frames.clear();
        pageFaults = 0;
        pageFaultCounter = 0;
    }

    public int getProcesNumber() {
        return procesNumber;
    }
    public int getUsingPages() {
        return usingPages;
    }
    public int getAssignedFrames() {
        return assignedFrames;
    }

    public void setProcesNumber(int procesNumber) {
        this.procesNumber = procesNumber;
    }
    public void setUsingPages(int usingPages) {
        this.usingPages = usingPages;
    }
    public void setAssignedFrames(int assignedFrames) {
        this.assignedFrames = assignedFrames;
    }
    public void setTimeWindow(int timeWindow) {
        this.timeWindow = timeWindow;
    }
    
    public void resetPageFaults(){
        this.pageFaults = 0;
    }

    public void addRequest(Page page){
        requests.add(page);
    }
    public void removeFormFrames(int index){
        frames.remove(index);
    }
    
    public double getPageFaults() {
        return pageFaults;
    }

    public void incrementPageFaults() {
        this.pageFaults++;
    }

    public double getPageFaultCounter() {
        return pageFaultCounter;
    }

    public void resetPageFaultCounter() {
        this.pageFaultCounter = 0;
    }

    public void incrementPageFaultCounter() {
        this.pageFaultCounter++;
    }

    public double getTimeWindow() {
        return timeWindow;
    }
    
    public int getWSS() {
        return wss;
    }

    public void updateFrames(){
        while(frames.size() > assignedFrames){
            frames.remove(frames.size()-1);
        }
    }

    public void updateWSS(int currentTime) {
        int startIndex = Math.max(0, requests.size() - (int) deltaT);
        int uniquePages = 0;
        ArrayList<Integer> seenPages = new ArrayList<>();
        for (int i = startIndex; i < requests.size(); i++) {
            int pageNumber = requests.get(i).getPageNumber();
            if (!seenPages.contains(pageNumber)) {
                seenPages.add(pageNumber);
                uniquePages++;
            }
        }
        this.wss = uniquePages;
    }

    public int LRU(Page page){

        incrementPageFaultCounter();
        // Jeśli jest wolna ramka, dodaj stronę bez błędu strony
        if(frames.size() < assignedFrames){
            frames.add(page);
            return 0;
        }
    
        // Sprawdź, czy strona już jest w ramkach
        for(int i=0;i<frames.size();i++){
            if(frames.get(i).getPageNumber() == page.getPageNumber()){
                // Przenieś stronę na koniec listy ramki (aktualizacja LRU)
                frames.remove(i);
                frames.add(page);
                return 0;
            }
        }
    
        // Jeżeli ramki są pełne, znajdź stronę do zastąpienia
        int min = toRemove(page);
    
        // Usuń najdawniej używaną stronę i dodaj nową stronę
        frames.remove(min);
        frames.add(page);
        
        incrementPageFaults();

        return 1;  // Wystąpił błąd strony
    }
    
    public int toRemove(Page page){
        int min = 0;
        for(int i=1;i<frames.size();i++){
            if(lastUsedIndex(frames.get(i)) < lastUsedIndex(frames.get(min))){
                min = i;
            }
        }

        return min;
    }

    // Metoda pomocnicza do znalezienia ostatniego użycia strony
    private int lastUsedIndex(Page page) {
        for(int i=requests.size()-1; i>=0; i--){
            if(requests.get(i).getPageNumber() == page.getPageNumber()){
                return i;
            }
        }
        return -1; // Ta sytuacja nie powinna się wydarzyć, jeśli strona była już w ramkach
    }
    
}
