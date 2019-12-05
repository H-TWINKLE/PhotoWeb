package com.ccc.utils;

import java.util.ArrayList;
import java.util.List;


import com.ccc.common.model.Post;
import com.ccc.plugins.PartsSpider;

public class PostTest {

	public static void main(String[] args) {

		Utils.INSTANCE.pluginStart();

		PartsSpider spider = new PartsSpider(Utils.Photographic_Master);

		List<Post> list = new ArrayList<>();

		for (int x = 2; x < 10; x++) {

			System.out.println("----------获取第 " + x + "  页数据中----------");

			list.addAll(spider.getListPartsAndSaveToPost(x));

		}

		System.out.println("----------获取页面数据完成，正在获取具体信息----------\n");

		for (Post p : list) {

			try {
				Thread.sleep(800);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (p != null) {
				Utils.INSTANCE.addRunnableToPool(() -> {
					System.out.println("----------正在获取" + p.getPostTitle() + " 数据 ----------");

					spider.getListPostAndSaveConent(p);
				});
			}

		}

	}

}
