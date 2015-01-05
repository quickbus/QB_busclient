package com.example.qb_busclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONPaser 
{
	static public ArrayList<String> aryRoutines;
	public static final int Max_Route_Num=100;
	
	public JSONPaser()
	{
		aryRoutines=null;
	}
	
	static List<Map<String,Object>> putJSONToArray(String response) throws JSONException
	{

		List<Map<String,Object>>  list = new ArrayList<Map<String, Object>>(); 			
		JSONObject item = new JSONObject(response);
		Iterator<?> it = item.keys();
		String[] line_id = new String[Max_Route_Num];
		int route_num=0;
		int i,j;
		String temp;
		
		while(it.hasNext())
		{	
			if(route_num < Max_Route_Num)
			{
				line_id[route_num] = (String)it.next();
				route_num++;
			}
			
		}
		
		for(j=0;j<route_num-1;j++)
		{
			for(i=j+1;i<route_num;i++)
			{
				if(Integer.parseInt(line_id[j])>Integer.parseInt(line_id[i]))//数组元素大小按升序排列           
				{                 
					temp=line_id[j];                 
					line_id[j]=line_id[i];                 
					line_id[i]=temp;
				}         
			} 
		}
		
		for(j=0;j<route_num;j++)
		{
			Map<String,Object> map = new HashMap<String, Object>(); // 存放到MAP里面
			map.put("pic", R.drawable.qb_busclient);
			map.put("id", line_id[j]);
			map.put("name", (String)(item.get(line_id[j])));
			list.add(map);
		}
			
		return list;
	
	}
/*	static List<Map<String,String>> putJSONToArray(String response) throws JSONException
	{

		List<Map<String,String>>  list = new ArrayList<Map<String, String>>(); 
		String s1=response.substring(1);
		String s2=s1.substring(0,s1.length()-1);
		String[] sa=s2.split(",");
		int cnt=sa.length;
		for(int i=0;i<cnt;i++)
		{
			/*int pos=sa[i].indexOf("{");
			String pairs=sa[i].substring(pos);
			String pairs1=pairs.replaceAll(":",",");
			*/
			
			
			/* sa[i]="16":"\ uxxxx" */
/*			String[] pp=sa[i].split(":");
			String relid=pp[0].substring(1,pp[0].length()-1);
			String jsnname=pp[1].substring(0,pp[1].length());
			String jsn="{\"name\":"+jsnname+"}";
;			
			JSONObject item =new JSONObject(jsn);
			
			Map<String,String> map = new HashMap<String, String>(); // 存放到MAP里面
			map.put("id", relid);
			map.put("name", item.getString("name"));
			list.add(map);
			
		}
		return list;
	
	}
*/
//	static List<Map<String,String>> putJSONToArrayReg(String response) throws JSONException
//	{
//		/**   
//	     * 获取类型复杂的JSON数据   
//	     *数据形式：   
//	        {"name":"小猪",   
//	         "age":23,   
//	         "content":{"questionsTotal":2,   
//	                    "questions": [ { "question": "what's your name?", "answer": "小猪"},{"question": "what's your age", "answer": "23"}]   
//	                   }   
//	        }   
//	     * @param path  网页路径   
//	     * @return  返回List   
//	     * @throws Exception
//	     */
//
//		
//		List<Map<String,String>>  list = new ArrayList<Map<String, String>>(); 
//		JSONArray jsonArray = new JSONArray(response); //数据直接为一个数组形式，所以可以直接 用android提供的框架JSONArray读取JSON数据，转换成Array
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject item = jsonArray.getJSONObject(i); //每条记录又由几个Object对象组成
//			//int id = item.getInt("id"); 	// 获取对象对应的值
//			String key=item.keys().next().toString();
//			String name = item.getString("name");
//			int id=0;
//			Map<String,String> map = new HashMap<String, String>(); // 存放到MAP里面
//			map.put("id", id + "");
//			map.put("name", name);
//			list.add(map);
//		}
//
//		return list;
//	}
	
}
