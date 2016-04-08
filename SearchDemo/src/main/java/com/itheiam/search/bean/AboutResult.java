package com.itheiam.search.bean;

import java.util.ArrayList;
import java.util.List;

public class AboutResult {

	public String errno;
	public String end_state;
	public int total;
	public List<AbountItem> data = new ArrayList<AbountItem>();

	public static class AbountItem {
		public String suggest;
	}
}
