package com.example.mazegame;

public class Level {

    int num;
    int rows;
    int columns;
    int score;

    public Level(int num, int rows, int columns) {
        this.num = num;
        this.rows = rows;
        this.columns = columns;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int nRows) {
        this.rows = nRows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int nColumns) {
        this.columns = nColumns;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
