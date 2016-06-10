package io;

import graph.VertexVal;

import java.io.IOException;

import org.apache.giraph.io.VertexOutputFormat;
import org.apache.giraph.io.VertexWriter;
import org.apache.giraph.io.formats.GiraphTextOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class OutputShortestPath extends VertexOutputFormat<IntWritable, VertexVal, NullWritable>{

	private GiraphTextOutputFormat textOutputFormat;
	
	@Override
	public void checkOutputSpecs(JobContext job) throws IOException,
			InterruptedException {
		textOutputFormat.checkOutputSpecs(job);
	}

	@Override
	public VertexWriter<IntWritable, VertexVal, NullWritable> createVertexWriter(
			TaskAttemptContext arg0) throws IOException, InterruptedException {
		//TODO create vertex writer after implementing class
		return null;
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext arg0)
			throws IOException, InterruptedException {
		
		return textOutputFormat.getOutputCommitter(arg0);
	}
	

}
