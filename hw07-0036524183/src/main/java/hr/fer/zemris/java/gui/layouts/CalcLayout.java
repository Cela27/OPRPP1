package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Layout made for calculator.
 */
public class CalcLayout implements LayoutManager2 {

	private static final int BR_REDAKA = 5;
	private static final int BR_STUPACA = 7;
	private int razmak;

	private Map<String, Component> mapa = new HashMap<>();

	/**
	 * Getter za razmak.
	 * 
	 * @return
	 */
	public int getRazmak() {
		return razmak;
	}

	/**
	 * Basic constructor.
	 */
	public CalcLayout() {
		razmak = 0;
	}

	/**
	 * Constructor with
	 * 
	 * @param razmak
	 */
	public CalcLayout(int razmak) {
		this.razmak = razmak;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (Map.Entry<String, Component> e : mapa.entrySet()) {
			if (e.getValue().equals(comp)) {
				mapa.remove(e.getKey());
			}
		}

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, true, false, false);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, false, false, true);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();
		if (ncomponents == 0) {
			return;
		}

		// Total parent dimensions
		Dimension size = parent.getSize();
		int totalW = size.width - (insets.left + insets.right);
		int totalH = size.height - (insets.top + insets.bottom);

		int visak = totalW % BR_STUPACA;

		// Cell dimensions, including padding
		int cellW = (totalW - razmak * (BR_STUPACA + 1)) / BR_STUPACA;
		int cellH = (totalH - razmak * (BR_REDAKA + 1)) / BR_REDAKA;

		for (int i = 0; i < ncomponents; i++) {
			Component c = parent.getComponent(i);

			for (Map.Entry<String, Component> e : mapa.entrySet()) {
				if (e.getValue().equals(c)) {
					RCPosition pos = RCPosition.parse(e.getKey());

					if (e.getKey().equals("1,1")) {
						int x = insets.left + razmak * pos.getColumn();
						int y = insets.top + razmak * pos.getRow();
						int w = cellW * 5 + razmak * 4;
						int h = cellH;

						if (visak > 2) {
							cellW = cellW + 1 * (visak - 2);
							visak = 2;
						}
						c.setBounds(x, y, w, h);

					} else {
						int x = insets.left + razmak + (cellW + razmak) * (pos.getColumn() - 1);
						int y = insets.top + razmak + (razmak + cellH) * (pos.getRow() - 1);
						int w = cellW;
						int h = cellH;
						if (pos.getColumn() != 1 && visak > 0) {
							visak--;
							w++;
						}

						c.setBounds(x, y, w, h);
					}

				}
			}

		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new NullPointerException();
		}
		if (!(constraints instanceof String || constraints instanceof RCPosition)) {
			throw new IllegalArgumentException();
		}
		RCPosition position;

		if (constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		} else {
			position = (RCPosition) constraints;
		}

		if (position.getRow() < 1 || position.getRow() > BR_REDAKA || position.getColumn() < 1
				|| position.getColumn() > BR_STUPACA)
			throw new CalcLayoutException();

		int row = position.getRow();
		int column = position.getColumn();

		if (row == 1 && (column == 2 || column == 3 || column == 4 || column == 5)) {
			throw new CalcLayoutException();
		}

		if (mapa.containsKey(row + "," + column)) {
			throw new CalcLayoutException();
		}

		mapa.put(position.position(), comp);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, false, true, false);
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	private Dimension getLayoutSize(Container parent,boolean isPrefered, boolean isMaximum, boolean isMinimum) {
		Insets insets= parent.getInsets();
		Dimension minSize = new Dimension();
		Dimension maxSize = new Dimension();
		Dimension prefSize = new Dimension();
		
		boolean prviProlaz=true;
		for (Map.Entry<String, Component> e : mapa.entrySet()) {
		
			Component comp = e.getValue();
			if(e.getKey().equals("1,1")) {
				if(prviProlaz) {
					minSize.height=comp.getMinimumSize().height;
					minSize.width=(comp.getMinimumSize().width-razmak*4)/5;
					
					maxSize.height=comp.getMaximumSize().height;
					maxSize.width=(comp.getMaximumSize().width-razmak*4);
					
					prefSize.height=comp.getPreferredSize().height;
					prefSize.width=(comp.getPreferredSize().width-razmak*4)/5;
					prviProlaz=false;
				}
				else {
					minSize.height=Math.min(comp.getMinimumSize().height, minSize.height);
					minSize.width=Math.min((comp.getMinimumSize().width-razmak*4)/5, minSize.width);
					
					maxSize.height=Math.max(comp.getMaximumSize().height, maxSize.height);
					maxSize.width=Math.max((comp.getMaximumSize().width-razmak*4)/5, maxSize.width);
					
					prefSize.height=Math.max(comp.getPreferredSize().height, prefSize.height);
					prefSize.width=Math.max((comp.getPreferredSize().width-razmak*4)/5, prefSize.width);
				}
				continue;
			}
			if(prviProlaz) {
				minSize.height=comp.getMinimumSize().height;
				minSize.width=comp.getMinimumSize().width;
				
				maxSize.height=comp.getMaximumSize().height;
				maxSize.width=comp.getMaximumSize().width;
				
				prefSize.width=comp.getPreferredSize().width;
				prefSize.height=comp.getPreferredSize().height;
				prviProlaz=false;
			}
			else {
				minSize.height=Math.min(comp.getMinimumSize().height, minSize.height);
				minSize.width=Math.min(comp.getMinimumSize().width, minSize.width);
				
				maxSize.height=Math.max(comp.getMaximumSize().height, maxSize.height);
				maxSize.width=Math.max(comp.getMaximumSize().width, maxSize.width);
				
				prefSize.height=Math.max(comp.getPreferredSize().height, prefSize.height);
				prefSize.width=Math.max(comp.getPreferredSize().width, prefSize.width);
			}
		}
		if(isPrefered)
			return new Dimension(prefSize.width*7+6*razmak+insets.left+insets.right, 
					prefSize.height*5+4*razmak+ insets.top+insets.bottom);
		
		if(isMaximum)
			return new Dimension(maxSize.width*7+8*razmak+insets.left+insets.right, 
					maxSize.height*5+6*razmak+ insets.top+insets.bottom);
		
		if(isMinimum)
			return new Dimension(minSize.width*7+8*razmak+insets.left+insets.right, 
					minSize.height*5+6*razmak+ insets.top+insets.bottom);
		
		return null;

	}

}
