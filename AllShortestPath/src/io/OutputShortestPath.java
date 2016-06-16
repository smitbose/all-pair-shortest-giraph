package io;

import graph.VertexVal;

import java.io.IOException;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.VertexWriter;
import org.apache.giraph.io.formats.TextVertexOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class OutputShortestPath extends TextVertexOutputFormat<IntWritable, VertexVal, NullWritable>{

	@Override
	public TextVertexOutputFormat<IntWritable, VertexVal, NullWritable>.TextVertexWriter createVertexWriter(
			TaskAttemptContext arg0) throws IOException, InterruptedException {
		
		return new ShortestPathWriter();
	}

	public class ShortestPathWriter extends TextVertexWriter
	{

		@Override
		public void writeVertex(
				Vertex<IntWritable, VertexVal, NullWritable> vert)
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
			getRecordWriter().write(new Text(line), null);
			
			
		}
		
		
		
	}
	

}
