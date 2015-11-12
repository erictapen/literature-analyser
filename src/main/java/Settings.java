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


public class Settings {
	private String inputFile;
	private String outputFile;
	private String rootCaption = "I";
	private boolean rootMustOpenSentence = true;
	private String wordSeperator = " ";
	private boolean forcePunctuationMarks = true;
	private String sentenceEnder = ".";
	private boolean exportDOT = true;
	private boolean exportPLAIN = false;
	private boolean exportSENTENCE = true;
	private boolean verbose = false;
	
	public Settings() {
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getRootCaption() {
		return rootCaption;
	}

	public void setRootCaption(String rootCaption) {
		this.rootCaption = rootCaption;
	}

	public String getWordSeperator() {
		return wordSeperator;
	}

	public void setWordSeperator(String wordSeperator) {
		this.wordSeperator = wordSeperator;
	}

	public boolean isExportDOT() {
		return exportDOT;
	}

	public void setExportDOT(boolean exportDOT) {
		this.exportDOT = exportDOT;
	}

	public boolean isExportPLAIN() {
		return exportPLAIN;
	}

	public void setExportPLAIN(boolean exportPLAIN) {
		this.exportPLAIN = exportPLAIN;
	}

	public boolean isExportSENTENCE() {
		return exportSENTENCE;
	}

	public void setExportSENTENCE(boolean exportSENTENCE) {
		this.exportSENTENCE = exportSENTENCE;
	}

	public boolean isRootMustOpenSentence() {
		return rootMustOpenSentence;
	}

	public void setRootMustOpenSentence(boolean rootMustOpenSentence) {
		this.rootMustOpenSentence = rootMustOpenSentence;
	}

	public boolean isForcePunctuationMarks() {
		return forcePunctuationMarks;
	}

	public void setForcePunctuationMarks(boolean forcePunctuationMarks) {
		this.forcePunctuationMarks = forcePunctuationMarks;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public String getSentenceEnder() {
		return sentenceEnder;
	}

	public void setSentenceEnder(String sentenceEnder) {
		this.sentenceEnder = sentenceEnder;
	}
	
	
	
}
