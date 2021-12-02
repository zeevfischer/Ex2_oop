import api.GeoLocation;
public class location implements GeoLocation{
    private double x;
    private double y;
    private double z;

    //const
    public void set_location(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public location(GeoLocation ot)
    {
        this.x = ot.x();
        this.y = ot.y();
        this.z = ot.z();
    }
    @Override
    public double x()
    {
        return this.x;
    }

    @Override
    public double y()
    {
        return this.y;
    }

    @Override
    public double z()
    {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g)
    {
        double xd=this.x-g.x();
        double yd=this.y-g.y();
        double zd=this.z-g.z();
        xd*=xd;
        yd*=yd;
        zd*=zd;
        double ans=xd+yd+zd;
        return (Math.sqrt(ans));
    }
}
