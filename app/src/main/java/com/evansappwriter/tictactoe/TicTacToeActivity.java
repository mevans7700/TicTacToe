package com.evansappwriter.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {
    private TicTacToeGame mGame;

    private Button[] mCellButtons;

    private TextView mInfoTV;

    private boolean mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mCellButtons = new Button[TicTacToeGame.getBOARDSIZE()];
        mCellButtons[0] = (Button) findViewById(R.id.one);
        mCellButtons[1] = (Button) findViewById(R.id.two);
        mCellButtons[2] = (Button) findViewById(R.id.three);
        mCellButtons[3] = (Button) findViewById(R.id.four);
        mCellButtons[4] = (Button) findViewById(R.id.five);
        mCellButtons[5] = (Button) findViewById(R.id.six);
        mCellButtons[6] = (Button) findViewById(R.id.seven);
        mCellButtons[7] = (Button) findViewById(R.id.eight);
        mCellButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTV = (TextView) findViewById(R.id.information);

        mGame = new TicTacToeGame();

        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.newGame:
                startNewGame();
                break;
            case R.id.exitGame:
                finish();
                break;
        }

        return true;
    }

    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mCellButtons.length; i++) {
            mCellButtons[i].setText("");
            mCellButtons[i].setEnabled(true);
            mCellButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mInfoTV.setText(R.string.first_human);

        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mCellButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);

                    int gameStatus = mGame.checkForWinner();

                    if (gameStatus == TicTacToeGame.KEEP_PLAYING) {
                        mInfoTV.setText(R.string.turn_computer);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.ANDROID_PLAYER, move);
                        gameStatus = mGame.checkForWinner();
                    }

                    if (gameStatus == TicTacToeGame.KEEP_PLAYING) {
                        mInfoTV.setText(R.string.turn_human);
                    } else if (gameStatus == TicTacToeGame.TIE) {
                        mInfoTV.setText(R.string.result_tie);
                        mGameOver = true;
                    } else if (gameStatus == TicTacToeGame.HUMAN_WON) {
                        mInfoTV.setText(R.string.result_human_wins);
                        mGameOver = true;
                    } else {
                        mInfoTV.setText(R.string.result_android_wins);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mCellButtons[location].setEnabled(false);
        mCellButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER) {
            mCellButtons[location].setTextColor(Color.GREEN);
        } else {
            mCellButtons[location].setTextColor(Color.RED);
        }
    }

}
