import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Exports a wordlist with a sentence in every line. Primary for debugging reasons
 *
 */
public class SentenceExport {
	
	public static void exportFile(ArrayList<ArrayList<String>> sentences, String ofile) {
		
		System.out.println("Starting sentence export of " + " to " + ofile);
		try{
			FileWriter writer = new FileWriter(ofile);
			
			for(ArrayList<String> lx : sentences) {
				for(String x : lx) {
					writer.append(x + " ");
				}
				writer.append("\n");
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
