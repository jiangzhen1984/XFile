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
		return currentState.actvie(this);
	}


	@Override
	public boolean finish() {
		boolean flag = true;
		if (states.size() <= 0) {
			return false;
		}
		if (!isFinish) {
			flag = currentState.deactive(this);
			if (!flag) {
				return false;
			}
			currentState = states.peekLast();
			flag = currentState.actvie(this);
			if (flag) {
				isFinish = true;
			}
		}
		return flag;
	}


	public boolean turnToBackState() {
		int index = states.lastIndexOf(currentState);
		if (index <= 0) {
			return false;
		}
		boolean flag = currentState.deactive(this);
		if (!flag) {
			return false;
		}
		currentState = states.get(index - 1);
		flag = currentState.actvie(this);
		if (!flag) {
			return false;
		}
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
			flag = currentState.deactive(this);
			if (!flag) {
				return false;
			}
			currentState = it.next();
			flag = currentState.actvie(this);
		}
		if (isFinishState() && flag) {
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
