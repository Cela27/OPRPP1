package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing complex number.
 * @author Antonio
 *
 */
public class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	public static final double PI=Math.PI;

	private double re;
	private double im;
	/**
	 * Basic constructor for 0+0i.
	 */
	public Complex() {
		this.im = 0;
		this.re = 0;
	}

	/**
	 * Constructor for Re+iIm complex number.
	 * @param re double
	 * @param im double
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns the module of complex number.
	 * @return double 
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/***
	 * Returns the multiplication of this complex number with given {@link Complex} number c.
	 * @param c {@link Complex} number given
	 * @return new {@link Complex} number got from multiplication
	 */
	public Complex multiply(Complex c) {
		double tmpRe = this.re * c.re - this.im * c.im;
		double tmpIm = this.re * c.im + c.re * this.im;
		return new Complex(tmpRe, tmpIm);
	}

	/***
	 * Returns the new {@link Complex} number which is this complex number divided with given {@link Complex} number c.
	 * @param c {@link Complex} number given
	 * @return new {@link Complex} number got from dividing
	 */
	public Complex divide(Complex c) {
		if (c.re == 0 && c.im == 0)
			throw new IllegalArgumentException();

		double brojnikRe = this.re * c.re + this.im * c.im;
		double brojnikIm = this.im * c.re - this.re * c.im;
		double nazivnik = c.re * c.re + c.im * c.im;

		return new Complex(brojnikRe / nazivnik, brojnikIm / nazivnik);
	}

	/***
	 * Returns the addition of this complex number with given {@link Complex} number c.
	 * @param c {@link Complex} number given
	 * @return new {@link Complex} number got from addition
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/***
	 * Returns the subtraction of this complex number with given {@link Complex} number c.
	 * @param c {@link Complex} number given
	 * @return new {@link Complex} number got from subtraction
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/***
	 * Returns the negation of this complex.
	 * 
	 * @return new {@link Complex} number got from negating 
	 */
	public Complex negate() {
		if(this.re==0 && this.im==0)
			return new Complex();
		else if(this.re==0)
			return new Complex(0, -1*this.im);
		else if(this.im==0)
			return new Complex(-1*this.re, 0);
		return new Complex(-1*this.re, -1*this.im);
	}

	/**
	 * Returns this {@link Complex} {@link Number} with power of n.
	 * @param n given power parameter
	 * @return new {@link Complex} number.
	 */
	public Complex power(int n) {
		Complex c = this;
		Complex fin = this;
		for (int i = 0; i < n-1; i++) {
			fin=fin.multiply(c);
		}
		return fin;
	}

	/**
	 * Returns n-th root of given complex number.
	 * @param n given parameter for root
	 * @return {@link List} of roots
	 */
	public List<Complex> root(int n) {
		double r= this.module();
		double theta=Math.atan(this.im/this.re);
		List<Complex> list = new ArrayList<>();
		double rRootOfN=Math.pow(r, 1./n);
		for(int i=0;i<n;i++) {
			double tempRe=rRootOfN*Math.cos((theta+2*i*PI)/n);
			double tempIm=rRootOfN*Math.sin((theta+2*i*PI)/n);
			list.add(new Complex(tempRe, tempIm));
		}
		
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(this.re);
		
		if(this.im>=0)
			sb.append("+").append("i").append(this.im);
		else
			sb.append("-").append("i").append(-this.im);
		
		return sb.toString();
	}
	/**
	 * Getter for real part of complex number.
	 * @return
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for imaginary part of complex number.
	 * @return
	 */
	public double getIm() {
		return im;
	}

}
