package com.example.qb_busclient;

//import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
//import java.util.List;
import java.util.Map;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

//import com.example.qb_busclient.MainActivity.MainHandler;







import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
//import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
//import android.util.Log;
import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
import android.content.ContextWrapper;

import java.lang.reflect.Field;
import java.io.File;

public class GpsActivity extends Activity implements Runnable 
{
	public static final int Max_Info_Num=1024;
	
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	private long trans_correct_count=0;
	private long trans_all_count=0;
	private int info_cnt=0;
//	private int i;
	private String[] info = new String[Max_Info_Num];
	
	static class MainHandler extends Handler 
	{
		WeakReference<GpsActivity> mActivity;  
		  
		public MainHandler(GpsActivity activity) {  
                mActivity = new WeakReference<GpsActivity>(activity);  
        }  
		@Override
		public void handleMessage(Message msg)
		{
			GpsActivity GpsWeakActivity = mActivity.get(); 
			switch (msg.what)
			{
				case Share.ST_GPS_UPDATE: 
				{
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss", Locale.getDefault());
	                String str=sdf.format(new Date());
	                GpsWeakActivity.tv7.setText(str);
	                GpsWeakActivity.tv9.setText(GpsWeakActivity.strLo_longitude);
	                GpsWeakActivity.tv11.setText(GpsWeakActivity.strLo_latitude);
	                GpsWeakActivity.tv13.setText(GpsWeakActivity.strLo_bearing);
	                GpsWeakActivity.tv15.setText(GpsWeakActivity.strLo_speed);
	                String net;
	                if(GpsWeakActivity.post_res.length()>10)
	                {
	                	net="认证错误!";
	                	GpsWeakActivity.tv2.setText(Share.route_sel_id + net);
	                	GpsWeakActivity.tv2.setTextColor(Color.RED);
	                }
	                else if(GpsWeakActivity.post_res!="")
	                {
	                	GpsWeakActivity.trans_correct_count +=1;
	                	net="传输正确"+String.valueOf(GpsWeakActivity.trans_correct_count);
	                	GpsWeakActivity.tv2.setText(Share.route_sel_id + net);
	                	GpsWeakActivity.tv2.setTextColor(Color.GREEN);
	                		 
	                }
	                else
	                {
	                	net="网络连接中..";
	                	GpsWeakActivity.tv2.setText(Share.route_sel_id + net);
	                	GpsWeakActivity.tv2.setTextColor(Color.BLUE);
	                }
	                break;
				}
	            default: break;
			}
		}
	}
	
	static class GpsHandler extends Handler
	{
		WeakReference<GpsActivity> mActivity;  
		  
		public GpsHandler(GpsActivity activity) {  
                mActivity = new WeakReference<GpsActivity>(activity);  
        }  
		public void handleMessage(Message msg)
		{
			GpsActivity GpsWeakActivity = mActivity.get();
			if(msg.what == 0x123)
			{
				GpsWeakActivity.updateToNewLocation(GpsWeakActivity.gpsloc);
			}
		}
	}
     
	TextView tv2;
	TextView tv5;
	TextView tv7;
	TextView tv9;
	TextView tv11;
	TextView tv13;
	TextView tv15;
	MySwitch bt;
	boolean bGpsRun;
	GPS gps;
	MainHandler hdl;
	String strLo;
	String strLo_latitude;
	String strLo_longitude;
	String strLo_bearing;
	String strLo_speed;
	LocationListener ll;
	LocationManager locationManager;
	String post_res;
	Thread t;
	Location gpsloc;
	GpsUpdate gpsUpdate;
	boolean gpsChecked=false;
	boolean gpsOnIndex=false;
	
	class GpsUpdate extends Thread
	{
		public GpsHandler mHandler;
		public void run()
		{
			Looper.prepare();
			mHandler = new GpsHandler(GpsActivity.this);
			Looper.loop();
		}
	}
	
//	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		AppManager.getAppManager().addActivity(this);
		
