package hr.fer.zemris.java.gui.charts;

/**
 * Class representing pair of values( x and y).
 * @author Antonio
 *
 */
public class XYValue {
	private int y;
	private int x;
	/**
	 * Basic constructor.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.y = y;
		this.x = x;
	}
	
	/**
	 * Setter for y.
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Setter for x.
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Getter for y.
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Getter for x.
	 * @return
	 */
	public int getX() {
		return x;
	}

	@Override
	public String toString() {
		return "XYValue [y=" + y + ", x=" + x + "]";
	}
	
	
}
