package elacier.transaction;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Linear State Transaction. <br />
 * Initial State <-> B  <-> C  <-> Finishing State. <br />
 * Notice: last state deactive never be called
 * @author jiangzhen
 * 
 * @see TransactionManager
 *
 */
public abstract class LinearTransaction extends Transaction {
	
	protected boolean isFinish;
	
	protected LinkedList<State> states;
	
	public LinearTransaction(Token token, Date timestamp) {
		super(token, timestamp);
		states = new LinkedList<State>();
	}
	
	
	public void addState(State state) {
		states.addLast(state);
	}
	
	
	
	
	@Override
	public boolean begin() {
		if (isFinish) {
			throw new RuntimeException("Can not start a finish transaction");
		}
		if (states.size() <= 0) {
			return false;
		}
		currentState = states.peekFirst();
		currentState.actvie(this);
		return true;
	}


	@Override
	public boolean finish() {
		if (states.size() <= 0) {
			return false;
		}
		if (!isFinish) {
			currentState.deactive(this);
			currentState = states.peekLast();
			currentState.actvie(this);
			isFinish = true;
		}
		return true;
	}


	public boolean turnToBackState() {
		int index = states.lastIndexOf(currentState);
		if (index <= 0) {
			return false;
		}
		currentState.deactive(this);
		currentState = states.get(index - 1);
		currentState.actvie(this);
		return true;
	}
	
	
	public  boolean turnToNextState() {
		boolean flag = false;
		if (isFinish) {
			return flag;
		}
		int index = states.lastIndexOf(currentState);
		if (index == -1) {
			return flag;
		}
		ListIterator<State> it = states.listIterator(index);
		if (it.hasNext()) {
			it.next();
		}
		
		if (it.hasNext()) {
			currentState.deactive(this);
			currentState = it.next();
			currentState.actvie(this);
			flag = true;
		} 
		if (isFinishState()) {
			isFinish = true;
		}
		return flag;
	}
	
	
	public boolean isBeginState() {
		if (states.size() <= 0) {
			throw new RuntimeException("No available state");
		}
		return currentState == states.peekFirst();
	}

	
	public boolean isFinishState() {
		if (states.size() <= 0) {
			throw new RuntimeException("No available state");
		}
		return currentState == states.peekLast();
	}

}
