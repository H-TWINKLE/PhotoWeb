package com.ccc.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class IndexInterceptor implements Interceptor {

	final static String active = "class='active'";

	@Override
	public void intercept(Invocation arg0) {

		setLi(arg0);

		arg0.invoke();
	}

	private void setLi(Invocation arg0) {

		if (arg0.getActionKey().contains("parts")) {
			arg0.getController().setAttr("li_2", active);
			return;
		} else if (arg0.getActionKey().contains("photo")) {
			arg0.getController().setAttr("li_3", active);
			return;
		}else if (arg0.getActionKey().contains("post")) {
			arg0.getController().setAttr("li_4", active);
			return;
		}else if (arg0.getActionKey().contains("addcomment")) {
			arg0.getController().setAttr("li_4", active);
			return;
		}else if (arg0.getActionKey().contains("work")) {
			arg0.getController().setAttr("li_5", active);
			return;
		}else if (arg0.getActionKey().contains("info")) {
			arg0.getController().setAttr("li_6", active);
			return;
		}						
		else {
			arg0.getController().setAttr("li_1", active);
			return;
		}

	}

}
