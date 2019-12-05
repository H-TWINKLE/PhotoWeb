package com.ccc.plugins;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.ccc.common.model.Post;
import com.ccc.entrty.Data;
import com.ccc.entrty.Parts;
import com.ccc.utils.Utils;
import com.jfinal.kit.StrKit;

public class PartsSpider {

	private String url;

	public PartsSpider(String url) {
		this.url = url;
	}

	public PartsSpider() {
		this.url = Utils.Photographic_Accessories;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Parts getPartsByPages(int pages) {

		if (pages == 0 || pages == 1) {
			return null;
		}

		Response res;

		try {
			res = Jsoup.connect(url + pages).execute();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return JSONObject.parseObject(res.body(), Parts.class);
	}

	private String getContentByUrl(String url) {

		if (StrKit.isBlank(url))
			return null;

		Document doc;

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Element ele = doc.selectFirst("div.txt-wrap");

		if (ele == null)
			return null;

		return ele.html();

	}

	public List<com.ccc.common.model.Parts> getListPartsAndSave(int pages) {

		Parts p = getPartsByPages(pages);

		if (p == null)
			return null;

		com.ccc.common.model.Parts parts;

		List<com.ccc.common.model.Parts> list = new ArrayList<>();

		for (Data d : p.getData()) {

			if (d != null) {

				parts = Utils.INSTANCE.entrtyTransToModel(d);

				// parts.save();

				if (parts != null) {
					list.add(parts);

				}

			}

		}

		return list;

	}

	public List<Post> getListPartsAndSaveToPost(int pages) {

		Parts p = getPartsByPages(pages);

		if (p == null)
			return null;

		Post post;

		List<Post> list = new ArrayList<>();

		for (Data d : p.getData()) {

			if (d != null) {

				post = Utils.INSTANCE.partsTransToPost(d);

				// post.save();

				if (post != null) {
					list.add(post);
				}

			}

		}

		return list;

	}

	public void getListPartsAndSaveConent(com.ccc.common.model.Parts p) {

		if (p == null)
			return;

		String content = getContentByUrl(p.getPartsUrl());

		if (StrKit.isBlank(content))
			return;

		p.setPartsContent(content);

		p.save();

	}

	public void getListPostAndSaveConent(Post p) {

		if (p == null)
			return;

		String content = getContentByUrl(p.getPostUrl());

		if (StrKit.isBlank(content))
			return;

		p.setPostContent(content);

		p.save();

	}

}
