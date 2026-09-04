public class Secant {
    /**
     * The maximum number of iterations that should be used when applying
     * Secant. Ensure this is *small* (e.g. at most 50) otherwise the
     * program may appear to freeze.
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance used to determin whether two points are 'equal'.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Secant method to.
     */
    private Polynomial f;


    /**
     * A root of the polynomial f corresponding to the root found by the
     * iterate() function below.
     */
    private Complex root;
    
    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An enumeration that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   OK: Nothing went wrong.
     *   ZERO: Difference went to zero during the algorithm.
     *   DNF: Reached MAXITER iterations (did not finish)
     */
    enum Error { OK, ZERO, DNF };
    private Error err = Error.OK;
    
    
    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor.
     *
     * @param p  The polynomial used for Secant.
     */
    public Secant(Polynomial p) {
        this.f = p;
    }

    // ========================================================
    // Accessor methods.
    // ========================================================
    
    /**
     * Returns the current value of the err instance variable.
     */
    public Error getError() {
        // You need to fill in this method with the correct code.
        return this.err;
    }

    /**
     * Returns the current value of the numIterations instance variable.
     */
    public int getNumIterations() { 
        return this.numIterations;
    }
    
    /**
     * Returns the current value of the root instance variable.
     */
    public Complex getRoot() {
        return new Complex(root.getReal(), root.getImag());
    }

    /**
     * Returns the polynomial associated with this object.
     */
    public Polynomial getF() {
        return new Polynomial(this.f.getCoeff());
    }

    // ========================================================
    // Secant method
    // ========================================================
    
    /**
     * Given two complex numbers z0 and z1, apply Secant to the polynomial f in
     * order to find a root within tolerance TOL.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to OK.
     *   - At some point the absolute difference between f(zn) and f(zn-1) becomes zero. 
     *     In this case, set err to ZERO and return.
     *   - After MAXITER iterations the algorithm has not converged. In this 
     *     case set err to DNF and return.
     *
     * @param z0,z1  The initial starting points for the algorithm.
     */
    public void iterate(Complex z0, Complex z1) {
        // Reset the error
        this.err = Error.OK;

        // Create 3 values for the algorithm
        Complex a = z0;
        Complex b = z1;
        Complex c = new Complex();
        Complex fraction;

        for (int i = 0; i < Secant.MAXITER; i++) {

            // Making sure |f(zM) - f(zM-1)| doesn't approach 0
            if (f.evaluate(b).subtract(f.evaluate(a)).abs() < Secant.TOL) {
                this.err = Error.ZERO;
                this.numIterations = Secant.MAXITER;
                return;
            }

            // Calculate the next iteration of the Secant algorithm
            fraction = b.subtract(a).divide(f.evaluate(b).subtract(f.evaluate(a)));
            c = b.subtract(f.evaluate(b).multiply(fraction));

            // Stop the function if the algorithm has approached a root
            if (c.subtract(b).abs() < Secant.TOL && f.evaluate(c).abs() < Secant.TOL) {
                this.numIterations = i+1;
                this.root = c;
                return;
            }  
            
            // Update variable for the next iteration
            a = b;
            b = c;
        }

        // Case when the algorithm doesn't approach a root
        this.numIterations = Secant.MAXITER;
        this.err = Error.DNF; 
    }
      
    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Basic tester: find a root of f(z) = z^3-1.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(), new Complex(), new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        Secant     s    = new Secant(p);
                
        s.iterate(new Complex(), new Complex(1.0,1.0));
        System.out.println(s.getNumIterations());   // 12
        System.out.println(s.getError());           // OK
        System.out.println("The root is " + s.getRoot());
        // find |z^3 - 1|
        System.out.println("|z^3 - 1| = " + s.getRoot().multiply(s.getRoot().multiply(s.getRoot())).subtract(new Complex(1)).abs());
    }
}
