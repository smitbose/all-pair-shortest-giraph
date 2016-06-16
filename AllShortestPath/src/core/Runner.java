package core;

import io.OutputShortestPath;
import io.SNAPInputFormat;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.io.formats.GiraphFileInputFormat;
import org.apache.giraph.job.GiraphJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Runner implements Tool {
	
	private Configuration conf;
	private static String inputpath;
	private static String outputpath;
	
	public static String getInputpath() {
		return inputpath;
	}

	public static void setInputpath(String inputpath) {
		Runner.inputpath = inputpath;
	}

	public static String getOutputpath() {
		return outputpath;
	}

	public static void setOutputpath(String outputpath) {
		Runner.outputpath = outputpath;
	}

	
	
	
	@Override
	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public int run(String[] arg0) throws Exception {
		String	inputfiles="graph.txt";
		setInputpath("/home/soumit/internship/dataset/"+inputfiles);
		GiraphConfiguration giraphConf=new GiraphConfiguration(getConf());
		giraphConf.setComputationClass(MyComputation.class);
		giraphConf.setEdgeInputFormatClass(SNAPInputFormat.class);
		//giraphConf.setEdgeOutputFormatClass(OutputShortestPath.class);
		giraphConf.setVertexOutputFormatClass(OutputShortestPath.class);
		GiraphFileInputFormat.addEdgeInputPath(giraphConf, new Path(getInputpath()));		
		giraphConf.setWorkerConfiguration(0, 1, 100.0f);
		giraphConf.SPLIT_MASTER_WORKER.set(giraphConf, false);
		giraphConf.LOG_LEVEL.set(giraphConf, "error");
		giraphConf.setLocalTestMode(true);
		giraphConf.setMaxNumberOfSupersteps(10000);
		giraphConf.setWorkerContextClass(MyWorkerContext.class);
		giraphConf.setMasterComputeClass(MyMasterCompute.class);
		GiraphJob job = new GiraphJob(giraphConf,getClass().getName());
		setOutputpath("/home/soumit/internship/"+"output");
		FileOutputFormat.setOutputPath(job.getInternalJob(), new Path(getOutputpath()));
		File directory=new File(getOutputpath());
		FileUtils.deleteDirectory(directory);
		job.run(true);
		return 1;
	}
	public static void main(String args[]) throws Exception
	{
		int t = ToolRunner.run(new Runner(),args);
		if (t!=1)
		{System.err.println("Error in setting configuration if giraph");}
	}

}
