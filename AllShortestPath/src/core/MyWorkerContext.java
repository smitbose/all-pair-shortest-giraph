package core;

import graph.VertexVal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.giraph.worker.WorkerContext;

public class MyWorkerContext extends WorkerContext{

	/**
	 * Map the list of computation threads for each vertex
	 */
	private Map<Integer,ArrayList<Compute>> compute_units = new HashMap<Integer,ArrayList<Compute>>();
	/**
	 * Aggregated value at each superstep
	 */
	private VertexVal previous;
	/**
	 * The pool of threads
	 */
	private ExecutorService pool;
	/**
	 * Variables to divide vertices to be processed by a thread
	 */
	int nthreads,offset;
	
	public ArrayList<Compute> getCompute_units(Integer i) {
		return compute_units.get(i);
	}
	public void setComputeUnits(Integer i,ArrayList<Compute> cunits)
	{
		compute_units.put(i, cunits);
	}
	public VertexVal getPrevious() {
		return previous;
	}

	public ExecutorService getPool() {
		return pool;
	}

	public int getNthreads() {
		return nthreads;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public void postApplication() {
		try{
			pool.shutdown();
			while(!pool.isTerminated())
				pool.awaitTermination(100, TimeUnit.MILLISECONDS);
		}
		catch(InterruptedException e)
		{
			System.err.println("Problem in shutting down thread pool");
		}
		finally
		{
			pool.shutdownNow();
			pool = null;
		}
	}

	@Override
	public void postSuperstep() { }

	@Override
	public void preApplication() throws InstantiationException,
			IllegalAccessException {
		//int processors = Runtime.getRuntime().availableProcessors();
		nthreads = 2; //temporarily assumed, to be changed later
		offset = (int)Math.ceil((double)getTotalNumVertices()/nthreads);
		pool = Executors.newFixedThreadPool(nthreads);
		
	}

	@Override
	public void preSuperstep() {
		
		previous = getAggregatedValue(MyAggregator.ID);
		
	}

}
