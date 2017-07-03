import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	
	private final Comparator<SearchNode> HAMMING_PRIORITY = new HammingComparator();
	private final Comparator<SearchNode> MANHATTAN_PRIORITY = new ManhattanComparator();
	
	private MinPQ<SearchNode> mpq = new MinPQ<>(MANHATTAN_PRIORITY);
	
	private static class SearchNode {
		Board board;
		int moves;
		SearchNode prevSN;
		
		SearchNode(){};
		
		SearchNode(Board board, int moves, SearchNode prevSN) {
			this.board = board;
			this.moves = moves;
			this.prevSN = prevSN;
		}
	}
	
	private static class HammingComparator implements Comparator<SearchNode> {

		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			int c0 = o1.board.hamming() + o1.moves;
			int c1 = o2.board.hamming() + o2.moves;
			if (c0 < c1)
				return -1;
			else if (c0 > c1)
				return 1;
			else
				return 0;
		}
		
	}
	
	private static class ManhattanComparator implements Comparator<SearchNode> {

		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			int c0 = o1.board.manhattan() + o1.moves;
			int c1 = o2.board.manhattan() + o2.moves;
			if (c0 < c1)
				return -1;
			else if (c0 > c1)
				return 1;
			else
				return 0;
		}
		
	}
	
	public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
		SearchNode sn = new SearchNode(initial, 0, null);
		mpq.insert(sn);
		
		SearchNode minSN = mpq.delMin();
		Iterable<Board> neighbors = minSN.board.neighbors();
		for (Board neighbor : neighbors) {
			if (!neighbor.equals(minSN.prevSN.board)) {
				mpq.insert(new SearchNode(neighbor, minSN.moves + 1, minSN));
			}
		}
		
	}
	
    public boolean isSolvable() {            // is the initial board solvable?
    	
    }
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
    	
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
    	
    }
    public static void main(String[] args) { // solve a slider puzzle (given below
    	
    }
}
