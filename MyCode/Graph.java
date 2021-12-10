package MyCode;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Graph implements DirectedWeightedGraph{

    //this will return a node according to its id
    //id - NodeData
    private HashMap<Integer,NodeData> node;
    ////////////////////////////////////////////
    //this will return the edge according to its "src,dest"
    //"src,dest" - EdgeData
    private HashMap<String,EdgeData> edge;
    ////////////////////////////////////////////
    //hash of hash
    //key -> hash of keys by the type of String "src,dest" -> gives me a specific EdgeData
    private HashMap<Integer,HashMap<String,EdgeData>> in;
    private HashMap<Integer,HashMap<String,EdgeData>> out;
    // counter
    private int mc;

    public Graph(List<NodeData> cities,DirectedWeightedGraph G)
    {
        for(int i =0;i<cities.size();i++)
        {
            int key =cities.get(i).getKey();
            addNode(G.getNode(key));
        }
        for(int i =0;i<cities.size();i++)
        {
            int key =cities.get(i).getKey();
            Iterator<EdgeData> Edges = G.edgeIter(key);
            while (Edges.hasNext())
            {
                EdgeData temp = Edges.next();
                connect(temp.getSrc(),temp.getDest(),temp.getWeight());
            }
        }
    }
    public Graph()
    {
        this.node = new HashMap<>();
        this.edge = new HashMap<>();
        this.in = new HashMap<>();
        this.out = new HashMap<>();
        mc=0;
    }

    public Graph(DirectedWeightedGraph G)
    {
        Iterator<NodeData> Node = G.nodeIter();
        this.node = new HashMap<>();
        while (Node.hasNext())
        {
            NodeData temp =new Node(Node.next());
            addNode(temp);
        }
        Iterator<EdgeData> Edge = G.edgeIter();
        while (Edge.hasNext())
        {
            EdgeData temp = new Edge(Edge.next());
            connect(temp.getSrc(),temp.getDest(),temp.getWeight());
        }
    }

    @Override
    public NodeData getNode(int key) {
        if(this.node.get(key)!= null)
        {
            return this.node.get(key);
        }
        else
        {
            return null;
        }
    }
    // Note to me src and dest are ids on nodes not actual locations
    @Override
    public EdgeData getEdge(int src, int dest)
    {
//        return this.test.get(src).get(""+src+","+dest+"");
        if(this.edge.get(""+src+","+dest+"") != null)
        {
            return this.edge.get("" + src + "," + dest + "");
        }
        else
        {
            return null;
        }
    }

    @Override
    public void addNode(NodeData n)
    {
        if(this.node==null)
        {
            this.node=new HashMap<Integer,NodeData>();
        }
        this.node.put(n.getKey(),n);
        mc++;
    }
    // src and dest are both ids of node
    @Override
    public void connect(int src, int dest, double w)
    {
        if(this.edge == null)
        {
            this.edge=new HashMap<String,EdgeData>();
        }
        if(this.out==null)
        {
            this.out=new HashMap<Integer,HashMap<String,EdgeData>>();
        }
        if(this.in==null)
        {
            this.in=new HashMap<Integer,HashMap<String,EdgeData>>();
        }
        mc++;
        if(this.node.get(src) != null && this.node.get(dest)!= null)
        {
            EdgeData E = new Edge(src,dest,w);
            this.edge.put(""+src+","+dest+"",E);
            ////////////////this will have all Eges from has node
            if(this.out.get(src) == null)
            {
                HashMap<String,EdgeData> temp =new HashMap<String,EdgeData>();
                this.out.put(src,temp);
                this.out.get(src).put(""+src+","+dest+"",E);
            }
            else
            {
                this.out.get(src).put(""+src+","+dest+"",E);
            }

            if(this.in.get(dest) == null)
            {
                HashMap<String,EdgeData> temp =new HashMap<String,EdgeData>();
                this.in.put(dest,temp);
                this.in.get(dest).put(""+src+","+dest+"",E);
            }
            else
            {
                this.in.get(dest).put(""+src+","+dest+"",E);
            }
        }
    }

    @Override
    public Iterator<NodeData> nodeIter()
    {
        return new NodeIterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter()
    {
        return new EdgeIterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id)
    {
        return new EdgeIteratorByNode(node_id);
    }

    @Override
    public NodeData removeNode(int key)
    {
        mc++;
        NodeData temp_N = new Node(this.node.get(key));
        this.node.remove(key);

        for(EdgeData E : this.out.get(key).values())
        {
            this.edge.remove(""+E.getSrc()+","+E.getDest()+"");
        }
        for(EdgeData E : this.in.get(key).values())
        {
            this.edge.remove(""+E.getSrc()+","+E.getDest()+"");
        }
        this.in.remove(key);
        this.out.remove(key);

        return temp_N;
    }

    @Override
    public EdgeData removeEdge(int src, int dest)
    {
        mc++;
        EdgeData temp = new Edge(this.edge.get(""+src+","+dest+""));
        this.edge.remove(""+src+","+dest+"");
        this.out.get(src).remove(""+src+","+dest+"");
        this.in.get(dest).remove(""+src+","+dest+"");
        return temp;
    }

    @Override
    public int nodeSize()
    {
        return this.node.size();
    }

    @Override
    public int edgeSize()
    {
        return this.edge.size();
    }

    @Override
    public int getMC()
    {
        return this.mc;
    }
    ////////////////////////////////////////////////////getters
    public HashMap<Integer,NodeData> get_node()
    {
        return this.node;
    }
    public HashMap<String,EdgeData> get_edge()
    {
        return this.edge;
    }
    public HashMap<Integer,HashMap<String,EdgeData>> get_in()
    {
        return this.in;
    }
    public HashMap<Integer,HashMap<String,EdgeData>> get_out()
    {
        return this.out;
    }
// Iterator update
    private class NodeIterator implements Iterator<NodeData> {
        private int MC;
        private Iterator<NodeData> I;
        private NodeData temp;

        public NodeIterator()
        {
            //this.mc//this.node
            MC = mc;
            I = node.values().iterator();
        }
        private void isValide()
        {
            if (MC != mc) {
                throw new RuntimeException("the graph was changed");
            }
        }
        @Override
        public boolean hasNext()
        {
            isValide();
            return I.hasNext();
        }
        @Override
        public NodeData next()
        {
            isValide();
            temp = I.next();
            return temp;
        }
        @Override
        public void remove()
        {
            isValide();
            MC++;
            I.remove();
            removeNode(temp.getKey());
        }
    }
    private class EdgeIterator implements Iterator<EdgeData> {
        private int MC;
        private Iterator<EdgeData> I;
        private EdgeData temp;
        //this.mc //this.edge
        public EdgeIterator()
        {
            MC = mc;
            I = edge.values().iterator();
        }
        private void isValide()
        {
            if (MC != mc) {
                throw new RuntimeException("the graph was changed");
            }
        }
        @Override
        public boolean hasNext()
        {
            isValide();
            return I.hasNext();
        }
        @Override
        public EdgeData next()
        {
            isValide();
            temp = I.next();
            return temp;
        }
        @Override
        public void remove()
        {
            isValide();
            MC++;
            I.remove();
            removeEdge(temp.getSrc(), temp.getDest());
        }
    }
    private class EdgeIteratorByNode implements Iterator<EdgeData> {
        private int MC;
        private Iterator<EdgeData> I;
        private EdgeData temp;
        public EdgeIteratorByNode(int node_id)
        {
            //this.mc//this.out
            MC = mc;
            if(out.get(node_id) != null)
            {
                I = out.get(node_id).values().iterator();
            }
            else
            {
                I = null;
            }
        }

        private void isValide()
        {
            if (MC != mc) {
                throw new RuntimeException("the graph was changed");
            }
        }
        @Override
        public boolean hasNext()
        {
            isValide();
            if(I == null)
            {
                return false;
            }
            return I.hasNext();
        }
        @Override
        public EdgeData next()
        {
            isValide();
            temp = I.next();
            return temp;
        }
        @Override
        public void remove()
        {
            isValide();
            MC++;
            I.remove();
            removeEdge(temp.getSrc(), temp.getDest());
        }
    }
}

