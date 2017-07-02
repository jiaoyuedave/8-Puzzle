import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private final int[][] blocks;
	private final int d;                // dimension
	
	
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
    	                                     // (where blocks[i][j] = block in row i, column j)
    	this.blocks = blocks;
    	d = dimension();
    }
                                           
    public int dimension() {                 // board dimension n
    	return blocks.length;
    }
    
    public int hamming() {                   // number of blocks out of place
    	int num = 0;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (blocks[i][j] != i * 3 + j + 1) {
    				num++;
    			}
    		}
    	}
    	// the last block is blank
		if (blocks[d - 1][d - 1] == 0) {    
			num--;
		}
    	return num;
    }
    
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
    	int sum = 0;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			int number = blocks[i][j];
    			if (number == 0) {
    				sum += (d - 1 - i) + (d - 1 - j);
    			} else {
    				sum += Math.abs((number - 1) / 3 - i) + Math.abs((number - 1) % 3 - j);
    			}
    		}
    	}
    	return sum;
    }
    
    public boolean isGoal() {                // is this board the goal board?
    	// eliminate the special case
    	if (blocks[2][2] != 0)
    		return false;
    	
    	// inspect the rest
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (blocks[i][j] != i * 3 + j + 1) {
    				if (i == 2 && j == 2) 
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
    	System.arraycopy(blocks, 0, twinBlocks, 0, blocks.length);
    	int temp = twinBlocks[0][0];
    	twinBlocks[0][0] = twinBlocks[0][1];
    	twinBlocks[0][1] = temp;
    	return new Board(twinBlocks);
    }
    
    public boolean equals(Object y) {        // does this board equal y?
    	Board board = (Board) y;
    	int[][] blocks = board.blocks;
    	for (int i = 0; i < d; i++) {
    		for (int j = 0; j < d; j++) {
    			if (this.blocks[i][j] != blocks[i][j]) {
    				return false;
    			}
    		}
    	}
    	return true;
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
    		System.arraycopy(blocks, 0, tempBlocks, 0, blocks.length);
    		tempBlocks[m][n] = tempBlocks[m - 1][n];
    		tempBlocks[m - 1][n] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (m < d - 1) {
    		// move down square up
    		int[][] tempBlocks = new int[d][d];
    		System.arraycopy(blocks, 0, tempBlocks, 0, blocks.length);
    		tempBlocks[m][n] = tempBlocks[m + 1][n];
    		tempBlocks[m + 1][n] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (n > 0) {
    		// move left square right
    		int[][] tempBlocks = new int[d][d];
    		System.arraycopy(blocks, 0, tempBlocks, 0, blocks.length);
    		tempBlocks[m][n] = tempBlocks[m][n - 1];
    		tempBlocks[m][n - 1] = 0;
    		list.add(new Board(tempBlocks));
    	}
    	if (n < d - 1) {
    		// move right square left
    		int[][] tempBlocks = new int[d][d];
    		System.arraycopy(blocks, 0, tempBlocks, 0, blocks.length);
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
    	
    }
}
