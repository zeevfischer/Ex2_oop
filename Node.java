import api.GeoLocation;
import api.NodeData;

import java.util.ArrayList;


public class Node implements NodeData{

    private final int key;//id
    private location location;
    private double weight;
    private int tag;

    private ArrayList<Edge> in;
    private ArrayList<Edge> out;



    public Node(int key,location location)
    {
        this.key=key;
        this.location=location;
        this.tag=0;
        this.weight=-1;
    }
    public Node(NodeData n)
    {
        this.key=n.getKey();
        this.location=new location(n.getLocation());
        this.weight=n.getWeight();
        this.tag=n.getTag();

        this.in=new ArrayList<>();
        this.out=new ArrayList<>();
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location.set_location(p.x(),p.y(),p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        if(this.weight==-1)
        {
            this.weight=w;
        }
        else
        {
            this.weight=Math.min(this.weight,w);
        }
    }

    @Override
    public String getInfo()
    {
        return null;
    }

    @Override
    public void setInfo(String s)
    {

    }

    @Override
    public int getTag()
    {
        return this.tag;
    }

    @Override
    public void setTag(int t)
    {
        this.tag=t;
    }

    // my functions
    public ArrayList<Edge> getInlist()
    {
        return this.in;
    }
    public ArrayList<Edge> getOutlist()
    {
        return this.out;
    }
    public void removeEdge(Edge e)
    {
        this.out.remove(e);
        this.in.remove(e);
    }

}
