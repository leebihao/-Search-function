# -Search-function
搜索框功能实现

###测试接口

    /**
     * 热词接口
     */
    public static String HOTWORD_URL = "http://221.130.199.88/html/hotword/client.json";
    /**
     * 相关词接口接口
     */
    public static String SUGGEST_URL = "http://182.118.31.142/suggest/zhushou?src=ms_zhushou&fm=home_5&m=c47532bbb1e2883c902071591ae1ec9b&m2=888516c4b08e20e7c9a2139bd8c22e06&v=3.2.23&re=1&ch=430848&os=16&&ppi=320x480&startCount=4&kw=a&count=20&os=16";
    /**
     * 精确搜索
     */
    public static String AccurateSearch_URL = "http://221.130.199.208/api/search/all?q=acfun%E5%BC%B9%E5%B9%95%E8%A7%86%E9%A2%91&src=ms_zhushou&inp=a&hfrom=&hidetab=&v2=1&ext_filter=1&os=16&webp=1&page=1&fm=home_5_6_ssall_suggest&m=c47532bbb1e2883c902071591ae1ec9b&m2=888516c4b08e20e7c9a2139bd8c22e06&v=3.2.23&re=1&ch=430848&&ppi=320x480&startCount=4&snt=-1";



###实现思路

    1.搜索 数据库(搜索记录) 服务器(热词 url)
    2.输入时 服务器(相关词)
    3.点击 搜索词进行请求( 数据库 保存点击词 服务器返回 搜索结果)

###请求数据

* 使用HttpClient结合AsyncTask来请求网络数据
* 采用Gson解析数据
* 布局使用ExpandableListView控件显示数据

> 需要注意的是：在android高版本中，HttpClient已经被认为是过时的，所以不能直接使用，所以需要在对应Module的gradle中加入如下代码：

      android {
          useLibrary 'org.apache.http.legacy'
      }



详细请参考代码
