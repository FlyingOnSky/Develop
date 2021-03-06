package com.example.jeremy8;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
	private SharedPreferences preference, MAXpre;
	private EditText edtguess;
	private TextView mTextView;//倒數計時
	private int i, MAX;

	//
	private ImageView iv;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	public int coordinates[];  // <--------------- 接收資料存放處
	
	String file = MAXpre.getString("roomname", "unknown");  // <------ 房間名稱+self
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
		
		
		
		//取得介面元件
		edtguess=(EditText)findViewById(R.id.editText6);
		
		preference=getSharedPreferences("guess",MODE_PRIVATE);
		MAXpre=getSharedPreferences("creatroom",MODE_PRIVATE);
		MAX = MAXpre.getInt("population", 0);
		
		
		this.iv = (ImageView) this.findViewById(R.id.imageView3);
		// 创建一张空白图片
		baseBitmap = Bitmap.createBitmap(480, 640, Bitmap.Config.ARGB_8888);
		// 创建一张画布
		canvas = new Canvas(baseBitmap);
		// 画布背景为白色
		canvas.drawColor(Color.WHITE);
		// 创建画笔
		paint = new Paint();
		// 画笔颜色为黑色
		paint.setColor(Color.BLACK);
		// 宽度5个像素
		paint.setStrokeWidth(5);
		// 先将灰色背景画上
		canvas.drawBitmap(baseBitmap, new Matrix(), paint);
		iv.setImageBitmap(baseBitmap);
		
		for(int j = 0; j < coordinates.length; j+=4)
		{
			canvas.drawLine(coordinates[j], coordinates[j+1], coordinates[j+2], coordinates[j+3], paint); 
		}
		
		try{
			 FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		 
			 final JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
	    writer.setIndent("  ");
		writer.beginObject();
	    
		//--------- round ---------
	    writer.name("round");
	    writer.beginArray();
	    writer.value(i);
	    writer.endArray();
	    
	    //---------- target --------
	    writer.name("target");
	    writer.beginArray();
	    if(self == MAX)  // <------ 要取得self變數及人數-從LISTVIEW取
		 {
			 writer.value(0);
		 }
		 else
		 {
		 writer.value(self+1); // <------ 要取得self變數-從LISTVIEW取
		 }
	    writer.endArray();
	    
	    //----------- title ----------
		writer.name("title");
		writer.beginArray();
		writer.value(edtguess.getText().toString());  // <----- 改成題目變數
		writer.endArray();
		writer.endObject();
		writer.flush();
		writer.close();
		}catch(Exception e) {
			  Log.e("log_tag", "Error saving string "+e.toString());
			  }
		
		//倒數計時
		time();
	}
	
	public void time(){	
		// 倒數計時     
		mTextView = (TextView)findViewById(R.id.timeView3);
		new CountDownTimer(5000,1000){
		            
			@Override
			public void onFinish() {
			// TODO Auto-generated method stub
				mTextView.setText("Time is up");
				//跳轉業面
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
	
	//儲存資料(使用者名稱)
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
