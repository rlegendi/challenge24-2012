//package F;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
//import edu.uci.ics.jung.graph.Graph;
//
//class ExchangeOffer {
//	double rate;
//	double limit;
//	
//	public ExchangeOffer(double rate, double limit) {
//		this.rate = rate;
//		this.limit = limit;
//	}
//	
//	@Override
//	public String toString() {
//		return "ExchangeOffer [rate=" + rate + ", limit=" + limit + "]";
//	}
//}
//
//class Path {
//	List<Integer> components = new ArrayList<Integer>();
//	
//	public Path() {
//		
//	}
//	
//	
//	public Path(Path p, Integer i) {
//		components.addAll( p.components );
//		components.add( i );
//	}
//
//	public int length() {
//		return components.size();
//	}
//
//}
//
//public class Main {
//	
//	static Graph<Integer, ExchangeOffer> graph = null;
//	static double money = 0.0, profit = 0.0;
//	
//	public static void main(String[] args) {
//		graph = readData( "doc\\PROBLEMSET\\input\\F\\test.in" );
//		
//		Path maxPath = null;
//		do {
//			maxPath = null;
//			double max = 0;
//			
//			for (Path act : allPathsFromZero()) {
//				
//				double fitness = fitness( act );
//				
//				if ( fitness > max ) {
//					maxPath = act;
//					max = fitness;
//				}
//			}
//			
//		} while ( maxPath != null );
//		
//		System.out.println( graph );
//	}
//	
//	static Graph<Integer, ExchangeOffer> readData(String fname) {
//		Graph<Integer, ExchangeOffer> graph = new DirectedSparseMultigraph<Integer, ExchangeOffer>();
//		
//		try {
//			BufferedReader br = new BufferedReader( new FileReader( fname ) );
//			
//			String[] header = br.readLine().split( "\\s+" );
//			int N = Integer.parseInt( header[0] );
//			int K = Integer.parseInt( header[1] );
//			money = Integer.parseInt( header[2] ); // S
//			
//			String line = null;
//			while ( ( line = br.readLine() ) != null ) {
//				String[] data = line.split( "\\s+" );
//				assert ( data.length == 4 );
//				
//				int i = 0;
//				int from = Integer.parseInt( data[i++] );
//				int to = Integer.parseInt( data[i++] );
//				double rate = Double.parseDouble( data[i++] );
//				int limit = Integer.parseInt( data[i++] );
//				
//				graph.addEdge( new ExchangeOffer( rate, limit ), from, to );
//			}
//		} catch (Exception e) {
//			throw new RuntimeException( e );
//		}
//		
//		return graph;
//	}
//	
//	private static List<Path> allPathsFromZero() {
//		return allPaths(0, money);
//	}
//	
//	private static List<Path> allPaths(int node, double usableMoney) {
//		//Graph<Integer, ExchangeOffer> modGraph = cloneGraph(graph); 
//		
//		ArrayList<Path> paths = new ArrayList<Path>();
//		
//		boolean profitableRouteFound = true;
//		
//		int pathLength = 1;
//		while (profitableRouteFound) {
//			
//			for (int i=0; i<pathLength; ++i) {
//				int possibleChange = 0;
//				
//				graph.getNeighbors( node );
//			}
//			
//			pathLength++;
//			
//			profitableRouteFound = ...;
//		}
//		
//		return paths;
//	}
//	
//	static void getPaths(Integer node, int requiredLength, Path act, List<Path> paths) {
//		if (act.length() == requiredLength) {
//			return;
//		}
//		
//		for (Integer n : graph.getNeighbors( node )) {
//			if (canAdd()) {
//				getPaths(n, requiredLength, new Path(path, n), paths);
//			}
//		}
//	}
//
//	private static Graph<Integer, ExchangeOffer> cloneGraph(Graph<Integer, ExchangeOffer> graphToClone) {
//		DirectedSparseMultigraph<Integer, ExchangeOffer> ret = new DirectedSparseMultigraph<Integer, ExchangeOffer>();
//		
//		for (Integer v : graphToClone.getVertices()) {
//			ret.addVertex( v );
//		}
//		
//		for (ExchangeOffer e : graphToClone.getEdges()) {
//			ret.addEdge( e, graphToClone.getEndpoints( e ) );
//		}
//		
//		return ret;
//	}
//
//	private static double fitness(Path act) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	
//	private static boolean moneyIncremented() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//}
