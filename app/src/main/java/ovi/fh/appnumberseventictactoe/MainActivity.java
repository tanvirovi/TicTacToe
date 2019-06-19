package ovi.fh.appnumberseventictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int playerTurn;
    //1 is yellow, 2 is red

    Random random = new Random();
    int coin = random.nextInt(2);

    String message = Integer.toString(coin);

    int[] gameState = {3,3,3,3,3,3,3,3,3};
    int[][] finalGameState = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int tapedCounter;
    boolean gameActive = true;
    int totalMoveCounter;


    public class Counter{

        public void firstPlayerSelect(){
            if (coin == 1){
                Toast.makeText(MainActivity.this,"yellow goes 1st",Toast.LENGTH_LONG).show();
                playerTurn = 1;
            }else {
                Toast.makeText(MainActivity.this,"red goes 1st",Toast.LENGTH_LONG).show();
                playerTurn = 2;
            }
        }

        public void winGame(){
            if (totalMoveCounter < 9) {
                for (int[] winningPosition : finalGameState) {
                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                            gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                            gameState[winningPosition[0]] != 3) {

                        if (playerTurn == 1) {
                            message = "red has won";
                        } else if (playerTurn == 2) {
                            message = "yellow has won";
                        }
                        gameActive = false;
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }else {
                message = "draw";
                gameActive = false;
            }
        }
    }

    Counter counter = new Counter();

    public void dropIn(View view){

        ImageView counterOnclick = (ImageView) view;

        Log.i("Tag",counterOnclick.getTag().toString());

        tapedCounter = Integer.parseInt(counterOnclick.getTag().toString());

        if (gameState[tapedCounter] == 3 && gameActive) {
            gameState[tapedCounter] = playerTurn;
            counterOnclick.setTranslationY(-1500); // to set image off the screen

            if (playerTurn == 1) {
                counterOnclick.setImageResource(R.drawable.yellow);
                playerTurn = 2;
            } else {
                counterOnclick.setImageResource(R.drawable.red);
                playerTurn = 1;
            }

            counterOnclick.animate().translationYBy(1500).rotation(1800).setDuration(1000);
            totalMoveCounter++;
            counter.winGame();
            Button playAgain = findViewById(R.id.playAgainButton);
            TextView textView = findViewById(R.id.winningText);
            textView.setText(message);

            if(gameActive == false){
                playAgain.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playAgain(View view){

        Button play = findViewById(R.id.playAgainButton);
        TextView textView = findViewById(R.id.winningText);
        play.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutImage);
        for (int j = 0; j < gridLayout.getChildCount(); j++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(j);
            imageView.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 3;
        }
        counter.firstPlayerSelect();
        gameActive = true;

        totalMoveCounter = 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter.firstPlayerSelect();
    }
}
