package com.ccc.common.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.ccc.common.base.BaseController;
import com.ccc.common.model.Comment;
import com.ccc.common.model.Email;
import com.ccc.common.model.Info;
import com.ccc.common.model.Parts;
import com.ccc.common.model.Pic;
import com.ccc.common.model.Post;
import com.ccc.common.model.User;
import com.ccc.common.service.CommService;
import com.ccc.common.validator.GobalValidator;
import com.ccc.plugins.MailPlugin;
import com.ccc.utils.Utils;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;

public class IndexController extends BaseController {

	@Inject
	CommService cService;

	@Inject
	MailPlugin mPlugin;

	public void index() {

		setTitle("映像探索");

		String cookies = getCookie(Utils.YOU);

		if (getUser() == null)
			getLoginUserByCookies(cookies);

		setIndex();

		setsessionIndex();

		render("index.html");

	}

	public void parts(Integer pages, Integer limit) {

		setTitle("摄影配件");

		pages = getDefaultPages(pages);

		limit = getDefaultLimit(limit);

		setAttr("list", cService.getPartsList(pages, limit));

		setAttr("pages", pages);

		render("parts.html");

	}

	public void photo(Integer pages, Integer limit) {

		setTitle("所有图组");

		pages = getDefaultPages(pages);

		limit = getDefaultLimit(limit);

		setAttr("list", cService.getPicList(pages, limit));

		setAttr("pages", pages);

		render("photo.html");
	}

	public void onephoto(String id) {

		vailT(id);

		List<Pic> p = cService.getPicByPicGraphUrl(id);

		vailList(p);
		
		setTitle("查看图集：" + p.get(0).getPicAuto());

		setAttr("pic", p);

		render("onephoto.html");

	}

	public void oneparts(Integer id) {

		vailId(id);

		Parts p = cService.getPartsById(id);

		vailT(p);

		setTitle(p.getPartsTitle());

		setAttr("parts", p);

		render("oneparts.html");

	}

	public void onepost(Integer id) {

		vailId(id);

		Post p = cService.getPostByIdAndUser(id);

		vailT(p);

		setTitle(p.getPostTitle());

		setAttr("post", p);
		
		setAttr("comm", cService.getCommentByPostId(id));

		render("onepost.html");

	}

	public void info(Integer pages, Integer limit) {

		setTitle("最新资讯");

		pages = getDefaultPages(pages);

		limit = getDefaultLimit(limit);

		setAttr("list", cService.getInfoList(pages, limit));

		setAttr("pages", pages);

		render("info.html");
	}

	public void oneinfo(Integer id) {

		vailId(id);

		Info p = cService.getInfoByIdAndUser(id);

		vailT(p);

		setTitle(p.getInfoTitle());

		setAttr("info", p);

		render("oneinfo.html");

	}

	public void work(Integer pages, Integer limit) {

		setTitle("作品精华");

		pages = getDefaultPages(pages);

		limit = getDefaultLimit(limit);

		setAttr("list", cService.getWorkListByPost(pages, limit));

		setAttr("pages", pages);

		render("work.html");

	}

	public void onework(Integer id) {

		vailId(id);

		Post p = cService.getPostByIdAndUser(id);

		vailT(p);

		setTitle(p.getPostTitle());

		setAttr("work", p);

		render("onework.html");

	}

	public void post(Integer pages) {

		setTitle("交流论坛");

		pages = getDefaultPages(pages);

		setAttr("pages", pages);

		setAttr("good", cService.getPostListInIndex(Utils.INDEX_GOOD, 5));

		setAttr("list", cService.getPostListByPost(pages, 20));

		render("post.html");

	}

	public void addcomment(Integer id, String message) {

		vailId(id);

		vailT(message);

		User u = getUser();

		if (u == null) {
			setTipMsg("登录后才可以评论喔！");
			forwardAction("/onepost");
			return;
		}

		Comment c = new Comment();

		c.setCommentAuthor(getUser().getUserId());
		c.setCommentPost(id);
		c.setCommentContent(message);
		c.setCommentDates(new Date());

		if (cService.save(c)) {
			setTipMsg("评论成功！");

		} else {
			setTipMsg("评论失败！");
		}

		forwardAction("/onepost");
		return;

	}
	

	public void login() { // 登录页面

		setTitle("登录");

		render("/admin/login.html");

	}

	public void logout() { // 退出登录 清除用户信息

		removeCookie(Utils.YOU);

		removeSessionAttr("user");

		redirect("/");

	}

	@Before(GobalValidator.class)
	public void tologin(String email, String pass) { // 登录控制

		User u = cService.toLogin(email, pass);

		if (u == null) {

			setTipMsg("邮箱或者密码错误，请重新输入！");
			forwardAction("/login");
			return;

		} else {

			setUser(u);

			if (getPara("remember") != null && getPara("remember").equals("on")) {

				setUserInToCookies(u);
			}

			redirect("/");
		}

	}

