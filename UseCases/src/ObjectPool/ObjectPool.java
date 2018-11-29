package ObjectPool;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * https://sourcemaking.com/design_patterns/object_pool/java
 * 
 * @author pagnih
 *
 */
public abstract class ObjectPool<T> {
	private long expirationTime;
	private Hashtable<T, Long> locked, unlocked;
	
	public ObjectPool() {
		super();
		this.expirationTime = 30000;
		this.locked = new Hashtable<>();
		this.unlocked = new Hashtable<>();
	}
	
	protected abstract T create();
	
	public abstract boolean validate(T t);
	
	public abstract  void expire(T t);
	
	public synchronized T checkout() {
		long now = System.currentTimeMillis();
		T t;
		if(unlocked.size()>0) {
			Enumeration<T> e=unlocked.keys();
			while(e.hasMoreElements()) {
				t=e.nextElement();
				if(now - unlocked.get(t).longValue() > expirationTime) {
					// object has expired
					unlocked.remove(t);
					expire(t);
					t=null;
				} else if(validate(t)) {
					unlocked.remove(t);
					locked.put(t, new Long(now));
					return t;
				} else {
					// object failed validation
					unlocked.remove(t);
					expire(t);
					t = null;
				}
			}
		}
		// no objects available, create a new one
		t = create();
		locked.put(t, now);
		return(t);
	}
	
	public synchronized void checkin(T t) {
		locked.remove(t);
		unlocked.put(t, System.currentTimeMillis());
	}
}

class JDBCConnectionPool<Connection> extends ObjectPool<Connection> {

	private String dsn, usr, pwd;
	
	public JDBCConnectionPool(String driver, String dsn, String usr, String pwd) {
		super();
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.dsn = dsn;
		this.usr = usr;
		this.pwd = pwd;
	}

	@Override
	protected Connection create() {
		try {
			return (Connection) DriverManager.getConnection(dsn, usr, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean validate(Connection o) {
		try {
			return !((java.sql.Connection) o).isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void expire(Connection o) {
		try {
			((java.sql.Connection) o).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}