package core;

import graph.VertexVal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.giraph.edge.Edge;
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
		
		V = (int) getTotalNumVertices();
		int s = (int) getSuperstep();
		k = s-1; //setting the intermediate vertex for this super step
		i_ = k_ = null;
		i = vert.getId().get(); //storing the current vertex id
		
		if(s == 0)
			setup(vert); //initialize shortest path distances for the current vertex
		else
			compute(vert); //perform local computation in current supersteps
		
		if(i == s) //setting this vertex to be treated as intermediate for the next super step
			aggregate(MyAggregator.ID,vert.getValue());
		if(V == s) //computation goes on V times after which it terminates
			vert.voteToHalt();
		
	}
	
	/**
	 * Method to perform computation at each super step except the 0th
	 * @param vert
	 * @throws IOException
	 */
	private void compute(Vertex<IntWritable, VertexVal, NullWritable> vert) 
			throws IOException {
		
		i_ = vert.getValue().getval(); //get the shortest path array
		if(i_[k]!=Integer.MIN_VALUE) //checking if a path exists to the intermediate
		{
			MyWorkerContext wc = (MyWorkerContext)getWorkerContext();
			ExecutorService pool = wc.getPool();
			/*
			 * Get the shortest path distances from the intermediate now
			 */
			k_ = wc.getPrevious().getval();
			try
			{
				ArrayList<Compute> compute_units = initPool(wc);
				getContext().getProgress(); //no idea why I'm doing this
				pool.invokeAll(compute_units);
				vert.getValue().setval(i_); //setting the new value
			}
			catch(InterruptedException e)
			{
				throw new IOException(e);
			}
		}
	}
	
	/**
	 * Initialize a pool of threads and divides the vertices among them
	 * @param wc
	 * @return
	 */
	private ArrayList<Compute> initPool(MyWorkerContext wc) {
		
		/*
		 * First get the previously created computational threads if any
		 */
		ArrayList<Compute> compute_units = null; //quick fix replacing the getter method from wc
		if(compute_units == null) //if no previous entries for this vertex existed
		{
			int start, stop;
			int offset = wc.getOffset();
			compute_units = new ArrayList<Compute>();
			/*
			 * Create a partition of the vertices to be processed
			 * by different threads
			 */
			for(int n=0;n<wc.getNthreads();n++)
			{
				start = offset*n;
				stop = start + offset;
				if(stop > V)
					stop = V;
				compute_units.add(new Compute(start,stop));
			}
			wc.setComputeUnits(i,compute_units);
		}
		
		return compute_units;
	}
	
	/**
	 * Method to be executed at the 0th super step to initialize the shortest path
	 * array for the current array, filling in values for directly connected vertices
	 * @param vert
	 */
	private void setup(Vertex<IntWritable, VertexVal, NullWritable> vert) {
		
		int i_[] = new int[V];
		Arrays.fill(i_,0,V,Integer.MIN_VALUE);
		
		for(Edge<IntWritable, NullWritable> edge:vert.getEdges())
		{
			int j = edge.getTargetVertexId().get();
			i_[j] = 1;
		}
		i_[i] = 0;
		vert.setValue(new VertexVal(i_)); //initializing shortest path distances
	}

	
	public class Compute implements Callable<Integer>
	{
		/**
		 * The start and stop limits of the vertices this thread is to process
		 */
		private int start,stop;
		
		/**
		 * Parameterized constructor
		 * @param start
		 * @param stop
		 */
		public Compute(int start,int stop)
		{
			this.start = start;
			this.stop = stop;
		}

		@Override
		public Integer call() throws Exception {
			
			/*
			 * Iterate for the vertices this thread has to process
			 * and relax the distances wherever applicable
			 */
			for(int j = start;j<stop;j++)
			{
				if(i == j)
					continue;
				else if(k_[j]!=Integer.MIN_VALUE)
				{
					int sum = k_[j] + i_[k];
					/*
					 * Relax the distances
					 */
					if(i_[j] == Integer.MIN_VALUE || sum<i_[j])
					{
						i_[j] = sum;
					}
				}
			}
			return 0;
		}
		
	}
}
