import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class BlockedReducer extends Reducer<IntWritable, NodeOrBoundaryCondition, IntWritable, Node> {

	
	 public void reduce(IntWritable key, Iterator<NodeOrBoundaryCondition> values, OutputCollector<IntWritable, Node> output,Context context)
				throws IOException, InterruptedException {
		 
		
		 HashMap<Integer, Node> nodeTable=new HashMap<Integer, Node>();
		NodeOrBoundaryCondition nodeOrBoundaryCondition;
		 HashMap<Integer,ArrayList<Integer>> BConditions=new HashMap<Integer,ArrayList<Integer>>();
		 while(values.hasNext()){
			 
			 nodeOrBoundaryCondition=values.next();
			 if(nodeOrBoundaryCondition.isNode()){
				 nodeTable.put(nodeOrBoundaryCondition.getNode().nodeid, nodeOrBoundaryCondition.getNode());
			 }
			 else{
				 
				 if(BConditions.containsKey(nodeOrBoundaryCondition.getBoundaryCondition().toNodeID)){
					 BConditions.get(nodeOrBoundaryCondition.getBoundaryCondition().toNodeID).add(nodeOrBoundaryCondition.getBoundaryCondition().fromNodeID);
				 }
				 else{
					 ArrayList<Integer> fromNodes=new ArrayList<Integer>();
					 fromNodes.add(nodeOrBoundaryCondition.getBoundaryCondition().fromNodeID);
					 BConditions.put(nodeOrBoundaryCondition.getBoundaryCondition().toNodeID,fromNodes);
				 }
				 
				 
			 }
			 	 
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
	 }
	
	
	
	
	
	
	
	/*void IterateBlockOnce(B) {
	    for( v ∈ B ) { NPR[v] = 0; }
	    for( v ∈ B ) {
	        for( u where <u, v> ∈ BE ) {
	            NPR[v] += PR[u] / deg(u);
	        }
	        for( u, R where <u,v,R> ∈ BC ) {
	            NPR[v] += R;
	        }
	        NPR[v] = d*NPR[v] + (1-d)/N;
	    }
	    for( v ∈ B ) { PR[v] = NPR[v]; }
	}*/
	
	void IterateBlockOnce(){
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
