import org.apache.hadoop.io.*;

public class MonthGroupingComparator extends WritableComparator {
    protected MonthGroupingComparator() {
        super(MonthRevenueKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        MonthRevenueKey k1 = (MonthRevenueKey) w1;
        MonthRevenueKey k2 = (MonthRevenueKey) w2;
        return k1.getMonth().compareTo(k2.getMonth());
    }
}
