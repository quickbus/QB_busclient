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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
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

	SimpleAdapter adapter ;
	private ListView lstV;
	MySwitch bn1;
	TextView tv1;
	MySwitch bn2;
	TextView tv2;
	MySwitch bn3;
	TextView tv3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabactivity_funcselect);
		AppManager.getAppManager().addActivity(this);
		
		TabHost tabHost=getTabHost();
		
		TabHost.TabSpec tab1=tabHost
				.newTabSpec("tab1")
				.setIndicator("��·ѡ��")
				.setContent(R.id.route_select);
		tabHost.addTab(tab1);
		
		TabHost.TabSpec tab2=tabHost
				.newTabSpec("tab2")
				.setIndicator("�������")
				.setContent(R.id.emergency_select);
		tabHost.addTab(tab2);
		
		tabHost.setCurrentTab(0);
		
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
					tv1.setText("������Ϣ���ͳɹ�!");
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
					tv2.setText("�¹���Ϣ���ͳɹ�!");
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
					tv3.setText("������Ϣ���ͳɹ�!");
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
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener 
	{


//		@SuppressWarnings("unchecked")
	
	

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

