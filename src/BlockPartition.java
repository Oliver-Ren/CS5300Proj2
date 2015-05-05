import java.util.Arrays;


public class BlockPartition {
	/* The the number of nodes in the graph. */
	private static final int GRAPH_SIZE = 5;

	/* The partitioned array for index binary search of the blocks. */
	private static final int blockPartition[] = {2, 4};
	
	
	/** 
	 * return the corresponding blockID of given nodeID.
	 * @param nodeID
	 * @return
	 */
	public static int getBlockID(int nodeID) {
		int index = Arrays.binarySearch(blockPartition, nodeID);
		if (index < 0) {
			index = -(index + 1);
		}
		return index;
	}
	
	/**
	 * return the graph size measured by total number of nodes.
	 * @return
	 */
	public static int getGraphSize() {
		return GRAPH_SIZE;
	}


}
