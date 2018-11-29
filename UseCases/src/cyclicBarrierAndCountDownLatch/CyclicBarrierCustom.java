package cyclicBarrierAndCountDownLatch;

public class CyclicBarrierCustom {
	private int initialParties;
	private int awaitingParties;
	private Runnable cyclicBarrierEvent;
	public CyclicBarrierCustom(int parties, Runnable cyclicBarrierEvent) {
		super();
		initialParties = parties;
		awaitingParties = parties;
		this.cyclicBarrierEvent = cyclicBarrierEvent;
	}
	public synchronized void await() throws InterruptedException {
		awaitingParties--;
		if(awaitingParties>0) {
			System.out.println(Thread.currentThread() + " is waiting at barrier.");
			this.wait();
		}
		else {
			awaitingParties = initialParties;
			cyclicBarrierEvent.run();
			notifyAll();
		}
	}

}
