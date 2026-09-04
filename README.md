# Generating fractals by applying the secant algorithm to complex polynomials

This project was completed as part of a Warwick university course entitled 'Programming for Scientists'.
It colours points in the complex plane based on which root of a polynomial the secant algorithm takes it
to (if any). This creates unique fractals for each polynomial.

### Design Choices

* There is a base class called Complex with basic functionality (adding, multiplying, conjugating, etc.). There is then a
  polynomial class which stores arrays of complex numbers representing polynomials of degree n (for this project we will
  only consider n = 0,1,2,3,4,5).

* There is a Secant class which holds a polynomial as a member variable. This class contains the iterate() function, which
  takes in 2 starting complex numbers and performs the Secant algorithm on these inputs, storing the result in the 'root' member variable.
  In reality this first number will always be the origin.

* Finally the Project2 class will generate a grid of pixels, will perform the Secant algorithm on each pixel, and will colour
  each pixel according to which root (if any) the Secant algorithm arrived on with that starting complex number. This will take
  some time if a high number of pixels is used (generally 1000+).

* The results are saved as two files, 'fractal-light' and 'fractal-dark', the second of which alters the brightness of each pixel
  according to how many iterations the Secant algorithm took to arrive on the root.

### Executing program

Can be run on any system with the JRE or the JDK installed, simply run the Project2 file. All necessary packages for the graphical
output are imported in the Project2 file.



## Authors

* Project completed by Thomas Hobro.
* The SetUpFractal(), colourPixel, and SaveFractal() files in 'Project2' were written by professor Khalil Challita of Warwick University.
* No code in this project was written by artificial intelligence.
