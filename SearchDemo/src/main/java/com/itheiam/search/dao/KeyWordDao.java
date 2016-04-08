package com.itheiam.search.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itheiam.search.bean.SearchResult.SearchItem;

public class KeyWordDao {
	private MyHelper mMyHelper;

	public KeyWordDao(Context context) {
		mMyHelper = new MyHelper(context);
	}

	private class MyHelper extends SQLiteOpenHelper {

		public MyHelper(Context context) {
			super(context, "words.db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = "create table keywords (id integer primary key  autoincrement,hotword text, updatetime long);";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	public void addOrUpdate(String keyword) {
		// 获取实例
		SQLiteDatabase db = mMyHelper.getReadableDatabase();
		String sql_query = " select id from keywords where hotword=?;";
		Cursor cursor = db.rawQuery(sql_query, new String[] { keyword });

		if (cursor.getCount() > 0) {
			// 2.再次搜索 更新时间
			String sql_update = "update keywords set updatetime=? where hotword=?;";
			db.execSQL(sql_update, new String[] { System.currentTimeMillis() + "", keyword });
		} else {
			// 1.未被搜索 添加
			String sql_insert = "insert into keywords(hotword,updatetime)values(?,?);";
			db.execSQL(sql_insert, new String[] { keyword, System.currentTimeMillis() + "" });
		}
		cursor.close();
		db.close();

	}

	// 1.设计表
	// 2.sql
	// 3.SqLiteOpenHelper帮助类 创建数据库 获取数据实例
	// SqliteDatabase:实例 调用crud方法或执行sql

	//
	public List<SearchItem> findAll() {
		// green dao
		List<SearchItem> list = new ArrayList<SearchItem>();
		SQLiteDatabase db = mMyHelper.getReadableDatabase();
		String sql_query = "select hotword from keywords order by updatetime desc;";
		// Cursor多条记录的集合 注意 :默认不指第一行数据
		Cursor cusor = db.rawQuery(sql_query, new String[] {});

		while (cusor.moveToNext()) {
			// /当前行
			SearchItem bean = new SearchItem();
			String hotword = cusor.getString(cusor.getColumnIndex("hotword"));
			bean.title = hotword;
			bean.hotword = hotword;
			list.add(bean);

		}
		cusor.close();
		db.close();
		return list;
	}
	//
	//

}
