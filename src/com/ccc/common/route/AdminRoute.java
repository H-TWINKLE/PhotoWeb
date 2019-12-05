package com.ccc.common.route;

import com.ccc.common.controller.AdminController;
import com.ccc.common.interceptor.UserInterceptor;
import com.jfinal.config.Routes;

public class AdminRoute extends Routes {

	@Override
	public void config() {
		
		setBaseViewPath("/admin");
		addInterceptor(new UserInterceptor());
		add("/admin", AdminController.class,"/");

	}

}
