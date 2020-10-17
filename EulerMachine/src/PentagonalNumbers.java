import java.util.ArrayList;

public class PentagonalNumbers {
    PentagonalNumbers() { }

    int[] generate(int max) {
        ArrayList<Integer> seq = new ArrayList<Integer>();

        int curr = 1;
        int index = 0;

        int singlecounter = 1;
        int oddcounter = 3;

        while(curr <= max) {
            seq.add( curr );
            if(index % 2 == 0) {
                curr += singlecounter++;
            } else {
                curr += oddcounter;
                oddcounter += 2;
            }
            index ++;
        }

        return seq.stream().mapToInt(i -> i).toArray();
    }
}
