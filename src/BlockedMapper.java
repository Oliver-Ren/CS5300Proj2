import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Mapper;


public class BlockedMapper extends Mapper<IntWritable, Text, Text, Text> {

	/**
	 * 
	 * @param key  blockID
	 * @param value	
	 * @param output  
	 */
	public void map(Text key, Text value, OutputCollector<Text, Text> output) {
		
		String line= value.toString();
		
		
		
		
		
	}
	
	
	
	
	
	
}
