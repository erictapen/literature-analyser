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

/** Import and Export functionality for .dot-files which are already sorted
 * and are therefore faster readable. There are only .dot-files with a single graph allowed!
 * There is a lot of code just pasted from RawGraph.java
 * @author justin
 *
 */
public class SortedGraphExport {

	/** Exports the graph! Every data, which is determined by now will be written into the file
	 * @param root The rootNode where to start. Every other node with posx=0.0, posy=0.0 
	 * will be seen as without position data!
	 * @param ofile Filename where to export. File must exist!
	 * @param minTreeSize 
	 */
	public static void exportFile(GraphNode root, String ofile) {
		
		System.out.println("Starting sorted DOT export of " + root.getCaption() + " to " + ofile);
		try{
			FileWriter writer = new FileWriter(ofile);
			
			writer.append(  "digraph " + root.getCaption() + " {\n");
			ArrayList<GraphNode> togo = new ArrayList<GraphNode>();
			ArrayList<GraphNode> togo2 = new ArrayList<GraphNode>();
			togo.addAll(root.getChildren());
			while(!togo.isEmpty()) {
				for(GraphNode x : togo) {
					String append = "\t%parentID [caption=\"%parentCaption\"] <-- %childID [caption=\"%childCaption\"]\n";
					append = append.replaceAll("%parentID", x.getParent().getId());
					append = append.replaceAll("%parentCaption", x.getParent().getCaption());
					append = append.replaceAll("%childID", x.getId());
					append = append.replaceAll("%childCaption", x.getCaption());
					writer.append(append);
					togo2.addAll(x.getChildren());
				}
				togo.clear();
				togo.addAll(togo2);
				togo2.clear();
			}
			
			writer.append("}");
			writer.close();
		} catch(IOException e)
		{
			System.out.println("Problem occured:");
			e.printStackTrace();
		}
		System.out.println("Export completed.");
	}
}
