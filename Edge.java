import api.EdgeData;

public class Edge implements EdgeData {
    private int src;
    private int dest;
    private double weight;
    private int tag;

    public Edge(int _src,int _dest,double _weight)
    {
        this.src=_src;
        this.dest=_dest;
        this.weight=_weight;
        this.tag=0;
    }
    public Edge(EdgeData E)
    {
        this.src=E.getSrc();
        this.dest=E.getDest();
        this.weight=E.getWeight();
        this.tag=E.getTag();
    }
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}