		hdl=new MainHandler(this);
		strLo=new String();
		tv2=(TextView)findViewById(R.id.textView2);
		tv2.setText(Share.route_sel_id);
		tv5=(TextView)findViewById(R.id.textView5);
		tv5.setText("");
		tv7=(TextView)findViewById(R.id.textView7);
		tv7.setText("");
		tv9=(TextView)findViewById(R.id.textView9);
		tv9.setText("");
		tv11=(TextView)findViewById(R.id.textView11);
		tv11.setText("");
		tv13=(TextView)findViewById(R.id.textView13);
		tv13.setText("");
		tv15=(TextView)findViewById(R.id.textView15);
		tv15.setText("");
		
/*		preferences = (SharedPreferences) getLastNonConfigurationInstance();
	    if (preferences == null) 
	    {*/
		
	    	try 
	    	{
	    		Field field;  
	    		// 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象  
	    		field = ContextWrapper.class.getDeclaredField("mBase");  
	    		field.setAccessible(true);  
	    		// 获取mBase变量  
	    		Object obj = field.get(this);  
	    		// 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径  
	    		field = obj.getClass().getDeclaredField("mPreferencesDir");  
	    		field.setAccessible(true);  
	    		// 创建自定义路径  
	    		File file = new File(Environment.getExternalStorageDirectory().getPath());  
//				File file = new File("/mnt/sdcard");
	    		// 修改mPreferencesDir变量的值  
	    		field.set(obj, file); 
			
	    		preferences=getSharedPreferences("QB_busclient", MODE_APPEND);
	    		editor=preferences.edit();
	    		editor.clear();
	    		editor.commit();
	    	}
	    	catch (Exception e) 
	    	{  
	    		e.printStackTrace();  
	    	}
//		catch (NoSuchFieldException e) 
//		{  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	    } 
//		catch (IllegalArgumentException e) 
//		{  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	    } 
//		catch (IllegalAccessException e) 
//		{  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	    }  
//	    }
		
		ll= new LocationListener(){
			@Override
			public void onLocationChanged(final Location loc)
			{
				if (loc != null)
				{	
//					updateToNewLocation(loc);
					gpsloc=loc;
					gpsUpdate.mHandler.sendEmptyMessageDelayed(0x123, 0);
				}    
	        }

	         // 当系统Setting -> Location& Security -> Use wirelessnetworks取消勾选，Use GPS                 satellites取消勾选时调用
			public void onProviderDisabled(final String s)
	        {
	                // Log.v(TAG, "onProviderDisabled. ");
	        }
	                       
	          // 当系统Setting -> Location& Security -> Use wirelessnetworks勾选，Use GPS satellites勾         选时调用
			public void onProviderEnabled(final String s)
	        {
	               //Log.v(TAG, "onProviderEnabled. ");
	        }

			public void onStatusChanged(final String s,final int i, final Bundle b)
			{
	               //sLog.v(TAG, "onStatusChanged. ");
	        }
		};
		
		post_res="";
		bt=(MySwitch)findViewById(R.id.pickup2);
		bt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
//			@SuppressWarnings("deprecation")
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				checkGps();	
				
				if(gpsChecked == true)
				{
					gps=new GPS();
					t=new Thread(GpsActivity.this);
					gpsUpdate = new GpsUpdate();
            	
					if(isChecked==true)
					{
						gps.updateLocation();
						t.start();
						gpsUpdate.start();
						gpsOnIndex=true;
					}
					else
					{
						gps.removeLocation();
						t.interrupt();
						gpsUpdate.interrupt();
						gpsOnIndex=false;
            		
//            			editor=preferences.edit();
//            			for(i=0;i<Max_Info_Num;i++)
//            			{	
//            				editor.putString("info"+String.valueOf(i), info[i]);
//            			}
//                		editor.commit();
					}
				}          
            }
        });
		
		
        
        checkGps();	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	        {
	        	if(gpsOnIndex == true)
	        	{
	        		gps.removeLocation();
	        		t.interrupt();
	        		gpsUpdate.interrupt();
	        		bt.setChecked(false);
	        	}
	        	this.finish();
	        	return true;
	        }
//	        else if(keyCode==KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0)
//	        {
//	        	return true;
//	        }
	        return super.onKeyDown(keyCode, event);
	}
	
