package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Draws Newton-Raphson iteration using parallel iteration.
 * 
 * @author Antonio
 *
 */
public class NewtonParallel {
	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	private static ComplexRootedPolynomial crp;
	private static double THRESHOLD = 0.002;
	private static int workers = 0;
	private static int tracks = 0;
	/**
	 * Main function starts the program.
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			if (args.length == 0) {
				Runtime r = Runtime.getRuntime();
				workers = r.availableProcessors();
				tracks = 4 * r.availableProcessors();
			} else if (args.length == 2) {
				if (args[0].startsWith("--workers=")) {
					workers = Integer.parseInt(args[0].substring(10));
				} else if (args[0].startsWith("-w ")) {
					workers = Integer.parseInt(args[0].substring(3));
				} else {
					throw new IllegalArgumentException();
				}

				if (args[1].startsWith("--tracks=")) {
					tracks = Integer.parseInt(args[1].substring(9));
				} else if (args[1].startsWith("-t ")) {
					tracks = Integer.parseInt(args[1].substring(3));
				} else {
					throw new IllegalArgumentException();
				}
			} else if (args.length == 1) {
				if (args[0].startsWith("--workers=")) {
					workers = Integer.parseInt(args[0].substring(10));
					Runtime r = Runtime.getRuntime();
					tracks = 4 * r.availableProcessors();
				} else if (args[0].startsWith("-w ")) {
					workers = Integer.parseInt(args[0].substring(3));
					Runtime r = Runtime.getRuntime();
					tracks = 4 * r.availableProcessors();
				} else if (args[0].startsWith("--tracks=")) {
					tracks = Integer.parseInt(args[0].substring(9));
					Runtime r = Runtime.getRuntime();
					workers = r.availableProcessors();
				} else if (args[0].startsWith("-t ")) {
					tracks = Integer.parseInt(args[0].substring(3));
					Runtime r = Runtime.getRuntime();
					workers = r.availableProcessors();
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Arguments are wrong.");
			e.printStackTrace();
		}
		System.out.println("Num of workers: " + workers);
		System.out.println("Num of tracks: " + tracks);
		
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
		
		FractalViewer.show(new MojProducer());
	}
	
	/**
	 * Version of {@link IFractalProducer} used in {@link Newton}.
	 * @author Antonio
	 *
	 */
	public static class MojProducer implements IFractalProducer {

		/**
		 * Function which controls threads for result iterations.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			if (tracks > height)
				tracks = height;
			int brojYPoTraci = height / tracks;

			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for (int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if (p == PosaoIzracuna.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}

			for (int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}

			for (int i = 0; i < tracks; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == tracks - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}
	/**
	 * Produces the results for iteration.
	 * @author Antonio
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		/**
		 * Empty constructor.
		 */
		private PosaoIzracuna() {
		}
		/**
		 * Normal constructor for getting result of iteration.
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param cancel
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public void run() {
			int offset = yMin*width;
			for (int y = yMin; y <= yMax; y++) {
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
					if (iter >= m)
						index = iter;
					else {
						index = crp.indexOfClosestRootFor(zn, THRESHOLD);
					}

					data[offset] = (short) (index + 1);
					offset++;
				}
			}
		}
	}
}
