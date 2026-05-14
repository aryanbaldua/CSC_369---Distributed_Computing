import java.io.*;
import java.net.URI;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class Top10Mapper extends
        Mapper<LongWritable, Text, MonthRevenueKey, Text> {

    private HashMap<Integer, String[]> storeInfo = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] caches = context.getCacheFiles();
        for (URI uri : caches) {
            String path = uri.getFragment() != null ? uri.getFragment() : new File(uri.getPath()).getName();
            if (!path.equals("stores.txt")) continue;
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(", ");
                if (t.length < 4) continue;
                storeInfo.put(Integer.parseInt(t[0]), new String[]{t[1], t[3]});
            }
            br.close();
        }
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] t = value.toString().trim().split("\t");
        if (t.length != 2) return;
        String[] sk = t[0].split("\\|");
        if (sk.length != 2) return;
        int storeId = Integer.parseInt(sk[0]);
        String month = sk[1];
        double revenue = Double.parseDouble(t[1]);
        String[] info = storeInfo.get(storeId);
        if (info == null) return;
        context.write(new MonthRevenueKey(month, revenue, storeId),
                new Text(info[0] + "|" + info[1] + "|" + revenue));
    }
}
