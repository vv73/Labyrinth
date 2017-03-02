package ru.samsung.itschool.labyrinth;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;


public class MainActivity extends Activity  {

	Labyrinth lab = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SurfaceView sv = (SurfaceView)this.findViewById(R.id.mysurfaceView);
		lab = new Labyrinth();
		new Thread(){
			public void run(){
				while(true) {
					Canvas canvas = sv.getHolder().lockCanvas();
					if (canvas != null) {
						lab.draw(canvas, 40);
						sv.getHolder().unlockCanvasAndPost(canvas);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}



	public void genLab(View v)
	{
		lab.showGeneration(0, 20, 20);
	};
	
	public void findWay(View v)
	{
		LabExplorer labExp = new LabExplorer(lab, lab.room[0][0]);
		SurfaceView sv = (SurfaceView)this.findViewById(R.id.mysurfaceView);
		labExp.findPath(lab.room[5][5], 100);
		
	}
}
