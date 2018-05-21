package com.example.aa;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
Button b1,b2,b3,b4;
EditText e1,e2,e3;
TextView t1,t2,t3,t4;
LinearLayout lay;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lay=(LinearLayout)findViewById(R.id.LinearLayout2);
        b1=new Button(this);
        b1.setText("+");
        b1.setBackgroundColor(Color.BLUE);
        b2=new Button(this);
        b2.setBackgroundColor(Color.CYAN);
        b2.setText("-");
        b3=new Button(this);
        b3.setBackgroundColor(Color.GRAY);
        b3.setText("*");
        b4=new Button(this);
        b4.setText("/");
        b4.setBackgroundColor(Color.RED);
        e1=new EditText(this); 
        e1.setBackgroundColor(Color.BLUE);
        e2=new EditText(this); 
        e2.setBackgroundColor(Color.CYAN);
        e3=new EditText(this); 
        e3.setBackgroundColor(Color.GREEN);
        t1=new TextView(this);
        t1.setText("您输入的数字");
        t1.setBackgroundColor(Color.BLUE);
        t2=new TextView(this);
        t2.setBackgroundColor(Color.CYAN);
        t2.setText("您输入的数字");
        t3=new TextView(this);
        t3.setText("结果为：");
        t3.setBackgroundColor(Color.GREEN);
        lay.addView(b1);
        lay.addView(b2);
        lay.addView(b3);
        lay.addView(b4);
        lay.addView(t1);
        lay.addView(e1);
        lay.addView(t2);
        lay.addView(e2);
        lay.addView(t3);
        lay.addView(e3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
	double a=Double.parseDouble(e1.getText().toString());
	double b=Double.parseDouble(e2.getText().toString());
	double c;
		if(v==b1){
			c=a+b;
			e3.setText(String.valueOf(c));
		}
		if(v==b2){
			c=a-b;
			e3.setText(String.valueOf(c));
		}
		if(v==b3){
			c=a*b;
			e3.setText(String.valueOf(c));
		}
		if(v==b4){
			c=a/b;
			e3.setText(String.valueOf(c));
		}
	}
    
}