	public void forgetpass() { // 忘记密码

		setTitle("忘记密码");
		render("/admin/forgetpass.html");

	}

	@Before(GobalValidator.class)
	public void veriforgetpass(String email) { // 验证忘记的密码

		User u = cService.getUserByEmail(email);

		if (u == null) {

			setTipMsg("该邮箱尚未注册！");

		} else {

			String code = StrKit.getRandomUUID();

			if (sendEmail("modifypass", email, code, "修改密码")) {

				u.setUserCode(code);
				u.update();

				setTipMsg("验证链接已经成功发送到你的邮箱，请前往邮箱修改密码！");

			} else {

				setTipMsg("内部错误！");

			}
		}

		forwardAction("/forgetpass");

	}

	public void modifypass(String code) { // 修改密码

		vailT(code);

		setTitle("修改密码");

		User u = cService.getUserByCode(code);

		vailT(u);

		setAttr("email", u.getUserEmail());
		setAttr("code", code);

		render("/admin/modifypass.html");

	}

	@Before(GobalValidator.class)
	public void tomodifypass(String pass1, String pass2, String email) { // 验证 修改密码

		User u = cService.getUserByEmail(email);

		vailT(u);

		u.setUserPass(pass2);
		u.setUserCode(StrKit.getRandomUUID());
		u.update();

		setTipMsg("密码修改成功，请登录！");

		forwardAction("/login");

	}

	public void sign() { // 注册

		setTitle("注册");

		render("/admin/sign-up.html");

	}

	@Before(GobalValidator.class)
	public void verisign(String email, String nickname, String pass2) { // 验证注册

		vailT(email);

		User u = cService.getUserByEmail(email);

		if (u != null) {

			setTipMsg("该用户已经注册，请登录！");

		} else {

			Email e = new Email();

			e.setEmailEmail(email);
			e.setEmailDates(new Date());
			e.setEmailIsveri(Utils.NOTVERI);
			e.setEmailNickname(nickname);
			e.setEmailPass(pass2);
			e.setEmailCode(StrKit.getRandomUUID());

			boolean flag = sendEmail("veriemail", e.getEmailEmail(), e.getEmailCode(), "激活邮箱");

			if (flag) {
				e.save();
				setTipMsg("验证链接已经成功发送到你的邮箱，请前往邮箱激活！");
			} else {
				setTipMsg("内部错误！");
			}

		}

		forwardAction("/sign");

	}

	public void veriemail(String code) { // 验证邮箱

		vailT(code);

		Email e = cService.getEmailByCode(code);

		vailT(e);

		User user = cService.getUserByEmail(e.getEmailEmail());

		if (user != null) {
			setTipMsg("该用户已经注册，请登录！");
		} else {

			setTipMsg("验证成功，请登录！");

			User u = new User();
			u.setUserNickname(e.getEmailNickname());
			u.setUserDates(new Date());
			u.setUserEmail(e.getEmailEmail());
			u.setUserPass(e.getEmailPass());
			u.setUserAuth(Utils.ASUSER);
			u.save();

		}

		forwardAction("/login");

	}

	private boolean sendEmail(String method, String email, String code, String theme) {

		try {

			mPlugin.sendMail(email, Utils.INSTANCE.sendEmilInText(email, Utils.INSTANCE.appendVefiUrl(method,
					getRequest().getContextPath(), getRequest().getServerPort(), code)), theme);
			return true;
		} catch (MessagingException e1) {
			e1.printStackTrace();
			return false;

		}

	}

	private void setUserInToCookies(User u) {

		String a = Utils.INSTANCE.encryptionString(u.getUserEmail());

		String p = Utils.INSTANCE.encryptionString(u.getUserPass());

		setCookie(Utils.YOU, addCookies(a, p), 60 * 60 * 24 * 5); // 5天
	}

	private void getLoginUserByCookies(String val) {

		if (StrKit.isBlank(val))
			return;

		String[] ap = val.split(",");

		if (ap.length == 2) {

			String a = Utils.INSTANCE.decryptString(ap[0]);

			String b = Utils.INSTANCE.decryptString(ap[1]);

			User u = cService.toLogin(a, b);

			if (u != null) {
				setUser(u);
			}

		}

	}

	private void setIndex() {

		setAttr("ad", cService.getPartsListInIndex(Utils.INDEX_MASTER));

		setAttr("banner", cService.getPostListInIndex(Utils.INDEX_MASTER, 3));

		setAttr("good", cService.getPostListInIndex(Utils.INDEX_GOOD, 10));

		setAttr("pic", cService.getPicListInIndex(Utils.INDEX_MASTER, 10));

	}

	private void setsessionIndex() {

		List<Pic> list = getSessionAttr("li_pic");

		if (list == null) {
			list = cService.getPicListInIndex(Utils.INDEX_MASTER, 32);
			setSessionAttr("li_pic", list);
		}

	}

}
