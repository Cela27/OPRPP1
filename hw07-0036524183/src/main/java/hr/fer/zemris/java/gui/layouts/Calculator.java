package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.StyleConstants;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.EventRequest;

public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	private CalcModelImpl model = new CalcModelImpl();

	private Stack<Object> stog = new Stack<>();

	private boolean inverz = false;
	
	/**
	 * Getter za kalkulator.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");

		initGUI();
		pack();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		cp.setBackground(Color.BLACK);
		// Text Area
		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setText("0");
		cp.add(textArea, new RCPosition(1, 1));
		textArea.setFont(new Font("Sans Serif", Font.BOLD, 22));
		//textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// NumButtons
		ActionListener actionNum = a -> {
			JButton b = (JButton) a.getSource();
			model.insertDigit(Integer.parseInt(b.getName()));
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton[] numButtons = new JButton[10];
		for (int i = 0; i < 10; i++) {
			numButtons[i] = new JButton(String.valueOf(i));
			numButtons[i].addActionListener(actionNum);
			numButtons[i].setName(String.valueOf(i));
			numButtons[i].setBackground(Color.YELLOW);
		}
		cp.add(numButtons[0], new RCPosition(5, 3));
		cp.add(numButtons[1], new RCPosition(4, 3));
		cp.add(numButtons[2], new RCPosition(4, 4));
		cp.add(numButtons[3], new RCPosition(4, 5));
		cp.add(numButtons[4], new RCPosition(3, 3));
		cp.add(numButtons[5], new RCPosition(3, 4));
		cp.add(numButtons[6], new RCPosition(3, 5));
		cp.add(numButtons[7], new RCPosition(2, 3));
		cp.add(numButtons[8], new RCPosition(2, 4));
		cp.add(numButtons[9], new RCPosition(2, 5));

		// Clear
		ActionListener actionClear = a -> {
			model.clear();
			textArea.setText("0");
		};
		JButton buttonClear = new JButton("clr");
		buttonClear.addActionListener(actionClear);
		cp.add(buttonClear, new RCPosition(1, 7));
		buttonClear.setBackground(Color.YELLOW);
		// reset
		ActionListener actionClearAll = a -> {
			model.clearAll();
			textArea.setText("0");
		};
		JButton buttonReset = new JButton("reset");
		buttonReset.addActionListener(actionClearAll);
		cp.add(buttonReset, new RCPosition(2, 7));
		buttonReset.setBackground(Color.YELLOW);
		
		// Swap
		ActionListener actionSwap = a -> {
			model.swapSign();
			textArea.setText(String.valueOf(model.getValue()));
		};
		JButton buttonSwap = new JButton("+/-");
		buttonSwap.addActionListener(actionSwap);
		cp.add(buttonSwap, new RCPosition(5, 4));
		buttonSwap.setBackground(Color.YELLOW);
		
		// insert .
		ActionListener actionDecimalPoint = a -> {
			model.insertDecimalPoint();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonDecimalPoint = new JButton(".");
		buttonDecimalPoint.addActionListener(actionDecimalPoint);
		cp.add(buttonDecimalPoint, new RCPosition(5, 5));
		buttonDecimalPoint.setBackground(Color.YELLOW);
		// Bin operations
		// +
		ActionListener actionBinOperationPlus = a -> {
			model.setPendingBinaryOperation(Double::sum);
			inicijalizirajModelzaBinOperaciju();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonBinOperationPlus = new JButton("+");
		buttonBinOperationPlus.addActionListener(actionBinOperationPlus);
		cp.add(buttonBinOperationPlus, new RCPosition(5, 6));
		buttonBinOperationPlus.setBackground(Color.YELLOW);
		// -
		ActionListener actionBinOperationMinus = a -> {
			model.setPendingBinaryOperation(new DoubleBinaryOperator() {

				@Override
				public double applyAsDouble(double left, double right) {
					return left - right;
				}
			});
			inicijalizirajModelzaBinOperaciju();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonBinOperationMinus = new JButton("-");
		buttonBinOperationMinus.addActionListener(actionBinOperationMinus);
		cp.add(buttonBinOperationMinus, new RCPosition(4, 6));
		buttonBinOperationMinus.setBackground(Color.YELLOW);
		// *
		ActionListener actionBinOperationMultiply = a -> {
			model.setPendingBinaryOperation(new DoubleBinaryOperator() {

				@Override
				public double applyAsDouble(double left, double right) {
					return left * right;
				}
			});
			inicijalizirajModelzaBinOperaciju();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonBinOperationMultiply = new JButton("*");
		buttonBinOperationMultiply.addActionListener(actionBinOperationMultiply);
		cp.add(buttonBinOperationMultiply, new RCPosition(3, 6));
		buttonBinOperationMultiply.setBackground(Color.YELLOW);
		// /
		ActionListener actionBinOperationDivide = a -> {
			model.setPendingBinaryOperation(new DoubleBinaryOperator() {

				@Override
				public double applyAsDouble(double left, double right) {
					return left / right;
				}
			});
			inicijalizirajModelzaBinOperaciju();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonBinOperationDivide = new JButton("/");
		buttonBinOperationDivide.addActionListener(actionBinOperationDivide);
		cp.add(buttonBinOperationDivide, new RCPosition(2, 6));
		buttonBinOperationDivide.setBackground(Color.YELLOW);
		// Equals
		ActionListener actionEquals = a -> {
			double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			textArea.setText(String.valueOf(result));
			model.setValue(result);
		};
		JButton buttonEquals = new JButton("=");
		buttonEquals.addActionListener(actionEquals);
		cp.add(buttonEquals, new RCPosition(1, 6));
		buttonEquals.setBackground(Color.YELLOW);
		// push
		ActionListener actionPush = a -> {
			stog.push(model.getUneseneZnamenke());
		};
		JButton buttonPush = new JButton("push");
		buttonPush.addActionListener(actionPush);
		cp.add(buttonPush, new RCPosition(3, 7));
		buttonPush.setBackground(Color.YELLOW);
		// pop
		ActionListener actionPop = a -> {
			Double value= Double.parseDouble((String) stog.pop());
			textArea.setText(value.toString());
			model.setValue(value);
		};
		JButton buttonPop = new JButton("pop");
		buttonPop.addActionListener(actionPop);
		cp.add(buttonPop, new RCPosition(4, 7));
		buttonPop.setBackground(Color.YELLOW);
		// Inverz
		ActionListener actionInverz = a -> {
			inverz = !inverz;
			System.out.println(inverz);
		};
		JCheckBox cbInverz = new JCheckBox("Inv");
		cbInverz.addActionListener(actionInverz);
		cp.add(cbInverz, new RCPosition(5, 7));
		cbInverz.setBackground(Color.YELLOW);
		// Inverzabilne operacija

		// sin
		ActionListener actionSin = a -> {
			double result;
			if (inverz) {
				result = Math.asin(model.getValue());
			} else {
				result = Math.sin(model.getValue());
			}
			textArea.setText(String.valueOf(result));
			model.setValue(result);
		};
		JButton buttonSin = new JButton("sin");
		buttonSin.addActionListener(actionSin);
		cp.add(buttonSin, new RCPosition(2, 2));
		buttonSin.setBackground(Color.YELLOW);
		// cos
		ActionListener actionCos = a -> {
			double result;
			if (inverz) {
				result = Math.acos(model.getValue());
			} else {
				result = Math.cos(model.getValue());
			}
			textArea.setText(String.valueOf(result));
			model.setValue(result);
		};
		JButton buttonCos = new JButton("cos");
		buttonCos.addActionListener(actionCos);
		cp.add(buttonCos, new RCPosition(3, 2));
		buttonCos.setBackground(Color.YELLOW);
		// tan
		ActionListener actionTan = a -> {
			double result;
			if (inverz) {
				result = Math.atan(model.getValue());
			} else {
				result = Math.tan(model.getValue());
			}
			textArea.setText(String.valueOf(result));
			model.setValue(result);
		};
		JButton buttonTan = new JButton("tan");
		buttonTan.addActionListener(actionTan);
		cp.add(buttonTan, new RCPosition(4, 2));
		buttonTan.setBackground(Color.YELLOW);
		// ctg
		ActionListener actionCtg = a -> {
			double result;
			if (inverz) {
				result = Math.atan(1 / model.getValue());
			} else {
				result = 1 / Math.tan(model.getValue());
			}
			model.setValue(result);
			textArea.setText(String.valueOf(result));
		};
		JButton buttonCtg = new JButton("ctg");
		buttonCtg.addActionListener(actionCtg);
		cp.add(buttonCtg, new RCPosition(5, 2));
		buttonCtg.setBackground(Color.YELLOW);
		// x^n
		ActionListener actionPow = a -> {
			model.setPendingBinaryOperation(new DoubleBinaryOperator() {

				@Override
				public double applyAsDouble(double left, double right) {
					if (inverz)
						return Math.pow(left, 1 / right);
					return Math.pow(left, right);
				}
			});
			inicijalizirajModelzaBinOperaciju();
			textArea.setText(model.getUneseneZnamenke());
		};
		JButton buttonPow = new JButton("x^n");
		buttonPow.addActionListener(actionPow);
		cp.add(buttonPow, new RCPosition(5, 1));
		buttonPow.setBackground(Color.YELLOW);
		// log
		ActionListener actionLog = a -> {
			double result;
			if (inverz) {
				result = Math.pow(10, model.getValue());
			} else {
				result = Math.log10(model.getValue());
			}
			model.setValue(result);
			textArea.setText(String.valueOf(result));
		};
		JButton buttonLog = new JButton("log");
		buttonLog.addActionListener(actionLog);
		cp.add(buttonLog, new RCPosition(3, 1));
		buttonLog.setBackground(Color.YELLOW);
		// ln
		ActionListener actionLn = a -> {
			double result;
			if (inverz) {
				result = Math.pow(Math.E, model.getValue());
			} else {
				result = Math.log(model.getValue());
			}
			model.setValue(result);
			textArea.setText(String.valueOf(result));
		};
		JButton buttonLn = new JButton("ln");
		buttonLn.addActionListener(actionLn);
		cp.add(buttonLn, new RCPosition(4, 1));
		buttonLn.setBackground(Color.YELLOW);
		
		// 1/x
		ActionListener actionReverse = a -> {
			double result;
			result=1/model.getValue();
			model.setValue(result);
			textArea.setText(String.valueOf(result));
		};
		JButton buttonReverse = new JButton("1/x");
		buttonReverse.addActionListener(actionReverse);
		cp.add(buttonReverse, new RCPosition(2, 1));
		buttonReverse.setBackground(Color.YELLOW);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.setVisible(true);
			}
		});
	}

	private void inicijalizirajModelzaBinOperaciju() {
		model.setActiveOperand(model.getValue());
		model.setUneseneZnamenke("");
		model.swapSign();
	}
}
