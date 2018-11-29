package concurrent.hashmap.lock.splitting;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentHashMap<K,V> {
	
	private static int cuncurrencyLevel = 16;
	
	/**
     * The segments, each of which is a hash map.
     */
    private MySegment<K,V>[] segments;
	
    static final int MIN_SEGMENT_SIZE = 2;
    
    public class MySegment<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = 1L;

		public MySegment(int capacity, float lf) {
    		super(capacity, lf);
    	}

    	private Lock lock = new ReentrantLock();
    	
    	public V put (K key, V value) {
    		lock.lock();
    		try{
    		return super.put(key, value);
    		} finally {
    			lock.unlock();
    		}
    	}
    	
    	public V get(Object key) {
    		return super.get(key);
    	}
    	
    }
    
    public MyConcurrentHashMap(final int initialCapacity) {
    	this(initialCapacity,0.75f, cuncurrencyLevel);
    }
    public MyConcurrentHashMap(final int initialCapacity, final float loadFactor, final int concurrencyLevel) {
    	cuncurrencyLevel = concurrencyLevel;
    	segments = new MySegment[cuncurrencyLevel];
    	//size of each hashmap
    	int cap = initialCapacity/concurrencyLevel;
    	if(cap < MIN_SEGMENT_SIZE) {
    		cap = MIN_SEGMENT_SIZE;
    	} for (int i = 0 ; i< concurrencyLevel; i++) {
    		segments[i] = new MySegment<>(cap, loadFactor);
    	}
    }
    
	private transient final int hashSeed = 0;
	
	private int hash(Object k) {
        int h = hashSeed;

        h ^= k.hashCode();

        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        return (h ^ (h >>> 16)) % cuncurrencyLevel;
    }
	
	public V put(K key, V value) {
		MySegment<K, V> s;
		if(value == null) {
			throw new NullPointerException();
		}
		int index = hash(key);
		
		s = segments[index];
		
		return s.put(key, value);
	}
	
	public V get(K key) {
		MySegment<K, V> s;
		int index = hash(key);
		s = segments[index];
		return s.get(key);
	}
	
	public static void main(String [] args) {
		MyConcurrentHashMap<String, String> myChm = new MyConcurrentHashMap<>(16);
		myChm.put("hello", "john");
		myChm.put("hi", "kapil");
		System.out.println(myChm.get("hello"));
		System.out.println(myChm.get("hi"));
	}
}
