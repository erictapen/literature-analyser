import java.util.ArrayList;


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

	public void simplifyTree() {
		ArrayList<GraphNode> known = new ArrayList<GraphNode>();
		ArrayList<GraphNode> toRemove = new ArrayList<GraphNode>();
		for(GraphNode x : this.children) {
			GraphNode foundNode = null;
			for(GraphNode y : known) {
				if(x.getCaption().equals(y.getCaption())) foundNode = y;
			}
			if(foundNode!=null) {
				foundNode.addChildren(x.getChildren());
				toRemove.add(foundNode);
			} else {
				known.add(x);
			}
		}
		this.children.removeAll(toRemove);
		for(GraphNode x : this.children) x.simplifyTree();
	}
}
