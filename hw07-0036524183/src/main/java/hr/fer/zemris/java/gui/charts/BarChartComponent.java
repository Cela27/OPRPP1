package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Bar component for {@link BarChart}.
 * @author Antonio
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int RAZMAK_OD_RUBA = 15;
	public static int VELICINA_FONTA_OPIS;
	public static int VELICINA_FONTA_BROJKE = 14;
	public static final int DULJINA_CRTICE = 5;
	private static Font brojke;

	private BarChart barChart;
	private int width;
	private int height;
	
	/**
	 * Basic constructor.
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;

	}
	
	/**
	 * Method for painting bar chart.
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		BarChartComponent c = new BarChartComponent(barChart);
		height = this.getHeight();
		width = this.getWidth();

		brojke = new Font("Sans Serif", Font.BOLD, VELICINA_FONTA_BROJKE);

		c.draw(g2, height, width);
	}
	
	/**
	 * method for drawing bar chart.
	 * @param g2 Graphics2d for drawing
	 * @param height
	 * @param width
	 */
	public void draw(Graphics2D g2, int height, int width) {
		Font font = g2.getFont();
		VELICINA_FONTA_OPIS = font.getSize();
		drawAxes(g2, height, width);
		drawBars(g2, height, width);
	}

	private void drawBars(Graphics2D g2, int height, int width) {
		Insets insets = this.getInsets();
		
		Color boja=Color.RED;
		Color original= g2.getColor();
		
		int numBars= barChart.getObjects().size();
		
		int maxBrojki = String.valueOf(barChart.getMaxY()).length();
		
		int chartX = (int) (width - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS - RAZMAK_OD_RUBA * 2)
				/ barChart.getObjects().size();
		
		int yMax = barChart.getMaxY();
		int yMin = barChart.getMinY();
		int brojcaniRazmak = barChart.getRazmak();
		
		int pikselniRazmak = (
				height -  2*RAZMAK_OD_RUBA - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS
				- insets.top - insets.bottom) / ((yMax-yMin) / brojcaniRazmak);
		
		
		List<XYValue> objects= barChart.getObjects();
		g2.setColor(boja);
		int jedinicniRazmak= pikselniRazmak/brojcaniRazmak;
		for(int i=0; i<numBars;i++) {
			
			Rectangle rec = new Rectangle(
					RAZMAK_OD_RUBA+VELICINA_FONTA_OPIS+ maxBrojki*VELICINA_FONTA_BROJKE+i*chartX, 
					
					//height-RAZMAK_OD_RUBA-VELICINA_FONTA_OPIS-maxBrojki*VELICINA_FONTA_BROJKE-jedinicniRazmak*(objects.get(i).getY()-barChart.getMinY()), 
					height-RAZMAK_OD_RUBA-VELICINA_FONTA_OPIS-maxBrojki*VELICINA_FONTA_BROJKE-pikselniRazmak*((objects.get(i).getY()-yMin)/brojcaniRazmak)-((objects.get(i).getY()-yMin)%brojcaniRazmak)*jedinicniRazmak, 
					
					chartX-1, 
					
					pikselniRazmak*((objects.get(i).getY()-yMin)/brojcaniRazmak)+((objects.get(i).getY()-yMin)%brojcaniRazmak)*jedinicniRazmak);
			g2.fill(rec);
		}
		g2.setColor(original);
		
	}

	private void drawAxes(Graphics2D g2, int height, int width) {
		Insets insets = this.getInsets();
		
		int maxBrojki=1;
		
		for(XYValue obj: barChart.getObjects()) {
			if(String.valueOf(obj.getX()).length()>maxBrojki) {
				maxBrojki=String.valueOf(obj.getX()).length();
			}
		}
		
		maxBrojki = Math.max(String.valueOf(barChart.getMaxY()).length(), maxBrojki);
		
		int chartX = (int) (width - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS - RAZMAK_OD_RUBA * 2)
				/ barChart.getObjects().size();

		/************* X-OS ******************************/
		String xLabel = barChart.getxDescription();
		int strWidth = (int) g2.getFontMetrics().getStringBounds(xLabel, g2).getWidth();

		g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,
				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top,
				width - RAZMAK_OD_RUBA - insets.right, height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS
						- maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom - insets.top);
		// opis x
		g2.drawString(xLabel,
				((width - 2 * RAZMAK_OD_RUBA - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS) - strWidth) / 2
						+ maxBrojki * VELICINA_FONTA_BROJKE + VELICINA_FONTA_OPIS + RAZMAK_OD_RUBA,
				height - RAZMAK_OD_RUBA);

		// brojke ispod x osi
		Font currFont = g2.getFont();
		g2.setFont(brojke);
		for (int i = 0; i < barChart.getObjects().size(); i++) {
			g2.drawString(String.valueOf(barChart.getObjects().get(i).getX()),
					RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + chartX / 2
							+ chartX * i + insets.left,
					height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - insets.bottom - insets.top);
		}
		g2.setFont(currFont);

		// crtice na x osi
		for (int i = 1; i <= barChart.getObjects().size() + 1; i++) {
			g2.drawLine(
					RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
							+ chartX * (i - 1),
					height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
							- insets.top,
					RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
							+ chartX * (i - 1),
					height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
							- insets.top + DULJINA_CRTICE);
		}

		// strelica
		// produženje
		g2.drawLine(
				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()),

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top,

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()) + DULJINA_CRTICE * 2,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top);

		// doljne krilce
		g2.drawLine(
				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()) + DULJINA_CRTICE * 2,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top,

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()) + DULJINA_CRTICE,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top + DULJINA_CRTICE);

		// gornja krilce
		g2.drawLine(
				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()) + DULJINA_CRTICE * 2,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top,

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
						+ chartX * (barChart.getObjects().size()) + DULJINA_CRTICE,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top - DULJINA_CRTICE);

		/*********************** Y-OS ***************/

		String yLabel = barChart.getyDescription();
		strWidth = (int) g2.getFontMetrics().getStringBounds(yLabel, g2).getWidth();

		g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,
				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- insets.top,
				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,
				RAZMAK_OD_RUBA - insets.top);

		// opis y
		Font font = new Font(null, currFont.getStyle(), currFont.getSize());
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(-90), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		g2.setFont(rotatedFont);
		g2.drawString(yLabel, RAZMAK_OD_RUBA + insets.left,
				(height - 2 * RAZMAK_OD_RUBA - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS - strWidth
						- insets.top - insets.left) / 2 + RAZMAK_OD_RUBA * 2 + maxBrojki * VELICINA_FONTA_BROJKE
						+ VELICINA_FONTA_OPIS);
		g2.setFont(currFont);

		// brojke uz y os
		g2.setFont(brojke);

		int yMax = barChart.getMaxY();
		int yMin = barChart.getMinY();
		int brojcaniRazmak = barChart.getRazmak();

		//bio je razmak*2
		int pikselniRazmak = (
				height -  2*RAZMAK_OD_RUBA - maxBrojki * VELICINA_FONTA_BROJKE - VELICINA_FONTA_OPIS
				- insets.top - insets.bottom) / ((yMax-yMin) / brojcaniRazmak);
		
		for (int i = yMin, j = 0; i <= yMax; i = i + brojcaniRazmak, j++) {
			int xOS=RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS;
			if(String.valueOf(i).length()<maxBrojki) {
				xOS=xOS+(VELICINA_FONTA_BROJKE/2)*(maxBrojki-String.valueOf(i).length());
			}
			g2.drawString(String.valueOf(i), xOS,
					height - RAZMAK_OD_RUBA- VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - pikselniRazmak * j - insets.bottom);
		}
		g2.setFont(currFont);

		// crtice na y osi
		for (int i = 0; i < yMax / brojcaniRazmak + 1; i++) {

			g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,

					height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
							- pikselniRazmak * i,

					RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left
							- DULJINA_CRTICE,

					height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
							- pikselniRazmak * i);
		}

		// strelica
		// produženje
		g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak)),

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak)) - DULJINA_CRTICE * 2);

		// ljevo krilce
		g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak)) - DULJINA_CRTICE*2,

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left - DULJINA_CRTICE,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak))- DULJINA_CRTICE);

		// desno krilce
		g2.drawLine(RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak)) - DULJINA_CRTICE*2,

				RAZMAK_OD_RUBA + VELICINA_FONTA_OPIS + maxBrojki * VELICINA_FONTA_BROJKE + insets.left + DULJINA_CRTICE,

				height - RAZMAK_OD_RUBA - VELICINA_FONTA_OPIS - maxBrojki * VELICINA_FONTA_BROJKE - insets.bottom
						- pikselniRazmak * (yMax / (brojcaniRazmak))-DULJINA_CRTICE);
	}

}
