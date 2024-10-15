public class Page {
    
    private int pageNumber;
    private int procesNumber;

    public Page(int pageNumber,int procesNumber){
        this.pageNumber = pageNumber;
        this.procesNumber = procesNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    public int getProcesNumber() {
        return procesNumber;
    }

    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void setProcesNumber(int procesNumber) {
        this.procesNumber = procesNumber;
    }
}
