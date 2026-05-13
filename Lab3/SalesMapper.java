import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesMapper extends
        Mapper<LongWritable, Text, CompositeKey, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] tokens = value.toString().trim().split(", ");
        if (tokens.length != 5) {
            return;
        }
        context.write(new CompositeKey(tokens[1], tokens[2]),
                new Text(tokens[2] + " " + tokens[0]));
    }
}
