package io;

import graph.VertexVal;

import java.io.IOException;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.formats.TextVertexOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class OutputCentrality extends TextVertexOutputFormat<IntWritable, VertexVal, NullWritable>{

	@Override
	public TextVertexOutputFormat<IntWritable, VertexVal, NullWritable>.TextVertexWriter createVertexWriter(
			TaskAttemptContext arg0) throws IOException, InterruptedException {
		
		return new CentralityWriter();
	}

	public class CentralityWriter extends TextVertexWriter
	{

		@Override
		public void writeVertex(
				Vertex<IntWritable, VertexVal, NullWritable> vert)
				throws IOException, InterruptedException {
			
			Integer i = vert.getId().get();
			int arr[] = vert.getValue().getval();
			
			String line = i.toString();
			line+="\t";
			double centrality = 0;
			for(int v = 0;v<arr.length;v++)
			{
				if(arr[v] != Integer.MIN_VALUE)
				{
					centrality += (1.0/(double)arr[v]);
				}
			}
			line += Double.toString(centrality);
			getRecordWriter().write(new Text(line), null);
			
			
		}
		
		
		
	}
	

}
