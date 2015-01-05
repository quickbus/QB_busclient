package com.example.qb_busclient;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;

//import com.example.qb_busclient.MainActivity.MainHandler;

public class Share {
	public static List<Map<String,Object>> lstRoutes=null;
	public static Map<String,String> login_info=null;
	public static String pswd="";
	public static String url="";
	public static int status=-1;
	
	public static final int ST_NET_ERR=-1;
	public static final int ST_PWD_ERR=0;
	public static final int ST_NO_ROUTES=1;
	public static final int ST_VALID_ROUTES=2;
	
	public static final int ST_GPS_UPDATE=10;
	public static final int ST_POST_ERR=11;

	
	public static String route_sel_id="";
	public static MainActivity mAct;
	public Activity sndAct;
	
	//get current login status;
	public static void getStatus()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
		
				try 
				{
							
					String resp=PostData.postFormsToURL(url,login_info);
					if(resp=="")
					{	//network error, check network or passwd
						status=Share.ST_NET_ERR;
					}
					else if(resp.length()>2 && resp.matches(".*(?i)HTML.*BODY.*"))
					{
						status=Share.ST_PWD_ERR;
					}
					else if (resp.length()<4)
					{
						status=Share.ST_NO_ROUTES;			
					}
					else
					{
						lstRoutes=JSONPaser.putJSONToArray(resp);
						status=Share.ST_VALID_ROUTES;
								
					}
					if(mAct!=null)
					{
						mAct.hdl.sendEmptyMessageDelayed(status, 0);
					}
							
							
				}
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try 
				{
					Thread.sleep(5*1000);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}).start();
	}
	

}
