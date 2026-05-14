import java.io.*;
import java.util.*;

public class calculateBonus {

    private static final int N = 10;

    static class StoreInfo {
        String name;
        String city;
        StoreInfo(String name, String city) { this.name = name; this.city = city; }
    }

    static class StoreRevenue {
        int storeId;
        String name;
        String city;
        double revenue;
        StoreRevenue(int storeId, String name, String city, double revenue) {
            this.storeId = storeId;
            this.name = name;
            this.city = city;
            this.revenue = revenue;
        }
    }

    public static void main(String[] args) throws IOException {
        String base = "../Lab1";
        String outPath = "javaoutput.txt";

        long t0 = System.nanoTime();

        HashMap<Integer, Double> productPrice = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(base + "/products.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] t = line.split(", ");
            if (t.length != 3) continue;
            productPrice.put(Integer.parseInt(t[0]), Double.parseDouble(t[2]));
        }
        br.close();

        HashMap<Integer, StoreInfo> stores = new HashMap<>();
        br = new BufferedReader(new FileReader(base + "/stores.txt"));
        while ((line = br.readLine()) != null) {
            String[] t = line.split(", ");
            if (t.length < 4) continue;
            stores.put(Integer.parseInt(t[0]), new StoreInfo(t[1], t[3]));
        }
        br.close();

        HashMap<Integer, int[]> saleInfo = new HashMap<>();
        HashMap<Integer, String> saleMonth = new HashMap<>();
        br = new BufferedReader(new FileReader(base + "/sales.txt"));
        while ((line = br.readLine()) != null) {
            String[] t = line.split(", ");
            if (t.length != 5) continue;
            int saleId = Integer.parseInt(t[0]);
            int storeId = Integer.parseInt(t[3]);
            String month = t[1].substring(0, 7).replace('/', '-');
            saleInfo.put(saleId, new int[]{storeId});
            saleMonth.put(saleId, month);
        }
        br.close();

        HashMap<String, Double> agg = new HashMap<>();
        br = new BufferedReader(new FileReader(base + "/lineItems.txt"));
        while ((line = br.readLine()) != null) {
            String[] t = line.split(", ");
            if (t.length != 4) continue;
            int saleId = Integer.parseInt(t[1]);
            int productId = Integer.parseInt(t[2]);
            int qty = Integer.parseInt(t[3]);
            Double price = productPrice.get(productId);
            int[] sale = saleInfo.get(saleId);
            String month = saleMonth.get(saleId);
            if (price == null || sale == null || month == null) continue;
            double rev = qty * price;
            String key = sale[0] + "|" + month;
            agg.merge(key, rev, Double::sum);
        }
        br.close();

        TreeMap<String, List<StoreRevenue>> byMonth = new TreeMap<>();
        for (Map.Entry<String, Double> e : agg.entrySet()) {
            String[] sk = e.getKey().split("\\|");
            int storeId = Integer.parseInt(sk[0]);
            String month = sk[1];
            StoreInfo si = stores.get(storeId);
            if (si == null) continue;
            byMonth.computeIfAbsent(month, k -> new ArrayList<>())
                   .add(new StoreRevenue(storeId, si.name, si.city, e.getValue()));
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath));
        for (Map.Entry<String, List<StoreRevenue>> e : byMonth.entrySet()) {
            List<StoreRevenue> list = e.getValue();
            list.sort((a, b) -> {
                int c = Double.compare(b.revenue, a.revenue);
                if (c != 0) return c;
                return Integer.compare(a.storeId, b.storeId);
            });
            StringBuilder sb = new StringBuilder();
            sb.append(e.getKey());
            int n = Math.min(N, list.size());
            for (int i = 0; i < n; i++) {
                StoreRevenue sr = list.get(i);
                long rev = Math.round(sr.revenue);
                sb.append(", (").append(sr.name).append(", ").append(sr.city).append(", $").append(rev).append(")");
            }
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();

        System.out.println("Java elapsed: " + (System.nanoTime() - t0) / 1_000_000 + " ms");
    }
}
