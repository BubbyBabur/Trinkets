import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class test {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("javasupremacy.txt")));
        BigInteger a = new BigInteger("256");
        a = a.pow(3);
        a = a.pow(512);
        a = a.pow(512);

        out.println(a);

        String b = a + "";
        System.out.println(b.length());

        out.close();
    }
}