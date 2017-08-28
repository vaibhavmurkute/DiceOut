package com.diceout.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button outputText;
    Button scoreText;
    ImageView imageDice1;
    ImageView imageDice2;
    ImageView imageDice3;
    NumberPicker guessDice1;
    NumberPicker guessDice2;
    NumberPicker guessDice3;
    Random rand;
    int dice1;
    int dice2;
    int dice3;
    int guessValue1;
    int guessValue2;
    int guessValue3;
    int score = 0;
    ArrayList<Integer> diceValues;
    ArrayList<ImageView> diceImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              rollDice(view);
            }
        });

        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!", Toast.LENGTH_SHORT).show();
        outputText = (Button) findViewById(R.id.outputText);
        scoreText = (Button) findViewById(R.id.textScore);
        imageDice1 = (ImageView) findViewById(R.id.imageDice1);
        imageDice2 = (ImageView) findViewById(R.id.imageDice2);
        imageDice3 = (ImageView) findViewById(R.id.imageDice3);

        guessDice1 = (NumberPicker) findViewById(R.id.guessDice1);
        guessDice2 = (NumberPicker) findViewById(R.id.guessDice2);
        guessDice3 = (NumberPicker) findViewById(R.id.guessDice3);

        guessDice1.setMinValue(1);
        guessDice1.setMaxValue(6);
        guessDice1.setWrapSelectorWheel(true);
        guessDice2.setMinValue(1);
        guessDice2.setMaxValue(6);
        guessDice2.setWrapSelectorWheel(true);
        guessDice3.setMinValue(1);
        guessDice3.setMaxValue(6);
        guessDice3.setWrapSelectorWheel(true);

        guessValue1 = guessDice1.getValue();
        guessValue2 = guessDice2.getValue();
        guessValue3 = guessDice3.getValue();

        guessDice1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                guessValue1 = i1;
            }
        });

        guessDice2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                guessValue2 = i1;
            }
        });

        guessDice3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                guessValue3 = i1;
            }
        });


        rand = new Random();

        diceValues = new ArrayList<>();
        diceImages = new ArrayList<>();
        diceImages.add(imageDice1);
        diceImages.add(imageDice2);
        diceImages.add(imageDice3);


    }

    public void rollDice(View view){

        dice1 = rand.nextInt(5)+1;
        dice2 = rand.nextInt(5)+1;
        dice3 = rand.nextInt(5)+1;

        diceValues.clear();
        diceValues.add(dice1);
        diceValues.add(dice2);
        diceValues.add(dice3);

        for(int diceNum = 0; diceNum < 3; diceNum++){
            String imageName = "die_"+diceValues.get(diceNum)+".png";
            try {
                InputStream is = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(is,null);
                diceImages.get(diceNum).setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        outputText.setText("You got a "+dice1+", a "+dice2+" and a "+dice3+"!");

        if(dice1 == guessValue1 && dice2 == guessValue2 && dice3 == guessValue3){
            score += 100;
            Toast.makeText(getApplicationContext(),"Jackpot! All three right guesses! 100 Points..", Toast.LENGTH_SHORT).show();
        }else if((dice1 == guessValue1 && dice2 == guessValue2) || (dice1 == guessValue1 && dice3 == guessValue3) || (dice2 == guessValue2 && dice3 == guessValue3)){
            score += 50;
            Toast.makeText(getApplicationContext(),"Wow! Two right guesses! 50 Points..", Toast.LENGTH_SHORT).show();
        }else if((dice1 == guessValue1) || (dice2 == guessValue2) || (dice3 == guessValue3)){
            score += 10;
            Toast.makeText(getApplicationContext(),"Not bad! One right guess! 10 Points..", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Too Wild! Better luck next Time!", Toast.LENGTH_SHORT).show();
        }

        scoreText.setText("SCORE : "+score);
    }

    public void clearScore(View view){
        score = 0;
        scoreText.setText("SCORE : 0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
