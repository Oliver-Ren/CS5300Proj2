import java.util.ArrayList;
import java.util.Iterator;


public class BEdge implements EdgeOrCondition {
	Integer from;
	Iterator<Integer> to;
	Double PR;
	
	
	
	public BEdge(Integer from, Iterator<Integer> to ) {
		// TODO Auto-generated constructor stub
		this.from=from;
		this.to=to;
	}

	@Override
	public boolean isEdge() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCondition() {
		// TODO Auto-generated method stub
		return false;
	}

}
