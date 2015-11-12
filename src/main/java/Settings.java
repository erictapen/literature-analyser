
public class Settings {
	private String inputFile;
	private String outputFile;
	private String rootCaption = "I";
	private boolean rootMustOpenSentence = true;
	private String wordSeperator = " ";
	private boolean forcePunctuationMarks = true;
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
	
	
	
}
