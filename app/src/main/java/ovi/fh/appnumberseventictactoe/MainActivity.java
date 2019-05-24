package ovi.fh.appnumberseventictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

    class Counter{

        private void firstPlayerSelect(){
            if (coin == 1){
                Toast.makeText(MainActivity.this,"yellow goes 1st",Toast.LENGTH_LONG).show();
                playerTurn = 1;
            }else {
                Toast.makeText(MainActivity.this,"red goes 1st",Toast.LENGTH_LONG).show();
                playerTurn = 2;
            }
        }

        private void winGame(){
            for (int[] winningPosition : finalGameState){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 3){

                    if (playerTurn == 1){
                        message = "red has won";
                    }else {
                        message = "yellow has won";
                    }
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    Counter counter = new Counter();

    public void dropIn(View view){

        ImageView counterOnclick = (ImageView) view;

        Log.i("Tag",counterOnclick.getTag().toString());

        int tapedCounter = Integer.parseInt(counterOnclick.getTag().toString());

        gameState[tapedCounter] = playerTurn;


        counterOnclick.setTranslationY(-1500); // to set image off the screen

        if (playerTurn == 1){
            counterOnclick.setImageResource(R.drawable.yellow);
            playerTurn = 2;
        }else {
            counterOnclick.setImageResource(R.drawable.red);
            playerTurn = 1;
        }
        
        counterOnclick.animate().translationYBy(1500).rotation(1800).setDuration(1000);
        counter.winGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter.firstPlayerSelect();
    }
}
