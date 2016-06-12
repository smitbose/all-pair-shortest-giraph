package io;

import graph.VertexVal;

import java.io.IOException;

import org.apache.giraph.io.VertexOutputFormat;
import org.apache.giraph.io.VertexWriter;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class OutputShortestPath extends VertexOutputFormat<IntWritable, VertexVal, NullWritable>{

	private FileOutputFormat<Text, Text> textOutputFormat;
	
	@Override
	public void checkOutputSpecs(JobContext job) throws IOException,
			InterruptedException {
		textOutputFormat.checkOutputSpecs(job);
	}

	@Override
	public VertexWriter<IntWritable, VertexVal, NullWritable> createVertexWriter(
			TaskAttemptContext arg0) throws IOException, InterruptedException {
		
		return new ShortestPathWriter(textOutputFormat);
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0)
			throws IOException, InterruptedException {
		
		return textOutputFormat.getOutputCommitter(arg0);
	}
	

}
