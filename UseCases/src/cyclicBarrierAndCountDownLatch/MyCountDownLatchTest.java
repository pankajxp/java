package cyclicBarrierAndCountDownLatch;

public class MyCountDownLatchTest {

	public static void main(String[] args) {
		MyCountDownLatch latch = new MyCountDownLatch(3);
		Worker worker = new Worker(latch);
		Thread workerThread = new Thread(worker);
		workerThread.start();
		System.out.println("Main thread waiting for worker thread to complete all the 3 tasks.");
		latch.await();
		System.out.println("Main thread released.");
	}

}
