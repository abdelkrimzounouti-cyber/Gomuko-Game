package fr.esisar.in450.gomoku.player;
import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.enums.CellColor;
import fr.esisar.in450.gomoku.gamecore.enums.WinnerState;
import fr.esisar.in450.gomoku.gamecore.model.Coords;
import fr.esisar.in450.gomoku.gamecore.model.GomokuBoard;

public class Minmax extends AbstractPlayer {

    public static int minmax(GomokuBoard board, int prof, boolean maximazing) {


        if (board.getWinnerState() == WinnerState.BLACK || board.getWinnerState() == WinnerState.WHITE ||
                prof == 0) {
            return evaluate(board);
        }
        if (maximazing) {
            int bestscore = Integer.MIN_VALUE;

            for (int i = 0; i < board.SIZE; i++) {
                for (int j = 0; j < board.SIZE; j++) {
                    if (board.coorempty(i, j)) {
                        board.setCellColor(i, j, CellColor.BLACK);
                        int score = minmax(board, prof - 1, false);
                        board.setCellColor(i, j, null);
                        bestscore = Math.max(bestscore, score);
                    }
                }
            }
            return bestscore;
        } else {
            int bestscore = Integer.MAX_VALUE;
            for (int i = 0; i < board.SIZE ; i++) {
                for (int j = 0; j < board.SIZE ; j++) {
                    if (board.coorempty(i, j)) {
                        board.setCellColor(i, j, CellColor.WHITE);
                        int score = minmax(board, prof - 1, true);
                        board.setCellColor(i, j, null);
                        bestscore = Math.min(bestscore, score);
                    }
                }
            }
            return bestscore;
        }
    }

    @Override
    public Coords play() {
        int bestscore = Integer.MIN_VALUE;
        Coords move = null;

        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                if (board.coorempty(i, j)) {
                    board.setCellColor(i, j, CellColor.BLACK );
                    int score = minmax(board, 1, false);
                    board.setCellColor(i, j, null);
                    if (score > bestscore) {
                        bestscore = score;
                        move = new Coords(i, j);
                    }
                }
            }
        }
        return move;
    }

    public static int evaluate(GomokuBoard board) {
        if (board.getWinnerState() == WinnerState.BLACK) {
            return 100000;
        }
        if (board.getWinnerState() == WinnerState.WHITE) {
            return -100000;
        }
        int scoreMax = 0;
        int scoreMin = 0;

        scoreMax += evaluatePlayer(board, CellColor.BLACK);
        scoreMin += evaluatePlayer(board, CellColor.WHITE);

        return scoreMax - scoreMin;
    }

    public static int evaluatePlayer(GomokuBoard board, CellColor color) {
        int score = 0;

        // check rows "wwwww" or "BBBBB"
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j <= board.SIZE - 5; j++) {
                int count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board.getCellColor(i, j + k) == color) {
                        count++;
                    } else {
                        break;
                    }
                }
                if(count  == 5){
                    score += 100000;
                }
                else if(count == 4){
                    score += 10000;
                }
                else if(count == 3){
                    score += 1000;
                }
                else if(count == 2){
                    score += 100;
                }
                else if(count == 1){
                    score += 10;
                }
            }
        }
        // check col
        for (int i=0; i <= board.SIZE - 5; i++) {
            for (int j=0; j < board.SIZE ; j++) {
                int count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board.getCellColor(i+ k, j) == color) {
                        count++;
                    }
                    else{
                        break;
                    }
                }
                if(count  == 5){
                    score += 100000;
                }
                else if(count == 4){
                    score += 10000;
                }
                else if(count == 3){
                    score += 1000;
                }
                else if(count == 2){
                    score += 100;
                }
                else if(count == 1){
                    score += 10;
                }

            }
        }
        // check diago
        for(int i=0; i <= board.SIZE - 5; i++){
            for(int j=0; j <= board.SIZE - 5 ; j++){
                int count = 0;
                for(int k=0; k < 5; k++){
                    if (board.getCellColor(i+ k, j+k) == color) {
                        count++;
                    }
                    else{
                        break;
                    }
                }
                if(count  == 5){
                    score += 100000;
                }
                else if(count == 4){
                    score += 10000;
                }
                else if(count == 3){
                    score += 1000;
                }
                else if(count == 2){
                    score += 100;
                }
                else if(count == 1) {
                    score += 10;
                }
            }
        }
        for(int i=0; i <= board.SIZE - 5; i++) {
            for (int j = 4; j < board.SIZE ; j++) {
                int count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board.getCellColor(i + k, j - k) == color) {
                        count++;
                    } else {
                        break;
                    }
                }
                if(count  == 5){
                    score += 100000;
                }
                else if(count == 4){
                    score += 10000;
                }
                else if(count == 3){
                    score += 1000;
                }
                else if(count == 2){
                    score += 100;
                }
                else if(count == 1){
                    score += 10;
                }
            }
        }

        return score;
    }
}
