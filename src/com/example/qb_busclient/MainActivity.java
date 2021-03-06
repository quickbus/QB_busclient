package com.example.qb_busclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONException;




//import android.R.color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.KeyEvent;
//import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import android.content.SharedPreferences;

public class MainActivity extends Activity implements OnClickListener 
{
	SharedPreferences preferences_config;
	SharedPreferences.Editor editor_config;
	
	Map<String,String> params;
	EditText txt1;
	EditText txt2;
//	EditText txtUrl;
	Button btn;
	Button btn_q;
	String res;
	ProgressBar pb;
	List<Map<String,Object>> ml;
	ImageButton imgB;
	String username_initial="";
	
	MainHandler hdl;
   // private static final int UPDATE_UI = 0;
   //采用内部类和弱引用
	static class MainHandler extends Handler 
	{
		
		WeakReference<MainActivity> mActivity;  
		  
		public MainHandler(MainActivity activity) {  
                mActivity = new WeakReference<MainActivity>(activity);  
        }  

		@Override
		public void handleMessage(Message msg) 
		{
			MainActivity theActivity = mActivity.get(); 
			theActivity.pb.setVisibility(-1);
			theActivity.btn.setEnabled(true);
			switch (msg.what) 
			{
             	
				case Share.ST_VALID_ROUTES: 
				{
					//StringBuilder sb=new StringBuilder();
					theActivity.ml=Share.lstRoutes;
					
	                Intent intent = new Intent(theActivity, FuncSelectTabActivity.class); 
	                theActivity.startActivity(intent); 
	                break;
                }
                case Share.ST_NO_ROUTES:
                {
                	new AlertDialog.Builder(theActivity)
                	.setTitle("QuickBus")
                	.setMessage("请首先创建路线!")
                	.setPositiveButton("确定" ,  null )  
                	.show();
                	String url="http://42.121.133.161/Users/login";
			    	Uri u = Uri.parse(url); 
			    	Intent it = new Intent(Intent.ACTION_VIEW, u); 
			    	theActivity.startActivity(it);
                	break;
                }
                default:
                {
                	new AlertDialog.Builder(theActivity)
                 	.setTitle("QuickBus")
                 	.setMessage("登陆失败![ERR=]"+msg.what)
                 	.setPositiveButton("确定" ,  null )  
                 	.show();
                }
                     break;
			}
		}
	}

	private void initShare()
	{
		params.put("data[User][username]",txt1.getText().toString());
		params.put("data[User][password]", txt2.getText().toString());
		Share.login_info=params;
		Share.url="http://42.121.133.161/Users/client_login";//txtUrl.getText().toString();
		Share.mAct=this;
		PostData.httpc=new DefaultHttpClient();//clear sessions
	}

     
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		
		preferences_config=getSharedPreferences("QB_busclient_config", MODE_PRIVATE);

		txt1=(EditText) findViewById(R.id.editText1);
		txt2=(EditText) findViewById(R.id.editText2);
	//	txtUrl=(EditText) findViewById(R.id.editText3);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		imgB=(ImageButton)findViewById(R.id.imageButton1);
		params=new HashMap<String,String>();
	
		pb.setVisibility(-1);
		hdl=new MainHandler(this);
		
		btn=(Button) findViewById(R.id.button1);
		btn_q=(Button) findViewById(R.id.button2);
		//btn.setBackgroundColor(Color.rgb(60, 200, 150));
		btn.setOnClickListener(this);
		btn_q.setOnClickListener(this);
		imgB.setOnClickListener(this);
		
		username_initial=preferences_config.getString("username", "");
		
		txt1.setText(username_initial);
		txt2.setText("");
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	        {
	        	AppManager.getAppManager().AppExit(this);
	        	return true;
	        }
//	        else if(keyCode==KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0)
//	        {
//	        	return true;
//	        }
	        return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		   
		switch (arg0.getId())
		{
			case R.id.button1:
		    {
		    	initShare();
		    	editor_config=preferences_config.edit();
		    	editor_config.putString("username", params.get("data[User][username]"));
		    	editor_config.commit();
				Share.getStatus();
				pb.setVisibility(1);
				//pb.animate();
				this.btn.setEnabled(false);
				break;
		    }
			case R.id.button2:
		    {
		    	AppManager.getAppManager().AppExit(this);
				break;
		    }
		     //Toast.makeText(MainActivity.this, "按钮1被单击", Toast.LENGTH_LONG).show();
		    case R.id.imageButton1:
		    {/*
		    	Intent intent = new Intent();
		    	intent.setData(Uri.parse("http://42.121.133.161/Users/"));
		    	intent.setAction(Intent.ACTION_VIEW);
		    	this.startActivity(intent); //启动浏览器
		    	*/
		    	String url="http://42.121.133.161/Users/register";
		    	Uri u = Uri.parse(url); 
		    	Intent it = new Intent(Intent.ACTION_VIEW, u); 
		    	MainActivity.this.startActivity(it);
		    	break;
		    }
		    default:
		    break;
		}
	}

}