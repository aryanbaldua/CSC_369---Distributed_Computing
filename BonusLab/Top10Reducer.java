import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class Top10Reducer extends
        Reducer<MonthRevenueKey, Text, Text, Text> {

    private static final int N = 10;

    @Override
    public void reduce(MonthRevenueKey key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Text v : values) {
            if (count >= N) break;
            String[] t = v.toString().split("\\|");
            if (t.length != 3) continue;
            long rev = Math.round(Double.parseDouble(t[2]));
            if (count > 0) sb.append(", ");
            sb.append("(").append(t[0]).append(", ").append(t[1]).append(", $").append(rev).append(")");
            count++;
        }
        context.write(new Text(key.getMonth()), new Text(sb.toString()));
    }
}
