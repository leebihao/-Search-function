package com.itheiam.search.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	public String  errno;
	public String  end_state;
	public String  from;
	public SearchData  data=new SearchData();
	
	public static class SearchItem {
		public String title;
		public String hotword;
	}

	public class SearchData {
	public	List<SearchItem> game = new ArrayList<SearchItem>();
	public	List<SearchItem> soft = new ArrayList<SearchItem>();
	public	List<SearchItem> video = new ArrayList<SearchItem>();
	public	List<SearchItem> theme = new ArrayList<SearchItem>();
	public	List<SearchItem> wallpaper = new ArrayList<SearchItem>();
	public	List<SearchItem> ebook = new ArrayList<SearchItem>();
	}
}
