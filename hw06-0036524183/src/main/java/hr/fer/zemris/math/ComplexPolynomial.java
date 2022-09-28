package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing complex polynom: zn*z^n+zn-1*z^(n-1)+...+z1*z+z
 * @author Antonio
 *
 */

public class ComplexPolynomial {
	private List<Complex> factors;

	/**
	 * Basic constructor.
	 * @param factors used in polynom.
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = new ArrayList<>();
		for (int i = 0; i < factors.length; i++) {
			this.factors.add(factors[i]);
		}
	}

	/**
	 * Returns order of polynom.
	 * @return short order
	 */
	public short order() {
		return (short) (factors.size()-1);
	}

	/***
	 * Returns the multiplication of this {@link ComplexPolynomial} with given {@link ComplexPolynomial} p.
	 * @param p {@link ComplexPolynomial} polynom given
	 * @return new {@link ComplexPolynomial} polynom got from multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		List<Complex> list = new ArrayList<>();
		for (int i = 0; i < this.factors.size() + p.factors.size()-1; i++) {
			list.add(new Complex());
		}
		
		for (int i = 0; i < this.factors.size(); i++) {
			for (int j = 0; j < p.factors.size(); j++) {
				Complex mul = this.factors.get(i).multiply(p.factors.get(j));
				System.out.println(mul);
				list.set(i + j, mul.add(list.get(i+j)));
			}
		}
		Complex[] arr= new Complex[list.size()];
		for(int i=0; i<list.size();i++) {
			arr[i]=list.get(i);
		}

		return new ComplexPolynomial(arr);
	}

	/**
	 * Returns new {@link ComplexPolynomial} which is derivation of this {@link ComplexPolynomial}.
	 * @return derivation of this {@link ComplexPolynomial}
	 */
	public ComplexPolynomial derive() {
		List<Complex> factorsNew=new ArrayList<>();
		for(int i=1;i<factors.size(); i++) {
			factorsNew.add(factors.get(i).multiply(new Complex(i, 0)));
		}
		Complex[] arr= new Complex[factorsNew.size()];
		for(int i=0; i<factorsNew.size();i++) {
			arr[i]=factorsNew.get(i);
		}
		return new ComplexPolynomial(arr);
	}

	/**
	 * Computes {@link ComplexPolynomial} with given point z.
	 * @param z given point
	 * @return {@link Complex} number got from computing. 
	 */
	public Complex apply(Complex z) {
		Complex fin = factors.get(0);

		for (int i = 1; i < factors.size(); i++) {
			fin = fin.add((z.power(i)).multiply(factors.get(i)));
		}

		return fin;
	}

	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		
		for(int i=factors.size()-1; i>=0;i--) {
			sb.append("(").append(factors.get(i)).append(")").append("*z^").append(i).append("+");
		}
		
		return sb.toString().substring(0, sb.toString().length()-5);
	}
}
