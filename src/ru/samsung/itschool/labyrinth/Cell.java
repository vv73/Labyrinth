package ru.samsung.itschool.labyrinth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

	static int connectedColor = Color.TRANSPARENT;
	static int addedColor = Color.RED;
	static int freeColor = Color.BLUE;

	boolean rightWall;
	boolean downWall;
	int x, y;
	int color;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.rightWall = this.downWall = true;
		this.color = Cell.freeColor;
	}

	void draw(Canvas canvas, int size) {
		Paint paint = new Paint();
		paint.setColor(this.color);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(size * this.x, size * this.y, size * (this.x + 1) - 2,
				size * (this.y + 1) - 2, paint);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);

		if (downWall) {
			canvas.drawLine(size * this.x, size * (this.y + 1), size
					* (this.x + 1), size * (this.y + 1), paint);
		}
		if (rightWall) {
			canvas.drawLine(size * (this.x + 1), size * this.y, size
					* (this.x + 1), size * (this.y + 1), paint);
		}
	}
}
