import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiHashing {
    int numEntries;
    int numHashes;
    HashRecord[] hashTable;
    List<Integer> hashes;

    public MultiHashing(int numEntries, int numHashes){
        this.numEntries = numEntries;
        this.numHashes = numHashes;
        hashTable = new HashRecord[numEntries];
        getHashes();
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
        for (int i=0; i<numHashes; i++){
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

    public void receive(List<Integer> flows) throws IOException {
        int recordedFlows = 0;
        int flowId;
        int hash;

        for (int flow : flows) {
            flowId = flow;
            int j;
            for (j = 0; j < hashes.size(); j++) {
                hash = hashes.get(j);
                int hashVal = flowId ^ hash;
                int hashCode = String.valueOf(hashVal).hashCode();
                if(hashCode<0) hashCode = hashCode*-1;
                int pos = hashCode % numEntries;
                if (null == hashTable[pos]) {
                    hashTable[pos] = new HashRecord(flowId);
                    recordedFlows++;
                    break;
                } else if (hashTable[pos].flowId == flowId) {
                    hashTable[pos].counter += 1;
                    break;
                }
            }
        }

        File fout = new File("MultiHashing.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("Number of hits = "+ recordedFlows);
        bw.newLine();
        bw.write("Number of misses = "+ (flows.size()-recordedFlows));
        bw.newLine();
        bw.write("\nHash Table:");

        bw.newLine();

        for (HashRecord record: hashTable){
            int values = record == null ? 0 : record.flowId;
            bw.write(Integer.toString(values));
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        List<Integer> flowList = getFlows(1000);
        MultiHashing multiHashing = new MultiHashing(1000, 3);
        multiHashing.receive(flowList);

        if(args.length == 3) {
            try {
                flowList = getFlows(Integer.valueOf(args[1]));
                multiHashing = new MultiHashing(Integer.valueOf(args[0]),Integer.valueOf(args[2]));
                multiHashing.receive(flowList);
            } catch(NumberFormatException nfe) {
                System.out.println("Invalid Command Line Arguments");
            }
        }
    }
}
