package com.itheiam.search.utils;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//以后 如果碰到 一个知识点  使用了适配器设计模式
//掌握适配器方法的含义
public abstract class HMAdpater<T> extends BaseAdapter {

	private List<T> list = new ArrayList<T>();

	public HMAdpater(List<T> list) {
		super();
		this.list = list;
	}

	// 返回行数
	@Override
	public int getCount() {
		return list.size();
	}

	// 返回指定下标的实例
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	// 返回 行视图 显示指定下标的数据
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);//将一个方法由具体实现 变成抽象 只要去掉{}
}
