package merge.sort;

public class Mergesort {

	private int[] data;
	private int[] tempData;
	private int length;

	public Mergesort(int[] data) {
		super();
		this.data = data;
		this.length = data.length;
		tempData = new int[length];
	}

	void sort() {
		int lowIndex = 0;
		int highIndex = this.length - 1;
		mergeSort(lowIndex, highIndex);
	}

	private void mergeSort(int lowIndex, int highIndex) {
		if (lowIndex < highIndex) {
			int midleIndex = lowIndex + (highIndex - lowIndex) / 2;
			mergeSort(lowIndex, midleIndex);
			mergeSort(midleIndex + 1, highIndex);
			mergeParts(lowIndex, midleIndex, highIndex);
		}
	}

	void mergeParts(final int startIndex, final int midleIndex, final int endIndex) {
		int i = startIndex;
		int j = midleIndex + 1;
		int tempIndex = startIndex;
		while (i <= midleIndex && j <= endIndex) {
			if (data[i] <= data[j]) {
				tempData[tempIndex++] = data[i++];
			} else {
				tempData[tempIndex++] = data[j++];
			}
		}
		while (i <= midleIndex) {
			tempData[tempIndex++] = data[i++];
		}
		while (j <= endIndex) {
			tempData[tempIndex++] = data[j++];
		}
		i = startIndex;
		while (i <= endIndex) {
			data[i] = tempData[i];
			i++;
		}
	}
}
