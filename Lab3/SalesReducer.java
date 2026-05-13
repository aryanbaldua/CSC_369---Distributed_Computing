import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesReducer
        extends Reducer<CompositeKey, Text, Text, Text> {

    @Override
    protected void reduce(CompositeKey key, Iterable<Text> values,
            Context context) throws IOException, InterruptedException {
        StringBuilder result = new StringBuilder();
        for (Text value : values) {
            result.append(value.toString()).append(", ");
        }
        String joined = result.length() > 0
                ? result.substring(0, result.length() - 2)
                : "";
        context.write(new Text(key.getDate()), new Text(joined));
    }
}
