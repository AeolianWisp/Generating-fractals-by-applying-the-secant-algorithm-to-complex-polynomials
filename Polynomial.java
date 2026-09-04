public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        int coeffLength = coeff.length;
        while (coeffLength > 1 && coeff[coeffLength-1].getReal() == 0 && coeff[coeffLength-1].getImag() == 0) {
            --coeffLength; // Remove any trailing zeros
        }

        // Return the array without the trailing zeros
        this.coeff = new Complex[coeffLength];
        System.arraycopy(coeff, 0, this.coeff, 0, coeffLength);
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        this.coeff = new Complex[] {new Complex()};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return  The coefficients array.
     */
    public Complex[] getCoeff() {
        // You need to fill in this method with the correct code.
        return coeff.clone();
        //return new Complex[] coeff.clone();
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable.  Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */
    public String toString() {
        String text = "";
        for (int i = 0; i < coeff.length; i++) {
            if (i == 0)
                text = "(" + coeff[i].toString() + ")";
            else if (i == 1)
                text += " + (" + coeff[i].toString() + ")z";
            else
                text += " + (" + coeff[i].toString() + ")z^" + Integer.toString(i);
        }
        // required format: "(-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2"
        return text;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return (coeff.length - 1);
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        // Set b to be a_n, the coefficient of the highest degree term
        Complex b = coeff[this.degree()];

        // Loop through all other terms and make the new term equal to a + z.b
        for (int i = 1; i <= this.degree(); i++) {  
            Complex a = coeff[this.degree() - i];
            b = a.add(z.multiply(b));
        }
        return new Complex(b.getReal(), b.getImag());
    }

    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Polynomial a = new Polynomial(new Complex[] {new Complex(1), new Complex(1), new Complex(1)});
        System.out.println(a.toString());
        System.out.println(a.evaluate(new Complex(1)).toString());
  

    }
}