This is an implementation of the Floyd Warshall Algorithm, on [Apache Giraph](http://giraph.apache.org/), an open source large scale graph processing software that is based on the [Bulk Synchronous Model](https://en.wikipedia.org/wiki/Bulk_synchronous_parallel) for parallel computation. This algorithm generates shortest path for all pair of vertices, that can be used for calculating Centrality metrics for vertices.

The classes are divided under the following packages:
* core - classes that are required for performing computation
* io - classes that are required for performing computation
* graph - classes that are defined for storing different types of values, and implementing the Writable interface.

All further references to $HADOOP_HOME refers to the hadoop installation folder and $GIRAPH_HOME refers to the Giraph installation folder.

Usage-

This project assumesm, Giraph and Hadoop are installed in the system. The project was developed with Giraph 1.2.0-SNAPSHOT on top of Hadoop-1.2.1. Before importing this project into eclipse the eclipse build path should be configured to include:
* The jar file generated in $GIRAPH_HOME/giraph-core/target/ after building giraph.
* The jar file generated in $GIRAPH_HOME/giraph-examples/taret/ after building giraph.
* The jar files in $HADOOP_HOME/lib.
* hadoop-client-1.2.1.jar
* hadoop-core-1.2.1.jar
* hadoop-test-1.2.1.jar
* hadoop-tools-1.2.1.jar
To do this, right click on the project select Build Path->Configure Build Path and make the necessary changes.

Import the project into eclipse and open the Runner.java class. There, set the inputfiles as the name of the file to be given as input, and write the complete input path as an argument to the setInputpath() method. Do similarly, for the setOuputpath(). Set the input format accordingly. The input format packaged with this code is SNAPInputFormat, which reads SNAP(https://snap.stanford.edu/) datasets. For output formats, you can use one of two:
* OutputShortestPath - outputs the shortest paths where first column is the vertex ID and following ith number denotes shortest paths to the ith vertex.
* OutputCentrality - this outputs the closeness centrality of the vertices, without printing all the distances.

To run it on eclipse, right click on Runner.java, click Run As->Java Application. To run it on a cluster with hadoop installed, export the code as a jar. In Eclipse, this can be done by right clicking on the project directory, Export->Java->Jar file. Specify output path in the dialog box and click Finish. 

On the cluster, set two environments, $HADOOP_CLASSPATH and $LIBJARS:
* export HADOOP_CLASSPATH=$GIRAPH_HOME/giraph-core/target/<giraph-jar>:<path to the jar>
* export LIBJARS=$GIRAPH_HOME/giraph-core/target/<giraph-jar>,<path to the jar>

Now run it with the following command:

$ hadoop jar $GIRAPH_HOME/giraph-core/target/<giraph jar> org.apache.giraph.GiraphRunner -libjars $LIBAJRS core.MyComputation /
-eif io.SNAPInputFormat -eip <hdfs path> -vof io.OutputShortestPath -op <hdfs output path> / 
-mc core.MyMasterCompute -wc core.MyWorkerContext -w 2 -ca giraph.useSuperstepCounters=false

To get Centrality directly, use OutputCentrality after -vof option.
