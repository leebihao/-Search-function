package com.itheiam.search.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.itheiam.search.MainActivity;
import com.itheiam.search.bean.AboutResult;
import com.itheiam.search.utils.HttpUtil;

public class GetAboutKeyTask extends AsyncTask<String, Void, Object> {

	private Context context;

	public GetAboutKeyTask(Context context) {
		super();
		this.context = context;
	}

	//onPreExcute==>doInBackground=AboutResult=>onPostExecute
	@Override
	protected Object doInBackground(String... params) {
	
		try {
			String json = HttpUtil.get(params[0], "utf-8");
			Gson gson = new Gson();
			AboutResult mAboutResult = gson.fromJson(json, AboutResult.class);
			Log.i("wzx", "json " + json);
			return mAboutResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null)
		{
			MainActivity activity=(MainActivity) context;
			activity.setAbountData(result);
		}
		
	}

}
