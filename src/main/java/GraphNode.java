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

import java.util.ArrayList;
import java.util.HashMap;


/** Node class for tree implementation.
 * @author justin
 *
 */
public class GraphNode {
	private String id;
	private String caption;
	private GraphNode parent;
	private ArrayList<GraphNode> children = new ArrayList<GraphNode>();
	
	public GraphNode(String id, String caption) {
		id = id.replaceAll("\\$", "");
		caption = caption.replaceAll("\\$", "");
		this.id = id;
		this.caption = caption;
	}
	
	public GraphNode(String id, String caption, GraphNode parent) {
		id = id.replaceAll("\\$", "");
		caption = caption.replaceAll("\\$", "");
		this.id = id;
		this.caption = caption;
		this.parent = parent;
	}
	
	public void addChild(GraphNode child) {
		this.children.add(child);
	}
	
	public void addChildren(ArrayList<GraphNode> children) {
		for(GraphNode x : children) {
			x.setParent(this);
		}
		this.children.addAll(children);
	}
	
	public String getCaption() {
		return caption;
	}
	public GraphNode getParent() {
		return parent;
	}

	public ArrayList<GraphNode> getChildren() {
		return children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParent(GraphNode parent) {
		this.parent = parent;
	}

	public void simplifyTree() {
		HashMap<String, GraphNode> known = new HashMap<String, GraphNode>();
		GraphNode foundNode = null;
		ArrayList<GraphNode> tempchildren = new ArrayList<GraphNode>();
		tempchildren.addAll(this.getChildren());
		for(GraphNode x : tempchildren) {
			foundNode = known.get(x.getCaption());
			if(foundNode!=null) {
				foundNode.addChildren(x.getChildren());
				this.children.remove(x);
			} else {
				known.put(x.getCaption(), x);
			}
		}
		for(GraphNode x : this.children) {
			x.simplifyTree();
		}
	}

	@Override
	public String toString() {
		return "GraphNode [caption=" + caption + "]";
	}
	
	
}
