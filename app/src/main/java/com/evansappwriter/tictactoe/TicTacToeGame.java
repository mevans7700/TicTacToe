package com.evansappwriter.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by markevans on 6/24/16.
 */
public class TicTacToeGame {
    private char mBoard[];
    private ArrayList<Integer> mHumanMoves = new ArrayList<>();

    private final static int BOARD_SIZE = 9;
    private final static int CENTER = 4;
    private final static List<Integer> CORNERS = Arrays.asList(0,2,6,8);
    private final static List<Integer> EDGES = Arrays.asList(1,3,5,7);

    public static final char HUMAN_PLAYER = 'X';
    public static final char ANDROID_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';

    public static final int KEEP_PLAYING = 0;
    public static final int TIE = 1;
    public static final int HUMAN_WON = 2;
    public static final int ANDROID_WON = 3;


    private Random mRand;

    public static int getBOARDSIZE() {
        return BOARD_SIZE;
    }

    public TicTacToeGame(){

        mBoard = new char[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }

        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }
        mHumanMoves.clear();
    }

    public void setMove(char player, int location) {
        mBoard[location] = player;
        if (player == HUMAN_PLAYER) {
            mHumanMoves.add(location);
        }
    }

    public int getComputerMove() {
        int move;

        // Android winning move
        for (int i = 0; i < getBOARDSIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = ANDROID_PLAYER;
                if (checkForWinner() == ANDROID_WON) {
                    setMove(ANDROID_PLAYER, i);
                    return i;
                }
                else {
                    mBoard[i] = curr;
                }
            }
        }

        // Block Human winning move
        for (int i = 0; i < getBOARDSIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == HUMAN_WON) {
                    setMove(ANDROID_PLAYER, i);
                    return i;
                }
                else {
                    mBoard[i] = curr;
                }
            }
        }

        if (mHumanMoves.size() == 1) {
            // corners
            if (isCorner(mHumanMoves.get(0))) {
                setMove(ANDROID_PLAYER, CENTER);
                return CENTER;
            }

            // edges
            if (isEdge(mHumanMoves.get(0))) {
                return (mHumanMoves.get(0) == 3 || mHumanMoves.get(0) == 5) ?
                        (mHumanMoves.get(0) - 3) : (mHumanMoves.get(0) - 1);
            }

            // center
            if (isCenter(mHumanMoves.get(0))) {
                setMove(ANDROID_PLAYER, 0);
                return 0;
            }
        }

        if (mHumanMoves.size() == 2) {
            if (EDGES.contains(mHumanMoves.get(0))) {
                setMove(ANDROID_PLAYER, CENTER);
                return CENTER;
            }
            if (CORNERS.contains(mHumanMoves.get(0)) && CORNERS.contains(mHumanMoves.get(1))) {
                setMove(ANDROID_PLAYER, 1);
                return 1;
            }
        }

        do {
            move = mRand.nextInt(getBOARDSIZE());
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == ANDROID_PLAYER);

        setMove(ANDROID_PLAYER, move);
        return move;
    }

    private boolean isCorner(int location) {
        return CORNERS.contains(location);
    }

    private boolean isCenter(int location) {
        return CENTER == location;
    }

    private boolean isEdge(int location) {
        return EDGES.contains(location);
    }

    public int checkForWinner() {
        // accross
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2] == HUMAN_PLAYER) {
                return HUMAN_WON;
            }
            if (mBoard[i] == ANDROID_PLAYER &&
                    mBoard[i+1] == ANDROID_PLAYER &&
                    mBoard[i+2] == ANDROID_PLAYER) {
                return ANDROID_WON;
            }
        }

        // down
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6] == HUMAN_PLAYER) {
                return HUMAN_WON;
            }
            if (mBoard[i] == ANDROID_PLAYER &&
                    mBoard[i+3] == ANDROID_PLAYER &&
                    mBoard[i+6] == ANDROID_PLAYER) {
                return ANDROID_WON;
            }
        }

        // diagonal
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER) {
            return HUMAN_WON;
        }
        if ((mBoard[0] == ANDROID_PLAYER &&
                mBoard[4] == ANDROID_PLAYER &&
                mBoard[8] == ANDROID_PLAYER) ||
                mBoard[2] == ANDROID_PLAYER &&
                        mBoard[4] == ANDROID_PLAYER &&
                        mBoard[6] == ANDROID_PLAYER) {
            return ANDROID_WON;
        }

        // any spots still open
        for (int i = 0; i < getBOARDSIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {
                return KEEP_PLAYING;
            }
        }

        // all spots taken
        return TIE;
    }
}
