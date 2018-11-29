package producer.consumer.blocking.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerUsingBlockingQueue {
	private static BlockingQueue<Integer> sharedQueue;

	public static void main(String[] args) {
		sharedQueue = new ArrayBlockingQueue<>(10);
		Thread prodThread = new Thread(new Producer(sharedQueue), "Producer");
		Thread ConsThread = new Thread(new Consumer(sharedQueue), "Consumer");
		prodThread.start();
		ConsThread.start();
	}
}

class Producer implements Runnable {
	private BlockingQueue<Integer> sharedQueue;

	public Producer(BlockingQueue<Integer> sharedQueue) {
		super();
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			try {
				sharedQueue.put(i);
				System.out.println(Thread.currentThread() + " produced: " + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {
	private BlockingQueue<Integer> sharedQueue;

	public Consumer(BlockingQueue<Integer> sharedQueue) {
		super();
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int i = sharedQueue.take();
				System.out.println(Thread.currentThread() + " Consumed: " + i);
				if (10 == i) {
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
