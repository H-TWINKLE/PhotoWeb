package com.ccc.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ccc.common.model.Parts;
import com.ccc.common.model.Post;
import com.ccc.common.model.User;
import com.ccc.common.model._MappingKit;
import com.ccc.entrty.Data;
import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import sun.misc.BASE64Decoder;

public enum Utils {

	INSTANCE;

	public static final String YOU = "youAreMySunShineOfMyEyes";

	public static final int ASADMIN = 2; // 管理员

	public static final int ASUSER = 1; // 用户

	public static final int ISVERI = 1; // 已经验证

	public static final int NOTVERI = 0; // 没有验证

	public static final int PLATEINWORK = 2; // 作品 微博形式

	public static final int PLATEINGOODPOST = 3; // 好一点的帖子 微博形式

	public static final int PLATEINPOST = 1; // 帖子 微博形式

	public static final int INFOASAD = 2; // 广告 贴吧形式

	public static final int INFOASDEFAULT = 1; // 资讯 贴吧形式

	public static final String ZODESK = "http://desk.zol.com.cn/pc/"; // 中关村桌面壁纸

	public static final String ZODESKIP = "http://desk.zol.com.cn"; // 中关村桌面壁纸

	public static final String Photographic_Accessories = "http://jjb.fengniao.com/index.php?action=getList&class_id=347&page="; // 摄影配件

	public static final String Photographic_Master = "http://image.fengniao.com/index.php?action=getList&class_id=192&sub_classid=0&page="; // 大师作品

	public static final String Photographic_Info = "http://www.nphoto.net/news/list/09/";

	public static final String Photographic_Info_End = ".shtml";

	public static final int PLATEINPARTS = 4; // 摄影配件

	public static final int PLATEININFO = 5; // 资讯 - 帖子

	public static final int CCC = 2; // 印象摄影官方工作室

	public static final int INDEX_MASTER = 100; // 首页展示 最好

	public static final int INDEX_GOOD = 90; // 首页展示 精品

	public static final int INDEX_DEFAULT = 0; // 首页展示 最好

	public static final String FENGNIAOASPIC = "7"; // 图组

	public static final String FENGNIAOASPOST = "1"; // 帖子

	// private final SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd
	// hh:mm:ss", Locale.CHINA);

	private final ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(20));

	public void addRunnableToPool(Runnable r) {
		pool.execute(r);
	}

	public String getFilePath(String name) {

		if (StrKit.isBlank(name)) {
			name = getFileName("");
		}

		return "/upload/temp/" + getFileName(name);
	}

	public String getFilePath(User u) {

		return "/upload/temp/" + getFileName(u);
	}

	public boolean base64ToImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片

		if (StrKit.isBlank(imgStr)) // 图像数据为空
			return false;

		String[] img = imgStr.split(",");

		if (img.length != 2) {
			return false;
		}

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(img[1]);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			OutputStream out = new FileOutputStream(PathKit.getWebRootPath() + imgFilePath);
			out.write(b);
			out.flush();
			out.close();

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public String sendEmilInText(String admin, String url) {

		StringBuffer content = new StringBuffer();

		content.append("<div><div style='margin-left:4%;'>");
		content.append("<p style='color:red;'>");
		content.append("用户  " + admin + "，您好：</p>");
		content.append("<p style='text-indent: 2em;'>您好！您正在进行邮箱验证，在浏览器地址栏里输入并访问以下激活链接即可进行账户验证：</p>");
		content.append("<a href=\"" + url + "\"><p style='text-indent: 2em;display: block;word-break: break-all;'>"
				+ url + "</p></a>");
		content.append("</div>");
		content.append("<ul style='color: rgb(169, 169, 189);font-size: 18px;'>");
		content.append("<li><h5>请您妥善保管，此邮件无需回复。</h5></li>");
		content.append("</ul>");
		content.append("</div>");

		return content.toString();

	}

	public String getFileName(User u) {

		StringBuffer s = new StringBuffer();

		s.append(u.getUserId());
		s.append("_");
		s.append("id");
		s.append("_");
		s.append(System.currentTimeMillis());
		s.append(".png");

		return s.toString();

	}

	public String getFileName(String val) {

		StringBuffer s = new StringBuffer();

		s.append(val);
		s.append("_");
		s.append("id");
		s.append("_");
		s.append(System.currentTimeMillis());
		s.append(".png");

		return s.toString();

	}

	public String appendVefiUrl(String method, String appName, int port, String code) {

		StringBuffer content = new StringBuffer();

		if (port == 80) { // 80为服务器
			content.append("http://htwinkle.cn");
			content.append(appName);
			content.append("/");
			content.append(method);
			content.append("?code=");
			content.append(code);
		} else {
			content.append("http://127.0.0.1:");
			content.append(port);
			content.append("/");
			content.append(method);
			content.append("?code=");
			content.append(code);
		}

		return content.toString();

	}

	public String encryptionString(String text) {

		if (StrKit.isBlank(text)) {
			return null;
		}

		return Base64Kit.encode(System.currentTimeMillis() + text);

	}

	public String decryptString(String text) {

		if (StrKit.isBlank(text)) {
			return null;
		}

		return Base64Kit.decodeToStr(text).substring(String.valueOf(System.currentTimeMillis()).length());

	}

	public void pluginStart() {
		DruidPlugin dp = new DruidPlugin(
				"jdbc:mysql://127.0.0.1:3306/photo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "root",
				"123");
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		_MappingKit.mapping(arp);

		dp.start();
		arp.start();
	}

	public Parts entrtyTransToModel(Data d) {

		if (d == null)
			return null;

		if (!FENGNIAOASPOST.equals(d.getPublished()))
			return null;

		Parts parts = new Parts();

		parts.setPartsTitle(d.getTitle());
		parts.setPartsUrl(d.getUrl());
		parts.setPartsHeadSrc(d.getHead_src());
		parts.setPartsPicUrl(d.getPic_url());
		parts.setPartsAuto(d.getContent());
		parts.setPartsDate(d.getDate());
		parts.setPartsDates(new Date());
		parts.setPartsPlate(PLATEINPARTS);
		parts.setPartsAuthor(CCC);

		return parts;

	}

	public Post partsTransToPost(Data d) {

		if (d == null)
			return null;

		Post p = new Post();

		if (!FENGNIAOASPOST.equals(d.getPublished()))
			return null;

		p.setPostTitle(d.getTitle());
		p.setPostUrl(d.getUrl());
		p.setPostAuto(d.getContent());
		p.setPostAuthor(CCC);
		p.setPostPlate(PLATEININFO);
		p.setPostView(ISVERI);
		p.setPostPic(d.getPic_url());
		p.setPostUrl(d.getUrl());
		p.setPostDates(new Date());

		return p;

	}

}
