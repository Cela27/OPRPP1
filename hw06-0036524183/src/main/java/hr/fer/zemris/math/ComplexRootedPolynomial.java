package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
/**
 * Class representing complex root polynom: z0*(z-z1)*(z-z2)*...*(z-zn).
 * @author Antonio
 *
 */
public class ComplexRootedPolynomial {
	private List<Complex> roots;
	private Complex constant;

	/**
	 * basic constructor for {@link ComplexRootedPolynomial}.
	 * @param constant z0
	 * @param roots z1...
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		for (int i = 0; i < roots.length; i++) {
			this.roots.add(roots[i]);
		}
	}

	/**
	 * Computes {@link ComplexRootedPolynomial} with given point z.
	 * @param z given point
	 * @return {@link Complex} number got from computing. 
	 */
	public Complex apply(Complex z) {
		Complex fin = constant;
		for (Complex c : roots) {
			fin = fin.multiply((z.sub(c)));
		}

		return fin;
	}

	/**
	 * Turns this {@link ComplexRootedPolynomial} to {@link ComplexPolynomial}.
	 * @return new {@link ComplexRootedPolynomial} got from this {@link ComplexRootedPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {;
		ComplexPolynomial curr= new ComplexPolynomial(constant);

		for(int i=0; i<roots.size();i++){
			curr=curr.multiply(new ComplexPolynomial(roots.get(i).negate(), new Complex(1,0)));
		}
		
		return curr;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(constant).append(")").append("*");
		
		for (int i = 0; i<roots.size(); i++) {
			sb.append("(").append("z-").append("(").append(roots.get(i)).append("))").append("*");
		}

		return sb.toString().substring(0, sb.toString().length()-1);
	}

	/**
	 * Finds index of closest root for given complex number z that is within treshold.
	 * If there is no such root, returns -1.
	 * @param z given {@link Complex} number
	 * @param treshold double
	 * @return int index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minDiff=Math.sqrt(Math.pow(z.getRe()-roots.get(0).getRe(), 2)+Math.pow(z.getIm()-roots.get(0).getIm(), 2));
		int indexMin=0;
		for(int i=1;i<roots.size();i++) {
			double diff=Math.sqrt(Math.pow(z.getRe()-roots.get(i).getRe(), 2)+Math.pow(z.getIm()-roots.get(i).getIm(), 2));
			if(diff<minDiff) {
				minDiff=diff;
				indexMin=i;
			}
		}
		if(minDiff<=treshold)
			return indexMin;
		return -1;
	}
}