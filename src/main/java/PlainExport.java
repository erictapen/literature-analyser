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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Exports a wordlist with a word in every line. Primary for debugging reasons
 *
 */
public class PlainExport {
	
	public static void exportFile(ArrayList<String> wordList, String ofile) {
		
		System.out.println("Starting plain export of " + " to " + ofile);
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
