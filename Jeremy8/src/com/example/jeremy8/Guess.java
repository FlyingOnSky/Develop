package com.example.jeremy8;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Guess extends Activity {
	private SharedPreferences preference;
	private EditText edtguess;
	private TextView mTextView;//计璸
	private int i;

	//
	private ImageView iv;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	private TextView txtAns;
	private String readAns;
	
	String file;
	String FILENAME = file+".json";
	ArrayList<Integer> gameArray;
	//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		
		Intent intent1=this.getIntent();
		Bundle bundle1=intent1.getExtras();
		i=bundle1.getInt("round");
		
		
		
		//眔ざじン
		edtguess=(EditText)findViewById(R.id.editText6);
		
		preference=getSharedPreferences("guess",MODE_PRIVATE);
		
		try{
			 FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		 
			 final JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
	    writer.setIndent("  ");
		writer.beginObject();
	    writer.beginArray();
	    writer.name("round");
	    writer.value(i);
	    writer.name("target");
	    if(self == MAX)  // <------ 璶眔self跑计の计-眖LISTVIEW
		 {
			 writer.value(0);
		 }
		 else
		 {
		 writer.value(self+1); // <------ 璶眔self跑计-眖LISTVIEW
		 }
		writer.name("title");
		writer.value(edtguess.getText().toString());  // <----- эΘ肈ヘ跑计
		writer.endArray();
		writer.endObject();
		writer.flush();
		writer.close();
		}catch(Exception e) {
			  Log.e("log_tag", "Error saving string "+e.toString());
			  }
		
		//计璸
		time();
	}
	
	public void time(){	
		// 计璸     
		mTextView = (TextView)findViewById(R.id.timeView3);
		new CountDownTimer(5000,1000){
		            
			@Override
			public void onFinish() {
			// TODO Auto-generated method stub
				mTextView.setText("Time is up");
				//铬锣穨
				if(i+1>7){
					Intent intent2=new Intent();
					intent2.setClass(Guess.this,EndGame.class);
					startActivity(intent2);
				}else{
				   Intent intent3=new Intent();
				   intent3.setClass(Guess.this,Palette.class);
				   Bundle bundle2=new Bundle();
				   bundle2.putInt("round",i+1);
				   intent3.putExtras(bundle2);
				   startActivity(intent3);
				}
			}

			@Override
			public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			mTextView.setText("seconds remaining:"+millisUntilFinished/1000);
			}
			            
			}.start();
		}
	
	//纗戈(ㄏノ嘿)
	protected void onStop(){
		super.onStop();
			preference.edit()
			.putString("name",edtguess.getText().toString())
			.commit();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guess, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
