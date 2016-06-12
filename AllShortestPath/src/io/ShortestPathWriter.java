package io;

import graph.VertexVal;

import java.io.IOException;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.VertexWriter;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class ShortestPathWriter extends VertexWriter<IntWritable, VertexVal, NullWritable>{

	private RecordWriter<Text, Text> lineRecordWriter;
	private TaskAttemptContext task;
	private FileOutputFormat<Text, Text> textOutputFormat;
	
	public ShortestPathWriter(FileOutputFormat<Text, Text> textOutputFormat)
	{
		this.textOutputFormat = textOutputFormat;
	}
	@Override
	public void writeVertex(Vertex<IntWritable, VertexVal, NullWritable> vert)
			throws IOException, InterruptedException {
		
		Integer i = vert.getId().get();
		int arr[] = vert.getValue().getval();
		
		String line = i.toString();
		line+="\t";
		for(int v = 0;v<arr.length;v++)
		{
			line+=Integer.toString(arr[v]);
			line+="\t";
		}
		lineRecordWriter.write(new Text(line), null);
	}

	@Override
	public void close(TaskAttemptContext arg0) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		lineRecordWriter.close(arg0);
	}

	@Override
	public void initialize(TaskAttemptContext arg0) throws IOException,
			InterruptedException {
		
		this.task = arg0;
		lineRecordWriter = textOutputFormat.getRecordWriter(task);
		
	}

}
