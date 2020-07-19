package commons.dataTypes;

import commons.game.Board;

import java.io.Serializable;

public class History implements Serializable {
    public String from,to,winner;
    public Board board;
    public long timeStamp;
    public int totalMoves;

    public History(String from, String to, String winner, Board board, long timeStamp, int totalMoves) {
        this.from = from;
        this.to = to;
        this.winner = winner;
        this.board = board;
        this.timeStamp = timeStamp;
        this.totalMoves = totalMoves;
    }
}
