package graph;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Writable;

public class VertexMap implements Writable{

	private static ArrayList<Integer> vertexMap;
	private int vertexId;
	
	public VertexMap()
	{
		vertexMap = null;
		vertexId = Integer.MIN_VALUE;
	}
	public VertexMap(int i)
	{
		vertexMap = null;
		vertexId = i;
	}
	
	public void addVert(int i)
	{
		if(i == Integer.MIN_VALUE)
			return;
		if(vertexMap == null)
			vertexMap = new ArrayList<Integer>();
		System.out.println("Adding: "+i+" size "+(vertexMap.size()+1));
		vertexMap.add(i);
	}
	
	public int getIndex()
	{
		return vertexId;
	}
	
	public ArrayList<Integer> getMap()
	{
		return vertexMap;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		
		vertexId = in.readInt();
		int size = in.readInt();
		if(size == 0)
		{
			vertexMap = null;
			return;
		}
		vertexMap = new ArrayList<Integer>();
		for(int i = 0;i<size;i++)
		{
			int a = in.readInt();
			vertexMap.add(a);
		}
		
		System.out.println("Size now: "+size);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		
		out.writeInt(vertexId);
		if(vertexMap == null)
		{
			out.writeInt(0);
			return;
		}
		System.out.println("Size: "+vertexMap.size());
		out.writeInt(vertexMap.size());
		for(int i=0;i<vertexMap.size();i++)
		{
			out.writeInt(vertexMap.get(i));
		}
		
	}

}
