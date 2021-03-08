import java.util.*;

public class MancalaImp implements MancalaAgent{
	
	public int move(int[] board){
		
		// Get original game state
		GameState current = new GameState(board, 1);
		
		// Expand current game state into minimax tree
		expand(8, current);
		
		// Search the tree for best move with alpha-beta pruining
		int bestMove = ABsearch(current);
		
		//Return the best move 
		return bestMove;
	}
	
	public String name(){
		return "Cool Agent";
	}
	
	public void reset(){
	}

	private int ABsearch(GameState current){
		
		GameState maxState = current;
		int max = -1000;
		int move = 0;
		
		for (GameState child: current.getChildren()) {
			
			// Find which child game state has the max value
			if (maxValue(child, -1000, 1000) > max){
				maxState = child;
				max = maxValue(child, -1000, 1000);
			}
			
		}
		
		// Find which action produces the best state
		
		List<Integer> moves = possibleMoves(current.getBoard(), current.getPlayer());
		
		for (Integer m: moves){
			
			int[] newBoard = updateBoard(current.getBoard(), m.intValue(), current.getPlayer());
			
			if (Arrays.equals(newBoard, maxState.getBoard())) {
					move = m.intValue();
			}
		

		}
		// return that action
		return move;
	}
	
	
	private int maxValue(GameState s, int alpha, int beta){
		
		// If s is a leaf node
		if (s.getChildren().isEmpty()) return s.getUtil();
		else {
			
			int v = -1000;
			for (GameState child : s.getChildren()) {
				
				v = Math.max(v, minValue(child, alpha, beta));
				if (v >= beta) return v;
				alpha = Math.max(alpha, v);
			}
		return v;
		}		
	}
	
	private int minValue(GameState s, int alpha, int beta) {
		
		// If s is a leaf node
		if (s.getChildren().isEmpty()) return s.getUtil();
		else {
			
			int w = 1000;
			for (GameState child : s.getChildren()) {
				
				w = Math.min(w, maxValue(child, alpha, beta));
				if (w >= alpha) return w;
				beta = Math.min(beta, w);
			}
			return w;
		}
	}
			
		
	private GameState expand(int depth, GameState root){
		
		ArrayList<GameState> visited = new ArrayList<GameState>();
		
		if (depth > 0){
			
			List<Integer> moves = possibleMoves(root.getBoard(), root.getPlayer());
			
			for (Integer m : moves) {
				
				// Update the board based on the move
				int[] newBoard = updateBoard(root.getBoard(), m.intValue(), root.getPlayer());
				
				// Add the new gamestate as a child of the old game state
				if(root.getPlayer() == 1)
					root.addChild(newBoard, 2);
				else 
					root.addChild(newBoard, 1);
				
				// Recursively look through the child game state
				for (GameState child : root.getChildren()) {
					if (!visited.contains(child)) {
						expand(depth-1, child);
						visited.add(child);
					}
				}
			}
		}
		return root;
	}
				
	
	// updates the state of the board given a move
	private static int[] updateBoard(int[] board, int mv, int player){
		
		int[] newBoard = new int[14];
		newBoard = board.clone();
		
		if(player == 1){
			int i = mv;
			while(newBoard[mv]>0){
				i=i==12?0:i+1;
				newBoard[i]++; newBoard[mv]--;
			}
       
			if(i<6 && newBoard[i]==1 && newBoard[12-i]>0){
				newBoard[6]+=newBoard[12-i]; 
				newBoard[12-i]=0;
				newBoard[6]+=newBoard[i];
				newBoard[i]=0;
			}
		}
		else {
			int i = mv;
			while(newBoard[mv]>0){
				i=i==5?7:i==13?0:i+1;
				newBoard[i]++; newBoard[mv]--;
			}
			if(i<13 && i>6 && newBoard[i]==1 && newBoard[12-i]>0){
				newBoard[13]+=newBoard[12-i]; 
				newBoard[12-i]=0;
				newBoard[13]+=newBoard[i];
				newBoard[i] = 0;
			}
      }
      
      // If the game is over adjust the board
      gameOver(newBoard);
      
      return newBoard;
    }
    
     //Tests to see if the game is over
  private static boolean gameOver(int[] board){
  	  
  	  boolean go = true;
  	  for(int i = 0; i<6; i++) go = go && board[i] == 0;
  	  if(go){ 
  	  	  for(int i = 7; i<13; i++){ 
  	  	  	  board[13]+=board[i];
  	  	  	  board[i] = 0;
 	    }
 	    return true;
 	  }
 	  go = true;
 	  for(int i = 7; i<13; i++) go = go && board[i] == 0;
 	  if(go){ 
 	  	  for(int i = 0; i<6; i++){ 
 	  	  	  board[6]+=board[i];
 	  	  	  board[i] = 0;
 	  	  }
 	  	  return true;
 	  }
 	  return false;
 	 }
  
    /*
    // TODO: Make this method more efficient
    private boolean newTurn(int[] board, int mv, int player){
    	
    	if(player == 1){
			int i = mv;
			while(board[mv]>0){
				i=i==12?0:i+1;
				board[i]++; board[mv]--;
			}
			if (i == 6) return true;
		}
		else {
			int i = mv;
			while(board[mv]>0){
				i=i==5?7:i==13?0:i+1;
				board[i]++; board[mv]--;
			}
			if (i == 13) return true;
		}
		return false;
	}
    */
    // Returns the possible moves for a player in a list
    private List<Integer> possibleMoves(int[] Board, int Player){
		
		List<Integer> moves = new ArrayList<Integer>();
		if (Player == 1){
			for(int i = 0; i <= 5; i++){
				if (Board[i] != 0) 
					moves.add(i);
			}
		}
		else{
			for(int i = 7; i <= 12; i++){
				if (Board[i] != 0) 
					moves.add(i);
			}
		}
	return moves;
	}
	
	public static void main(String[] args){
		
		int[] mancalaBoard = {3,3,3,3,3,3,0,3,3,3,3,3,3,0};
		
		MancalaImp x = new MancalaImp();
		
		long start = System.currentTimeMillis();
		System.out.println(x.move(mancalaBoard));
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		
		System.out.println(timeElapsed);
	}
}

class GameState{
	
	private int player; 
	private int[] board;
	private int utility;
	ArrayList<GameState> children;
	
	public GameState(int[] currentBoard, int currentPlayer){
		this.board = currentBoard;
		this.player = currentPlayer;
		this.utility = setUtil();
		this.children = new ArrayList<GameState>();
	}
	
	private int setUtil() {
		if(player == 1)
			return board[6];	
		else
			return board[13];
	}
	
	public void addChild(int[] currentBoard, int currentPlayer){
		children.add(new GameState(currentBoard, currentPlayer));
	}
	
	public int[] getBoard(){
		return this.board;
	}
	
	public int getPlayer(){
		return this.player;
	}			
	
	public int getUtil(){
		return this.utility;
	}
	
	public ArrayList<GameState> getChildren(){
		return this.children;
	}
	
}
			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		