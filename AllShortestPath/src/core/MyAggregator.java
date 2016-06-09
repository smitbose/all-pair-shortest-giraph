package core;

import graph.VertexVal;

import org.apache.giraph.aggregators.BasicAggregator;

public class MyAggregator extends BasicAggregator<VertexVal>{

	/**
	 * The identifier of this aggregator used in Computation
	 */
	public static String ID = MyAggregator.class.getName();
	
	@Override
	public void aggregate(VertexVal val) {
		getAggregatedValue().setval(val.getval());
	}

	@Override
	public VertexVal createInitialValue() {
		return new VertexVal();
	}

}
