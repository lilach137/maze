package com.example.mazegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mazegame.Activities.Activity_Maze;
import com.example.mazegame.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class GameMazeView extends View {

    private enum Directions {UP, DOWN, RIGHT, LEFT}

    private Cell[][] cells;
    private Cell player, exit;
    private int COLS;
    private int ROWS;
    private int LEVEL;
    private static final float WALL_THICKNESS = 4;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint, playerPaint, exitPaint;
    private Random random;

    private Context mContext;
    private MySensors mySensors;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private SensorEventListener sensorEventListener;

    //timer
    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }
    private Timer timer = new Timer();
    private int delay=1000;
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;


    public GameMazeView(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.GREEN);

        Intent intent = ((Activity)mContext).getIntent();
        Bundle extras = intent.getExtras();

        ROWS = extras.getInt("rows");
        COLS = extras.getInt("columns");
        LEVEL = extras.getInt("level");

        exitPaint = new Paint();
        exitPaint.setColor(Color.YELLOW);

        random = new Random();
        initSensors();
        createMaze();
        mySensors.getSensorManager().registerListener(accSensorEventListener, mySensors.getAccSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }


    private void createMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell current, next;
        cells = new Cell[COLS][ROWS];
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        player = cells[0][0];
        exit = cells[COLS - 1][ROWS - 1];

        current = cells[0][0];
        current.visited = true;
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }
        } while (!stack.empty());

    }

    private void removeWall(Cell current, Cell next) {
        if (current.col == next.col && current.row == next.row + 1) {
            current.topWall = false;
            next.bottomWall = false;
        }
        if (current.col == next.col && current.row == next.row - 1) {
            current.bottomWall = false;
            next.topWall = false;
        }
        if (current.col == next.col + 1 && current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }
        if (current.col == next.col - 1 && current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    private Cell getNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        //left neighbour
        if (cell.col > 0)
            if (!cells[cell.col - 1][cell.row].visited)
                neighbours.add(cells[cell.col - 1][cell.row]);
        //right neighbour
        if (cell.col < COLS - 1)
            if (!cells[cell.col + 1][cell.row].visited)
                neighbours.add(cells[cell.col + 1][cell.row]);
        //top neighbour
        if (cell.row > 0)
            if (!cells[cell.col][cell.row - 1].visited)
                neighbours.add(cells[cell.col][cell.row - 1]);
        //bottom neighbour
        if (cell.row < ROWS - 1)
            if (!cells[cell.col][cell.row + 1].visited)
                neighbours.add(cells[cell.col][cell.row + 1]);

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(R.drawable.ic_background);
        int width = getWidth();
        int height = getHeight();

        if (width / height < COLS / ROWS)
            cellSize = width / (COLS + 1);
        else
            cellSize = height / (ROWS + 1);

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;

        canvas.translate(hMargin, vMargin);
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (cells[x][y].topWall)
                    canvas.drawLine(x * cellSize, y * cellSize, (x + 1) * cellSize, y * cellSize, wallPaint);
                if (cells[x][y].rightWall)
                    canvas.drawLine((x + 1) * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
                if (cells[x][y].leftWall)
                    canvas.drawLine(x * cellSize, y * cellSize, x * cellSize, (y + 1) * cellSize, wallPaint);
                if (cells[x][y].bottomWall)
                    canvas.drawLine(x * cellSize, (y + 1) * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
            }

        }
        float margin = cellSize / 10;

        canvas.drawRect(
                player.col * cellSize + margin,
                player.row * cellSize + margin,
                (player.col + 1) * cellSize - margin,
                (player.row + 1) * cellSize - margin,
                playerPaint);

        canvas.drawRect(
                exit.col * cellSize + margin,
                exit.row * cellSize + margin,
                (exit.col + 1) * cellSize - margin,
                (exit.row + 1) * cellSize - margin,
                exitPaint);

    }

    private void movePlayer(Directions direction) {
        if (player!=exit) {
            switch (direction) {
                case UP:
                    if (!player.topWall)
                        player = cells[player.col][player.row - 1];
                    break;
                case DOWN:
                    if (!player.bottomWall)
                        player = cells[player.col][player.row + 1];
                    break;
                case LEFT:
                    if (!player.leftWall)
                        player = cells[player.col - 1][player.row];
                    break;
                case RIGHT:
                    if (!player.rightWall)
                        player = cells[player.col + 1][player.row];
                    break;
            }
        }
            checkExit();
            invalidate();

        }

    private void checkExit() {
        if (player == exit){
            Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);
            DataManager.getInstance().getCurrentUser().setLastLevel(LEVEL+1);
            mContext.startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            return true;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = hMargin + (player.col + 0.5f) * cellSize;
            float playerCenterY = vMargin + (player.row + 0.5f) * cellSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    if (dx > 0)
                        movePlayer(Directions.RIGHT);
                    else
                        movePlayer(Directions.LEFT);
                } else {
                    if (dy > 0)
                        movePlayer(Directions.DOWN);
                    else
                        movePlayer(Directions.UP);
                }
            }
            return true;

        }
        return super.onTouchEvent(event);
    }

    private class Cell {
        boolean visited = false;
        boolean topWall = true, leftWall = true, bottomWall = true, rightWall = true;
        int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

    private void initSensors() {
        mySensors = new MySensors();
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mySensors.setSensorManager(sensorManager);
        mySensors.initSensor();

    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];

            if (x < -5) {// move right
                movePlayer(Directions.RIGHT);
            } else if (x > 5) {// move left
                movePlayer(Directions.LEFT);
            } else if (y < -3) {// move up
                movePlayer(Directions.UP);
            } else if (y > 5) {// move down
                movePlayer(Directions.DOWN);
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            Log.d("pttt", "onAccuracyChanged");
        }

    };


}

