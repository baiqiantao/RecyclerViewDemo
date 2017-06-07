package rv.bqt.com.recyclerviewdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PicUrls {
	private static final String HOST0 = "http://img1.mm131.com/pic/";//网站【http://www.mm131.com/】
	private static final String HOST1 = "http://img.mmjpg.com/";//网站【http://www.mmjpg.com/】
	private static final String HOST2 = "http://pic.meituba.com/uploads/allimg/";//网站【http://www.meituba.com/】

	public static final UrlBean BIG_BEANS_0 = new UrlBean.Builder()//http://pic.meituba.com/uploads/allimg/2015/10/23/220.jpg
			.host(HOST2).urlHeader("2015/10/23/").picIndexFrom(220).picCount(100).picDes("100张动漫卡通壁纸").build();
	public static final UrlBean BIG_BEANS_1 = new UrlBean.Builder()//http://pic.meituba.com/uploads/allimg/2017/03/27/121_5600.jpg
			.host(HOST2).urlHeader("2017/03/27/121_").picIndexFrom(5600).picCount(100).picDes("100张搞笑内涵图片").build();
	public static final UrlBean BIG_BEANS_2 = new UrlBean.Builder()//http://pic.meituba.com/uploads/allimg/2015/10/23/360.jpg
			.host(HOST2).urlHeader("2015/10/23/").picIndexFrom(360).picCount(750).picDes("750张性感美女图").build();
	public static final UrlBean BIG_BEANS_3 = new UrlBean.Builder()//http://pic.meituba.com/uploads/allimg/2016/03/25/43_20335.jpg
			.host(HOST2).urlHeader("2016/03/25/43_").picIndexFrom(20335).picCount(1400).picDes("1400张动漫卡通壁纸").build();

	private static final UrlBean[] simpleBeans = {
			//http://img1.mm131.com/pic/996/1.jpg
			new UrlBean.Builder().host(HOST0).urlHeader("996/").picCount(10).picDes("北影校花余雨高清写真图片").build(),
			new UrlBean.Builder().host(HOST0).urlHeader("2958/").picCount(10).picDes("童颜嫩妹桃子黑丝大尺度诱惑").build(),
			new UrlBean.Builder().host(HOST0).urlHeader("2939/").picCount(10).picDes("清纯少女刘奕宁酥胸覆白色内衣").build(),
			new UrlBean.Builder().host(HOST0).urlHeader("2343/").picCount(10).picDes("萌妹销魂写真身材惹火让人欲罢不能").build(),
			new UrlBean.Builder().host(HOST0).urlHeader("2935/").picCount(10).picDes("性感女神杨晨晨透视睡衣大胆秀乳").build(),

			//http://img.mmjpg.com/2015/444/1.jpg
			new UrlBean.Builder().host(HOST1).urlHeader("2015/444/").picCount(10).picDes("模范学院美少女柳侑绮制服大片").build(),
			new UrlBean.Builder().host(HOST1).urlHeader("2015/74/").picCount(10).picDes("极品女神可儿私拍秀完美身材").build(),
			new UrlBean.Builder().host(HOST1).urlHeader("2017/990/").picCount(10).picDes("香艳妹子雪白的美胸绝对让你大饱眼福").build(),
			new UrlBean.Builder().host(HOST1).urlHeader("2017/962/").picCount(10).picDes("真诱人啊!女神雪白的美胸看着很有感觉").build(),
			new UrlBean.Builder().host(HOST1).urlHeader("2017/936/").picCount(10).picDes("身材娇美纯天然美女小叶子美胸艺术照").build(),

			//http://pic.meituba.com/uploads/allimg/2015/10/23/247.jpg
			new UrlBean.Builder().host(HOST2).urlHeader("2015/10/23/").picIndexFrom(247).picCount(10).picDes("呆萌可爱的哆啦A梦动漫").build(),
			new UrlBean.Builder().host(HOST2).urlHeader("2016/03/25/43_").picIndexFrom(20574).picCount(10).picDes("海贼王红发香克斯").build(),
			new UrlBean.Builder().host(HOST2).urlHeader("2016/03/25/44_").picIndexFrom(11275).picCount(10).picDes("英雄联盟游戏图片").build(),
			new UrlBean.Builder().host(HOST2).urlHeader("2016/07/30/43_").picIndexFrom(485).picCount(10).picDes("精选超萌小猫咪").build(),
			new UrlBean.Builder().host(HOST2).urlHeader("2016/09/08/43_").picIndexFrom(476).picCount(10).picDes("可爱快乐的女孩动漫").build(),
	};

	//******************************************************************************************

	/**
	 * 10*15张各种各样的带有描述的图片
	 */
	public static ArrayList<BasicPicBean> getPicList() {
		List<UrlBean> urlList = Arrays.asList(simpleBeans);

		ArrayList<BasicPicBean> picList = new ArrayList<>();
		for (UrlBean bean : urlList) {
			picList.addAll(getPicList(bean));
		}
		return picList;
	}

	/**
	 * 每一个UrlBean都有超多图片，多的有几千张
	 */
	public static ArrayList<BasicPicBean> getPicList(UrlBean bean) {
		ArrayList<BasicPicBean> picList = new ArrayList<>();
		for (int i = 0; i < bean.picCount; i++) {
			String picIndex = "" + (bean.picIndexFrom + i);
			for (int j = picIndex.length(); j < bean.minLength; j++) {
				if (picIndex.length() < bean.minLength) {
					picIndex = "0" + picIndex;
				} else break;
			}
			String picUrl = bean.host + bean.urlHeader + picIndex + bean.urlEnder;//例如.../1.jpg
			String picDes = bean.picDes + "-" + i;
			picList.add(new BasicPicBean(picUrl, picDes, i));
		}
		return picList;
	}

	//******************************************************************************************
	static class UrlBean {
		public String picDes;//图片描述
		public String host;//存放图片的主机地址
		public String urlHeader;
		public String urlEnder;//默认为= ".jpg"
		public int picCount;//此系列图片的数量。所有图片都是以数字命名的，比如1.jpg、2.jpg
		public int picIndexFrom;//此系列图片开始的序号位置，默认为1
		public int minLength;//如，当为1.jpg时实际为01.jpg或001.jpg

		private UrlBean(Builder builder) {
			picDes = builder.picDes;
			host = builder.host;
			urlHeader = builder.urlHeader;
			urlEnder = builder.urlEnder;
			picCount = builder.picCount;
			picIndexFrom = builder.picIndexFrom;
			minLength = builder.minLength;
		}

		static final class Builder {
			private String picDes = "包青天作品";
			private String host = "";
			private String urlHeader = "";
			private String urlEnder = ".jpg";//默认为".jpg"
			private int picCount = 1;
			private int picIndexFrom = 1;
			private int minLength = 0;

			public Builder() {
			}

			public Builder urlHeader(String val) {
				urlHeader = val;
				return this;
			}

			public Builder urlEnder(String val) {
				urlEnder = val;
				return this;
			}

			public Builder picCount(int val) {
				picCount = val;
				return this;
			}

			public Builder picIndexFrom(int val) {
				picIndexFrom = val;
				return this;
			}

			public Builder minLength(int val) {
				minLength = val;
				return this;
			}

			public Builder host(String val) {
				host = val;
				return this;
			}

			public Builder picDes(String val) {
				picDes = val;
				return this;
			}

			public UrlBean build() {
				return new UrlBean(this);
			}
		}
	}

	//******************************************************************************************

	public static class BasicPicBean {
		public String url;
		public String name;
		public int index;

		public BasicPicBean(String url, String name, int index) {
			this.url = url;
			this.name = name;
			this.index = index;
		}
	}
}
