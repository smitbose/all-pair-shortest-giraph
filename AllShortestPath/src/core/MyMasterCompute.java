package core;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.giraph.master.MasterCompute;

public class MyMasterCompute extends MasterCompute{

	@Override
	public void readFields(DataInput in) throws IOException {	}

	@Override
	public void write(DataOutput out) throws IOException {	}

	@Override
	public void compute() {	}

	@Override
	public void initialize() throws InstantiationException,
			IllegalAccessException {
		registerAggregator(MyAggregator.ID,MyAggregator.class);
		
	}

}
