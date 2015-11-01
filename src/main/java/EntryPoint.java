import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class EntryPoint {
	
	private static String rootCaption = "I";
	
	private static ArrayList<String> wordList = new ArrayList<String>();
	private static ArrayList<ArrayList<String>> sentences;
	private static GraphNode root;

	public static void main(String[] args) {
		rootCaption = args[1];
		try {
			importFile(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + args[0] + "\" not found. Abort.");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("IOException. Abort.");
			e.printStackTrace();
			return;
		}
		getTreeFromWords();
		System.out.println(wordList.size() + " words in memory.");
		SortedGraphExport.exportFile(root, "out/test.dot");
		PlainExport.exportFile(wordList, "out/test.plain");
		SentenceExport.exportFile(sentences, "out/test.sen");

	}
	
	public static void importFile(String infile) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
				getWordsFromLine(line);
				i++;
			}
			System.out.println(i + " lines of text imported.");
		}
	}

	private static void getWordsFromLine(String line) {
		System.out.println(line.length());
		ArrayList<String> words = new ArrayList<String>();
		String word = "";
		int i=0;
		while(i < line.length()) {
			if(		line.startsWith(",", i) || 
					line.startsWith("'", i) ||
					line.startsWith("’", i) ||
					line.startsWith(".", i) ||
					line.startsWith("“", i) ||
					line.startsWith("”", i) ||
					line.startsWith("[", i) ||
					line.startsWith("]", i) ||
					line.startsWith("(", i) ||
					line.startsWith(")", i) ) {
				words.add(word);
				word = "";
				words.add(line.substring(i, i+1));
				i++;				
			} else if (line.startsWith(" ", i)) {
				words.add(word);
				word = "";
				i++;
			} else {
				word = word + line.substring(i, i+1);
				if(i < line.length()) i++;
			}
		}
		while(words.remove("")){}
		wordList.addAll(words);
		System.out.println(words);
	}
	
	private static void getTreeFromWords() {
		System.out.println("Building tree. WordList has " + wordList.size() + " entries.");
		sentences = new ArrayList<ArrayList<String>>();
		boolean record = false;
		for(String x : wordList) {
			if(x.equals(rootCaption)) {
				record = true;
				sentences.add(new ArrayList<String>());
			}
			else if(x.equals(".")) record = false;
			if(record) sentences.get(sentences.size()-1).add(x);
		}
		root = new GraphNode(rootCaption, rootCaption);
		for(ArrayList<String> lx : sentences) {
			GraphNode cursor = root;
			for(int i=1; i<lx.size(); i++) {
				GraphNode newNode = new GraphNode(String.valueOf(Math.random()).substring(2, 13), lx.get(i), cursor);
				cursor.addChild(newNode);
				cursor = newNode;
			}
		}
		System.out.println("Simplifying tree.");
		root.simplifyTree();
		System.out.println("Tree builded. Root has " + root.getChildren().size() + " children.");
	}

}
