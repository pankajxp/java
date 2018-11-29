package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {
	private boolean isstopped = false;
	private List<MyThread> threads = new ArrayList<>();
	private BlockingQueue<Runnable> queue = null;
	public MyThreadPool(int NoOfThreads, int maxNoOfTasks) {
		queue = new ArrayBlockingQueue<>(maxNoOfTasks);
		for(int i=0; i<NoOfThreads; i++) {
			threads.add(new MyThread(queue));
		}
		for(MyThread thread : threads) {
			thread.start();
		}
	}
	
	public synchronized void execute(Runnable task) {
		if(this.isstopped) {
			throw new IllegalStateException("Threadpool is stopped");
		}
		queue.add(task);
	}
	
	public synchronized void shutdown() {
		isstopped = true;
		for(MyThread thread : threads) {
			thread.doStop();
		}
	}

}
