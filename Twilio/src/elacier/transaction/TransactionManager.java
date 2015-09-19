package elacier.transaction;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionManager {
	
	private static TransactionManager instance;
	
	
	private AtomicLong al = new AtomicLong();
	
	private Map<Token, Transaction> mMap;
	
	private TransactionManager() {
		mMap = new ConcurrentHashMap<Token, Transaction>();
		
	}
	
	
	public static TransactionManager getInstance() {
		if (instance == null) {
			instance = new TransactionManager();
		}
		return instance;
	}
	
	
	public Transaction createTransaction(Class<? extends Transaction> cls) {
		try {
			cls.getDeclaredConstructors();
			Constructor<? extends Transaction> constr = cls.getDeclaredConstructor(Token.class);
			LongToken token = new LongToken(al.getAndIncrement());
			Transaction trans = constr.newInstance(token);
			mMap.put(token, trans);
			return trans;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void beginTransaction(Transaction trans) {
		if (trans != null) {
			trans.begin();
		}
	}
	
	
	public void finishTransaction(Transaction trans) {
		if (trans == null) {
			throw new IllegalArgumentException(" trans is null");
		}
		if (trans.getToken() == null) {
			throw new IllegalArgumentException(" transation token is null");
		}
		mMap.remove(trans.getToken());
		trans.finish();
	}
	
	
	public Transaction getTransaction(Token token) {
		if (token == null) {
			return null;
		}
		return mMap.get(token);
	}
	
	
	public Transaction getTransaction(long transId) {
		Token key = new LongToken(transId);
		return mMap.get(key);
	}

}
