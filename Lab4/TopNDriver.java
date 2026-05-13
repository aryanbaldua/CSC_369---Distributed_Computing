import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class TopNDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInt("N", 10);
        conf.set("mapreduce.output.textoutputformat.separator", "");

        Job job = Job.getInstance(conf, "Top N Products");

        job.setJarByClass(TopNDriver.class);
        job.setMapperClass(TopNMapper.class);
        job.setReducerClass(TopNReducer.class);
        job.setNumReduceTasks(1);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        long t0 = System.currentTimeMillis();
        boolean ok = job.waitForCompletion(true);
        System.out.println("Hadoop elapsed: " + (System.currentTimeMillis() - t0) + " ms");
        System.exit(ok ? 0 : 1);
    }
}
