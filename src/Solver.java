import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
//	private final Comparator<SearchNode> HAMMING_PRIORITY = new HammingComparator();
	private final Comparator<SearchNode> MANHATTAN_PRIORITY = new ManhattanComparator();
	
	private final SearchNode finalSN;
	private final boolean solvable;
	
	private static class SearchNode {
		Board board;
		int moves;
		SearchNode prevSN;
				
		SearchNode(Board board, int moves, SearchNode prevSN) {
			this.board = board;
			this.moves = moves;
			this.prevSN = prevSN;
		}
	}
	
//	private static class HammingComparator implements Comparator<SearchNode> {
//
//		@Override
//		public int compare(SearchNode o1, SearchNode o2) {
//			int c0 = o1.board.hamming() + o1.moves;
//			int c1 = o2.board.hamming() + o2.moves;
//			if (c0 < c1)
//				return -1;
//			else if (c0 > c1)
//				return 1;
//			else
//				return 0;
//		}
//		
//	}
	
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
		if (initial == null) {
			throw new IllegalArgumentException();
		}
		MinPQ<SearchNode> initPQ = new MinPQ<>(MANHATTAN_PRIORITY);
		MinPQ<SearchNode> twinPQ = new MinPQ<>(MANHATTAN_PRIORITY);
		Board twin = initial.twin();
		
		initPQ.insert(new SearchNode(initial, 0, null));
		twinPQ.insert(new SearchNode(twin, 0, null));
		
		while (true) {
			SearchNode initSN = initPQ.delMin();
			SearchNode twinSN = twinPQ.delMin();
			if (initSN.board.isGoal()) {
				finalSN = initSN;
				solvable = true;
				break;
			} else if (twinSN.board.isGoal()) {
				finalSN = null;
				solvable = false;
				break;
			}
			Iterable<Board> neighbors = initSN.board.neighbors();
			for (Board neighbor : neighbors) {
				if (initSN.prevSN != null && neighbor.equals(initSN.prevSN.board)) {
					continue;
				}
				initPQ.insert(new SearchNode(neighbor, initSN.moves + 1, initSN));
			} 
			neighbors = twinSN.board.neighbors();
			for (Board neighbor : neighbors) {
				if (twinSN.prevSN != null && neighbor.equals(twinSN.prevSN.board)) {
					continue;
				}
				twinPQ.insert(new SearchNode(neighbor, twinSN.moves + 1, twinSN));
			} 
		}
		
	}
	
    public boolean isSolvable() {            // is the initial board solvable?
    	return solvable;
    }
    
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
    	if (solvable) {
    		return finalSN.moves;
    	} else {
    		return -1;
    	}
    }
    
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
    	Stack<Board> solutions = new Stack<>();
    	SearchNode sn = finalSN;
    	while (sn != null) {
    		Board board = sn.board;
    		solutions.push(board);
    		sn = sn.prevSN;
    	}
    	return solutions;
    }
    
    public static void main(String[] args) { // solve a slider puzzle (given below
    	// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
