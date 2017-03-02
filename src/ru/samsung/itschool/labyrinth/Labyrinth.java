package ru.samsung.itschool.labyrinth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Labyrinth{
	int width = 0;
	int height = 0;
	Cell[][] room = new Cell[0][0];
	ArrayList<LabExplorer> explorers = new ArrayList<>();

	Labyrinth() {
	}

	void showGeneration(final int delay, final int width, final int height) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				generate(delay, width, height);
			}

		}).start();
       
	}

	void draw(Canvas canvas, int size) {
		
		Paint paint = new Paint();
		canvas.drawColor(Color.WHITE);
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[0].length; j++)
				room[i][j].draw(canvas, size);
		}
		
		paint.setStrokeWidth(5);
		paint.setColor(Color.DKGRAY);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		// Draw the Lab's frame
		canvas.drawRect(0, 0, this.width * size, this.height * size, paint);

		//Draw all explorers
		for (LabExplorer explorer: explorers)
			explorer.draw(canvas, size);

	}

	static int random(int a, int b) {
		return (int) (Math.random() * (b - a + 1) + a);
	}

	public void generate(int delay, int width, int height) {

		this.width = width;
		this.height = height;
        explorers.clear();
		room = new Cell[this.height][this.width];
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[0].length; j++)
				room[i][j] = new Cell(j, i);
		}

		LinkedList<Cell> qRooms = new LinkedList<Cell>();
		// start from random room
		int startX = random(0, this.width - 1);
		int startY = random(0, this.height - 1);
		// add new Room in queue;
		room[startY][startX].color = Cell.addedColor;
		qRooms.add(room[startY][startX]);
		int k = 0;
		while (!qRooms.isEmpty()) {
			// Get current cell
			Cell curRoom = qRooms.pop();

			ArrayList<Cell> neibs = new ArrayList<Cell>();
			int curX = curRoom.x;
			int curY = curRoom.y;
			if (curX > 0)
				neibs.add(room[curY][curX - 1]);

			if (curX < (this.width - 1))
				neibs.add(room[curY][curX + 1]);
			if (curY > 0)
				neibs.add(room[curY - 1][curX]);
			if (curY < (this.height - 1))
				neibs.add(room[curY + 1][curX]);
			Collections.shuffle(neibs);

			boolean foundConnected = false;
			for (Cell neib : neibs) {
				if (!foundConnected && neib.color == Cell.connectedColor) {
					if (neib.x < curX)
						neib.rightWall = false;
					if (neib.x > curX)
						room[curY][curX].rightWall = false;
					if (neib.y > curY)
						room[curY][curX].downWall = false;
					if (neib.y < curY)
						neib.downWall = false;
					foundConnected = true;
				} else if (neib.color == Cell.freeColor) {
					neib.color = Cell.addedColor;
					qRooms.push(neib);
				}
			}

			curRoom.color = Cell.connectedColor;
			// Collections.shuffle(qRooms);

				try {
					Thread.currentThread().sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
	}

	boolean opening(int x1, int y1, int x2, int y2) {
		boolean res = true;
		if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 || x1 >= this.width
				|| x2 >= this.width || y1 >= this.height || y2 >= this.height) {
			res = false;
			return res;
		}
		int dist = Math.abs(x1 - x2) + Math.abs(y1 - y2);
		if (dist > 1) {
			
			return false;
		}
		if (x1 < x2 && room[y1][x1].rightWall)
			res = false;
		if (x1 > x2 && room[y1][x2].rightWall)
			res = false;
		if (y1 < y2 && room[y1][x1].downWall)
			res = false;
		if (y1 > y2 && room[y2][x1].downWall)
			res = false;
		return res;
	}

	ArrayList<Cell> getConnectedList(Cell cell) {
		ArrayList<Cell> res = new ArrayList<Cell>();
		if (this.opening(cell.x, cell.y, cell.x - 1, cell.y))
			res.add(room[cell.y][cell.x - 1]);
		if (this.opening(cell.x, cell.y, cell.x + 1, cell.y))
			res.add(room[cell.y][cell.x + 1]);
		if (this.opening(cell.x, cell.y, cell.x, cell.y - 1))
			res.add(room[cell.y - 1][cell.x]);
		if (this.opening(cell.x, cell.y, cell.x, cell.y + 1))
			res.add(room[cell.y + 1][cell.x]);
		return res;
	}

}
