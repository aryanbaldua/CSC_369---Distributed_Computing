import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class TopNMapper extends
        Mapper<LongWritable, Text, NullWritable, Text> {

    public static final int DEFAULT_N = 10;
    private int n = DEFAULT_N;
    private TreeSet<Record> top = new TreeSet<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.n = context.getConfiguration().getInt("N", DEFAULT_N);
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String[] tokens = value.toString().trim().split(", ");
        if (tokens.length != 3) return;
        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        double price = Double.parseDouble(tokens[2]);
        top.add(new Record(id, name, price));
        if (top.size() > n) {
            top.remove(top.last());
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Record r : top) {
            context.write(NullWritable.get(), new Text(r.toString()));
        }
    }
}
