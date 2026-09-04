// These next lines import the relevant classes needed to output an image
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;

public class Project2 {
    /**
     * A reference to the Secant iterator object.
     */
    private Secant iterator;
    
    /**
     * The top-left corner of the square in the complex plane to examine.
     */
    private Complex origin;
    
    /**
     * The width of the square in the complex plane to examine.
     */
    private double width;
    
    /**
     * A list of roots of the polynomial.
     */
    private ArrayList<Complex> roots;
    
    /**
     * A two dimensional array holding the colours of the plot.
     */
    private Color[][] colours;

    /**
     * A flag indicating the type of plot to generate. If true, colourPixel will
     * choose darker colours if a particular root takes longer to converge.
     */
    private boolean colourIterations;

    /**
     * A standard Java object which allows us to store a simple image in
     * memory. This will be set up by setupFractal.
     */
    private BufferedImage fractal;
    
    /**
     * This object is a standard Java object which allows us to perform
     * basic graphical operations (drawing lines, rectangles, pixels, etc) on
     * the BufferedImage, which will be set up by setupFractal.
     */
    private Graphics2D g2;

    /**
     * Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image.
     */
    public static final int NUMPIXELS = 400;
    
    // ========================================================
    // Constructor function.
    // ========================================================
    
    /**
     * Constructor function which initialises the instance variables
     * above.
     *
     * @param p       The polynomial to generate the fractal of.
     * @param origin  The top-left corner of the square to image.
     * @param width   The width of the square to image.
     */
    public Project2(Polynomial p, Complex origin, double width) {
        this.iterator = new Secant(p);
        this.origin = origin;
        this.width = width;
        this.roots = new ArrayList<Complex>();
        this.setupFractal();
    }
    
    // ========================================================
    // Basic operations.
    // ========================================================

    /**
     * Print out all of the roots found so far, which are contained in the
     * roots ArrayList.
     */
    public void printRoots() {
        for (Complex i: roots) {
            System.out.println(i);
        }
    }

    /**
     * 
     * @return an ArrayList of roots for the polynomial
     */
    public ArrayList<Complex> getRoots() {
        // This method is complete.
        return roots;
    }
    
    /**
     * Check to see if root is in the roots ArrayList (up to tolerance).
     *
     * @param root  Root to find in this.roots.
     * @return The index of root within roots (-1 if the root is not found)
     */
    public int index(Complex r) {
        // Loop through all complex numbers in the roots array
        for (Complex i: roots) {

            // Return the index of any correct roots
            if (r.subtract(i).abs() < Secant.TOL) {
                return roots.indexOf(i);
            }
        }
        // Return -1 if the root r isn't in the roots array
        return -1;
    }
    
    /**
     * Convert from pixel indices (i,j) to the complex number (origin.real +
     * i*dz, origin.imag - j*dz).
     *
     * @param i  x-axis co-ordinate of the pixel located at (i,j)
     * @param j  y-axis co-ordinate of the pixel located at (i,j)
     * 
     */
    public Complex pixelToComplex(int i, int j) {
        double dZ = this.width / NUMPIXELS;
        return new Complex(origin.getReal() + i*dZ, origin.getImag() -j*dZ);
    }
    
    // ========================================================
    // Fractal generating function.
    // ========================================================

    /**
     * Generate the fractal image.  Use colourPixel() to add coloured pixels
     * to the image for this fractal.
     */
    public void createFractal(boolean colourIterations) {
        this.colourIterations = colourIterations;
        // Loop through each pixel
        for (int i = 0; i < NUMPIXELS; i++) {
            for (int j = 0; j < NUMPIXELS; j++) {
                Complex z = pixelToComplex(i, j);

                // Find root for the number z with the secant algorithm
                iterator.iterate(new Complex(), z);
                Complex r = new Complex();

                // Check if the algorithm has converged
                if (iterator.getError() == Secant.Error.OK) {
                    r = iterator.getRoot();
                }
                else continue;

                // Check if the root hasn't already been found
                int rIndex = index(r);
                if (rIndex == -1) {
                    roots.add(r);
                    rIndex = index(r);
                }
                colourPixel(i, j, rIndex, iterator.getNumIterations());
            }
        }
    }


    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Here is some example code which generates the two images seen in
        // figure 1 of the formulation.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0),new Complex(), new Complex(), new Complex(-1), new Complex(), new Complex(1.0,0.0)};
        Polynomial p    = new Polynomial(coeff);
        double k = 1.8;
        Project2 project = new Project2(p, new Complex(-k,k), 2*k);
        
        project.createFractal(false);
        project.saveFractal("fractal-light.png");
        project.createFractal(true);
        project.saveFractal("fractal-dark.png");
    }
    
    // ====================================================================
    // OTHER FUNCTIONS
    //
    // The rest of the functions in this class, which deal with the graphics, were written
    // by my professor and not by me.
    // ====================================================================
    
    /**
     * Sets up all the fractal image. Make sure that your constructor calls
     * this function!
     */
    private void setupFractal()
    {
        // This function is complete!
        int i, j;

        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 5)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive!");

        this.colours       = new Color[5][Secant.MAXITER];
        this.colours[0][0] = Color.RED;
        this.colours[1][0] = Color.GREEN;
        this.colours[2][0] = Color.BLUE;
        this.colours[3][0] = Color.CYAN;
        this.colours[4][0] = Color.MAGENTA;

        
        for (i = 0; i < 5; i++) {
            float[] components = colours[i][0].getRGBComponents(null);
            float[] delta      = new float[3];
            
            for (j = 0; j < 3; j++)
                delta[j] = 0.8f*components[j]/Secant.MAXITER;
            
            for (j = 1; j < Secant.MAXITER; j++) {
                float[] tmp  = colours[i][j-1].getRGBComponents(null);
                colours[i][j] = new Color(tmp[0]-delta[0], tmp[1]-delta[1], 
                                         tmp[2]-delta[2]);
            }
        }
        
        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2      = fractal.createGraphics();
    }
    
    /**
     * Colours a pixel in the image.
     *
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColour  An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.
     */
    private void colourPixel(int i, int j, int rootColour, int numIter) 
    {
        // This function is complete!
        if (colourIterations)
            g2.setColor(colours[rootColour][numIter-1]);
        else
            g2.setColor(colours[rootColour][0]);
        g2.fillRect(i,j,1,1);
    }

    /**
     * Saves the fractal image to a file.
     *
     * @param fileName  The filename to save the image as. Should end in .png.
     */
    public void saveFractal(String fileName) {
        // This function is complete!
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("I got an error trying to save! Maybe you're out of space?");
        }
    }
}