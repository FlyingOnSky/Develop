package com.example.jeremy8;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class GameRoom extends Activity {
	private TextView txtroomname,txtpopulation;
	private SharedPreferences preference;
	private String readroomname,readpopulation;
	
	//清單+人物列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_room);
		
		//取得介面元件
		txtroomname=(TextView)findViewById(R.id.textView8);
		txtpopulation=(TextView)findViewById(R.id.textView14);
		
		//尋找儲存檔
		preference=getSharedPreferences("creatroom",MODE_PRIVATE);
		//拿資料
		readroomname=preference.getString("roomname","unknown");
		readpopulation=preference.getString("population", "unknown");
		//顯示
		txtroomname.setText(readroomname);	
		txtpopulation.setText(readpopulation);
		
		//先直接開始~~
		new AlertDialog.Builder(GameRoom.this)
		.setTitle("~~Start~~")
		.setIcon(R.drawable.ic_launcher)
		.setMessage("Sure to Start?")
		.setPositiveButton("Sure",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialoginterface,int i){
				Intent intent=new Intent();
				intent.setClass(GameRoom.this,Title.class);
				startActivity(intent);
			}
		})
		.show();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;   
        }   
        return super.onKeyDown(keyCode, event);   
    }
    public void ConfirmExit(){//退出確認
        AlertDialog.Builder ad=new AlertDialog.Builder(GameRoom.this);
        ad.setTitle("Leave Room");
        ad.setMessage("Sure to Leave GameRoom?");
        ad.setPositiveButton("YES", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
            	Intent intent=new Intent();
				intent.setClass(GameRoom.this,OutSide.class);
				startActivity(intent);
                GameRoom.this.finish();//關閉activity
  
            }
        });
        ad.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//示對話框
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_room, menu);
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
