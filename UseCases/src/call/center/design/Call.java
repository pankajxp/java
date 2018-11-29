package call.center.design;

import java.util.LinkedList;
import java.util.List;

public class Cal {
	private List<String> comments;
	private Level level;
	public Call(Level level) {
		super();
		this.level = level;
		comments = new LinkedList<>();
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(String comment) {
		this.comments.add(comment);
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}

}
