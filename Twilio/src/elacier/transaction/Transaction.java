package elacier.transaction;

import java.util.Date;


/**
 * Transaction definition <br/>
 * 
 * @see TransactionManager
 * @see LinearTransaction
 * 
 * @author jiangzhen
 * 
 */
public abstract class Transaction {
	
	protected Token token;
	
	protected Date timestamp;
	
	protected State currentState;

	public Transaction(Token token, Date timestamp) {
		this.token = token;
		if (token == null) {
			throw new NullPointerException(" Token is null");
		}
		this.timestamp = timestamp;
	}

	public Token getToken() {
		return token;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	
	
	public State getState() {
		return currentState;
	}

	public abstract boolean begin();
	
	
	
	public abstract boolean finish();
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	
	
	
	
	

}
