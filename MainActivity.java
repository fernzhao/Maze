package com.comp2601.mazesolver;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Scale of the game (number of rows and columns)
    private  static final int NUM_ROWS = 10;
    private  static final int NUM_COLS = 10;


    // Initial Start and Destination locations
    private  static final int INITIAL_START_ROW = 0;
    private  static final int INITIAL_START_COL = 0;
    private  static final int INITIAL_DESTINATION_ROW = NUM_ROWS - 1;
    private  static final int INITIAL_DESTINATION_COL = NUM_COLS - 1;


    private  Button solveMazeButton;
    private Maze model; //not needed for tutorial

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    boolean start = true;
    boolean end = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Maze(NUM_ROWS, NUM_COLS, this); //for the assignment

        // Adding buttons with UI Threads
        TableLayout gameLayout = findViewById(R.id.gameTable);
        for (int i=0; i< NUM_ROWS; i++){
            TableRow tableRow = new TableRow(MainActivity.this);

            for (int j=0; j<NUM_COLS; j++){
                Button button = new Button(MainActivity.this);

                button.setText(getResources().getText(R.string.empty));
                button.setBackgroundColor(getResources().getColor(R.color.empty));
                tableRow.addView(button);
                buttons[i][j] = button;

            }
            gameLayout.addView(tableRow);
        }

        buttons[INITIAL_DESTINATION_ROW][INITIAL_DESTINATION_COL].setText(getResources().getText(R.string.destination));
        buttons[INITIAL_DESTINATION_ROW][INITIAL_DESTINATION_COL].setBackgroundColor(getResources().getColor(R.color.destination));
        buttons[INITIAL_START_ROW][INITIAL_START_COL].setBackgroundColor(getResources().getColor(R.color.start));
        buttons[INITIAL_START_ROW][INITIAL_START_COL].setText(getResources().getText(R.string.start));



        solveMazeButton = (Button) findViewById(R.id.button_solve_maze);
        solveMazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                solveMaze solve = new solveMaze();
                solve.execute();
                for(int i=0; i<NUM_ROWS; i++){
                    for(int j=0; j<NUM_COLS; j++){
                        buttons[i][j].setClickable(false);
                    }
                }
                Toast t = Toast.makeText(MainActivity.this, b.getText(), Toast.LENGTH_SHORT);
                t.show();
            }



        });

        for(int i=0;i<NUM_ROWS;i++){
            for(int j=0;j<NUM_COLS;j++){
                final int I = i;
                final int J = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button) v;

                        if(b.getText().equals(getResources().getText(R.string.empty))){
                            if(start && end){
                                b.setText(getResources().getText(R.string.wall));
                                b.setBackgroundColor(getResources().getColor(R.color.wall));
                                model.setWall(I, J);
                            }
                            else if (!start){
                                b.setText(getResources().getText(R.string.start));
                                b.setBackgroundColor(getResources().getColor(R.color.start));
                                start = true;
                                model.setStart(I, J);
                            }
                            else if(!end){
                                b.setText(getResources().getText(R.string.destination));
                                b.setBackgroundColor(getResources().getColor(R.color.destination));
                                end = true;
                                model.setEnd(I, J);
                            }
                        }
                        else if(b.getText().equals(getResources().getText(R.string.wall))){
                            b.setText(getResources().getText(R.string.empty));
                            b.setBackgroundColor(getResources().getColor(R.color.empty));
                            model.setEmpty(I, J);
                        }

                        else if(b.getText().equals(getResources().getText(R.string.destination))){

                            b.setText(getResources().getText(R.string.empty));
                            b.setBackgroundColor(getResources().getColor(R.color.empty));
                            end = false;
                            model.setEmpty(I, J);


                        }
                        else if(b.getText().equals(getResources().getText(R.string.start))){

                            b.setText(getResources().getText(R.string.empty));
                            b.setBackgroundColor(getResources().getColor(R.color.empty));
                            start = false;
                            model.setEmpty(I, J);

                        }
                        Toast t = Toast.makeText(MainActivity.this,b.getText(),Toast.LENGTH_SHORT);
                        t.show();
                    }

                });
            }
        }



    }
    private class solveMaze extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            model.move(getRow(),getCol());
            for (int i=0;i<NUM_ROWS;i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    if (model.getPath(i,j)){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.path));
                        buttons[i][j].setText(getResources().getString(R.string.path));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result){
            //executed on main UI thread

        }
    }
    public int getRow(){
        for (int i=0;i<NUM_ROWS;i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (buttons[i][j].getText()==getResources().getText(R.string.start)){
                    return i;
                }
            }
        }
        return 0;
    }
    public int getCol(){
        for (int i=0;i<NUM_ROWS;i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (buttons[i][j].getText()==getResources().getText(R.string.start)){
                    return j;
                }
            }
        }
        return 0;
    }




}


