package hr.fer.zemris.java.gui.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel, CalcValueListener{
	
	//moras u sve metode dodat valu changed poziziv a valu changed obavjestava sve  value listenere da se promjena desila
	private boolean editable=true;
	private boolean pozitivan=true;
	private String uneseneZnamenke="";
	private Double value=0.;
	private String vrijednostPrikaza=null;
	
	private Double activeOperand=null;
	private DoubleBinaryOperator pendingOperation;
	
	private List<CalcValueListener> promatraci= new ArrayList<>();
	
	/**
	 * Getter za pozitivan.
	 * @return
	 */
	public boolean isPozitivan() {
		return pozitivan;
	}
	
	/**
	 * Setter za pozitivan;
	 * @param pozitivan
	 */
	public void setPozitivan(boolean pozitivan) {
		this.pozitivan = pozitivan;
	}
	/*
	 * Getter za unesene znamenke u kalkulator.
	 */
	public String getUneseneZnamenke() {
		return uneseneZnamenke;
	}
	/**
	 * Setter za unesene znamenke.
	 * @param uneseneZnamenke
	 */
	public void setUneseneZnamenke(String uneseneZnamenke) {
		this.uneseneZnamenke = uneseneZnamenke;
		valueChanged(this);
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l==null) throw new NullPointerException();
		
		promatraci.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l==null) throw new NullPointerException();
		
		promatraci.remove(l);
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value=value;
		uneseneZnamenke=uneseneZnamenke+String.valueOf(value);
		editable=false;
		vrijednostPrikaza=String.valueOf(this.value);
		valueChanged(this);
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		value=0.;
		uneseneZnamenke="";
		valueChanged(this);
	}

	@Override
	public void clearAll() {
		value=0.;
		uneseneZnamenke="";
		editable=true;
		pozitivan=true;
		
		vrijednostPrikaza=null;
		activeOperand=null;
		valueChanged(this);
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException();
		value=value*-1;
		pozitivan=!pozitivan;
		
		valueChanged(this);
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException();
		
		if(uneseneZnamenke.contains("."))
			throw new CalculatorInputException();
		
		if(uneseneZnamenke=="")
			throw new CalculatorInputException();
		
		uneseneZnamenke=uneseneZnamenke+".";
		
		vrijednostPrikaza=null;
		valueChanged(this);
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) throw new CalculatorInputException();
		
		try {
			if(uneseneZnamenke.length()>=308)
				throw new CalculatorInputException();
			
			uneseneZnamenke= uneseneZnamenke+String.valueOf(digit);
			value= Double.parseDouble(uneseneZnamenke);
			
			while(uneseneZnamenke.startsWith("0")) {
				uneseneZnamenke=uneseneZnamenke.substring(1);
			}
			
			if(uneseneZnamenke.length()==0)
				uneseneZnamenke="0";
			if(uneseneZnamenke.startsWith("."))
				uneseneZnamenke="0"+uneseneZnamenke;
			if(!pozitivan)
				value=value*-1;
		}catch(Exception e) {
			throw new CalculatorInputException();
		}
		vrijednostPrikaza=uneseneZnamenke;
		valueChanged(this);
	}

	@Override
	public boolean isActiveOperandSet() {
		if(activeOperand==null) return false;
		return true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand==null) throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand=activeOperand;	
	}

	@Override
	public void clearActiveOperand() {
		activeOperand=null;
		valueChanged(this);
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation=op;
		
	}
	
	@Override
	public String toString() {
		if(Double.isNaN(value))
			return "NaN";
		if (vrijednostPrikaza!=null) {
			if(pozitivan)
				return vrijednostPrikaza;
			return "-"+vrijednostPrikaza;
		}else {
			if(pozitivan)
				return "0";
			return "-"+0;
		}
			
	}

	@Override
	public void valueChanged(CalcModel model) {
		for(var l: promatraci) {
			l.valueChanged(model);
		}
		
	}
}
