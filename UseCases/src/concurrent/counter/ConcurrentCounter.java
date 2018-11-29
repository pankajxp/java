package concurrent.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter {
	private AtomicInteger counter;
	
	public ConcurrentCounter(int initialValue) {
		super();
		counter = new AtomicInteger(initialValue);
	}

	public int increment() {
		return counter.incrementAndGet();
	}
	
	public void decrement() {
		counter.decrementAndGet();
	}
	
	public void reset() {
		counter.set(0);
	}
	
	public int getcount() {
		return counter.get();
	}
}
