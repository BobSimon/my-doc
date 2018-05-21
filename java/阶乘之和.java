package com.example.fg;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
TextView t1,t2,t3;
EditText e1,e2;
Button b1;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.checkedTextView1);
        t2=(TextView)findViewById(R.id.checkedTextView2);
        t3=(TextView)findViewById(R.id.textView1);
       
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
      
        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public static int function(int i){
		if(i==0||i==1){
			return 1;
		}
		else{
			 return i* function(i-1);
		}
	}

   

	@Override
	public void onClick(View v) {
		int a=Integer.parseInt(e1.getText().toString());
		int sum=0;
for(int i=1;i<=a;i++){
	sum+=function(i);
}
e2.setText(String.valueOf(sum));

	}
    
}
