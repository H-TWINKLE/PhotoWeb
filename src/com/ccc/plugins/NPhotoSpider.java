package com.ccc.plugins;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ccc.common.model.Info;
import com.ccc.utils.Utils;
import com.jfinal.kit.StrKit;

public class NPhotoSpider {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public NPhotoSpider() {
		super();

	}

	public NPhotoSpider(Integer pages) {
		this.url = Utils.Photographic_Info + pages + Utils.Photographic_Info_End;
	}

	public List<Info> getInfoByPages(Integer pages) {

		Document doc;

		this.url = Utils.Photographic_Info + pages + Utils.Photographic_Info_End;

		if (StrKit.isBlank(url))
			return null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Elements ele = doc.select("div.article");

		if (ele == null)
			return null;

		List<Info> list = new ArrayList<>();

		Info i;

		for (Element e : ele) {

			i = new Info();

			if (e.selectFirst("img") != null) {
				i.setInfoImg(e.selectFirst("img").attr("src"));
			}

			if (e.selectFirst("span.title14bold") != null) {
				i.setInfoTitle(e.selectFirst("span.title14bold").text());
			}

			if (e.selectFirst("div[style='margin-top:5px;']") != null) {
				i.setInfoAuto(e.selectFirst("div[style='margin-top:5px;']").text());
			}

			if (e.selectFirst("a") != null) {
				i.setInfoUrl(e.selectFirst("a").attr("href"));
			}

			i.setInfoDates(new Date());
			i.setInfoAuthor(Utils.CCC);
			i.setInfoPlate(Utils.PLATEININFO);

			list.add(i);

		}

		return list;

	}

	public Info getInfoContent(Info i) {

		if (i == null)
			return null;

		Document doc;

		try {
			doc = Jsoup.connect(i.getInfoUrl()).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Element ele = doc.selectFirst("div.content");

		if (ele == null)
			return null;

		i.setInfoContent(ele.html());

		return i;

	}

}
