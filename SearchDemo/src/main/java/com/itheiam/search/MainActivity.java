package com.itheiam.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheiam.search.activity.SearchResultActivity;
import com.itheiam.search.bean.AboutResult;
import com.itheiam.search.bean.AboutResult.AbountItem;
import com.itheiam.search.bean.SearchResult;
import com.itheiam.search.bean.SearchResult.SearchItem;
import com.itheiam.search.task.GetAboutKeyTask;
import com.itheiam.search.task.GetDataTask;
import com.itheiam.search.utils.HMAdpater;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private ExpandableListView listview_ex;
	private ListView listview;
	private EditText input;
	private BaseExpandableListAdapter adapter;
	private AbountWordAdpter aaAdapter;

	private List<SearchItem> hotWords = new ArrayList<SearchItem>();
	private List<SearchItem> historyWorkds = new ArrayList<SearchItem>();
	private List<AbountItem> abountWorkds = new ArrayList<AbountItem>();

	private class AbountWordAdpter extends HMAdpater<AbountItem> {

		public AbountWordAdpter(List<AbountItem> list) {
			super(list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = View.inflate(getBaseContext(), R.layout.view_item_listview, null);
			TextView keywordView = (TextView) view.findViewById(R.id.child_name);

			AbountItem item = abountWorkds.get(position);
			keywordView.setText(item.suggest);
			return view;
		}

	}

	public void setAbountData(Object abountData) {

		abountWorkds.clear();
		AboutResult result = (AboutResult) abountData;
		abountWorkds.addAll(result.data);
		// 显示列表 keyword>0
		// 覆盖之前内容
		if (abountWorkds.size() > 0) {
			listview.setVisibility(View.VISIBLE);
			if (aaAdapter == null) {
				aaAdapter = new AbountWordAdpter(abountWorkds);
				listview.setAdapter(aaAdapter);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						//传给下个页面
						AbountItem item=abountWorkds.get(position);
						Intent intent=new Intent(getBaseContext(),SearchResultActivity.class);
						intent.putExtra("search", item.suggest);
						startActivity(intent);
					}
				});
			} else {
				aaAdapter.notifyDataSetChanged();
			}
		} else {
			listview.setVisibility(View.GONE);
		}
		// 不显示 ==0
	}

	private class MyTextWatcher implements TextWatcher {
		// CharSequence 字符序列 String
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.i("wzx", s.toString());

			//
			String keyword = s.toString().trim();
			if (keyword.length() == 0) {
				// 不发请求 不显示列表
				listview.setVisibility(View.GONE);
			} else {
				listview.setVisibility(View.VISIBLE);

				abountWorkds.clear();
				if (aaAdapter != null) {
					aaAdapter.notifyDataSetChanged();
				}
				String url = "http://182.118.31.142/suggest/zhushou?src=ms_zhushou&fm=home_5&m=c47532bbb1e2883c902071591ae1ec9b&m2=888516c4b08e20e7c9a2139bd8c22e06&v=3.2.23&re=1&ch=430848&os=16&&ppi=320x480&startCount=4&kw=" + keyword + "&count=20&os=16";
				// 发请求
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GetAboutKeyTask task = new GetAboutKeyTask(MainActivity.this);
				task.execute(url);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview_ex = (ExpandableListView) findViewById(R.id.listview_ex);
		listview = (ListView) findViewById(R.id.listview);

		// ===============监听输入
		input = (EditText) findViewById(R.id.input);
		TextWatcher watcher = new MyTextWatcher();
		input.addTextChangedListener(watcher);
		// 1.搜索 数据库(搜索记录) 服务器(热词 url)
		// 2.输入时 服务器(相关词)
		// 3.点击 搜索词进行请求( 数据库 保存点击词 服务器返回 搜索结果)
		GetDataTask task = new GetDataTask(this);
		String url = "http://221.130.199.88/html/hotword/client.json";
		task.execute(url);
	}

	/**
	 * 暴露给其它对象调用
	 * 
	 * @param
	 */
	public void setData(Object netData, List<SearchItem> historyWords) {
		SearchResult result = (SearchResult) netData;
		hotWords.clear();
		hotWords.addAll(result.data.ebook);
		hotWords.addAll(result.data.game);
		hotWords.addAll(result.data.soft);
		hotWords.addAll(result.data.theme);
		hotWords.addAll(result.data.video);
		hotWords.addAll(result.data.wallpaper);

		this.historyWorkds.clear();
		this.historyWorkds.addAll(historyWords);
		adapter = new MyBaseExpandableListAdapter();

		// 添加点击事件 ListView --OnItemClickListener
		// ExpandableListView 1.支持 属性 isChildSelectable true2.添加监听器
		listview_ex.setAdapter(adapter);

		// 去掉箭头
		listview_ex.setGroupIndicator(null);
		OnChildClickListener mOnChildClickListener = new MYOnChildClickListener();
		listview_ex.setOnChildClickListener(mOnChildClickListener);
	}

	private class MYOnChildClickListener implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			SearchItem item = (SearchItem) adapter.getChild(groupPosition, childPosition);
			Toast.makeText(getBaseContext(), item.title,Toast.LENGTH_SHORT).show();
			
			//传给下个页面
			Intent intent=new Intent(getBaseContext(),SearchResultActivity.class);
			intent.putExtra("search", item.hotword);
			startActivity(intent);
			return true;// 处理
		}

	}

	// 2倍的baseAdapter
	private class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
		// 返回组数
		@Override
		public int getGroupCount() {
			if (historyWorkds.size() == 0) {
				return 1;
			} else {
				return 2;
			}
		}

		// 取得 指定下标的组数
		@Override
		public Object getGroup(int groupPosition) {

			if (groupPosition == 0) {
				return "热门搜索";
			} else if (groupPosition == 1) {
				return "搜索历史";
			}
			return null;
		}

		// 返回组视图 显示组名称
		// groupPosition组下标
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

			View view = View.inflate(getBaseContext(), R.layout.view_item_group, null);
			TextView groupname = (TextView) view.findViewById(R.id.group_name);
			// 数据
			String name = (String) getGroup(groupPosition);
			groupname.setText(name);
			return view;
		}

		// 组里面的元素
		@Override
		public int getChildrenCount(int groupPosition) {

			if (groupPosition == 0) {
				return hotWords.size();
			} else if (groupPosition == 1) {
				return historyWorkds.size();
			}
			return 0;
		}

		// 返回 组标 groupPosition
		// childPosition分组元素个数
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			if (groupPosition == 0) {
				return hotWords.get(childPosition);
			} else if (groupPosition == 1) {
				return historyWorkds.get(childPosition);
			}
			return null;
		}

		// 返回 元素视图
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			SearchItem item = (SearchItem) getChild(groupPosition, childPosition);
			View view = View.inflate(getBaseContext(), R.layout.view_item_child, null);
			TextView childName = (TextView) view.findViewById(R.id.child_name);
			childName.setText(item.title);
			return view;
		}

		// 支持点击
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
