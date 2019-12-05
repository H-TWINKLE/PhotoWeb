package com.ccc.plugins;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ccc.common.model.Pic;
import com.ccc.utils.Utils;
import com.jfinal.kit.StrKit;

public class ImgSpider {

	public List<Pic> getPicTypes() {

		Document doc;

		try {
			doc = Jsoup.connect(Utils.ZODESK).get();
		} catch (Exception e) {
			return null;
		}

		Elements ele = doc.select("dl.filter-item:nth-child(1) > dd:nth-child(2) > a");

		if (elesIsNull(ele))
			return null;

		List<Pic> list = new ArrayList<>();

		Pic p;

		for (Element e : ele) {

			p = new Pic();

			if (StrKit.notBlank(e.attr("href"))) {

				p.setPicHtmlType(e.attr("href"));
				p.setPicTypeName(e.text());
				list.add(p);
			}

		}

		return list;

	}

	public List<Pic> getPicList(Pic pic) {

		if (pic == null)
			return null;

		Document doc;

		try {
			doc = Jsoup.connect(Utils.ZODESKIP + pic.getPicHtmlType()).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Elements ele = doc.select("li.photo-list-padding");

		if (elesIsNull(ele))
			return null;

		List<Pic> list = new ArrayList<>();

		Pic p;

		for (Element e : ele) {

			Element ea = e.selectFirst("a.pic");

			p = new Pic();

			p.setPicHtmlType(pic.getPicHtmlType());
			p.setPicTypeName(pic.getPicTypeName());

			if (ea != null && StrKit.notBlank(ea.attr("href"))) {

				p.setPicHtmlGraphUrl(ea.attr("href"));

			}

			ea = e.selectFirst("img");

			if (ea != null && StrKit.notBlank(ea.attr("title"))) {

				p.setPicAuto(ea.attr("title"));

			}

			list.add(p);

		}

		return list;

	}

	public List<Pic> getListPicFileUrl(Pic pic) {

		if (pic == null)
			return null;

		Document doc;

		try {
			doc = Jsoup.connect(Utils.ZODESKIP + pic.getPicHtmlGraphUrl()).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Element e = doc.getElementById("showImg");

		if (e == null)
			return null;

		Elements ele = e.select("img");

		if (elesIsNull(ele))
			return null;

		List<Pic> list = new ArrayList<>();

		Pic p;

		for (Element el : ele) {

			if (StrKit.notBlank(el.attr("srcs"))) {
				p = new Pic();

				p.setPicHtmlType(pic.getPicHtmlType());
				p.setPicAuto(pic.getPicAuto());
				p.setPicHtmlGraphUrl(pic.getPicHtmlGraphUrl());
				p.setPicTypeName(pic.getPicTypeName());
				p.setPicFileUrl(el.attr("srcs").replace("144x90", "1920x1080"));
				p.setPicDates(new Date());
				p.setPicIp(Utils.ZODESKIP);
				p.setPicHot(50);
				p.save();

				list.add(p);

			}

		}

		return list;

	}

	private boolean elesIsNull(Elements ele) {

		if (ele == null || ele.size() == 0)
			return true;

		return false;

	}

}
