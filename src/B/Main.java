package B;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

class Mine {
	static long ctr = 0;
	long id = ctr++;
	final long x, y, r;
	
	public Mine(final long x, final long y, final long r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public boolean canBlast(final Mine act) {
		// TODO Epsilon?
		return Math.sqrt( ( x - act.x ) * ( x - act.x ) + ( y - act.y ) * ( y - act.y ) ) <= r;
	}
	
	@Override
	public String toString() {
		return "Mine " + id + " [x=" + x + ", y=" + y + ", r=" + r + "]";
	}
}

class Canvas
		extends JPanel {
	Graph<Mine, Long> graph;
	
	public Canvas(final Graph<Mine, Long> graph) {
		this.graph = graph;
	}
	
	@Override
	public void paint(final Graphics g) {
		final int W = getWidth();
		final int H = getHeight();
		
		for (final Mine mine : graph.vertexSet()) {
			g.drawOval( (int) ( mine.x + W / 2 ), (int) ( mine.y + H / 2 ), (int) mine.r, (int) mine.r );
		}
	}
}

public class Main {
	private static final boolean DEBUG = true;
	private static final boolean SHOW_GRAPH = false;
	
	protected static final String INPUT_PATH = "doc\\PROBLEMSET\\input\\B";
	protected static final String OUTPUT_PATH = "sol\\B";
	
	private static JGraphModelAdapter m_jgAdapter;
	
	public static void main(final String[] args) {
		process( 1 );
	}
	
	private static void process(final int i) {
		final String ifile = INPUT_PATH + "\\" + i + ".in";
		//DirectedGraph<Mine, Long> graph = readGraph( ifile );
		
		//showFrame( graph );
		
		final ListenableDirectedGraph<Mine, Long> g = new ListenableDirectedGraph<>( Long.class );
		m_jgAdapter = new JGraphModelAdapter( g );
		readGraph( g, ifile );
		
		// create a visualization using JGraph, via an adapter
		
		final JGraph jgraph = new JGraph( m_jgAdapter );
		
		if ( SHOW_GRAPH ) {
			final JFrame frame = new JFrame( "Network" );
			adjustDisplaySettings( jgraph );
			frame.add( new JScrollPane( jgraph ) );
			frame.resize( DEFAULT_SIZE );
			frame.setVisible( true );
			frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		}
		
		//WeakComponentClusterer<Mine, Long> clusterer = new WeakComponentClusterer<>();
		//Set<Set<Mine>> clusters = clusterer.transform( graph );
		
		//final StrongConnectivityInspector<Mine, Long> clusterer = new StrongConnectivityInspector<>( g );
		//final List<Set<Mine>> clusters = clusterer.stronglyConnectedSets();
		final ConnectivityInspector<Mine, Long> clusterer = new ConnectivityInspector<>( g );
		final List<Set<Mine>> clusters = clusterer.connectedSets();
		
		// TODO Get subgraphs and call getBase() - ain't sure what it does...
		
		final List<Long> minesToBlast = findSolutions( g, clusters );
		System.out.println( minesToBlast );
	}
	
	private static List<Long> findSolutions(final ListenableDirectedGraph<Mine, Long> g, final List<Set<Mine>> clusters) {
		final ArrayList<Long> ret = new ArrayList<>();
		
		for (final Set<Mine> cluster : clusters) {
			ret.add( findRoot( g, cluster ) );
		}
		
		System.out.println("Found " + clusters.size() + " different clusters...");
		//Collections.sort( ret );
		
		return ret;
	}
	
	private static long findRoot(final ListenableDirectedGraph<Mine, Long> g, final Set<Mine> cluster) {
		if ( DEBUG ) {
			System.out.println( cluster );
		}
		
		if ( cluster.isEmpty() ) {
			throw new RuntimeException( "No members in cluster!" );
		}
		
		long ret = Long.MAX_VALUE;
		
		for (final Mine mine : cluster) {
			if ( 0 == g.inDegreeOf( mine ) ) {
				//System.out.println( "ROOT: " + mine.id );
				
				if ( ret != Long.MAX_VALUE ) {
					System.out.println( "MULTIPLE ROOTS FOR CLUSTER! " + ret + " " + mine.id );
				}
				
				if ( mine.id < ret ) {
					ret = mine.id;
				}
			}
		}
		
		// No roots - blast any mine, e.g., the one with min. index?
//		if (Long.MAX_VALUE == ret) {
//			long min = Integer.MAX_VALUE;
//			for (final Mine mine : cluster) {
//				if (mine.id < min) {
//					min = mine.id;
//				}
//			}
//			
//			ret = min;
//		}
		
		if (Long.MAX_VALUE == ret) {
			ret = cluster.iterator().next().id;
		}
		
		return ret;
	}
	
//	private static void showFrame(Graph<Mine, Long> graph) {
//		final JFrame frame = new JFrame();
//		Canvas canvas = new Canvas( graph );
//		canvas.setSize( 100_000, 100_000 );
//		frame.add( new JScrollPane( canvas ) );
//		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
//		
//		SwingUtilities.invokeLater( new Runnable() {
//			@Override
//			public void run() {
//				frame.setVisible( true );
//			}
//		} );
//	}
	
	private static long edgeCtr = 0;
	
	private static void readGraph(final ListenableGraph<Mine, Long> g, final String ifile) {
		//DirectedGraph<Mine, Long> ret = new DefaultDirectedGraph<>( Long.class );
		
		try {
			final BufferedReader br = new BufferedReader( new FileReader( ifile ) );
			
			final long N = Long.parseLong( br.readLine() );
			
			if ( DEBUG ) {
				System.out.println( "Parsing graph with " + N + " nodes..." );
			}
			
			for (int i = 0; i < N; ++i) {
				final String[] data = br.readLine().split( "\\s+" );
				
				int idx = 0;
				final long x = Long.parseLong( data[idx++] );
				final long y = Long.parseLong( data[idx++] );
				final long r = Long.parseLong( data[idx++] );
				
				final Mine mine = new Mine( x, y, r );
				
				g.addVertex( mine );
				positionVertexAt( mine, (int) x, (int) y );
			}
			
			for (final Mine mine : g.vertexSet()) {
				for (final Mine act : g.vertexSet()) {
					if ( mine.equals( act ) ) {
						continue; // skip self loops
					}
					
					if ( mine.canBlast( act ) ) {
						g.addEdge( mine, act, edgeCtr++ );
					}
					
//					if ( act.canBlast( mine ) ) {
//						g.addEdge( act, mine, edgeCtr++ );
//					}
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException( e );
		}
		
//		return g;
	}
	
	private static final Color DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
	private static final Dimension DEFAULT_SIZE = new Dimension( 50000, 50000 );
	
	private static void adjustDisplaySettings(final JGraph jg) {
		jg.setPreferredSize( DEFAULT_SIZE );
		
		final Color c = DEFAULT_BG_COLOR;
		jg.setBackground( c );
	}
	
	private static void positionVertexAt(final Object vertex, final int x, final int y) {
		final DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
		final Map attr = cell.getAttributes();
		final Rectangle b = GraphConstants.getBounds( attr ).getBounds();
		
		GraphConstants.setBounds( attr, new Rectangle( x, y, b.width, b.height ) );
		
		final Map cellAttr = new HashMap();
		cellAttr.put( cell, attr );
		m_jgAdapter.edit( cellAttr, null, null, null );
	}
}
