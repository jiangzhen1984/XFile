package elacier.transaction;

public abstract class State {
	
	
	public abstract boolean actvie(Transaction trans);
	
	
	
	public abstract boolean deactive(Transaction trans);

}
