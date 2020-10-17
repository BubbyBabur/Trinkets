import java.math.BigInteger;

public class App {
    public static void main(String[] args) throws Exception {
        EulerMachine e = new EulerMachine();

        // Answering the question in the video
        BigInteger a = e.getPartition(666);
        System.out.println(a);
    }
}
