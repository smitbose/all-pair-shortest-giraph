package graph;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class VertexVal implements Writable{
	
	/*
	 * holds the shortest path to each vertex
	 */
	private int []shortestpath;
	/**
	 * Non-parameterized constructor
	 */
	public VertexVal()
	{
		shortestpath = null;
	}
	/**
	 * Parameterized constructor
	 * @param arr Array to initialize shortest path
	 */
	public VertexVal(int arr[])
	{
		if(arr == null)
			shortestpath = null;
		else
		{
			shortestpath = new int[arr.length];
			System.arraycopy(arr, 0, shortestpath, 0, arr.length);
		}
	}
	
	/**
	 * Parameterized constructor
	 * @param size length of the array to be initialized
	 */
	public VertexVal(int size)
	{
		shortestpath = new int[size];
		for(int i = 0;i<size;i++)
		{
			shortestpath[i] = 0;
		}
	}
	
	/**
	 * setter method for shortest path
	 * @param arr The new array whose value is to be copied
	 */
	public void setval(int arr[])
	{
		if(arr == null)
		{
			shortestpath = null;
			return;
		}
		if(shortestpath == null)
			shortestpath = new int[arr.length];
		System.arraycopy(arr, 0, shortestpath, 0, arr.length);
	}
	
	/**
	 * getter method for shortest path
	 */
	public int[] getval()
	{
		return shortestpath;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		int size = in.readInt();
		if(size == 0)
		{
			shortestpath = null;
			return;
		}
		shortestpath = new int[size];
		for(int i=0;i<size;i++)
		{
			shortestpath[i] = in.readInt();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		if(shortestpath == null)
		{
			out.writeInt(0);
			return;
		}
		out.writeInt(shortestpath.length);
		for(int i=0;i<shortestpath.length;i++)
		{
			out.writeInt(shortestpath[i]);
		}
		
	}
	

}
