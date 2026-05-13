import java.io.*;
import java.util.*;

public class calculateSales {

    public static void main(String[] args) throws IOException {
        String inPath = "../Lab1/sales.txt";
        String outPath = "javaoutput.txt";

        long t0 = System.nanoTime();

        TreeMap<String, ArrayList<String>> byDate = new TreeMap<>();

        BufferedReader br = new BufferedReader(new FileReader(inPath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(", ");
            if (p.length != 5) continue;
            byDate.computeIfAbsent(p[1], k -> new ArrayList<>())
                  .add(p[2] + " " + p[0]);
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath));
        for (Map.Entry<String, ArrayList<String>> e : byDate.entrySet()) {
            Collections.sort(e.getValue());
            bw.write(e.getKey() + " " + String.join(", ", e.getValue()));
            bw.newLine();
        }
        bw.close();

        long elapsedMs = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Java elapsed: " + elapsedMs + " ms");
    }
}
