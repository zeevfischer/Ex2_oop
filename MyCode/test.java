package MyCode;

import api.DirectedWeightedGraph;
import api.GeoLocation;
import api.NodeData;

import java.util.*;

public class test
{
    DirectedWeightedGraph graph;
    Algo algoGraph;
    test()
    {
        graph = new Graph();
        algoGraph =new Algo();
        algoGraph.init(graph);
    }
    public static void main(String[] args) {
//        new test();
        DirectedWeightedGraph graph=new Graph();
        Algo alg=new Algo();
        Node n1,n2,n3,n4,n5,n6;
        location a1,a2,a3,a4,a5,a6;
        a1=new location(1,2,0);
        a2=new location(5,1,0);
        a3=new location(2,5,0);
        a4=new location(6,7,0);
        a5=new location(8,4,0);
        a6=new location(5,4,0);
        n1=new Node(0,a1);
        n2=new Node(1,a2);
        n3=new Node(2,a3);
        n4=new Node(3,a4);
        n5=new Node(4,a5);
        n6=new Node(5,a6);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);
        graph.connect(0,2,14);
        graph.connect(2,0,14);
        graph.connect(0,5,9);
        graph.connect(5,0,9);
        graph.connect(0,1,7);
        graph.connect(1,0,7);
        graph.connect(1,5,10);
        graph.connect(5,1,10);
        graph.connect(1,4,15);
        graph.connect(4,1,15);
        graph.connect(2,5,2);
        graph.connect(5,2,2);
        graph.connect(5,4,20);
        graph.connect(4,5,20);
        graph.connect(4,3,60);
        graph.connect(3,4,60);
        graph.connect(2,3,100);
        graph.connect(3,2,100);
        alg.init(graph);
//        System.out.println(graph.getMC());
//        alg.dijkstra(1,3);
//        System.out.println();
//        alg.shortestPathDist(1,3);

//        alg.shortestPathDist(1,2);
//        alg.shortestPathDist(0,3);
//        System.out.println(alg.shortestPathDist(1,2));
//        System.out.println(alg.shortestPathDist(0,3));
//        alg.print_path();
        Iterator<NodeData> a = alg.getGraph().nodeIter();
        List<NodeData> run = new ArrayList<>();
//        List<NodeData> path = alg.shortestPath(2,3);
//        for(int i=0;i<path.size();i++)
//        {
//            System.out.print(path.get(i).getKey()+"->");
//        }
        while (a.hasNext())
        {
            run.add(a.next());
        }
        List<NodeData> test = alg.tsp(run);
        for(int i=0;i<test.size();i++)
        {
            System.out.print(test.get(i).getKey()+"->");
        }

//        NodeData s = alg.center();
//        System.out.println(s.getKey());
//        alg.save("my_algo graf");



    }
}
