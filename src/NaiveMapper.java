import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class NaiveMapper extends Mapper<IntWritable, Node, IntWritable, NodeOrBoundaryCondition> {
    public void map(IntWritable key, Node value, Context context) throws IOException, InterruptedException {

        	
    	double p = value.getPageRank() / value.outgoingSize();
    	NodeOrBoundaryCondition pagerankShare = new NodeOrBoundaryCondition(p);
    	
    	//pass along graph structure
    	context.write(key, new NodeOrBoundaryCondition(value));
    	
    	Iterator itr = value.iterator();
    	
    	while (itr.hasNext()) {
    		IntWritable nid = new IntWritable((int)itr.next());
    		context.write(nid, pagerankShare);
    	}
    	
    	
    	
	
    }
}
