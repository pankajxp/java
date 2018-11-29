package file.sorting.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class FileOps {
	private File file;
	private BufferedReader br;
	private String line;
	Queue<String> queue;
	private String word;

	public String getword() {
		return word;
	}

	public FileOps(File file) throws FileNotFoundException {
		super();
		this.file = file;
		this.br = new BufferedReader(new FileReader(file));
		queue = new LinkedList();
	}

	public String nextWord() throws IOException {
		if (queue.isEmpty()) {
			line = br.readLine();
			if(null == line) {
				br.close();
				word = null;
				return word;
			}
			for (String word : line.split(" ")) {
				queue.add(word);
			}
		}
		word = queue.remove();
		return word;
	}

}
