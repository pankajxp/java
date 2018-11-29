package producer.consumer.wait.notify;

import java.util.Vector;

/**
 * @author pagnih
 *
 */
public class ProducerConsumerSolution {
	public static void main(String[] args) {
		Vector<Integer> sharedQueue = new Vector<Integer>();
		int size = 4;
		Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
		Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
		prodThread.start();
		consThread.start();
	}
}

/**
 * @author pagnih
 *
 */
class Producer implements Runnable {
	private final Vector<Integer> sharedQueue;
	private final int SIZE;
	public Producer(Vector<Integer> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.SIZE = size;
	}

	public void run() {
		for (int i=0;i<7;i++) {
			System.out.println("Produced: " + i);
			try {
				produce(i);
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	private void produce(int i) throws InterruptedException {
		synchronized (sharedQueue) {
			while (isFull()) {
				System.out.println("sharedQueue is full");
				sharedQueue.wait();
			}
			sharedQueue.add(i);
			sharedQueue.notifyAll();
		}
	}
	
	private boolean isFull() {
		boolean isfull = false;
		if(sharedQueue.size() == SIZE) {
			isfull = true;
		}
		return isfull;
	}
}

/**
 * @author pagnih
 *
 */
class Consumer implements Runnable { private volatile Vector<Integer> sharedQueue;
	public Consumer(Vector<Integer> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
				if(6==consume())
					return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Integer consume() throws InterruptedException {
		synchronized (sharedQueue) {
			while (isEmpty()) {
				System.out.println("sharedQueue is Empty");
				sharedQueue.wait();
			}
			Integer received = sharedQueue.remove(0);
			System.out.println("Consumed: " + received);
			sharedQueue.notifyAll();
			return received;
		}
	}
	
	private boolean isEmpty() {
		boolean isempty = false;
		if(sharedQueue.size() == 0) {
			isempty = true;
		}
		return isempty;
	}
}
