package core;

import java.io.IOException;

import graph.VertexVal;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

public class MyComputation extends BasicComputation<IntWritable, VertexVal, NullWritable, NullWritable>{

	@Override
	public void compute(Vertex<IntWritable, VertexVal, NullWritable> arg0,
			Iterable<NullWritable> arg1) throws IOException {
		
		
	}

}
