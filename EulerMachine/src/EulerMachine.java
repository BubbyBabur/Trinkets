import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class EulerMachine {
    private PentagonalNumbers s;
    
    public EulerMachine() {
        s = new PentagonalNumbers();
    };

    private BigInteger[] generateWithInc(int tofind, int increment) {

        int[] template = s.generate(tofind);
        
        BigInteger[] partitions = new BigInteger[tofind+1];
        partitions[0] = new BigInteger("1");

        for(int i = 1; i < tofind+1; i++){
            BigInteger currpartition = new BigInteger( "0" );

            for (int j = 0; j < template.length; j++) {
                if(template[j] > i) {
                    break;
                }
                
                int pos = (j % 4 >= 2) ? -1 : 1;
                currpartition = currpartition.add( new BigInteger( Integer.toString(pos)).multiply(partitions[i - template[j]]) );
            }

            partitions[i] = currpartition;

            partitions[0] = partitions[0].add(new BigInteger(Integer.toString(increment)));
        }

        return partitions;

    }

    public BigInteger[] getPartitionsArray(int max) {
        return this.generateWithInc(max, 0);
    }

    public BigInteger[] getSumsOfFactorsArray(int max) {
        return this.generateWithInc(max, 1);
    }

    private boolean[] primebools(int max) {
        boolean[] b = new boolean[max + 1];
        BigInteger[] sums = this.getSumsOfFactorsArray(max);
        b[0] = false;
        for (int i = 1; i < max + 1; i++) {
            b[i] = sums[i].equals(new BigInteger(Integer.toString(i + 1)));
        }
        return b;
    }

    public boolean[] perfectBools(int max) {
        boolean[] b = new boolean[max + 1];
        BigInteger[] sums = this.getSumsOfFactorsArray(max);
        b[0] = false;
        for (int i = 1; i < max + 1; i++) {
            b[i] = sums[i].equals(new BigInteger(Integer.toString(i * 2)));
        }
        return b;
    }

    public String getPartitionsString(int max) {
        return Arrays.toString(this.getPartitionsArray(max));
    }

    public String getSumsOfFactorsString(int max) {
        return Arrays.toString(this.getSumsOfFactorsArray(max));
    }

    public BigInteger getPartition(int tofind) {
        return this.getPartitionsArray(tofind)[tofind];
    }

    public BigInteger getSumOfFactors(int p) {
        return this.getSumsOfFactorsArray(p)[p];
    }

    public boolean isPrime(int p){
        return this.primebools(p)[p];
    }

    public boolean isPerfect(int p){
        return this.perfectBools(p)[p];
    }

    public int[] getPrimesArray(int max) {
        ArrayList<Integer> p = new ArrayList<Integer>();
        boolean[] pbools = this.primebools(max);
        for(int i = 0; i < pbools.length; i++){
            if(pbools[i]){
                p.add(i);
            }
        }
        return p.stream().mapToInt(i -> i).toArray();
    }

    public String getPrimesString(int max){
        return Arrays.toString(this.getPrimesArray(max));
    }

    public int[] getPerfectArray(int max) {
        ArrayList<Integer> p = new ArrayList<Integer>();
        boolean[] pbools = this.perfectBools(max);
        for (int i = 0; i < pbools.length; i++) {
            if (pbools[i]) {
                p.add(i);
            }
        }
        return p.stream().mapToInt(i -> i).toArray();
    }

    public String getPerfectString(int max) {
        return Arrays.toString(this.getPerfectArray(max));
    }

}
