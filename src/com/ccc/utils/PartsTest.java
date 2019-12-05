package com.ccc.utils;

import java.util.ArrayList;
import java.util.List;

import com.ccc.common.model.Parts;
import com.ccc.plugins.PartsSpider;

public class PartsTest {

	public static void main(String[] args) {

		Utils.INSTANCE.pluginStart();

		PartsSpider spider = new PartsSpider();

		List<Parts> list = new ArrayList<>();

		for (int x = 2; x < 10; x++) {

			System.out.println("----------获取第 " + x + "  页数据中----------");

			list.addAll(spider.getListPartsAndSave(x));

		}

		System.out.println("----------获取页面数据完成，正在获取具体信息----------/n");

		for (Parts p : list) {

			try {
				Thread.sleep(800);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (p != null) {
				Utils.INSTANCE.addRunnableToPool(() -> {
					System.out.println("----------正在获取" + p.getPartsTitle() + " 数据 ----------");

					spider.getListPartsAndSaveConent(p);
				});
			}

		}

	}

}
