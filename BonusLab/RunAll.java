import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapreduce.*;

public class RunAll {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: RunAll <bonusBaseHdfsDir>");
            System.err.println("  Expects: <base>/input/{sales.txt,lineItems.txt,products.txt,stores.txt}");
            System.err.println("  Writes:  <base>/job1, <base>/job2, <base>/output");
            System.exit(2);
        }
        String base = args[0];
        String input = base + "/input";
        String job1Out = base + "/job1";
        String job2Out = base + "/job2";
        String finalOut = base + "/output";
        String productsHdfs = input + "/products.txt";
        String storesHdfs = input + "/stores.txt";
        String saleRevenueHdfs = job1Out + "/part-r-00000";

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(job1Out), true);
        fs.delete(new Path(job2Out), true);
        fs.delete(new Path(finalOut), true);

        long total = 0;

        long t1 = System.currentTimeMillis();
        Job job1 = SaleRevenueDriver.build(new Configuration(),
                input + "/lineItems.txt", job1Out, productsHdfs);
        if (!job1.waitForCompletion(true)) System.exit(1);
        long e1 = System.currentTimeMillis() - t1;
        System.out.println("Job1 elapsed: " + e1 + " ms");
        total += e1;

        long t2 = System.currentTimeMillis();
        Job job2 = StoreMonthDriver.build(new Configuration(),
                input + "/sales.txt", job2Out, saleRevenueHdfs);
        if (!job2.waitForCompletion(true)) System.exit(1);
        long e2 = System.currentTimeMillis() - t2;
        System.out.println("Job2 elapsed: " + e2 + " ms");
        total += e2;

        long t3 = System.currentTimeMillis();
        Job job3 = Top10Driver.build(new Configuration(),
                job2Out, finalOut, storesHdfs);
        if (!job3.waitForCompletion(true)) System.exit(1);
        long e3 = System.currentTimeMillis() - t3;
        System.out.println("Job3 elapsed: " + e3 + " ms");
        total += e3;

        System.out.println("Hadoop elapsed: " + total + " ms");
    }
}
