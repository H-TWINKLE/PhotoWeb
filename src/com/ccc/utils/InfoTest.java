package com.ccc.utils;

import java.util.ArrayList;
import java.util.List;

import com.ccc.common.model.Info;
import com.ccc.plugins.NPhotoSpider;

public class InfoTest {

	public static void main(String[] args) {

		Utils.INSTANCE.pluginStart();

		NPhotoSpider spider = new NPhotoSpider();

		List<Info> list = new ArrayList<>();

		for (int x = 1; x < 10; x++) {

			System.out.println("----------获取第 " + x + "  页数据中----------");
			
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			list.addAll(spider.getInfoByPages(x));

		}

		System.out.println("----------获取页面数据完成，正在获取具体信息----------/n");

		for (Info p : list) {

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (p != null) {
				Utils.INSTANCE.addRunnableToPool(() -> {
					
					System.out.println("----------正在获取 " + p.getInfoTitle() + "----------");

					Info ps = spider.getInfoContent(p);

					if (ps != null) {
						ps.save();
					}

				});
			}

		}
		
		System.out.println("----------获取页面数据完成,good! ----------/n");

	}

}
