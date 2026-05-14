import org.apache.hadoop.io.*;

public class MonthRevenueComparator extends WritableComparator {
    protected MonthRevenueComparator() {
        super(MonthRevenueKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        MonthRevenueKey k1 = (MonthRevenueKey) w1;
        MonthRevenueKey k2 = (MonthRevenueKey) w2;
        return k1.compareTo(k2);
    }
}
