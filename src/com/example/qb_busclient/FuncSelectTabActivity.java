package com.example.qb_busclient;

//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import android.os.Bundle;
//import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
//import android.widget.TextView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class FuncSelectTabActivity extends TabActivity 
{

	private static final String TAG = "yank";
	SimpleAdapter adapter ;
	private ListView lstV;
	TextView tvbarCode;
	MySwitch bn1;
	TextView tv1;
	MySwitch bn2;
	TextView tv2;
	MySwitch bn3;
	TextView tv3;
	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data)
	{
			try 
			{
				super.onActivityResult(requestCode, resultCode, data);
				String uid=data.getStringExtra("user");
				
				Share.route_sel_id=uid;
				Log.d(TAG, "ID: ="+Share.route_sel_id);
				Thread.sleep(300);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				Intent intent = new Intent(FuncSelectTabActivity.this, GpsActivity.class); 
				startActivity(intent); 
			} 
			
	     	
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabactivity_funcselect);
		AppManager.getAppManager().addActivity(this);
		
		TabHost tabHost=getTabHost();
		
		TabHost.TabSpec tab1=tabHost
				.newTabSpec("tab1")
				.setIndicator("线路选择")
				.setContent(R.id.route_select);
		tabHost.addTab(tab1);
		
		TabHost.TabSpec tab2=tabHost
				.newTabSpec("tab2")
				.setIndicator("紧急情况")
				.setContent(R.id.emergency_select);
		tabHost.addTab(tab2);
		
		tabHost.setCurrentTab(0);
		
		tvbarCode=(TextView)findViewById(R.id.textView1);
		tvbarCode.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		Intent intent = new Intent("com.qb.action.BARCODE");
        		startActivityForResult(intent,1); 
        	}
        });
		
		tv1=(TextView)findViewById(R.id.textView2);
		tv1.setText("");
		tv2=(TextView)findViewById(R.id.textView3);
		tv2.setText("");
		tv3=(TextView)findViewById(R.id.textView4);
		tv3.setText("");
		bn1=(MySwitch)findViewById(R.id.emergency_btn);
		bn2=(MySwitch)findViewById(R.id.accident_btn);
		bn3=(MySwitch)findViewById(R.id.police_btn);
		bn1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
//			@SuppressWarnings("deprecation")
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
            	
				if(isChecked==true)
				{
					tv1.setText("急救信息发送成功!");
				}
				else
				{
					tv1.setText("");
				}
			}          
        });
		
		bn2.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
//			@SuppressWarnings("deprecation")
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
            	
				if(isChecked==true)
				{
					tv2.setText("事故信息发送成功!");
				}
				else
				{
					tv2.setText("");
				}
			}          
        });
		
		bn3.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
//			@SuppressWarnings("deprecation")
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
            	
				if(isChecked==true)
				{
					tv3.setText("报警信息发送成功!");
				}
				else
				{
					tv3.setText("");
				}
			}          
        });


		
		lstV=(ListView) findViewById(R.id.listView1);
		//lstV.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
		lstV.setAdapter(null);
		adapter = new SimpleAdapter(
				this, 
				Share.lstRoutes,
				R.layout.yanklst,
				new String[] { "pic", "id", "name" },
				new int[] { R.id.pic, R.id.title, R.id.message});
		lstV.setAdapter(adapter);
		lstV.setOnItemClickListener(new OnItemClickListenerImpl());
		lstV.setVisibility(-1);
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener 
	{


//		@SuppressWarnings("unchecked")
	
	

		private static final String TAG = "qb_rid";

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
		{
			// TODO Auto-generated method stub
			HashMap<?,?> item = (HashMap<?,?>)arg0.getItemAtPosition(arg2);
			String id_sel=(String)item.get("id");
			String id_sel2=id_sel+"-" +(String)item.get("name");
			  
			//TextView tv=(TextView)RouteActivity.this.findViewById(R.id.textView2);
			//id_sel.getChars(0, end, buffer, index)
			//tv.setText(id_sel2);
			Share.route_sel_id=id_sel2;
			Log.d(TAG, "ID: ="+Share.route_sel_id);
			try 
			{
				Thread.sleep(300);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				Intent intent = new Intent(FuncSelectTabActivity.this, GpsActivity.class); 
				startActivity(intent); 
			} 
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	        {
	        	this.finish();
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
		getMenuInflater().inflate(R.menu.route, menu);
		return true;
	}

}

