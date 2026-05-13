import java.io.*;
import java.util.*;

public class calculateSales {

    public static List<Map.Entry<String, Integer>> getSalesByDate(String filePath) throws IOException {
        HashMap<String, Integer> salesMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(", ");
            String date = parts[1];
            salesMap.put(date, salesMap.getOrDefault(date, 0) + 1);
        }
        br.close();

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(salesMap.entrySet());
        sorted.sort(Map.Entry.comparingByKey());
        return sorted;
    }

    public static void main(String[] args) throws IOException {
        String path = "../Lab1/sales.txt";

        long t0 = System.nanoTime();
        List<Map.Entry<String, Integer>> result = getSalesByDate(path);

        BufferedWriter bw = new BufferedWriter(new FileWriter("javaoutput.txt"));
        for (Map.Entry<String, Integer> entry : result) {
            bw.write(entry.getKey() + "\t" + entry.getValue());
            bw.newLine();
        }
        bw.close();
        System.out.println("Java elapsed: " + (System.nanoTime() - t0) / 1_000_000 + " ms");
    }
}
