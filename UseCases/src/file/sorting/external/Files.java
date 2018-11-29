package file.sorting.external;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Files {
	List<File> files = new ArrayList<>();
	public void add(File file) {
		files.add(file);
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	
}
