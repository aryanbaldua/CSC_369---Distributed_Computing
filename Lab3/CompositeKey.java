import java.io.*;
import org.apache.hadoop.io.*;

public class CompositeKey implements WritableComparable<CompositeKey> {
    private final Text date = new Text();
    private final Text time = new Text();

    public CompositeKey() {}

    public CompositeKey(String date, String time) {
        this.date.set(date);
        this.time.set(time);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        date.write(out);
        time.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        date.readFields(in);
        time.readFields(in);
    }

    @Override
    public int compareTo(CompositeKey other) {
        int result = date.compareTo(other.date);
        if (result == 0) {
            result = time.compareTo(other.time);
        }
        return result;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTime() {
        return time.toString();
    }
}
