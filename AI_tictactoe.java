import java.util.Scanner;

public class AI_tictactoe {

    private char[][] board;
    private static final int SIZE = 3; // The size of the Tic-Tac-Toe board (3x3)
    private static final char PLAYER_X = 'X'; // Symbol for the human player
    private static final char PLAYER_O = 'O'; // Symbol for the AI player
    private static final char EMPTY = ' '; // Represents an empty cell on the board

    // Constructor to initialize the game
    public AI_tictactoe() {
        board = new char[SIZE][SIZE];
        initializeBoard();
    }

    // Initialize the board with empty cells
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Print the current state of the board
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("   ");
            }
        }
    }

    // Check if the board is full (a draw)
    public boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if the game is over (either a win or a draw)
    public boolean isGameOver() {
        return checkWin(PLAYER_X) || checkWin(PLAYER_O) || isBoardFull();
    }

    // Check if a player has won
    public boolean checkWin(char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    // Make a move on the board
    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != EMPTY) {
            return false; // Invalid move
        }

        board[row][col] = player; // Place the player's symbol on the board
        return true; // Move is successful
    }

    public int minimax(char player) {
        // Check for terminal states: win for X, win for O, or a draw
        if (checkWin(PLAYER_X)) {
            return -10; // X has won, return a score indicating a bad outcome for AI
        } else if (checkWin(PLAYER_O)) {
            return 10; // O has won, return a score indicating a good outcome for AI
        } else if (isBoardFull()) {
            return 0; // It's a draw, return a neutral score
        }
    
        // Initialize the bestScore based on the current player
        int bestScore = (player == PLAYER_O) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    
        // Iterate through the board and evaluate possible moves
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;
    
                    // Recursively call minimax for the opponent's turn
                    if (player == PLAYER_O) {
                        int score = minimax(PLAYER_X);
                        bestScore = Math.max(bestScore, score); // For Player O, maximize the score
                    } else {
                        int score = minimax(PLAYER_O);
                        bestScore = Math.min(bestScore, score); // For Player X, minimize the score
                    }
    
                    board[i][j] = EMPTY; // Undo the move
                }
            }
        }
    
        return bestScore; // Return the best score for the current player (AI or human)
    }
    
    // Make a move for the AI based on the minimax evaluation
    public void makeAIMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        // Iterate through the board and evaluate AI's moves
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;

                    int score = minimax(PLAYER_X);

                    board[i][j] = EMPTY; // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        makeMove(bestRow, bestCol, PLAYER_O); // Make the best move for the AI
    }

    // Main game loop
    public void play() {
        Scanner scanner = new Scanner(System.in);
        char currentPlayer = PLAYER_X;

        while (!isGameOver()) {
            printBoard();

            if (currentPlayer == PLAYER_X) {
                System.out.println("Player Aniket's turn. Enter row and column (0-2):");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (makeMove(row, col, currentPlayer)) {
                    currentPlayer = PLAYER_O;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("AI player's turn.");
                makeAIMove();
                currentPlayer = PLAYER_X;
            }
        }

        scanner.close();
        printBoard();

        if (checkWin(PLAYER_X)) {
            System.out.println("Aniket wins!");
        } else if (checkWin(PLAYER_O)) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static void main(String[] args) {
        AI_tictactoe game = new AI_tictactoe();
        game.play();
    }
}
