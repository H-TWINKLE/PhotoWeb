package com.ccc.utils;

import java.util.List;

import com.ccc.common.model.Pic;
import com.ccc.plugins.ImgSpider;

public class ImgTest {

	public static void main(String[] args) {

		Utils.INSTANCE.pluginStart();

		ImgSpider i = new ImgSpider();

		List<Pic> list = i.getPicTypes();

		if (list != null) {

			for (Pic p : list) {
				
				System.out.println("----------正在获取类型："+p.getPicHtmlType()+"------------");

				List<Pic> li = 	i.getPicList(p);
				
				if(li!=null) {
					
					for(Pic pic :li) {
							
						try {
							Thread.sleep(800);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						Utils.INSTANCE.addRunnableToPool(()->{
							
							System.out.println("-------正在获取图集："+pic.getPicAuto()+" -------------");
							i.getListPicFileUrl(pic);
							
						});
												
						
					}
					
				}
				
				

			}

		}

	}

}
