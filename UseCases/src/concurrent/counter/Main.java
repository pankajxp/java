package concurrent.counter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	static volatile AtomicInteger sharedCounter;

	static AtomicBoolean isShutdown = new AtomicBoolean(false);

	static volatile AtomicInteger threadCounter;

	final static int MAX_THREAD_COUNT = 4;

	final static int MAX_COUNTER = 10;

	public final static CyclicBarrier cb = new CyclicBarrier(MAX_THREAD_COUNT+1);

	volatile List<Integer> threadSequence = new LinkedList<>();

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
//		ConcurrentCounter counter = new ConcurrentCounter(0);

		sharedCounter = new AtomicInteger(1);
		threadCounter = new AtomicInteger(1);

		Thread t1 = new CounterThread(1, sharedCounter);
		Thread t2 = new CounterThread(2, sharedCounter);
		Thread t3 = new CounterThread(3, sharedCounter);
		Thread t4 = new CounterThread(4, sharedCounter);

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		cb.await();

		System.out.println("final concurrent counter value: " + sharedCounter.get());
//		System.out.println("final integer counter value: " + sharedCounter.toString());
	}

}

class CounterThread extends Thread {
	private boolean isConcurrentCounter = true;
	private AtomicInteger counter;
	private Integer name;

	public CounterThread(Integer name, AtomicInteger counter) {
		super();
		this.counter = counter;
		this.name = name;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Main.threadCounter.get() == name) {
				if (Main.MAX_THREAD_COUNT == name) {
					Main.threadCounter.compareAndSet(Main.MAX_THREAD_COUNT, 0);
				}
				Main.threadCounter.incrementAndGet();
				System.out.println("Thread name: " + name + ", Counter: " + counter.getAndIncrement());
				if (counter.get() == Main.MAX_COUNTER) {
					Main.isShutdown.set(true);
					try {
						Main.cb.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					return;
				}

			} else {
				if (Main.isShutdown.get() == true) {
					try {
						Main.cb.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}
}
