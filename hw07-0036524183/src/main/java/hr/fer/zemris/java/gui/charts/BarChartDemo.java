package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Frame for drawing {@link BarChart}.
 * @author Antonio
 *
 */
public class BarChartDemo extends JFrame {
	
	private static String putanja;
	private static final long serialVersionUID = 1L;
	private static BarChart bc;

	/**
	 * Basic constructor.
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");

		initGUI();
		setSize(800,500);
	}
	
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		BarChartComponent bcc= new BarChartComponent(bc);
		this.add(bcc, BorderLayout.CENTER);

		JLabel labela= new JLabel(putanja,SwingConstants.CENTER);
		
		this.add(labela, BorderLayout.NORTH);
	}


	public static void main(String[] args) {
		List<XYValue> objects = new ArrayList<>();
		String xDescription;
		String yDescription;
		int minY;
		int maxY;
		int razmak;
		putanja=args[0];
		try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
			String line;
			line = reader.readLine();

			xDescription = line;

			line = reader.readLine();
			yDescription = line;

			line = reader.readLine();
			String[] splits = line.split(" ");

			for (String split : splits) {
				String[] values = split.split(",");
				objects.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
			}

			minY = Integer.parseInt(reader.readLine());
			maxY = Integer.parseInt(reader.readLine());
			razmak = Integer.parseInt(reader.readLine());
			
			bc= new BarChart(objects, xDescription, yDescription, minY, maxY, razmak);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.setVisible(true);
		});

	}
}
