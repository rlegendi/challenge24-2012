package A;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

/**
 * Solution for problem A.
 * 
 * <p>
 * The trick is that there are generally two possible valid triangle variations. Specifically, which the following code
 * searches for are these ones:
 * </p>
 * 
 * <pre>
 * 
 *    ###########                   #
 *     #        #                  ##
 *      #       #                 # #
 *       #      #                #  #
 *        #     #               #   #
 *         #    #              #    #
 *          #   #               #   #
 *           #  #                #  #
 *            # #                 # #
 *             ##                  ##
 *              #                   #
 * 
 * </pre>
 * 
 * <p>
 * The solution for <i>A1</i> contains a 30 different triangles: 4 of them can be recognized instantly, but the definition of
 * triangle allows triangles consisting of 3 or 4 pixels (like the one at <i>(8,8)x(9x9)x(9,8)</i> coordinates).
 * </p>
 * 
 * <p>
 * What I do is that I search for the above mentioned two triangles, then I rotate the image clockwise. I repeat this 3 more
 * times, so I get all rotated versions of the triangles. Simple solution, but works like charm (<i>don't forget to turn off
 * the output for the last few input files since the IO operations of printing out the found triangles drastically slows down
 * the evaluation</i>). It is trivial to rotate the image but to write the other 6 triangle verifications
 * </p>
 * 
 * @author rlegendi
 */
public class Main {
	private static final boolean DEBUG = false;
	private static final int WHITE = - 1;
	
	protected static final String INPUT_PATH = "doc\\PROBLEMSET\\input\\A";
	protected static final String OUTPUT_PATH = "sol\\A";
	
	protected static int[][] getImagePixels(final String fname) {
		try {
			final BufferedImage image = ImageIO.read( new File( fname ) );
			final int[][] pixels = new int[image.getWidth()][image.getHeight()];
			
			for (int i = 0; i < image.getWidth(); ++i) {
				for (int j = 0; j < image.getHeight(); ++j) {
					pixels[i][j] = image.getRGB( i, j );
				}
			}
			
			return pixels;
		} catch (final Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	protected static int[][] rotatePixelsBy90Degrees(final int[][] px) {
		assert ( px.length > 0 );
		final int width = px[0].length;
		final int height = px.length;
		
		final int[][] ret = new int[width][height];
		
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				ret[i][j] = px[height - 1 - j][i];
			}
		}
		
		return ret;
	}
	
	protected static boolean isBlack(final int col) {
		return col != WHITE;
	}
	
	protected static boolean isWhite(final int col) {
		return col == WHITE;
	}
	
	public static void main(final String[] args) {
		for (int i = 1; i <= 10; ++i) {
			final String ifname = INPUT_PATH + "\\" + i + ".png";
			final String ofname = OUTPUT_PATH + "\\A" + i + ".out";
			
			final long sum = findTrianglesInPng( ifname );
			saveSolution( ofname, sum );
			System.out.println( ifname + " - Found: " + sum + " triangles." );
		}
	}
	
	public static long findTrianglesInPng(final String fname) {
		int[][] px = getImagePixels( fname );
		
		long sum = 0;
		
		for (int i = 0; i < 4; ++i) {
			if ( i > 0 ) {
				px = rotatePixelsBy90Degrees( px );
			}
			
			sum += process( px );
		}
		return sum;
	}
	
	public static long process(final int[][] px) {
		long sum = 0;
		
		for (int j = 0; j < px[0].length; ++j) {
			for (int i = 0; i < px.length; ++i) {
				if ( isBlack( px[i][j] ) ) {
					sum += checkEastAndSouthEast( px, i, j );
					sum += bigRight( px, i, j );
				}
			}
		}
		
		return sum;
	}
	
	private static int checkEastAndSouthEast(final int[][] px, final int i, final int j) {
		int ret = 0;
		
		for (int k = 1; i + k < px.length && j + k < px[0].length; ++k) {
			if ( isWhite( px[i + k][j] ) || isWhite( px[i + k][j + k] ) ) {
				break;
			}
			
			boolean isConnected = true;
			
			for (int u = j; u < j + k; ++u) {
				if ( isWhite( px[i + k][u] ) ) {
					isConnected = false;
					break;
				}
			}
			
			if ( isConnected ) {
				if ( DEBUG ) {
					System.out.printf( "Triangle ExSE found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j + k, i + k, j );
				}
				
				ret++;
			}
		}
		
		return ret;
	}
	
	private static int bigRight(final int[][] px, final int i, final int j) {
		int ret = 0;
		
		for (int k = 1; i - k >= 0 && j + 2 * k < px[0].length; ++k) {
			if ( isWhite( px[i - k][j + k] ) || isWhite( px[i][j + k] ) ) {
				break;
			}
			
			boolean isConnected = true;
			
			for (int u = 1; u <= k; ++u) {
				if ( isWhite( px[i][j + k + u] ) ) {
					isConnected = false;
					break;
				}
			}
			
			if ( isConnected ) {
				for (int u = 1; u <= k; ++u) {
					if ( isWhite( px[i - k + u][j + k + u] ) ) {
						isConnected = false;
						break;
					}
				}
			}
			
			if ( isConnected ) {
				if ( DEBUG ) {
					System.out.printf( "Triangle BIG RIGHT found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j + 2 * k,
							i - k, j + k );
				}
				
				ret++;
			}
		}
		
		return ret;
	}
	
	private static void saveSolution(final String fname, final long sum) {
		try {
			final PrintWriter pw = new PrintWriter( fname );
			pw.println( sum );
			pw.close();
		} catch (final Exception e) {
			throw new RuntimeException( e );
		}
	}
	
}
