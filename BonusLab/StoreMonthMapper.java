import java.io.*;
import java.net.URI;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class StoreMonthMapper extends
        Mapper<LongWritable, Text, Text, DoubleWritable> {

    private HashMap<Integer, Double> saleRevenue = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] caches = context.getCacheFiles();
        for (URI uri : caches) {
            String path = uri.getFragment() != null ? uri.getFragment() : new File(uri.getPath()).getName();
            if (!path.equals("saleRevenue.txt")) continue;
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\t");
                if (t.length != 2) continue;
                saleRevenue.put(Integer.parseInt(t[0]), Double.parseDouble(t[1]));
            }
            br.close();
        }
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] t = value.toString().trim().split(", ");
        if (t.length != 5) return;
        int saleId = Integer.parseInt(t[0]);
        String date = t[1];
        int storeId = Integer.parseInt(t[3]);
        Double rev = saleRevenue.get(saleId);
        if (rev == null) return;
        String month = date.substring(0, 7).replace('/', '-');
        context.write(new Text(storeId + "|" + month),
                new DoubleWritable(rev));
    }
}
