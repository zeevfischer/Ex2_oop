import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import api.EdgeData;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph{

    //this will return a node according to its id
    private HashMap<Integer,Node> node;
    //this will return the edge according to its "src,dest"
    private HashMap<String,Edge> edge;
    // counter
    private int mc;

    public Graph()
    {
        node = new HashMap<>();
        mc=0;
    }

    @Override
    public NodeData getNode(int key) {
        return this.node.get(key);
    }
    // Note to me src and dest are ids on nodes not actual locations
    @Override
    public EdgeData getEdge(int src, int dest)
    {
        return this.edge.get(""+src+","+dest+"");
    }

    @Override
    public void addNode(NodeData n)
    {
        Node temp =new Node(n);
        this.node.put(n.getKey(),temp);
    }
    // src and dest are both ids of node
    @Override
    public void connect(int src, int dest, double w)
    {
        if(this.node.get(src) != null && this.node.get(dest)!= null)
        {
            Edge temp = new Edge(src,dest,w);
            this.edge.put(""+src+","+dest+"",temp);
        }
    }

    @Override
    public Iterator<NodeData> nodeIter()
    {
//        return node.values().iterator();

        ArrayList<NodeData> temp = new ArrayList<>();
        node.forEach((k,v)->temp.add(node.get(v)));
        return temp.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter()
    {
        ArrayList<EdgeData> temp = new ArrayList<>();
        edge.forEach((k,v)->temp.add(edge.get(v)));
        return temp.iterator();
    }
    //this will return the edges coming out of the node given now here we can start playing and adding hash maps or lists to deal with this shit

    @Override
    public Iterator<EdgeData> edgeIter(int node_id)
    {
        Node n = this.node.get(node_id);
        ArrayList<EdgeData> temp = new ArrayList<>(n.getOutlist());
        return temp.iterator();

    }

    @Override
    public NodeData removeNode(int key)
    {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest)
    {
        return null;
    }

    @Override
    public int nodeSize()
    {
        return 0;
    }

    @Override
    public int edgeSize()
    {
        return 0;
    }

    @Override
    public int getMC()
    {
        return 0;
    }
}
