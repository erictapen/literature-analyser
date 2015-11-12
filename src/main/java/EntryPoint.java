/* Copyright 2015 Justin Humm
	
	This file is part of literature-analyser.

    literature-analyser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    literature-analyser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with literature-analyser.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;


public class EntryPoint {
	
	private static String rootCaption;
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
			if(cmd.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "literature-analyser", options );
				System.exit(0);
			}
			settings = generateSettingsFromCmd(cmd);
		} catch (UnrecognizedOptionException e) {
			System.out.println("Wrong command line options usage. "
					+ "Use -h for a list of available options.");
			System.exit(0);
		} catch (ParseException e) {
			e.printStackTrace();
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

	/** This is just for a better overview. All possible punctuation marks are defined here. It is possible,
	 * that I forgot some special unicode characters, that someone may need. In this case, please add them
	 * via PullRequest.
	 * 
	 */
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

	/** Import a text file
	 * @param infile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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

	/** This detects words in a passed line of text.
	 * @param line
	 */
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
	
	/** This turns the list of all detected words into the tree we need.
	 * 
	 */
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
	
	/** All possible command line options are defined in a seperate function.
	 * @param options
	 */
	private static void addCommandLineOptions(Options options) {
		options.addOption("h", "help", false, "displays this help message and exits.");
		
		Option i = new Option("i", "input-file", true, "the plain text to process");
		i.setArgName("file");
		options.addOption(i);
		
		Option o = new Option("o", "output-file", true, "output-file name. "
				+ "File extension will be added automatically.");
		o.setArgName("file");
		options.addOption(o);
		
		Option r = new Option("r", "root", true, "this word and the following will be added into the tree."
				+ " Default is \"I\".");
		r.setArgName("string");
		options.addOption(r);
		
		Option b = new Option("b", "root-starts-sentence", true, "if true, only sentences starting "
				+ "with root will be processed. Default is true.");
		b.setArgName("boolean");
		options.addOption(b);
		
		Option w = new Option("w", "word-seperator", true, "the symbol, which seperates words. "
				+ "Default is \" \".");
		w.setArgName("string");
		options.addOption(w);
		
		Option m = new Option("m", "force-punctuation-marks", true, "if true, this will force punctuation "
				+ "marks like '.',';','`' and ',' to be seperated words, regardless if they are "
				+ "surrounded by word-seperators or not. Default is true.");
		m.setArgName("boolean");
		options.addOption(m);
		
		Option e = new Option("e", "sentence-end", true, "This word ends a sentence. Default is '.'.");
		e.setArgName("string");
		options.addOption(e);
		
		Option d = new Option("d", "export-dot", true, "if true, export to *.dot format. Default is true.");
		d.setArgName("boolean");
		options.addOption(d);
		
		Option p = new Option("p", "export-plain", true, "if true, export to *.plain format. "
				+ "Default is false.");
		p.setArgName("boolean");
		options.addOption(p);
		
		Option s = new Option("s", "export-sentences", true, "if true, export to *.sentence format. "
				+ "Lists every matching sentence. Default is true.");
		s.setArgName("boolean");
		options.addOption(s);
		
		options.addOption("v", "verbose", false, "");
	}
	
	/** Gets an instance of CommandLine and produces a Settings object out of it. The settings object
	 * holds all the information, the program instance needs.
	 * @param cmd
	 * @return
	 */
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
