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
