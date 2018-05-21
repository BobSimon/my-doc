
package com.example.aa;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class test extends View {
int time;

	public test(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		time=time+10;
		  Paint paint=new Paint();
		paint.setColor(Color.rgb(100, 300, 200));
		Path path=new Path();
		int xx,yy;
		int x=200;
		int y=300;
		for(int i=0;i<=360;i++){
			double j=i*2*Math.PI/360;
			double a=(i+time)%360*2*Math.PI/360;
			xx=x+(int)(200*Math.cos(3*j)*Math.cos(a));
			yy=y+(int)(200*Math.cos(3*j)*Math.sin(a));
			if(i==0)
				path.moveTo(xx,yy);
			else 
				path.lineTo(xx,yy);
		}
		path.close();
		canvas.drawText("三叶玫瑰线Banana made", 200, 300, paint);
		canvas.drawPath(path, paint);
		this.invalidate();
	}

}
//其中在这程序中必须注意要把这个class文件绑定到一个MainActivity 在这个语句中要写成这样： setContentView(new test(this));
