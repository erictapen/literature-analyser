import java.util.ArrayList;


/** Node class for tree implementation.
 * @author justin
 *
 */
public class GraphNode {
	private String caption;
	private GraphNode parent;
	private ArrayList<GraphNode> children = new ArrayList<GraphNode>();
	
	public GraphNode(String caption) {
		this.caption = caption;
	}
	
	public GraphNode(String caption, GraphNode parent) {
		this.caption = caption;
		this.parent = parent;
	}
	
	public void addChild(GraphNode child) {
		this.children.add(child);
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
	
	

	
}
