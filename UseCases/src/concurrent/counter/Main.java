package concurrent.counter;

public class Main {
	
	volatile static Integer intCounter;

	public static void main(String[] args) throws InterruptedException {
		ConcurrentCounter counter = new ConcurrentCounter(0);
		
		intCounter = new Integer(0);

		Thread t1 = new CounterThread(counter, intCounter);
		Thread t2 = new CounterThread(counter, intCounter);
		Thread t3 = new CounterThread(counter, intCounter);
		Thread t4 = new CounterThread(counter, intCounter);
		
		t1.start();t2.start();t3.start();t4.start();
		
		t1.join();t2.join();t3.join();t4.join();

		System.out.println("final concurrent counter value: " + counter.getcount());
		System.out.println("final integer counter value: " + intCounter.toString());
	}

}

class CounterThread extends Thread {
	private boolean isConcurrentCounter = true;
	private ConcurrentCounter counter;
	private static Integer intCounter;

	public CounterThread(ConcurrentCounter counter, Integer intCounter) {
		super();
		this.counter = counter;
		this.intCounter = intCounter;
	}

	public void run() {
		if(isConcurrentCounter){
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread() + " increments : " + counter.increment());
			}
		} else {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread() + " increments : " + intCounter++);
			}
		}
	}
}
