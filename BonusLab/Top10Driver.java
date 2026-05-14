import java.net.URI;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class Top10Driver {
    public static Job build(Configuration conf, String inputPath, String outputPath,
                             String storesHdfsPath) throws Exception {
        conf.set("mapreduce.output.textoutputformat.separator", ", ");
        Job job = Job.getInstance(conf, "Bonus Job3 Top10PerMonth");
        job.setJarByClass(Top10Driver.class);
        job.setMapperClass(Top10Mapper.class);
        job.setReducerClass(Top10Reducer.class);
        job.setNumReduceTasks(1);

        job.setSortComparatorClass(MonthRevenueComparator.class);
        job.setGroupingComparatorClass(MonthGroupingComparator.class);

        job.setMapOutputKeyClass(MonthRevenueKey.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.addCacheFile(new URI(storesHdfsPath + "#stores.txt"));
        return job;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = build(conf, args[0], args[1], args[2]);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
