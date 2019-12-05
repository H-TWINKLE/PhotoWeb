package com.ccc.common.interceptor;

import com.ccc.common.model.User;
import com.ccc.utils.Utils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		Controller c = inv.getController();

		User u = c.getSessionAttr("user");

		if (u == null) {

			c.redirect("/login");

		} else {

			if (u.getUserAuth() == Utils.ASADMIN) {
				setSideView(c);
			}
			
			inv.invoke();

		}

	}

	private void setSideView(Controller c) {

		String action = c.getRequest().getRequestURI();

		String style = "class='active'";

		if (action.contains("index")) {
			c.setAttr("s1", style);
		} else if (action.contains("user")) {
			c.setAttr("s2", style);
		} else if (action.contains("info")) {
			c.setAttr("s3", style);
		} else if (action.contains("post")) {
			c.setAttr("s4", style);
		} else if (action.contains("parts")) {
			c.setAttr("s5", style);
		} else if (action.contains("ipic")) {
			c.setAttr("s6", style);
		}  else {
			c.setAttr("s1", style);
		}

	}

}
