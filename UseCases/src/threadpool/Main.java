package threadpool;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		MyThreadPool pool = new MyThreadPool(5, 5);
		pool.execute(new Runnable(){public void run(){System.out.println("task 1");}});
		Thread.currentThread().sleep(2000);
		pool.shutdown();
	}

}
