package A;

import static A.Main.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {
	
	@Test
	public void testSymmetricRotation() {
		final int[][] px = {
				{
						1, 0, 0
				}, {
						0, 1, 0
				}, {
						0, 0, 1
				}
		};
		
		final int[][] res = Main.rotatePixelsBy90Degrees( px );
		final int[][] exp = new int[][] {
				{
						0, 0, 1
				}, {
						0, 1, 0
				}, {
						1, 0, 0
				}
		};
		
		arrayEquals( exp, res );
	}
	
	@Test
	public void testNonSymmetricRotation() {
		final int[][] px = {
				{
						1, 0, 0, 0
				}, {
						0, 1, 0, 0
				}
		};
		
		final int[][] res = Main.rotatePixelsBy90Degrees( px );
		final int[][] exp = new int[][] {
				{
						0, 1
				}, {
						1, 0
				}, {
						0, 0
				}, {
						0, 0
				}
		};
		
		arrayEquals( exp, res );
	}
	
	@Test
	public void testNonSymmetricRotationForAllPositioins() {
		final int[][] px = {
				{
						1, 2, 3, 4
				}, {
						5, 6, 7, 8
				}
		};
		
		final int[][] res = Main.rotatePixelsBy90Degrees( px );
		final int[][] exp = new int[][] {
				{
						5, 1
				}, {
						6, 2
				}, {
						7, 3
				}, {
						8, 4
				}
		};
		
		arrayEquals( exp, res );
	}
	
	// ----------------------------------------------------------------------------------------------------------------------
	// Concrete tests
	
	@Test
	public void testA1() {
		final long sum = getTriangles( 1 );
		assertEquals( 30, sum );
	}
	
	@Test
	public void testA2() {
		final long sum = getTriangles( 2 );
		assertEquals( 906, sum );
	}
	
	@Test
	public void testA3() {
		final long sum = getTriangles( 3 );
		assertEquals( 7_951, sum );
	}
	
	@Test
	public void testA4() {
		final long sum = getTriangles( 4 );
		assertEquals( 31_763, sum );
	}
	
	@Test
	public void testA5() {
		final long sum = getTriangles( 5 );
		assertEquals( 1_079_121, sum );
	}
	
	@Test
	public void testA6() {
		final long sum = getTriangles( 6 );
		assertEquals( 1_498_038, sum );
	}
	
	@Test
	public void testA7() {
		final long sum = getTriangles( 7 );
		assertEquals( 1_090_186, sum );
	}
	
	@Test
	public void testA8() {
		final long sum = getTriangles( 8 );
		assertEquals( 35_927_404, sum );
	}
	
	@Test
	public void testA9() {
		final long sum = getTriangles( 9 );
		assertEquals( 17_496_955, sum );
	}
	
	@Test
	public void testA10() {
		final long sum = getTriangles( 10 );
		assertEquals( 45_033_456, sum );
	}
	
	// ----------------------------------------------------------------------------------------------------------------------
	// Utilities
	
	private long getTriangles(final int i) {
		final String ifname = INPUT_PATH + "\\" + i + ".png";
		return findTrianglesInPng( ifname );
	}
	
	private void arrayEquals(final int[][] exp, final int[][] act) {
		assertEquals( exp.length, act.length );
		for (int i = 0; i < exp.length; ++i) {
			assertArrayEquals( exp[i], act[i] );
		}
	}
}
