package com.ccc.common.base;

import java.util.List;

import com.ccc.common.model.User;
import com.ccc.utils.Utils;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public abstract class BaseController extends Controller {

	protected void setPages(Integer pages) {
		setAttr("pages", pages);
	}

	protected int getDefaultPages(Integer pages) {

		if (pages == null || pages == 0) {
			return 1;
		}

		return pages;
	}

	protected int getDefaultLimit(Integer limit) {

		if (limit == null || limit == 0) {
			return 8;
		}

		return limit;
	}

	protected void setTitle(String title) {

		if (StrKit.notBlank(title)) {

			setAttr("title", title);
		}

	}

	protected void toErrorPages(int error) {
		renderError(error);
		return;
	}

	protected <T> void vailT(T t) {
		if (t == null)
			renderError(404);
		return;
	}

	protected <T extends List<?>> void vailList(T t) {
		if (t == null || t.size() == 0)
			renderError(404);
		return;

	}

	protected void setTipMsg(String value) {
		if (StrKit.notBlank(value)) {

			setAttr("TipMsg", value);
		}
	}

	protected void setTypes(String types) {
		if (StrKit.notBlank(types)) {

			setAttr("types", types);
		}
	}

	protected void vailId(Integer id) {

		if (id == null) {
			renderError(404);
			return;
		}

		if (id == 0)
			renderError(404);
		return;

	}

	protected void vailTypes(String types) {

		if (StrKit.isBlank(types))
			renderError(404);
		return;

	}

	protected void vailIdAndTypes(Integer id, String types) {

		vailId(id);

		vailTypes(types);

	}

	protected void setUser(User u) {

		if (u != null) {
			setSessionAttr("user", u);
		}

	}

	protected User getUser() {

		return getSessionAttr("user");

	}

	protected boolean getCookiesInUserHaveValue(String value) {

		if (StrKit.isBlank(value))
			return false;

		String val = getCookie(value);

		return StrKit.notBlank(val);

	}

	protected String getCookiesValue(String vaString) {

		if (StrKit.isBlank(vaString))
			return "";

		return Utils.INSTANCE.decryptString(vaString);

	}

	protected String addCookies(String a, String p) {
		StringBuffer s = new StringBuffer();
		s.append(a);
		s.append(",");
		s.append(p);
		return s.toString();
	}

}
