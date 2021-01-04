import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;

public class factorialbrute {
    public static void main(String[] args) throws IOException {
        
        int target = 100000;
        int times = 10;
        boolean milestones = false;
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("factorials/" + target + ".txt")));

        long[] performance = new long[times];

        BigInteger factorial = new BigInteger("1");

        for(int j = 0; j < times; j++ ) {
            
            final long startTime = System.currentTimeMillis();

            factorial = new BigInteger("1");

            for(int i = 1; i <= target; i++) {
                factorial = factorial.multiply(new BigInteger("" + i));

                if(milestones && i % 10000 == 0) {
                    System.out.println(i);
                }
            }

            final long endTime = System.currentTimeMillis();

            System.out.println("Attempt " + (j+1) + "; Total execution time: " + (endTime - startTime));

            performance[j] = endTime - startTime;
        }

        out.println(factorial);

        out.close();


        PrintWriter performanceout = new PrintWriter(new BufferedWriter(new FileWriter("factorials/" + target + "performance.txt")));
        long average = 0;
        for(long time : performance) {
            average += time;
        }
        average /= times;

        performanceout.println(Arrays.toString(performance));
        performanceout.println(average);
        performanceout.close();
    }
}