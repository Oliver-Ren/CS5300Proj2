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
	public final static double DAMPING_FACTOR = 0.85;
	
	 public void reduce(IntWritable key, Iterator<NodeOrBoundaryCondition> values, OutputCollector<IntWritable, Node> output,Context context)
				throws IOException, InterruptedException {
		 
		
		 HashMap<Integer, Node> nodeTable=new HashMap<Integer, Node>();
		 HashMap<Integer, Double> n2PR=new HashMap<Integer, Double>();
		NodeOrBoundaryCondition nodeOrBoundaryCondition;
		 HashMap<Integer,ArrayList<Integer>> BConditions=new HashMap<Integer,ArrayList<Integer>>();
		 while(values.hasNext()){
			 
			 nodeOrBoundaryCondition=values.next();
			 if(nodeOrBoundaryCondition.isNode()){
				 nodeTable.put(nodeOrBoundaryCondition.getNode().nodeid, nodeOrBoundaryCondition.getNode());
				 while(nodeOrBoundaryCondition.getNode().iterator().hasNext()){
					 
				 }
				 
			 }
			 else{
				 if(!n2PR.containsKey(nodeOrBoundaryCondition.getBoundaryCondition().fromNodeID)){
					 
					 n2PR.put(nodeOrBoundaryCondition.getBoundaryCondition().fromNodeID, nodeOrBoundaryCondition.getBoundaryCondition().pageRank);
				 }
				 
				 
				 
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
		 
		 
		 /*
		  * Iterate block for 3 times
		  */
		for(int i=0; i<3; i++){
			for(Node n: nodeTable.values()){
				
					n.nextPageRank=0;
				
			}
			
			for(Node n: nodeTable.values()){
				while(n.iterator().hasNext()){
					int u=n.iterator().next();
					if(nodeTable.get(u).blockID==key.get()){
						n.nextPageRank += nodeTable.get(u).pageRank/nodeTable.get(u).outgoingSize();
					}
					
				}

				if(BConditions.containsKey(n.nodeid)){
					for(Integer u: BConditions.get(n.nodeid)){
						
						n.nextPageRank += n2PR.get(u);
					}
					
				}
				n.nextPageRank=DAMPING_FACTOR*n.nextPageRank+(1-DAMPING_FACTOR);
			}
			
			
			for(Node n: nodeTable.values()){
				
					n.pageRank=n.nextPageRank;
					n.nextPageRank=0;
				
			}
			
			
			
			
		}
		 
		 
		
		for(Node n:nodeTable.values()){
			if(n.getBlockID()==key.get()){
				
				context.write(key, n);
				
			}
			
		}
		 
		 
		 
		 
		 
	 }
	
	
	
}
