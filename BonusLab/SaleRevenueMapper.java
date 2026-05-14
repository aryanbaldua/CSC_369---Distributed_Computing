import java.io.*;
import java.net.URI;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SaleRevenueMapper extends
        Mapper<LongWritable, Text, Text, DoubleWritable> {

    private HashMap<Integer, Double> productPrice = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] caches = context.getCacheFiles();
        for (URI uri : caches) {
            String path = uri.getFragment() != null ? uri.getFragment() : new File(uri.getPath()).getName();
            if (!path.equals("products.txt")) continue;
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(", ");
                if (t.length != 3) continue;
                productPrice.put(Integer.parseInt(t[0]), Double.parseDouble(t[2]));
            }
            br.close();
        }
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] t = value.toString().trim().split(", ");
        if (t.length != 4) return;
        int saleId = Integer.parseInt(t[1]);
        int productId = Integer.parseInt(t[2]);
        int qty = Integer.parseInt(t[3]);
        Double price = productPrice.get(productId);
        if (price == null) return;
        context.write(new Text(String.valueOf(saleId)),
                new DoubleWritable(qty * price));
    }
}
