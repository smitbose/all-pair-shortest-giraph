package core;

import java.io.IOException;

import graph.VertexVal;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

public class MyComputation extends BasicComputation<IntWritable, VertexVal, NullWritable, NullWritable>{

	/**
	 * Array to hold the shortest path for the current vertex
	 */
	private int i_[];
	/**
	 * Array to hold the shortest distances from the vertex currently
	 * considered intermediate
	 */
	private int k_[];
	/**
	 * Variables holding information about the current vertex, the current
	 * intermediate vertex and the number of vertices respectively
	 */
	private int i,k,V;
	@Override
	public void compute(Vertex<IntWritable, VertexVal, NullWritable> vert,
			Iterable<NullWritable> mess) throws IOException {		
		
		int V = (int) getTotalNumVertices();
		int s = (int) getSuperstep();
		k = s-1; //setting the intermediate vertex for this super step
		i_ = k_ = null;
		i = vert.getId().get(); //storing the current vertex id
		
		if(s == 0)
			setup();
		else
			compute();
		
		if(i == s) //setting this vertex to be treated as intermediate for the next super step
			aggregate(MyAggregator.ID,vert.getValue());
		if(V == s) //computation goes on V times after which it terminates
			vert.voteToHalt();
		
	}
	private void compute() {
		// TODO Auto-generated method stub
		
	}
	private void setup() {
		// TODO Auto-generated method stub
		
	}

}
