package com.mygdx.game;


import java.util.ArrayList;

public class HighScores implements java.io.Serializable{
    //array list untuk score
    ArrayList<Float> highScoresList;
    //last score
    Float lastScore;

    //constructor
    public HighScores(ArrayList<Float> highScoresList, Float lastScore) {
        this.highScoresList = highScoresList;
        this.lastScore = lastScore;
    }

}
