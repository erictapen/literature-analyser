import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class EntryPoint {
	
	private static String rootCaption = "I";
	
	private static ArrayList<String> wordList = new ArrayList<String>();
	private static ArrayList<ArrayList<String>> sentences;
	private static GraphNode root;
	private static Settings settings = null;
	private static ArrayList<String> pmarks = new ArrayList<String>();

	public static void main(String[] args) {
		addPMarks();
		Options options = new Options();
		addCommandLineOptions(options);
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			settings = generateSettingsFromCmd(cmd);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		rootCaption = settings.getRootCaption();
		try {
			importFile(settings.getInputFile());
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + settings.getInputFile() + "\" not found. Abort.");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("IOException. Abort.");
			e.printStackTrace();
			return;
		}
		getTreeFromWords();
		System.out.println(wordList.size() + " words in memory.");
		if(settings.isExportDOT()) 
			SortedGraphExport.exportFile(root, "out/" + settings.getOutputFile() + ".dot");
		if(settings.isExportPLAIN()) 
			PlainExport.exportFile(wordList, "out/" + settings.getOutputFile() + ".plain");
		if(settings.isExportSENTENCE()) 
			SentenceExport.exportFile(sentences, "out/" + settings.getOutputFile() + ".sentences");
	}

	private static void addPMarks() {
		pmarks.add("'");
		pmarks.add("’");
		pmarks.add(".");
		pmarks.add(",");
		pmarks.add("“");
		pmarks.add("”");
		pmarks.add("[");
		pmarks.add("]");
		pmarks.add("(");
		pmarks.add(")");		
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
		if(settings.isVerbose()) System.out.println(line.length());
		ArrayList<String> words = new ArrayList<String>();
		String word = "";
		int i=0;
		while(i < line.length()) {
			if(settings.isForcePunctuationMarks() && pmarks.contains(line.substring(i, i+1))) {
				words.add(word);
				word = "";
				words.add(line.substring(i, i+1));
				i++;				
			} else if (line.startsWith(settings.getWordSeperator(), i)) {
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
		if(settings.isVerbose()) System.out.println(words);
	}
	
	private static void getTreeFromWords() {
		System.out.println("Building tree. WordList has " + wordList.size() + " entries.");
		sentences = new ArrayList<ArrayList<String>>();
		boolean record = false;
		boolean beganSentence = true;
		for(String x : wordList) {
			if(x.equals(rootCaption) && beganSentence) {
				record = true;
				sentences.add(new ArrayList<String>());
			}
			if(settings.isRootMustOpenSentence()) beganSentence = false;
			if(x.equals(settings.getSentenceEnder())) {
				record = false;
				beganSentence = true;
			}
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
	
	private static void addCommandLineOptions(Options options) {
		options.addOption("i", "input-file", true, "the plain text to process");
		options.addOption("o", "output-file", true, "output-file name. "
				+ "File extension will be added automatically.");
		options.addOption("r", "root", true, "this word and the following will be added into the tree.");
		options.addOption("b", "root-starts-sentence", true, "only sentences starting with root will "
				+ "be processed.");
		options.addOption("w", "word-seperator", true, "the symbol, which seperates words. "
				+ "Default is ' '.");
		options.addOption("m", "force-punctuation-marks", true, "this will force punctuation marks like"
				+ "'.',';','`' and ',' to be seperated words, regardless if they are surrounded by "
				+ "word-seperators or not.");
		options.addOption("e", "sentence-end", true, "This word ends a sentence. Default is '.'");
		options.addOption("d", "export-dot", true, "export to *.dot format.");
		options.addOption("p", "export-plain", true, "export to *.plain format. Only useful for debugging.");
		options.addOption("s", "export-sentences", true, "export to *.sen format. "
				+ "Lists every matching sentence.");
		options.addOption("v", "verbose", false, "");
	}
	
	private static Settings generateSettingsFromCmd(CommandLine cmd) {
		Settings res = new Settings();
		String i = cmd.getOptionValue("i");
		if(i!=null) res.setInputFile(i);
		else {
			System.out.println("Please specify an input file. Abort.");
			System.exit(0);
		}
		
		String o = cmd.getOptionValue("o");
		if(o!=null) res.setOutputFile(o);
		else {
			System.out.println("Please specify an output file. Abort.");
			System.exit(0);
		}
		
		String r = cmd.getOptionValue("r");
		if(r!=null) res.setRootCaption(r);
		else System.out.println("Warning! No root caption was set. Default value \"" 
				+ res.getRootCaption() + "\" will be used.");
		
		String b = cmd.getOptionValue("b");
		if(b!=null) {
			res.setRootMustOpenSentence(Boolean.parseBoolean(b));
		}
		
		String w = cmd.getOptionValue("w");
		if(w!=null) res.setWordSeperator(w);
		
		String m = cmd.getOptionValue("m");
		if(m!=null) res.setForcePunctuationMarks(Boolean.parseBoolean(m));
		
		String e = cmd.getOptionValue("e");
		if(e!=null) res.setSentenceEnder(e);
		
		String d = cmd.getOptionValue("d");
		if(d!=null) res.setExportDOT(Boolean.parseBoolean(d));
		
		String p = cmd.getOptionValue("p");
		if(p!=null) res.setExportPLAIN(Boolean.parseBoolean(p));
		
		String s = cmd.getOptionValue("s");
		if(s!=null) res.setExportSENTENCE(Boolean.parseBoolean(s));
		
		if(cmd.hasOption("v")) res.setVerbose(true);
				
		return res;
	}

}
