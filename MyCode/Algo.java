package MyCode;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Algo implements DirectedWeightedGraphAlgorithms{
    private DirectedWeightedGraph G;
    private ArrayList<Integer> path;

    @Override
    public void init(DirectedWeightedGraph g)
    {
        this.G =new Graph(g);
    }

    @Override
    public DirectedWeightedGraph getGraph()
    {
        return this.G;
    }

    @Override
    public DirectedWeightedGraph copy()
    {
        DirectedWeightedGraph Copy = new Graph(this.G);
        return Copy;
    }

    @Override
    public boolean isConnected()
    {
        // reset
        Iterator<NodeData> I = this.G.nodeIter();
        NodeData temp = I.next();
        temp.setTag(0);
        while (I.hasNext())
        {
            I.next().setTag(0);
        }
        // this meens that there is a path from the first to all of the Nodes
        //algo
        DFS(this.getGraph(),temp);
        //reset
        I = this.G.nodeIter();
        //check
        while (I.hasNext())
        {
            if(I.next().getTag() == 0)
            {
                return false;
            }
        }
        // new we check if there is a path to the first Node;
        DirectedWeightedGraph Revers = reverse();
        I=Revers.nodeIter();
        temp=I.next();
        temp.setTag(0);
        while (I.hasNext())
        {
            I.next().setTag(0);
        }
        DFS(Revers,temp);
        while (I.hasNext())
        {
            if(I.next().getTag() == 0)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest)
    {
        if(src==dest)
        {
            return 0;
        }
        dijkstra(src,dest);
        return this.G.getNode(dest).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest)
    {
        dijkstra(src,dest);
        ArrayList<NodeData> shortestPath = new ArrayList<>();
        shortestPath.add(this.G.getNode(dest));
        int temp =this.G.getNode(dest).getTag();
        while (this.G.getNode(src) != shortestPath.get(shortestPath.size()-1))
        {
            shortestPath.add(this.getGraph().getNode(temp));
            temp=this.G.getNode(temp).getTag();
        }
        return shortestPath_revers(shortestPath);
    }
    public List<NodeData> shortestPath_revers(List<NodeData> path)
    {
        ArrayList<NodeData> shortestPath_revers = new ArrayList<>();
        for(int i=path.size()-1; i >= 0 ;i--)
        {
            shortestPath_revers.add(path.get(i));
        }
        return shortestPath_revers;
    }

    @Override
    public NodeData center()
    {
        Iterator<NodeData> Nodes = this.G.nodeIter();
        double dist=Integer.MAX_VALUE;
        int idNode = -1;
        if(!isConnected())
        {
            return null;
        }
        else
        {
            double temp_min1=0;
            double temp_min2=0;
            while (Nodes.hasNext())
            {
                Iterator<NodeData> run = this.G.nodeIter();
                NodeData cur =Nodes.next();
                temp_min2=0;
                while (run.hasNext())
                {
                    NodeData runer = run.next();
                    temp_min1 = shortestPathDist(cur.getKey(),runer.getKey());
                    if(temp_min1 > temp_min2)
                    {
                        temp_min2=temp_min1;
                    }
                }
                //here i get the biggest min from cur
                if(temp_min2 < dist)
                {
                    dist = temp_min2;
                    idNode = cur.getKey();
                }
            }
            return this.G.getNode(idNode);
        }
    }

//    @Override
//    public List<NodeData> tsp(List<NodeData> cities)
//    {
//        DirectedWeightedGraph city = new Graph(cities,this.G);
//        Iterator<NodeData> zero =city.nodeIter();
//        while (zero.hasNext())
//        {
//            zero.next().setWeight(-1);
//        }
//        ArrayList<NodeData> tsp = new ArrayList<>();
//        tsp.add(cities.get(0));
//        city.getNode(0).setWeight(1);
//        boolean stop =true;
//        Iterator<EdgeData> Edges = city.edgeIter(cities.get(0).getKey());
//        while (tsp.size() != cities.size())
//        {
//            PriorityQueue<EdgeData> choos = new PriorityQueue(cities.size(),new NodeComparator(city));
//            while (Edges.hasNext())
//            {
//                EdgeData temp =Edges.next();
//                choos.add(temp);
//            }
//            EdgeData temp =choos.poll();
//            while(temp.getDest() == tsp.get(tsp.size()-1).getKey() || city.getNode(temp.getDest()).getWeight() != -1)
//            {
//                temp = choos.poll();
//            }
//            tsp.add(city.getNode(temp.getDest()));
//            city.getNode(temp.getDest()).setWeight(1);
//            Edges = city.edgeIter(cities.get(temp.getDest()).getKey());
//        }
//        return tsp;
//    }
    @Override
    public List<NodeData> tsp(List<NodeData> cities)
    {
        List<NodeData> p =new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for(int i =0;i<cities.size();i++)
        {
            temp.add(cities.get(i).getKey());
        }

        List<NodeData> shortestpath =new ArrayList<>();
        NodeData cur = cities.get(0);
        p.add(this.G.getNode(temp.get(0)));
        temp.remove(0);
        while (!temp.isEmpty())
        {
            double shortestDist = Integer.MAX_VALUE;
            int idShort = -1;
            int location = -1;
            for(int i = 0 ; i < temp.size();i++)
            {
                int key =temp.get(i);
                if(shortestPathDist(cur.getKey(),key) < shortestDist)
                {
                    shortestDist = shortestPathDist(cur.getKey(),key);
                    idShort = key;
                    location = i;
                }
            }
            shortestpath = shortestPath(cur.getKey(),idShort);
            shortestpath.remove(0);
            while (!shortestpath.isEmpty())
            {
                p.add(shortestpath.get(0));
                shortestpath.remove(0);
            }
            cur = cities.get(temp.get(location));
            temp.remove(temp.get(location));
        }
        return p;
    }

    @Override
    public boolean save(String file)
    {
        JSONObject ans=new JSONObject();
        JSONArray edges=new JSONArray();
        JSONArray nodes=new JSONArray();
        Iterator<EdgeData> iterEdges = this.G.edgeIter();
        while(iterEdges.hasNext())
        {
            EdgeData e = iterEdges.next();
            JSONObject edge=new JSONObject();
            edge.put("src",e.getSrc());
            edge.put("w",e.getWeight());
            edge.put("dest",e.getDest());
            edges.add(edge);
        }
        Iterator<NodeData> iterNodes = this.G.nodeIter();
        while (iterNodes.hasNext())
        {
            NodeData v = iterNodes.next();
            // {
            //      "pos": "35.19381366747377,32.102419275630254,0.0",
            //      "id": 16
            //    }
            JSONObject vertex=new JSONObject();
            vertex.put("pos",v.getLocation().toString());
            vertex.put("id",v.getKey());
        }
        ans.put("Edges",edges);
        ans.put("Nodes",nodes);
        try (FileWriter writer = new FileWriter(file)) {
            //We can write any JSONArray or JSONObject instance to the file
            writer.write(ans.toJSONString());
            writer.flush();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean load(String file)
    {
        try
        {
            DirectedWeightedGraph ans = new Graph();

            // parsing file json_file
            Object obj = new JSONParser().parse(new FileReader(file));

            // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;
            //////reading the nodes
            JSONArray nodes = (JSONArray) jo.get("Nodes");
            Iterator i= nodes.iterator();
            while (i.hasNext())
            {
                HashMap<String,Object> map= (HashMap<String, Object>) i.next();
                String pos= (String) map.get("pos");
                int id= (int) ((long)map.get("id"));
                String[]position=pos.split(",");
                location l=new location(Double.parseDouble(position[0])
                        ,Double.parseDouble(position[1])
                        ,Double.parseDouble(position[2]));
                NodeData node=new Node(id,l);
                ans.addNode(node);
            }
            ///////reading the edges
            JSONArray edges= (JSONArray) jo.get("Edges");
            i= edges.iterator();
            while (i.hasNext())
            {
                HashMap<String,Object> map= (HashMap<String, Object>) i.next();
                int src= (int)(long) map.get("src");
                int dest=(int)(long) map.get("dest");
                double w=(double) map.get("w");
                ans.connect(src,dest,w);
            }
            this.init(ans);
            return true;
        }
        catch (IOException e1)
        {
            return false;
        }
        catch(ParseException e2)
        {
            return false;
        }

    }
    //////////////////////////my functions
    public void DFS (DirectedWeightedGraph G,NodeData N)
    {
        N.setTag(1);
        Iterator<EdgeData> I = G.edgeIter(N.getKey());
        while (I.hasNext())
        {
            EdgeData cur_edge = I.next();
            NodeData next_node = G.getNode(cur_edge.getDest());
            if (next_node.getTag() == 0)
            {
                DFS(G, next_node);
            }
        }
    }
    private DirectedWeightedGraph reverse() {
        DirectedWeightedGraph new_Graph = new Graph();
        Iterator<NodeData> Node = this.G.nodeIter();
        Iterator<EdgeData> Edge = this.G.edgeIter();
        //Node loop
        while (Node.hasNext()) {
            NodeData N = Node.next();
            NodeData newNode = new Node(N);
            new_Graph.addNode(newNode);
        }
        //Edge loop
        while (Edge.hasNext()) {
            EdgeData E = Edge.next();
            new_Graph.connect(E.getDest(), E.getSrc(), E.getWeight());
        }
        return new_Graph;
    }

    // set all weight to max value exept the first
    public void start_dijkstra(int src , int dest)
    {
        Iterator<NodeData> I = this.G.nodeIter();
        while (I.hasNext())
        {
            NodeData temp =I.next();
            temp.setWeight(Integer.MAX_VALUE);
            System.out.print("");
        }
        this.G.getNode(src).setWeight(0);
    }
    //this is the start of the queue with the Edges frome the first Node
    public void start_dijkstra_queue(int src,PriorityQueue<EdgeData> DIJ)
    {
        Iterator<EdgeData> SrcEdge = this.G.edgeIter(src);
        while (SrcEdge.hasNext())
        {
            EdgeData runer = SrcEdge.next();
            NodeData runner_dest = this.G.getNode(runer.getDest());
            //cur weight > src + Edge
            if(this.G.getNode(runer.getDest()).getWeight() > runer.getWeight()+this.G.getNode(runer.getSrc()).getWeight())
            {
                //this sets the weight of the Node not the EDGE
                this.G.getNode(runer.getDest()).setWeight(runer.getWeight()+this.G.getNode(runer.getSrc()).getWeight());
                runner_dest.setTag(runer.getSrc());

            }
            DIJ.add(runer);
        }
    }
    public void dijkstra(int src, int dest)
    {
        this.path = new ArrayList<Integer>();
        path.add(src);
        start_dijkstra(src,dest);
        PriorityQueue<EdgeData> DIJ =new PriorityQueue<>(this.G.nodeSize(),new NodeComparator(this.G));
        start_dijkstra_queue(src,DIJ);
        // the algorithm that starts running
        int last_src;
        while (!DIJ.isEmpty())
        {
            EdgeData EdgeFromDIJ = DIJ.poll();
            Iterator<EdgeData> SrcEdge = this.G.edgeIter(EdgeFromDIJ.getDest());
            while (SrcEdge.hasNext())
            {
                EdgeData runer = SrcEdge.next();
                //cur weight > src + Edge
                //to make shore you dont go back and forth
                double runner_dest_weight = this.G.getNode(runer.getDest()).getWeight();
                double runner_weight_plus_Edge_src = runer.getWeight()+this.G.getNode(runer.getSrc()).getWeight();
                if(runner_dest_weight >= runner_weight_plus_Edge_src && runer.getDest() != EdgeFromDIJ.getSrc())
                {
                    NodeData runner_dest = this.G.getNode(runer.getDest());
                    runner_dest.setWeight(runner_weight_plus_Edge_src);
                    runner_dest.setTag(runer.getSrc());
                    DIJ.add(runer);

//                    if(path.size() >= 1)
//                    {
//                        update(EdgeFromDIJ.getSrc());
//                    }
//                    if(this.path.get(this.path.size()-1) != EdgeFromDIJ.getSrc())
//                    {
//                        path.add(EdgeFromDIJ.getSrc());
//                    }
//                    if(this.path.get(this.path.size()-1) != EdgeFromDIJ.getDest())
//                    {
//                        path.add(EdgeFromDIJ.getDest());
//                    }
                }
            }
            if(EdgeFromDIJ.getDest()==dest)
            {
                break;
            }
        }
        // at the end each Node now has a weight that represents its shortest dist not the path itself
    }
    public void update(int src)
    {
        while(path.size() > 1 && path.get(path.size()-1) != src)
        {
            path.remove(path.size()-1);
        }
    }
    public void print_path()
    {
        for(int i=0;i<this.path.size();i++)
        {
            System.out.print(this.path.get(i)+" -> ");
        }
    }
    public class NodeComparator implements Comparator<EdgeData>{
        private DirectedWeightedGraph G;
        public NodeComparator(DirectedWeightedGraph copy)
        {
            this.G=copy;
        }
        @Override
        public int compare(EdgeData o1, EdgeData o2) {
            if(o1.getWeight()+this.G.getNode(o1.getSrc()).getWeight() < o2.getWeight()+this.G.getNode(o2.getSrc()).getWeight())
            {
                return -1;
            }
            else if(o1.getWeight()+this.G.getNode(o1.getSrc()).getWeight() > o2.getWeight()+this.G.getNode(o2.getSrc()).getWeight())
            {
                return  1;
            }
            return 0;
        }
    }
}
