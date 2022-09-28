package hr.fer.zemris.java.gui.layouts;

/**
 * Class for row-column position.
 * @author Antonio
 *
 */
public class RCPosition {
	private int row;
	private int column;
	
	/**
	 * Basic constructor.
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row.
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter for row.
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Getter for column.
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Setter for column.
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Parses text in {@link RCPosition}.
	 * @param text
	 * @return
	 */
	public static RCPosition parse(String text) {
		try {
			String[] splits=text.split(",");
			return new RCPosition(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]));
		}catch(Exception e) {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Returns string of position. For example 1,2.
	 * @return
	 */
	public String position() {
		return String.valueOf(row)+","+String.valueOf(column);
	}
}
