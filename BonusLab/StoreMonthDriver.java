import java.net.URI;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class StoreMonthDriver {
    public static Job build(Configuration conf, String inputPath, String outputPath,
                             String saleRevenueHdfsPath) throws Exception {
        Job job = Job.getInstance(conf, "Bonus Job2 StoreMonthRevenue");
        job.setJarByClass(StoreMonthDriver.class);
        job.setMapperClass(StoreMonthMapper.class);
        job.setCombinerClass(StoreMonthReducer.class);
        job.setReducerClass(StoreMonthReducer.class);
        job.setNumReduceTasks(1);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.addCacheFile(new URI(saleRevenueHdfsPath + "#saleRevenue.txt"));
        return job;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = build(conf, args[0], args[1], args[2]);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
