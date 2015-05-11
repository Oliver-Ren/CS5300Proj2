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
	public final static int N=BlockPartition.getGraphSize();
	public void reduce(IntWritable key, Iterable<NodeOrBoundaryCondition> values,Context context)
			throws IOException, InterruptedException {


		HashMap<Integer, Node> nodeTable=new HashMap<Integer, Node>();
		HashMap<Integer, Double> n2PR=new HashMap<Integer, Double>();
		NodeOrBoundaryCondition nodeOrBoundaryCondition;
		HashMap<Integer,ArrayList<Integer>> BConditions=new HashMap<Integer,ArrayList<Integer>>();
		HashMap<Integer,ArrayList<Integer>> InComings= new HashMap<Integer, ArrayList<Integer>>();
		
		for (NodeOrBoundaryCondition value : values) {


			if (value.isNode()) {
				nodeTable.put(value.getNode().nodeid, value.getNode());
				//System.out.println("added: " + value.getNode().nodeid);
				Iterator i=value.getNode().iterator();
				
				while(i.hasNext()){
					
					Integer ni=((Node)i.next()).nodeid;
					if (((Node)value.getNode()).getBlockID() == BlockPartition.getBlockID(ni)) {
					if(!InComings.containsKey(ni)){
					ArrayList<Integer> tmp=new ArrayList<Integer>();
					tmp.add(value.getNode().nodeid);
					InComings.put(ni, tmp);

					}
					else{
						InComings.get(ni).add(value.getNode().nodeid);
					}
					}
				}
				
			}
			else{
				BoundaryCondition boundaryEdge = value.getBoundaryCondition();

				Double pageRank=boundaryEdge.pageRank;
				Integer from=boundaryEdge.fromNodeID;
				Integer to=boundaryEdge.toNodeID;

				if(!n2PR.containsKey(from)){

					n2PR.put(from, pageRank);
				}
				
				System.out.println("The boundary edge is " + pageRank + " , "+ from + "->" + to);

				//				 
				//				 
				if(BConditions.containsKey(to)){
					BConditions.get(to).add(from);
					System.out.println("BCdonidion contasns to" + to);
				}
				else{
					ArrayList<Integer> fromNodes=new ArrayList<Integer>();
					fromNodes.add(from);
					BConditions.put(to,fromNodes);
					System.out.println("BCondtions do not contain to" + to);
				} 


			}
		}
		//			 	 

		//		 
		HashMap<Integer, Node> originTable = (HashMap<Integer, Node>)nodeTable.clone();
		Double residual = Double.MAX_VALUE;
		//		 /*
		//		  * until residual<0.001
		//		  */
		while(residual>0.001){
			residual=0.0;
	
			//			
			//			/* my version. */
			for (Node n : nodeTable.values()) {
				Iterator<Integer> outGoing = n.iterator();
				while (outGoing.hasNext()) {
					int endNodeID = (int)outGoing.next();
					if (n.getBlockID() == BlockPartition.getBlockID(endNodeID)) {
						nodeTable.get(endNodeID).nextPageRank += n.pageRank/n.outgoingSize();
					}
				}

				if(BConditions.containsKey(n.nodeid)){
					System.out.println("BConditions contains " + n.nodeid);
					for(Integer u: BConditions.get(n.nodeid)){
						System.out.println("Adding the pagerank of boundary from " + u + "to " + n.nodeid);
						n.nextPageRank += n2PR.get(u);
					}

				}
				//				
				//				
			}
			for (Node n : nodeTable.values()) {

				n.nextPageRank=DAMPING_FACTOR*n.nextPageRank+(1-DAMPING_FACTOR)/N;
			}

			/* orignia version. */
			/*			for(Node n: nodeTable.values()){
					for(Node u:nodeTable.values()){


						while(u.iterator().hasNext()){
							int un=u.iterator().next();
							if(un==n.nodeid){
								n.nextPageRank += u.pageRank/u.outgoingSize();
								break;
							}
					}
				}

				if(BConditions.containsKey(n.nodeid)){
					for(Integer u: BConditions.get(n.nodeid)){

						n.nextPageRank += n2PR.get(u);
					}

				}
				n.nextPageRank=DAMPING_FACTOR*n.nextPageRank+(1-DAMPING_FACTOR)/N;
			}
			 */


			for(Node n: nodeTable.values()){

				n.pageRank=n.nextPageRank;
				n.nextPageRank=0;

				System.out.println("The reducer, node is " + n.nodeid + "pagerank: " + n.getPageRank() + "block:" + n.getBlockID());

			}

			for(Node n:nodeTable.values()){
				residual += Math.abs(n.pageRank-originTable.get(n.nodeid).pageRank)/n.pageRank;
			}

			residual = residual / nodeTable.size();

			System.out.println("The residual is" + residual);

		}

		/* post-condition: the residual is less than 0.001. 
		 * We should add the residual of this block into the 
		 * global counter. */
		long magnifiedResidual = (long)(residual * 100000);
		context.getCounter(CounterType.RESIDUAL_VALUE).increment(magnifiedResidual);
		//		

		/* emit the result as the <nodeID, Node> pair. */
		for (Node n:nodeTable.values()) {
			IntWritable nodeID = new IntWritable(n.nodeid);
			context.write(nodeID, n);

		}





	}



}
