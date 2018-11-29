package threadpool;

import java.util.concurrent.BlockingQueue;

public class MyThread extends Thread {

	public MyThread(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}
	private boolean isstopped = false;
	private BlockingQueue<Runnable> queue;

	public void run() {
		while (!isstopped) {
			try {
				Runnable task = queue.poll();
				if(null != task)
				{
					task.run();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void doStop() {
		isstopped  = true;
		this.interrupt();//break pool thread out of poll() call.
	}
	 public synchronized boolean isStopped() {
		 return isstopped;
	 }
}
