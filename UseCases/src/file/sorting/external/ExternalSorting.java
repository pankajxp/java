package file.sorting.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalSorting {

	private Files files;
	private List<FileOps> smallestList;

	public ExternalSorting() {
		super();
		this.files = new Files();
		this.smallestList = new ArrayList<>();
	}

	public void sortFile(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			while (null != (line = br.readLine())) {
				sb.append(line).append(" ");
			}
		} finally {
			br.close();
		}
		String[] words = sb.toString().split(" ");
		Arrays.sort(words);
		//
		File newFile = new File(file.getPath() + "_temp");
		saveFile(words, newFile);
		files.add(newFile);
	}

	private void saveFile(String[] words, File file) throws IOException {

		FileWriter fw = new FileWriter(file);
		try {
			for (String word : words) {
				fw.write(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}
	}

	public void setSmallestList(FileOps word) {
		smallestList.add(word);
	}

	public void setSmallestList(Collection<FileOps> words) {
		smallestList.addAll(words);
	}

	public String popSmallest() {
		String word = null;
		FileOps smallest = null;
		for (FileOps wordObj : smallestList) {
			if (smallest == null && wordObj.getword() != null) {
				smallest = wordObj;
				continue;
			}
			if (smallest.getword().compareTo(wordObj.getword()) > 0) {
				smallest = wordObj;
			}
		}
		word = smallest.getword();
		try {
			if(null == smallest.nextWord()) {
				smallestList.remove(smallest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return word;
	}

	public void sort() throws IOException {
		Map<FileOps, String> map = new HashMap<>();
		FileOps fp;
		String word;
		for (Object file : files.getFiles().toArray()) {
			File f = (File) file;
			fp = new FileOps(f);
			setSmallestList(fp);
		}
		StringBuilder sb = new StringBuilder();
		while(smallestList.size() !=0) {
			
			sb.append(popSmallest());
			if(sb.length() >100) {
				//persist(sb.toString);persist to db
			}
		}
		//persist(sb.toString);
	}

}
