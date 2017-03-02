package ru.samsung.itschool.labyrinth;

import java.util.ArrayList;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

public class LabExplorer {
    Labyrinth lab;
    Cell location;
    Cell goal;

    LabExplorer(Labyrinth lab, Cell start) {
        this.lab = lab;
        lab.explorers.add(this);
        this.location = start;
    }

    LinkedList<Cell> visited;

    void findPath(Cell goal, final int delay) {
        this.goal = goal;
        visited = new LinkedList<Cell>();
        new Thread(new Runnable() {
            public void run() {
                findPath(delay);
            }
        }).start();

    }

    private boolean findPath(int delay) {
        try {
            Thread.currentThread().sleep(delay);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        synchronized (visited) {
            visited.add(location);
        }

       //TO DO...



        return true;
    }

    public void draw(Canvas canvas, int size) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(size * goal.x + 2, size * goal.y + 2, size
                * (goal.x + 1) - 4, size * (goal.y + 1) - 4, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(size * location.x + 2, size * location.y + 2, size
                * (location.x + 1) - 4, size * (location.y + 1) - 4, paint);
        synchronized (visited) {
                for (Cell cell : visited) {

                    canvas.drawRect(size * cell.x + 5, size * cell.y + 5, size
                            * (cell.x + 1) - 8, size * (cell.y + 1) - 8, paint);
                }
            }

    }
}
