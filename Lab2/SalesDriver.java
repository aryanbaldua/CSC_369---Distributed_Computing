import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class SalesDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Sales Count");

        job.setJarByClass(SalesDriver.class);
        job.setMapperClass(SalesMapper.class);
        job.setCombinerClass(SalesCombiner.class);
        job.setReducerClass(SalesReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        long t0 = System.currentTimeMillis();
        boolean ok = job.waitForCompletion(true);
        System.out.println("Hadoop elapsed: " + (System.currentTimeMillis() - t0) + " ms");
        System.exit(ok ? 0 : 1);
    }
}
