package com.example.jeremy8;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Title extends Activity {
	private EditText edttitle;
	private SharedPreferences preference;
	private TextView mTextView;//倒數計時
	private int i=1;
	//任軒-json檔名-從LISTVIEW拿
	String file;
	String FILENAME = file+".json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		
		
		
		//取得介面元件
		edttitle=(EditText)findViewById(R.id.editText5);
		
		//建立儲存檔
		preference=getSharedPreferences("ans",MODE_PRIVATE);
		
		//倒數計時
		time();
		
		//創JSON檔
		try{
		  String FILENAME = file+".json";
		  //gives file name
		  FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		  JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		  writer.setIndent("  ");
		  writer.beginObject();
		  writer.beginArray();
		  writer.name("round").value(i);
		  writer.endArray();
		  writer.endObject();
		  writer.flush();
		  writer.close();
		      
	      }catch(Exception e) {
		  Log.e("log_tag", "Error saving string "+e.toString());
		  }
  


	}
	
	//儲存資料(使用者名稱)
	protected void onStop(){
		super.onStop();		
			preference.edit()
			.putString("name",edttitle.getText().toString())
			.commit();	
	}
	
	public void time(){	
		// 倒數計時     
		mTextView = (TextView)findViewById(R.id.timeView1);
		new CountDownTimer(5000,1000){
		            
			@Override
			public void onFinish() {
			// TODO Auto-generated method stub
				mTextView.setText("Time is up");
				//儲存資料
				
				//跳轉業面
				Intent intent=new Intent();
				intent.setClass(Title.this,Palette.class);
				Bundle bundle=new Bundle();
				bundle.putInt("round",i+1);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			@Override
			public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			mTextView.setText("seconds remaining:"+millisUntilFinished/1000);
			}
			            
			}.start();
		}

			
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title, menu);
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
