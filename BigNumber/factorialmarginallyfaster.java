
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class factorialmarginallyfaster {

    public static int[] primes(int a) {
        ArrayList<Integer> primes = new ArrayList<>();

        boolean[] ok = new boolean[a+1];
        for(int i = 0; i <= a; i++) {
            ok[i] = true;
        }

        for(int i = 2; i <= a; i++) {
            if(ok[i]) {
                primes.add(i);

                int position = i;
                while(position < ok.length) {
                    ok[position] = false;
                    position += i;
                }
            }
        }

        int[] finalarr = new int[primes.size()];
        for(int i = 0; i < primes.size(); i++) {
            finalarr[i] = primes.get(i);
        }
        return finalarr;
    }


    public static void main(String[] args) throws IOException {

        int target = 100000;
        int times = 10;
        boolean milestones = false;

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("factorials/" + target + ".txt")));

        long[] performance = new long[times];

        // BigInteger factorial = new BigInteger("1");

        for(int i = 0; i < times; i++) {

            final long startTime = System.currentTimeMillis();

            int[] primes = primes(target);

            BigInteger factorial = new BigInteger("1");

            int milestone = 1;

            for(int prime : primes) {
                int number = 0;

                long curr = prime;
                while(curr < target) {
                    number += target / (int) curr;
                    curr *= prime;
                }

                BigInteger currpower = BigInteger.valueOf((long) prime);
                currpower = currpower.pow(number);

                factorial = factorial.multiply(currpower);

                if(milestones && prime > milestone) {
                    milestone *= 10;
                    System.out.println(prime);
                }

            }

            final long endTime = System.currentTimeMillis();
            System.out.println("Attempt " + (i + 1) + "; Total execution time: " + (endTime - startTime));

            performance[i] = endTime - startTime;
        }
        // out.println(factorial);
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
