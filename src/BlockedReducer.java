import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class BlockedReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {

	
	 public void reduce(IntWritable key, Iterator<EdgeOrCondition> values, OutputCollector<IntWritable, Node> output,Context context)
				throws IOException, InterruptedException {
		 
		
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
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
