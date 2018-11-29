package cyclicBarrierAndCountDownLatch;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCountDownLatch {
	private volatile AtomicInteger counter;
	private int count;
	public MyCountDownLatch(int count) {
		super();
		this.counter = new AtomicInteger(count);
	}

	public synchronized void await() {
		while(counter.get()>0) {
			try {
				System.out.println("waiting");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Releasing all theads");
	}
	
	public void countDown(){
		counter.decrementAndGet();
		synchronized(this) {
			notifyAll();
		}
	}
}

class Worker implements Runnable {
	volatile MyCountDownLatch latch ;
	public Worker(MyCountDownLatch latch) {
		super();
		this.latch = latch;
	}
	void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		sleep();
		System.out.println("task 1 completed");
		latch.countDown();
		sleep();
		System.out.println("task 2 completed");
		latch.countDown();
		sleep();
		System.out.println("task 3 completed");
		latch.countDown();
	}
	
}
