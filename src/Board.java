import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	
	private final int[][] blocks;
	private final int d;                // dimension
	
	
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
    	                                     // (where blocks[i][j] = block in row i, column j)
    	d = blocks.length;
    	this.blocks = new int[d][d];
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			this.blocks[i][j] = blocks[i][j];
    		}
    	}
    }
                                           
    public int dimension() {                 // board dimension n
    	return d;
    }
    
    public int hamming() {                   // number of blocks out of place
    	int num = 0;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (blocks[i][j] != i * d + j + 1) {
    				num++;
    			}
    		}
    	}
    	// the last block is blank
    	num--;
//		if (blocks[d - 1][d - 1] == 0) {    
//			num--;
//		}
    	return num;
    }
    
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
    	int sum = 0;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			int number = blocks[i][j];
    			if (number != 0) {
    				sum += Math.abs((number - 1) / d - i) + Math.abs((number - 1) % d - j);
    			}
    		}
    	}
    	return sum;
    }
    
    public boolean isGoal() {                // is this board the goal board?
    	// eliminate the special case
    	if (blocks[d - 1][d - 1] != 0)
    		return false;
    	
    	// inspect the rest
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (blocks[i][j] != i * d + j + 1) {
    				if (i == d - 1 && j == d - 1) 
    					continue;
    				else 
    					return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
    	int[][] twinBlocks = new int[d][d];
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			twinBlocks[i][j] = blocks[i][j];
    		}
    	}
    	
    	int p = 0, m = 0;
    	int q = 0, n = 1;
    	int cnt = 0;
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				if (twinBlocks[i][j] != 0) {
					if (cnt == 0) {
						p = i;
						m = j;
						cnt++;
					} else if (cnt == 1) {
						q = i;
						n = j;
						cnt++;
					} else {
						break;
					}
				}
			}
		}
    	
    	int temp = twinBlocks[p][m];
    	twinBlocks[p][m] = twinBlocks[q][n];
    	twinBlocks[q][n] = temp;
    	return new Board(twinBlocks);
    }
    
    public boolean equals(Object y) {        // does this board equal y?
    	if (y == null) {
    		return false;
    	}
    	if (y instanceof Board) {
			Board board = (Board) y;
			if (this.d != board.d) {
				return false;
			}
			int[][] blocks = board.blocks;
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < d; j++) {
					if (this.blocks[i][j] != blocks[i][j]) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
    }
    
    public Iterable<Board> neighbors() {     // all neighboring boards
    	List<Board> list = new ArrayList<>();
    	
    	// find the blank square
    	int m = 0, n = 0;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (blocks[i][j] == 0) {
    				m = i;
    				n = j;
    			}
    		}
    	}
    	
    	if (m > 0) {
    		// move up square down
    		int[][] tempBlocks = new int[d][d];
    		for (int i = 0; i < d; i++) {
        		for (int j = 0; j < d; j++) {
        			tempBlocks[i][j] = blocks[i][j];
        		}
        	}
    		tempBlocks[m][n] = tempBlocks[m - 1][n];
    		tempBlocks[m - 1][n] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (m < d - 1) {
    		// move down square up
    		int[][] tempBlocks = new int[d][d];
    		for (int i = 0; i < d; i++) {
        		for (int j = 0; j < d; j++) {
        			tempBlocks[i][j] = blocks[i][j];
        		}
        	}
    		tempBlocks[m][n] = tempBlocks[m + 1][n];
    		tempBlocks[m + 1][n] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (n > 0) {
    		// move left square right
    		int[][] tempBlocks = new int[d][d];
    		for (int i = 0; i < d; i++) {
        		for (int j = 0; j < d; j++) {
        			tempBlocks[i][j] = blocks[i][j];
        		}
        	}
    		tempBlocks[m][n] = tempBlocks[m][n - 1];
    		tempBlocks[m][n - 1] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (n < d - 1) {
    		// move right square left
    		int[][] tempBlocks = new int[d][d];
    		for (int i = 0; i < d; i++) {
        		for (int j = 0; j < d; j++) {
        			tempBlocks[i][j] = blocks[i][j];
        		}
        	}
    		tempBlocks[m][n] = tempBlocks[m][n + 1];
    		tempBlocks[m][n + 1] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	
    	return list;
    }
    
    public String toString() {               // string representation of this board (in the output format specified below)
    	StringBuilder sb = new StringBuilder();
    	sb.append(d + "\n");
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			sb.append(" " + blocks[i][j]);
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
    	// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        StdOut.println(initial);
        StdOut.println(initial.isGoal());
    }
}
