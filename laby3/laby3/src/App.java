import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class App {
    
    static void FIFO(int[] requests, int size){

        int pageFaluts = 0;
        Queue<Integer> frames = new LinkedList<Integer>();
        int occupiedFrames = 0;
        boolean found = false;


        for(int i=0;i<requests.length;i++){

            found = false;

            if(frames.contains(requests[i])){
                found = true;
            }

            if(occupiedFrames < size && !found){
                pageFaluts++;
                occupiedFrames++;
                frames.add(requests[i]);
            }
            else if(!found){
                frames.poll();
                frames.add(requests[i]);
                pageFaluts++;
            }

        }

        System.out.println("FIFO: " + pageFaluts);
    }

    static void Random(int[] requests, int size){
        int pageFaluts = 0;
        int[] frames = new int[size];
        int occupiedFrames = 0;
        boolean found = false;


        for(int i=0;i<requests.length;i++){

            found = false;
            
            for(int j=0;j<frames.length;j++){
                if(frames[j] == requests[i]){
                    found = true;
                }
            }

            if(occupiedFrames < size && !found){
                pageFaluts++;
                frames[occupiedFrames] = requests[i];
                occupiedFrames++;
            }
            else if(!found){
                pageFaluts++;
                int rand = (int) (Math.random() * (size-1));

                frames[rand] = requests[i];
            }
            
        }

        System.out.println("Random: " + pageFaluts);
    }

    static void OPT(int[] requests, int size){
        
        int pageFaluts = 0;
        int occupiedFrames = 0;
        boolean found = false;
        
        int[] frames = new int[size];
        int[] nearFuture = new int[size];
        for(int j =0;j<size;j++){
            nearFuture[j] = Integer.MAX_VALUE;
        }

        for(int i=0;i<requests.length;i++){

            found = false;

            for(int j=0;j<frames.length;j++){
                if(frames[j] == requests[i]){
                    found = true;
                }
            }

            if(occupiedFrames < size && !found){
                pageFaluts++;
                frames[occupiedFrames] = requests[i];
                occupiedFrames++;
            }
            else if(!found){
                pageFaluts++;
                
                int max = 0;
                for(int j=i+1;j<requests.length;j++){
                    for(int k=0;k<size;k++){
                        if(requests[j] == frames[k] && nearFuture[k] == Integer.MAX_VALUE){
                            nearFuture[k] = j;
                        }
                    }
                }
                
                for(int j=0;j<size;j++){
                    if(nearFuture[j] > nearFuture[max]){
                        max = j;
                    }
                }
            
                frames[max] = requests[i];
                for(int j =0;j<size;j++){
                    nearFuture[j] = Integer.MAX_VALUE;
                }
            }
        }

        System.out.println("OPT: " + pageFaluts);
    }

    static void LRU(int[] requests, int size){
        
        int pageFaluts = 0;
        int occupiedFrames = 0;
        boolean found = false;
        
        int[] frames = new int[size];
        int[] nearFuture = new int[size];
        for(int j =0;j<size;j++){
            nearFuture[j] = 0;
        }

        for(int i=0;i<requests.length;i++){

            found = false;

            for(int j=0;j<frames.length;j++){
                if(frames[j] == requests[i]){
                    found = true;
                }
            }

            if(occupiedFrames < size && !found){
                pageFaluts++;
                frames[occupiedFrames] = requests[i];
                occupiedFrames++;
            }
            else if(!found){
                pageFaluts++;
                
                int min = 0;
                for(int j=i-1;j>=0;j--){
                    for(int k=0;k<size;k++){
                        if(requests[j] == frames[k] && nearFuture[k] == 0){
                            nearFuture[k] = j;
                        }
                    }
                }
                
                for(int j=1;j<size;j++){
                    if(nearFuture[j] < nearFuture[min]){
                        min = j;
                    }
                }
            
                frames[min] = requests[i];
                for(int j =0;j<size;j++){
                    nearFuture[j] = 0;
                }
            }
        }

        System.out.println("LRU: " + pageFaluts);
    }

    static void LRU_a(int[] requests, int size){

        ArrayList<Integer> frames = new ArrayList<>();
        ArrayList<Integer> secondChance = new ArrayList<>();

        int pageFaluts = 0;
        int occupiedFrames = 0;
        boolean found = false;
        boolean found0 = false;

        for(int i=0;i<requests.length;i++){

            found = false;

            if(frames.contains(requests[i])){
                secondChance.set(frames.indexOf(requests[i]), 1);
                
                found = true;
            }

            if(occupiedFrames < size && !found){
                frames.add(requests[i]);
                secondChance.add(1);

                pageFaluts++;
                occupiedFrames++;
            }
            else if(!found){
                pageFaluts++;
                found0 = false;

                while(!found0){

                    if(secondChance.get(0) == 1){
                        secondChance.remove(0);
                        frames.add(frames.remove(0));
                        secondChance.add(0);

                    }
                    else{
                        found0 = true;
                        frames.remove(0);
                        secondChance.remove(0);

                        frames.add(requests[i]);
                        secondChance.add(1);
                    }

                }

            }

        }

        System.out.println("LRU_a: " + pageFaluts);
    }

    static int[] fill(int size, int groups){

        int[] tab = new int[(int) ((size/groups) * 0.5)];
        
        int[] requests = new int[size];

        //System.out.println(size/groups);

        for(int i=0;i<size;i += (size/groups)){ 
            
            for(int k=0;k < tab.length;k++){
                tab[k] = (int) ((Math.random()*200));
            }

            for(int j=i;j< i + (size/groups) && j<size ;j++){    
                
                requests[j] = tab[(int) (Math.random()*(tab.length-1))];
            }
        }
        
        return requests;
    }
    

    public static void main(String[] args) throws Exception {
        //int[] requests = {1, 2, 3, 4, 1, 2, 5, 3, 2, 1, 4, 5};
        //int[] requests1 = {3,2,1,3,4,1,6,2,4,3,4,2,1,4,5,2,1,3,4};
        int frames = 20;
        int[] requests = fill(2000,16);


        FIFO(requests, frames);
        OPT(requests, frames);
        LRU(requests, frames);
        LRU_a(requests, frames);
        Random(requests, frames);
    }
}
