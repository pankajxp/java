package merge.sort;

import performance.Perf;

public class Main {

	public static void main(String[] args) {
		Perf perf = new Perf();
		perf.start();
		int data[] = { 9, 7, 3, 2, 1, 6, 5, 8 ,276,21837,87,2768,3298,37,367,9};
		Mergesort msort = new Mergesort(data);
		msort.sort();
		perf.stop();
		for (int i : data) {
			System.out.print(i + " ");
		}
	}

}
