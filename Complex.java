public class Complex {
    /**
     * Real part x of the complex number x+iy.
     */
    private double x;
    
    /**
     * Imaginary part y of the complex number x+iy.
     */
    private double y;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Constructor: Initializes x, y.
     *
     * @param x  The initial value of the real component.
     * @param y  The initial value of the imaginary component.
     */
    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Real constructor - initialises with a real number.
     *
     * @param x  The initial real number to initialise to.
     */
    public Complex(double x) {
        this.x = x;
        this.y = 0;
    }

    /**
     * Default constructor; initialise x and y to zero.
     */
    public Complex() {
        this.x = 0;
        this.y = 0;
    }
    
    // ========================================================
    // Accessor and mutator methods.
    // ========================================================
    
    /**
     * Accessor Method: get real part of the complex number.
     * 
     * @return The real part of the complex number.
     */
    public double getReal() {
        // You need to fill in this method with the correct code.
        return this.x;
    }

    /**
     * Accessor Method: get imaginary part of the complex number.
     *
     * @return The imaginary part of the complex number
     */
    public double getImag() {
        // You need to fill in this method with the correct code.
        return this.y;
    }

    /**
     * Mutator method: set the real part of the complex number.
     *
     * @param x  The replacement real part of z.
     */
    public void setReal(double x) {
        this.x = x;
    }
    
    /**
     * Mutator method: set the imaginary part of the complex number.
     *
     * @param y  The replacement imaginary part of z.
     */
    public void setImag(double y) {
        this.y = y;
    }
    
    // ========================================================
    // Operations and functions with complex numbers.
    // ========================================================

    /**
     * Converts the complex number to a string. This is an important method as
     * it allows us to print complex numbers using System.out.println.
     *
     * @return A string describing the complex javnumber.
     */
    public String toString() {
        // This function is complete.
        return String.format("%.3f%s%.3fi" , x , (y < 0.0 ? "-" : "+") , Math.abs(y));
    }

    /**
     * Computes square of the absolute value (magnitude) of the complex number
     * (i.e. |z|^2).
     *
     * @return The square of the absolute value of this complex number.
     */
    public double abs2() {
        // You need to fill in this method with the correct code.
        return (x*x + y*y);
    }

    /**
     * Computes absolute value (magnitude) of the complex number.
     *
     * @return The absolute value of the complex number.
     */
    public double abs() {
        // You need to fill in this method with the correct code.
        return Math.sqrt(abs2());
    }
    
    /**
     * Calculates the conjugate of this complex number.
     *
     * @return A Complex containing the conjugate.
     */
    public Complex conjugate() {
        // You need to fill in this method with the correct code.
        return new Complex(x, -y);
    }

    /**
     * Adds a complex number to this one.
     *
     * @param b  The complex number to add to this one.
     * @return   The sum of this complex number with b.
     */
    public Complex add(Complex b) {
        // You need to fill in this method with the correct code.
        return new Complex(x + b.getReal(), y + b.getImag());
    }
    
    /**
     * Calculates -z.
     *
     * @return The complex number -z = -x-iy
     */
    public Complex negate() {
        return new Complex(-x, -y);
    }

    public Complex subtract(Complex b) {
        return new Complex(x - b.getReal(), y - b.getImag());

    }
    /**
     * Multiplies this complex number by a constant.
     *
     * @param alpha   The constant to multiply by.
     * @return        The product of alpha with z.
     */
    public Complex multiply(double alpha) {
        return new Complex(alpha*x, alpha*y);
    }
    
    /**
     * Multiplies this complex number by another complex number.
     *
     * @param b   The complex number to multiply by.
     * @return    The product of b with z.
     */
    public Complex multiply(Complex b) {
        // You need to fill in this method with the correct code.
        return new Complex(x*b.getReal() - y*b.getImag(), x*b.getImag() + y*b.getReal());
    }

    /**
     * Divide this complex number by another.
     *
     * @param b  The complex number to divide by.
     * @return   The division z/a.
     */
    public Complex divide(Complex b) {
    // Calculate the dividend
        double div = b.abs2();
        Complex numerator = this.multiply(b.conjugate());

        Complex quotient = new Complex(numerator.getReal()/div, numerator.getImag()/div);
        return quotient;
    }
        
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Complex A = new Complex(2.0, 2.0);
        
        System.out.println("Constructor test:");
        System.out.println("A = "+A.toString());
    }
}