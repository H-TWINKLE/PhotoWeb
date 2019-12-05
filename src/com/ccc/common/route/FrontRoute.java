package com.ccc.common.route;

import com.ccc.common.controller.IndexController;
import com.ccc.common.controller.UserController;
import com.ccc.common.interceptor.IndexInterceptor;
import com.jfinal.config.Routes;

public class FrontRoute extends Routes {

	@Override
	public void config() {

		setBaseViewPath("/fronts");
		
		addInterceptor(new IndexInterceptor());
		add("/", IndexController.class);
		add("/user", UserController.class,"/");

	}

}
