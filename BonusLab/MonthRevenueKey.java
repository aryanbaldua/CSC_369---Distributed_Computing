import java.io.*;
import org.apache.hadoop.io.*;

public class MonthRevenueKey implements WritableComparable<MonthRevenueKey> {
    private final Text month = new Text();
    private double revenue;
    private int storeId;

    public MonthRevenueKey() {}

    public MonthRevenueKey(String month, double revenue, int storeId) {
        this.month.set(month);
        this.revenue = revenue;
        this.storeId = storeId;
    }

    public String getMonth() { return month.toString(); }
    public double getRevenue() { return revenue; }
    public int getStoreId() { return storeId; }

    @Override
    public void write(DataOutput out) throws IOException {
        month.write(out);
        out.writeDouble(revenue);
        out.writeInt(storeId);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        month.readFields(in);
        revenue = in.readDouble();
        storeId = in.readInt();
    }

    @Override
    public int compareTo(MonthRevenueKey other) {
        int c = month.compareTo(other.month);
        if (c != 0) return c;
        c = Double.compare(other.revenue, this.revenue);
        if (c != 0) return c;
        return Integer.compare(this.storeId, other.storeId);
    }
}
