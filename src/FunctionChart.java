import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.lang.*;
public class FunctionChart
{
    final static double A = 5,
    pi = 3.1415,
    omega = 2*pi,
    rho = 1.21,
    S = 3,
    u = 10,
    min = 0,
    max = 40;
    public static double function(double x /*空间位置*/, double E, double t /*时间*/)
    {
        return rho*S*Math.pow(A,2)*Math.pow(omega,2)*Math.pow(Math.sin(omega*(t-x/u)),2);
    }
    public static double R_Kmethod(double x, double y, double t,double h)
    {
        double k1 = function (x , y ,t) ;
        double k2 = function ( x + h /2 , y + h /2 * k1,t ) ;
        double k3 = function ( x + h /2 , y + h /2 * k2 ,t) ;
        double k4 = function ( x + h , y + h * k3 ,t) ;
        return y + h * ( k1 + 2* k2 + 2* k3 + k4 ) / 6;
    }

    public static double accurate(double a, double b, double t)
    {
        return 0.5*rho * S * Math.pow(A, 2) * Math.pow(omega, 2) * ((b - a) + (u / (2 * omega)) * (Math.sin(2 * omega * (t - (b / u))) - Math.sin(2 * omega * (t - (a / u)))));
    }
    public static void main(String[] args)
    {

        final double h = 1.0;
        double t = 4;
        double x = 0;
        double y = 0;
        int n =  (int)(max-min)/(int)h;
        XYSeries series = new XYSeries("xySeries");
        System.out.println("x = " + String.format("%.6f",x ) + " y = " + String.format("%.6f",y ));
        for(int i = 0; i <= n; i++)
        {
            y = R_Kmethod(x,y,t,h);
            x += h;

            System.out.println("x = " + String.format("%.6f",x ) + " y = " + String.format("%.6f",y )+" accurate = "+
                    String.format("%.6f",accurate(0,x,t))
                    +" ERROR="+String.format("%.6f",Math.abs(accurate(0,x,t)-y))
            );
            series.add(x,y);

        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart
                (
                "the relationship between cut-off length and wave wave mechanical energy", // chart title
                "x", // x axis label
                "E", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                false, // tooltips
                false // urls
        );

        ChartFrame frame = new ChartFrame("Wave mechanical energy", chart);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}