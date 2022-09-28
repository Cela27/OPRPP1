package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Draws Newton-Raphson iteration using  serial iteration.
 * 
 * @author Antonio
 *
 */
public class Newton {

	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	private static  ComplexRootedPolynomial crp;
	private static double THRESHOLD=0.002;
	
	/**
	 * Main function starts the program.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner s = new Scanner(System.in);
		String line;
		int brojac = 1;
		List<Complex> roots = new ArrayList<>();
		System.out.print("Root 1> ");
		while ((line = s.nextLine()) != null) {
			if (line.equals("done"))
				break;

			String[] splits = line.split(" ");
			if (splits.length == 3) {
				int re = 0;
				try {
					re = Integer.parseInt(splits[0]);
				} catch (NumberFormatException e) {
					System.out.println("Wrong argument try again.");
					System.out.print("Root " + brojac + "> ");
					continue;
				}

				int im;
				if (splits[2].equals("i")) {
					im = 1;
				} else {
					im = Integer.parseInt(splits[2].substring(1));
				}
				if (splits[1].equals("-")) {
					im = im * -1;
				} else if (splits[1].equals("+")) {
					im = im * 1;
				} else {
					System.out.println("Wrong argument try again.");
					System.out.print("Root " + brojac + ">");
					continue;
				}
				roots.add(new Complex(re, im));
			} else if (splits.length == 1) {
				boolean neg = false;
				if (splits[0].startsWith("-")) {
					neg = true;
					splits[0] = splits[0].substring(1);
				}
				if (splits[0].contains("i")) {
					int re = 0;
					int im;
					if (splits[0].equals("i"))
						im = 1;
					else
						im = Integer.parseInt(splits[0].substring(1));
					if (neg)
						im = im * -1;
					roots.add(new Complex(re, im));
				} else {
					int im = 0;
					int re = 0;
					try {
						re = Integer.parseInt(splits[0]);
					} catch (NumberFormatException e) {
						System.out.println("Wrong argument try again.");
						System.out.print("Root " + brojac + "> ");
						continue;
					}
					if (neg)
						re = re * -1;
					roots.add(new Complex(re, im));
				}
			} else {
				System.out.println("Wrong argument try again.");
				System.out.print("Root " + brojac + "> ");
				continue;
			}
			brojac++;
			System.out.print("Root " + brojac + "> ");
			continue;
		}
		Complex[] arr = new Complex[roots.size()];
		for (int i = 0; i < roots.size(); i++) {
			arr[i] = roots.get(i);
		}
		crp = new ComplexRootedPolynomial(new Complex(1, 0), arr);
		polynomial = crp.toComplexPolynom();
		derived = polynomial.derive();
		System.out.println(crp);
		System.out.println(polynomial);
		System.out.println(derived);
		FractalViewer.show(new MojProducer());
	}
	
	/**
	 * Version of {@link IFractalProducer} used in {@link Newton}.
	 * @author Antonio
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		/**
		 * Function which produces results of iterations.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];

			for (int y = 0; y < height; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					double cRe = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cIm = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex c = new Complex(cRe, cIm);
					
					Complex zn = c;
					int iter = 0;
					double module;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while (module > THRESHOLD && iter < m);
					int index;
					if(iter>=m)
						index=iter;
					else {
						index= crp.indexOfClosestRootFor(zn, THRESHOLD);
					}
						
					data[offset]=(short) (index+1);
					offset++;
				}
			}
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}

	}
}
