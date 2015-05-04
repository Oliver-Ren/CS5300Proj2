
public class BCondition implements EdgeOrCondition {
	Double PR;
	Integer from;
	Integer to;
	
	public BCondition(Integer from, Integer to) {
		// TODO Auto-generated constructor stub
		this.from=from;
		this.to=to;
	}

	@Override
	public boolean isEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCondition() {
		// TODO Auto-generated method stub
		return true;
	}

}
