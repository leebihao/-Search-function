package com.itheiam.search.task;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.itheiam.search.MainActivity;
import com.itheiam.search.bean.SearchResult;
import com.itheiam.search.bean.SearchResult.SearchItem;
import com.itheiam.search.dao.KeyWordDao;
import com.itheiam.search.utils.HttpUtil;

public class GetDataTask extends AsyncTask<String, Void, SearchResult> {

	private Context context;

	private KeyWordDao mKeyWordDao;
	public GetDataTask(Context context) {
		super();
		this.context = context;
		mKeyWordDao=new KeyWordDao(context);
	}
	List<SearchItem> keyHistory=new ArrayList<SearchItem>();
	// onPreExecute--->doInBackground--SearchResult->onPostExcute
	// 取数据
	@Override
	protected SearchResult doInBackground(String... params) {
		System.out.println("doInBackground---");
		try {
			String json = HttpUtil.get(params[0], "utf-8");
			Gson gson = new Gson();
			SearchResult result = gson.fromJson(json, SearchResult.class);
			System.out.println("SearchResult---" + result.end_state);
			keyHistory=mKeyWordDao.findAll();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception---" + e.getMessage());
		}
		return null;
	}

	@Override
	protected void onPostExecute(SearchResult result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		System.out.println("onPostExecute---" + result);
		if (result != null) {
			Log.i("wzx", result.data.ebook.size() + "");
			
			//比传递控件更简洁
			MainActivity activity=(MainActivity) context;
			activity.setData(result,keyHistory);
		}

	}

	// Alt+shift+S

}
