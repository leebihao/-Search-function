package com.itheiam.search.activity;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.itheiam.search.R;
import com.itheiam.search.dao.KeyWordDao;

public class SearchResultActivity extends Activity {

	private KeyWordDao dao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_search_result);

		Intent intent = getIntent();
		// search
		String search = intent.getStringExtra("search");
		dao = new KeyWordDao(this);
		dao.addOrUpdate(search);
		// GetSearchDataTask
		try {
			String url = "http://221.130.199.208/api/search/all?q=" + URLEncoder.encode(search, "utf-8")
					+ "&src=ms_zhushou&inp=a&hfrom=&hidetab=&v2=1&ext_filter=1&os=16&webp=1&page=1&fm=home_5_6_ssall_suggest&m=c47532bbb1e2883c902071591ae1ec9b&m2=888516c4b08e20e7c9a2139bd8c22e06&v=3.2.23&re=1&ch=430848&&ppi=320x480&startCount=4&snt=-1";
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
