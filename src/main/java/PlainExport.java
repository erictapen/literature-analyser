import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Exports a wordlist with a word in every line. Primary for debugging reasons
 *
 */
public class PlainExport {
	
	public static void exportFile(ArrayList<String> wordList, String ofile) {
		
		System.out.println("Starting plain export of " + wordList.get(0) + " to " + ofile);
		try{
			FileWriter writer = new FileWriter(ofile);
			
			for(String x : wordList) {
				writer.append(x + "\n");
			}
			writer.close();
		} catch(IOException e)
		{
			System.out.println("Problem occured:");
			e.printStackTrace();
		}
		System.out.println("Export completed.");
	}
}
