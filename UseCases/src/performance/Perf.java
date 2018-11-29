package performance;

public class Perf {
	private long startTime;
	private long duration;

	public Perf() {
		super();
		duration = 0;
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void stop() {
		duration = System.currentTimeMillis() - startTime;
		System.out.println("Perf in milliseconds: " + duration);
	}

}