/*	@Override
	public Object onRetainNonConfigurationInstance() 
	{
		final SharedPreferences preferences_temp = preferences;
		if(gpsOnIndex == true)
    	{
    		gps.removeLocation();
    		t.interrupt();
    		gpsUpdate.interrupt();
    		bt.setChecked(false);
    	}
	    
	    return preferences_temp;
	}*/
	
/*	public void onBackPressed()
	{
    	if(gpsOnIndex == true)
    	{
    		gps.removeLocation();
    		t.interrupt();
    		gpsUpdate.interrupt();
    		bt.setChecked(false);
    	}
    	this.finish();
	}*/

	private void checkGps()
	{
		try
		{
			locationManager = (LocationManager) GpsActivity.this.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
			{
				strLo="GPS正常!";
				gpsChecked=true;
			}
			else
			{
				strLo="当前系统尚未开启GPS！请开启!!!";
				bt.setChecked(false);
				gpsChecked=false;
       
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		tv5.setText(strLo);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gps, menu);
		return true;
	}
	
	class GPS
	{	
		public GPS()
		{
			try
			{
				locationManager = (LocationManager) GpsActivity.this.getSystemService(Context.LOCATION_SERVICE);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		private void updateLocation()
	    {
	        // 获取位置管理服务
			try
			{
	        // 查找到服务信息
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(true);
				criteria.setSpeedRequired(true);
				criteria.setCostAllowed(true);
				criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

				String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
				Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
				updateToNewLocation(location);
				//locationManager.requestLocationUpdates(provider, 5 * 1000, 0.5f, ll);
				locationManager.requestLocationUpdates(provider, 3500, 50, ll);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
	      
	    }
		
		private void removeLocation()
	    {
	        // 停止跟新坐标
			try
			{
				locationManager.removeUpdates (ll);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	      
	    }

		
	}
	
	private void updateToNewLocation(Location location)
	{
		double latitude=0;
		double longitude=0;
		double b=0;
		double s=0;
        if(location != null) 
        {
        	latitude = location.getLatitude();
        	longitude= location.getLongitude();
            b= location.getBearing();
            s= location.getSpeed();
            strLo_latitude = String.valueOf(latitude);
            strLo_longitude = String.valueOf(longitude);
            strLo_bearing = String.valueOf(b);
            strLo_speed = String.valueOf(s);
        } 
        else
        {
        	strLo_latitude="无法获取位置信息";
        	strLo_longitude = "无法获取位置信息";
            strLo_bearing = "无法获取位置信息";
            strLo_speed = "无法获取位置信息";
        }
        
        Map<String,String>map =new HashMap<String,String>();
        map.put("data[RealTimePosition][user_route_id]", Share.route_sel_id.split("-")[0]);
        map.put("data[RealTimePosition][latitude]", String.valueOf(latitude));
        map.put("data[RealTimePosition][longitude]", String.valueOf( longitude) );
        map.put("data[RealTimePosition][heading]", String.valueOf(b) );
        
        try
        {    
        	post_res= PostData.postFormsToURL("http://42.121.133.161/RealTimePositions/upload", map);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	String str_log;
        	SimpleDateFormat sdf_log=new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss", Locale.getDefault());
            String str_t=sdf_log.format(new Date());
            String net_log;
            trans_all_count +=1;
            if(post_res.length()>10)
            {
            	net_log="error";

            }
            else if(post_res!="")
            {
            	net_log="correct";
            		 
            }
            else
            {
            	net_log="linking";
            }
            if(info_cnt < Max_Info_Num)
            {
            	str_log="$"+str_t+"$"+strLo_longitude+"$"+strLo_latitude+"$"+net_log+"$"+String.valueOf(trans_all_count)+"$"+post_res+"$";
            	info[info_cnt]=str_log;
//            	editor=preferences.edit();	
            	editor.putString("info"+String.valueOf(info_cnt), info[info_cnt]);
            	editor.commit();
            	info_cnt++;
            	
            	if(info_cnt == Max_Info_Num)
            	{
            		info_cnt=0;
            	}
            }

            hdl.sendEmptyMessageDelayed(Share.ST_GPS_UPDATE, 0);
        }
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		while(bt.isChecked()==true && !Thread.interrupted())
		{
			
			try
			{
				
				Thread.sleep(60000);
				Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				updateToNewLocation(location);
				
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
