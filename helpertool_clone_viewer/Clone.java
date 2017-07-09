
import java.util.*;

public class Clone {
	public int lengthInstanceLines;
	public int lengthInstanceTokens;
	//public int length;
	//public int occurences;
	public List<Integer> filesStart;
	public List<String> filesName;
	public List<String> filesFragment;

	public Clone(String lengthInstanceLines, String lengthInstanceTokens) {
		this.lengthInstanceLines = Integer.parseInt(lengthInstanceLines);
		this.lengthInstanceTokens = Integer.parseInt(lengthInstanceTokens);
		this.filesStart = new ArrayList<Integer>();
		this.filesName = new ArrayList<String>();
		this.filesFragment = new ArrayList<String>();
	}

	public void addFile(String startLine, String fileName, String fileFragment) {
		filesStart.add(Integer.parseInt(startLine));
		filesName.add(fileName);
		filesFragment.add(fileFragment);
	}

	public int occurences() {
		return filesStart.size();
	}
	
	public int length() {
		return lengthInstanceLines * occurences();
	}


}