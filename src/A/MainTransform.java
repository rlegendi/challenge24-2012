package A;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MainTransform {
	static final int white = -1;
	
	static int[][] px = null;
	static int width;
	static int height;
	
	static int[][] getImagePixels(String fname) {
		try {
			BufferedImage image =  ImageIO.read(new File(fname));
			int[][] pixels = new int[image.getWidth()][image.getHeight()];
			
			width = image.getWidth();
			height = image.getHeight();
			
			for (int i=0; i<image.getWidth(); ++i) {
				for (int j=0; j<image.getHeight(); ++j) {
					pixels[i][j] = image.getRGB( i, j );
				}
			}
			
			return pixels;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static boolean isBlack(int col) {
		return col != white;
	}
	
	static boolean isWhite(int col) {
		return col == white;
	}
	
	private static long sum = 0;
	
	public static void main(String[] args) {
		process("doc\\PROBLEMSET\\input\\A\\10_a.png");
		process("doc\\PROBLEMSET\\input\\A\\10_b.png");
		process("doc\\PROBLEMSET\\input\\A\\10_c.png");
		process("doc\\PROBLEMSET\\input\\A\\10_d.png");
		
		System.out.println("Found: " + sum + " triangles."); 
	}

	public static void process(String fname) {
		px = getImagePixels( fname );
		
		for (int j=0; j<px[0].length; ++j) {
			for (int i=0; i<px.length; ++i) {
				if (isBlack( px[i][j] )) {
					sum += checkEastAndSouthEast(i, j);
					
					sum += bigRight(i, j);
				}
			}
		}
	}

	private static int bigRight(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i-k >= 0 && j+2*k<height; ++k) {
			if ( isWhite( px[i-k][j+k] ) || isWhite( px[i][j+k] )) {
				break;
			}
			
			boolean isConnected = true;
			
			for (int u=1; u<=k; ++u) {
				if (isWhite( px[i][j+k+u] )) {
					isConnected = false;
					break;
				}
			}
			
			if (isConnected) {
				for (int u=1; u<=k; ++u) {
					if (isWhite( px[i-k+u][j+k+u] )) {
						isConnected = false;
						break;
					}
				}
			}
			
			
			if (isConnected) {
				//System.out.printf("Triangle BIG RIGHT found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+2*k, i-k, j+k);
				ret++;
			}
		}
		
		return ret;
	}
	
	private static int checkEastAndSouthEast(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i+k<width && j+k<height; ++k) {
			if ( isWhite( px[i+k][j] ) || isWhite( px[i+k][j+k] )) {
				break;
			}
			
			boolean isConnected = isStraightVerticalLine( i, j, k );
			
			if (isConnected) {
				//System.out.printf("Triangle ExSE found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+k, i+k, j);
				ret++;
			}
		}
		
		return ret;
	}

	public static boolean isStraightVerticalLine(int i, int j, int k) {
		for (int u = j; u<j+k; ++u) {
			if (isWhite( px[i+k][u] )) {
				return false;
			}
		}
		
		return true;
	}
	
}
