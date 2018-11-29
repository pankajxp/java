package cyclicBarrierAndCountDownLatch;

public class CyclicBarrierCustomTest {

	public static void main(String[] args) {
		CyclicBarrierCustom barrier = new CyclicBarrierCustom(3, new MyBarrierRaunnable());
		System.out.println("CyclicBarrierCustom has been created with parties=3,"
                + " when all 3 parties will reach common barrier point "
                + "CyclicBarrrierEvent will be triggered");
		MyThread t4, t3, t2, t1 = new MyThread(barrier);
		t2 = new MyThread(barrier);
		t3 = new MyThread(barrier);
		t4 = new MyThread(barrier);
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
		new Thread(t4).start();
	}

}

class MyThread implements Runnable {
	CyclicBarrierCustom cyclicBarrier;

	public MyThread(CyclicBarrierCustom cyclicBarrier) {
		super();
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			System.out.println(Thread.currentThread() + " is reached barrier.");
			cyclicBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("As all threads have reached common barrier point " + Thread.currentThread().getName()
				+ " has been released");
	}

}

class MyBarrierRaunnable implements Runnable {
	public void run() {
		System.out.println(
				"As all threads have reached common barrier point " + ", CyclicBarrrierEvent has been triggered");
	}
}
