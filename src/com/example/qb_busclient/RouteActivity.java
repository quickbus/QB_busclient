package com.example.qb_busclient;

//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
//import android.widget.TextView;

public class RouteActivity extends Activity 
{

	SimpleAdapter adapter ;
	private ListView lstV;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		
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
				Intent intent = new Intent(RouteActivity.this, GpsActivity.class); 
				startActivity(intent); 
			} 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route, menu);
		return true;
	}

}
