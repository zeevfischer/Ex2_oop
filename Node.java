import api.GeoLocation;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;


public class Node implements NodeData{

    private final int key;//id
    private GeoLocation location;
    private double weight;
    private int tag;

    public Node(int key, GeoLocation loc)
    {
        this.key=key;
        this.location=new location(loc);
        this.tag=0;
        this.weight=-1;
    }
    public Node(NodeData n)
    {
        this.key=n.getKey();
        this.location=new location(n.getLocation());
        this.weight=n.getWeight();
        this.tag=n.getTag();
    }

    @Override
    public int getKey()
    {
        return this.key;
    }

    @Override
    public GeoLocation getLocation()
    {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p)
    {
        this.location=new location(p);
    }

    @Override
    public double getWeight()
    {
        return this.weight;
    }

    @Override
    public void setWeight(double w)
    {
        this.weight=w;
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
}
