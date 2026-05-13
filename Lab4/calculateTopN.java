import java.io.*;
import java.util.*;

public class calculateTopN {

    private static final int N = 10;

    public static void main(String[] args) throws IOException {
        String inPath = "../Lab1/products.txt";
        String outPath = "javaoutput.txt";

        long t0 = System.nanoTime();

        TreeSet<Record> top = new TreeSet<>();

        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.trim().split(", ");
            if (tokens.length != 3) continue;
            int id = Integer.parseInt(tokens[0]);
            String name = tokens[1];
            double price = Double.parseDouble(tokens[2]);
            top.add(new Record(id, name, price));
            if (top.size() > N) {
                top.remove(top.last());
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath));
        for (Record r : top) {
            bw.write(r.toString());
            bw.newLine();
        }
        bw.close();

        System.out.println("Java elapsed: " + (System.nanoTime() - t0) / 1_000_000 + " ms");
    }
}
