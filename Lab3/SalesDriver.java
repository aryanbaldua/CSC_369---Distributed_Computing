import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class SalesDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator", " ");

        Job job = Job.getInstance(conf, "Sales By Date With Times");

        job.setJarByClass(SalesDriver.class);
        job.setMapperClass(SalesMapper.class);
        job.setReducerClass(SalesReducer.class);
        job.setNumReduceTasks(1);

        job.setSortComparatorClass(CompositeKeyComparator.class);
        job.setGroupingComparatorClass(NaturalKeyGroupingComparator.class);

        job.setMapOutputKeyClass(CompositeKey.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        long t0 = System.currentTimeMillis();
        boolean ok = job.waitForCompletion(true);
        long elapsed = System.currentTimeMillis() - t0;
        System.out.println("Hadoop elapsed: " + elapsed + " ms");

        System.exit(ok ? 0 : 1);
    }
}
