package hr.fer.zemris.java.gui.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTests {
	@Test
	public void testRCparametersExceptionsWork() {
		CalcLayout lay= new CalcLayout();
		JComponent comp= new JLabel();
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(-2, 7)));
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(5, -7)));
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(7, 2)));
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(5, 8)));
	}
	
	@Test
	public void testComponentAtThatPositionAlreadyAddedException() {
		CalcLayout lay= new CalcLayout();
		JComponent comp= new JLabel();
		lay.addLayoutComponent(comp, new RCPosition(2, 5));
		assertThrows(CalcLayoutException.class, ()->lay.addLayoutComponent(comp, new RCPosition(2, 5)));
	}
	
	@Test
	public void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPreferredSizeSecond() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();

		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}
