import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class TopNReducer extends
        Reducer<NullWritable, Text, NullWritable, Text> {

    private int n = TopNMapper.DEFAULT_N;
    private TreeSet<Record> top = new TreeSet<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.n = context.getConfiguration().getInt("N", TopNMapper.DEFAULT_N);
    }

    @Override
    public void reduce(NullWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        for (Text value : values) {
            String[] tokens = value.toString().trim().split(", ");
            if (tokens.length != 3) continue;
            int id = Integer.parseInt(tokens[0]);
            String name = tokens[1];
            double price = Double.parseDouble(tokens[2]);
            top.add(new Record(id, name, price));
            if (top.size() > n) {
                top.remove(top.last());
            }
        }
        for (Record r : top) {
            context.write(NullWritable.get(), new Text(r.toString()));
        }
    }
}
