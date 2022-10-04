import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DLeftHashing {

    int numEntries;
    int numSegments;
    HashRecord[] hashTable;
    List<Integer> hashes;
    int segSize;

    public DLeftHashing(int numEntries, int numSegments){
        this.numEntries = numEntries;
        this.numSegments = numSegments;
        hashTable = new HashRecord[numEntries];
        this.segSize = numEntries/ this.numSegments;
        getHashes();
    }

    public boolean receive(int flowId){
        int hash;
        for (int i=0; i<numSegments; i++){
            hash = hashes.get(i);
            int hashVal = flowId ^ hash;
            int hashCode = String.valueOf(hashVal).hashCode();
            if(hashCode<0) hashCode = hashCode*-1;
            int pos = (hashCode % segSize)+(i* segSize);
            if(null != hashTable[pos] && hashTable[pos].flowId ==flowId){

                hashTable[pos].counter++;
                return true;
            }
        }

        return insert(flowId);
    }

    public boolean insert(int flowId){
        int hash;
        for (int i=0; i<numSegments; i++){
            hash = hashes.get(i);
            int hashVal = flowId ^ hash;
            int hashCode = String.valueOf(hashVal).hashCode();
            if(hashCode<0) hashCode = hashCode*-1;
            int pos = (hashCode % segSize)+(i* segSize);
            if(null==hashTable[pos]){

                hashTable[pos] = new HashRecord(flowId);
                return true;
            }
        }

        return false;
    }

    public static List<Integer> getFlows(int numFlows){
        List<Integer> flows = new ArrayList<>();
        int flowId;
        for (int i=0; i<numFlows; i++){
            flowId = getRandom();
            flows.add(flowId);
        }
        return flows;
    }

    public void getHashes(){
        hashes = new ArrayList<>();
        int hash = getRandom();
        for (int i = 0; i< numSegments; i++){
            while (hashes.contains(hash)){
                hash = getRandom();
            }
            hashes.add(hash);
        }
    }

    private static int getRandom(){
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE - 1);
    }

    public void receiveFlows(List<Integer> flows) throws IOException {
        int recordedFlows = 0;
        for (int flowId: flows){
            if(receive(flowId)) recordedFlows++;
        }

        File fout = new File("DLeftHashing.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("Number of hits = "+ recordedFlows);
        bw.newLine();
        bw.write("Number of misses = "+ (flows.size()-recordedFlows));
        bw.newLine();
        bw.write("\nHash Table");
        bw.newLine();

        for (HashRecord entry: hashTable){
            int output = entry == null ? 0 : entry.flowId;
            bw.write(Integer.toString(output));
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        List<Integer> flowList = getFlows(1000);
        DLeftHashing dLeftHashing = new DLeftHashing(1000, 4);
        dLeftHashing.receiveFlows(flowList);

        if(args.length == 3) {
            try {
                flowList = getFlows(Integer.valueOf(args[1]));
                dLeftHashing = new DLeftHashing(Integer.valueOf(args[0]),Integer.valueOf(args[2]));
                dLeftHashing.receiveFlows(flowList);
            } catch(NumberFormatException nfe) {
                System.out.println("Invalid Command Line Arguments");
            }
        }
    }
}
