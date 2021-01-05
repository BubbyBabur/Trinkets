import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class factorialbrute {
    public static void main(String[] args) throws IOException {
        
        int target = 100000;
        boolean milestones = false;
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./data/out.txt")));

        BigInteger factorial = new BigInteger("1");
            
        final long startTime = System.currentTimeMillis();

        for(int i = 1; i <= target; i++) {
            factorial = factorial.multiply(new BigInteger("" + i));

            if(milestones && i % 10000 == 0) {
                System.out.println(i);
            }
        }

        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));

        // long performance = endTime - startTime;

        out.println(factorial);
        // out.println(performance);

        out.close();
    }
}