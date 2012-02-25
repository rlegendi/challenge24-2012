package A;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Main {
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
	
	public static void main(String[] args) {
		px = getImagePixels( "doc\\PROBLEMSET\\input\\A\\1.png");
		
		System.out.println();
		
		int sum = 0;
		
		for (int j=0; j<px[0].length; ++j) {
			for (int i=0; i<px.length; ++i) {
				if (isBlack( px[i][j] )) {
//					sum += checkEastAndSouthEast(i, j);
//					sum += checkEastAndSouth(i, j);
//					sum += checkSouthEastAndSouth(i, j);
//					sum += checkSouthAndSouthWest(i, j);
					
					sum += bigUpper(i, j);
					sum += bigLower(i, j);
					sum += bigLeft(i, j);
					sum += bigRight(i, j);
				}
			}
		}
		
		System.out.println("Found: " + sum + " triangles."); 
	}

	private static int bigRight(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i-k >= 0 && j+k<height; ++k) {
			if ( isWhite( px[i-k][j+k] ) || isWhite( px[i][j+k] )) {
				break;
			}
			
			boolean isConnected = true;
			//for (int u = i-k; u<i; ++u) {
			
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
				System.out.printf("Triangle BIG RIGHT found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+2*k, i-k, j+k);
				ret++;
			}
		}
		
		return ret;
	}

	private static int bigLeft(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int bigLower(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int bigUpper(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int checkSouthAndSouthWest(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i-k >= 0 && j+k<height; ++k) {
			if ( isWhite( px[i-k][j+k] ) || isWhite( px[i][j+k] )) {
				break;
			}
			
			boolean isConnected = true;
			for (int u = i-k; u<i; ++u) {
				if (isWhite( px[u][j+k] )) {
					isConnected = false;
				}
			}
			
			if (isConnected) {
				System.out.printf("Triangle SExS found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+k, i-k, j+k);
				ret++;
			}
		}
		
		return ret;
	}

	
	//////// ------------
	private static int checkSouthEastAndSouth(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i+k<width && j+k<height; ++k) {
			if ( isWhite( px[i][j+k] ) || isWhite( px[i+k][j+k] )) {
				break;
			}
			
			boolean isConnected = isStraightHorizontalLine( i, j, k );
			
			if (isConnected) {
				System.out.printf("Triangle SExS found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+k, i+k, j+k);
				ret++;
			}
		}
		
		return ret;
	}

	private static int checkEastAndSouth(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i+k<width && j+k<height; ++k) {
			if ( isWhite( px[i+k][j] ) || isWhite( px[i][j+k] )) {
				break;
			}
			
			boolean isConnected = is45Line( i, j, k);
			
			if (isConnected) {
				System.out.printf("Triangle ExS found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i+k, j, i, j+k);
				ret++;
			}
		}
		
		return ret;
	}

	public static boolean is45Line(int i, int j, int k) {
		for (int u = i; u<i+k; ++u) {
			if (isWhite( px[u][j+k-(u-i)] )) {
				return false;
			}
		}
		return true;
	}

	private static int checkEastAndSouthEast(int i, int j) {
		int ret = 0;
		
		for (int k = 1; i+k<width && j+k<height; ++k) {
			if ( isWhite( px[i+k][j] ) || isWhite( px[i+k][j+k] )) {
				break;
			}
			
			boolean isConnected = isStraightVerticalLine( i, j, k );
			
			if (isConnected) {
				System.out.printf("Triangle ExSE found at (%d, %d) x (%d, %d) x (%d, %d)%n", i, j, i, j+k, i+k, j);
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
	
	public static boolean isStraightHorizontalLine(int i, int j, int k) {
		for (int u = i; u<i+k; ++u) {
			if (isWhite( px[u][j+k] )) {
				return false;
			}
		}
		
		return true;
	}
	
}
