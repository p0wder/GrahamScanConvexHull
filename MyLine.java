
public class MyLine implements Comparable<MyLine>
{
	MyPoint point1;
	MyPoint point2;
	double slope;
	
	public MyLine(MyPoint local_point1, MyPoint local_point2)
	{
		point1 = local_point1;
		point2 = local_point2;
		
		calculateSlope();
	}
	
	public void calculateSlope()
	{
		slope = (point2.y - point1.y) / (point2.x - point1.x);
	}
	
	@Override
	public int compareTo(MyLine o) 
	{
		//System.out.println("Measuring from " + slope + " to " + o.slope);
		//System.out.println("The answer is " + (int) ((slope - o.slope)*100));
		return (int) ((slope*100 - o.slope*100));
	}
	
	
	public Boolean isItClockwise(MyLine o)
	{
		double point1_x_after = point2.x - point1.x;
		double point1_y_after = point2.y - point1.y;
		double point2_x_after = o.point2.x - o.point1.x;
		double point2_y_after = o.point2.y - o.point1.y;
		
		if(point1_y_after * point2_x_after < point1_x_after * point2_y_after)
			return false;
		else
			return true;
	}
}
