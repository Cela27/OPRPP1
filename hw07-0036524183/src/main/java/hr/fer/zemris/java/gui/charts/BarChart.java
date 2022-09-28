package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class representing 
 * @author Antonio
 *
 */
public class BarChart {
	private List<XYValue> objects;
	private String xDescription;
	private String yDescription;
	private int minY;
	private int maxY;
	private int razmak;
	
	/**
	 * Getter for {@link XYValue} objects.
	 * @return
	 */
	public List<XYValue> getObjects() {
		return objects;
	}

	/**
	 * Getter for x description.
	 * @return
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter for y description.
	 * @return
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter for yMin.
	 * @return
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter for yMax.
	 * @return
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Getter za razmak.
	 * @return
	 */
	public int getRazmak() {
		return razmak;
	}

	/**
	 * Basic constructor.
	 * @param objects
	 * @param xDescription
	 * @param yDescription
	 * @param minY
	 * @param maxY
	 * @param razmak
	 */
	public BarChart(List<XYValue> objects, String xDescription, String yDescription, int minY, int maxY, int razmak) {
		if (minY < 0)
			throw new IllegalArgumentException();

		if (minY >= maxY)
			throw new IllegalArgumentException();

		this.objects = objects;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.razmak = razmak;
		
		if ((maxY-minY)%razmak!=0) {
			this.minY= (maxY)%razmak;
		}
		
		for(XYValue value: objects) {
			if(value.getY()<minY)
				throw new IllegalArgumentException();
		}
	}
}
