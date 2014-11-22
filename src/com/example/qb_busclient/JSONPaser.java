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
	
	public JSONPaser()
	{
		aryRoutines=null;
	}
	
	static List<Map<String,Object>> putJSONToArray(String response) throws JSONException
	{

		List<Map<String,Object>>  list = new ArrayList<Map<String, Object>>(); 			
		JSONObject item = new JSONObject(response);
		Map<String,Object> map = new HashMap<String, Object>(); // ��ŵ�MAP����
		Iterator<?> keys = item.keys();
		
		while(keys.hasNext())
		{	
			String line_id = (String)keys.next();
			map.put("pic", R.drawable.qb_busclient);
			map.put("id", line_id);
			map.put("name", (String)(item.getString(line_id)));
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
			
			Map<String,String> map = new HashMap<String, String>(); // ��ŵ�MAP����
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
//	     * ��ȡ���͸��ӵ�JSON����   
//	     *������ʽ��   
//	        {"name":"С��",   
//	         "age":23,   
//	         "content":{"questionsTotal":2,   
//	                    "questions": [ { "question": "what's your name?", "answer": "С��"},{"question": "what's your age", "answer": "23"}]   
//	                   }   
//	        }   
//	     * @param path  ��ҳ·��   
//	     * @return  ����List   
//	     * @throws Exception
//	     */
//
//		
//		List<Map<String,String>>  list = new ArrayList<Map<String, String>>(); 
//		JSONArray jsonArray = new JSONArray(response); //����ֱ��Ϊһ��������ʽ�����Կ���ֱ�� ��android�ṩ�Ŀ��JSONArray��ȡJSON���ݣ�ת����Array
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject item = jsonArray.getJSONObject(i); //ÿ����¼���ɼ���Object�������
//			//int id = item.getInt("id"); 	// ��ȡ�����Ӧ��ֵ
//			String key=item.keys().next().toString();
//			String name = item.getString("name");
//			int id=0;
//			Map<String,String> map = new HashMap<String, String>(); // ��ŵ�MAP����
//			map.put("id", id + "");
//			map.put("name", name);
//			list.add(map);
//		}
//
//		return list;
//	}
	
}
