package com.ccc.common.controller;

import java.util.Date;
import java.util.List;

import com.ccc.common.base.BaseController;
import com.ccc.common.model.Info;
import com.ccc.common.model.Parts;
import com.ccc.common.model.Pic;
import com.ccc.common.model.Post;
import com.ccc.common.model.User;
import com.ccc.common.service.CommService;
import com.ccc.utils.Utils;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class AdminController extends BaseController {

	@Inject
	CommService csService;

	public void index(Integer pages) { // 作品管理 works 首页

		setTitle("映像探索交流论坛");

		pages = getDefaultPages(pages);

		Page<Post> list;

		list = csService.getPostByPlateIsWorkAndGood(pages);

		setAttr("list", list);
		render("index.html");

	}

	public void addwork(Integer id, String plate) {

		setAttr("plate", plate);

		if (id != null) {
			setAttr("post", csService.getPostById(id));
			setTitle("修改");
		} else {
			setTitle("新增");
		}

		render("addwork.html");

	}

	public void addpost(Integer id) { // 新增帖子

		if (id == null) {
			setTitle("新增帖子");
		} else {

			Post i = csService.getPostById(id);
			setTitle("查看：" + i.getPostTitle());
			setAttr("post", i);

		}

		render("addpost.html");

	}

	public void savepost() {

		Post p = getModel(Post.class);

		User u = getUser();

		p.setPostAuthor(u.getUserId());
		p.setPostDates(new Date());
		p.setPostPlate(Utils.PLATEININFO);
		p.setPostView(Utils.ISVERI);
		p.setPostHot(Utils.INDEX_MASTER);

		boolean flag = false;

		if (p.getPostId() != null) {
			flag = csService.update(p);
		} else {
			flag = csService.save(p);
		}

		if (flag) {
			setTipMsg("操作成功！");
		} else {
			setTipMsg("操作失败！");
		}

		forwardAction("/admin/");

	}

	public void savework() {

		Post p = getModel(Post.class);

		User u = getUser();

		p.setPostAuthor(u.getUserId());
		p.setPostDates(new Date());
		p.setPostPlate(Utils.PLATEINPOST);
		p.setPostView(Utils.ISVERI);

		if (getParaToInt("plate")!=null&& getParaToInt("plate")== 5) {
			
			p.setPostHot(Utils.INDEX_MASTER);
		} else {
			p.setPostHot(Utils.INDEX_DEFAULT);
		}

		String[] pic = getParaValues("pic");

		if (pic != null) {

			StringBuffer s = new StringBuffer();

			for (int x = 0; x < pic.length; x++) {

				String path = Utils.INSTANCE.getFilePath(u);

				if (Utils.INSTANCE.base64ToImage(pic[x], path)) {

					if (p.getPostPic() == null) {
						p.setPostPic(path);
					}

					s.append(path);

				}

				if (x < pic.length - 1) {
					s.append(",");
				}

			}

			p.setPostMpic(s.toString());

		}

		boolean flag = false;

		if (p.getPostId() != null) {
			flag = csService.update(p);
		} else {
			flag = csService.save(p);
		}

		if (flag) {
			setTipMsg("操作成功！");
		} else {
			setTipMsg("操作失败！");
		}

		forwardAction("/admin/");

	}

	public void recommendpost(Integer id) { // 推荐该作品

		vailId(id);

		Post p = csService.getPostById(id);

		boolean hot = p.getPostHot() != null
				&& (p.getPostHot() == Utils.INDEX_MASTER || p.getPostHot() == Utils.INDEX_GOOD);

		if (hot) {
			p.setPostHot(Utils.INDEX_DEFAULT);
		} else {
			p.setPostHot(Utils.INDEX_GOOD);
		}

		p.setPostDates(new Date());
		csService.update(p);

		setTipMsg("操作成功！");

		forwardAction("/admin/");

	}

	public void recommendpostasmaster(Integer id) { // 推荐该作品

		vailId(id);

		Post p = csService.getPostById(id);

		boolean hot = p.getPostHot() != null && p.getPostHot() == Utils.INDEX_MASTER;

		if (hot) {
			p.setPostHot(Utils.INDEX_DEFAULT);
		} else {
			p.setPostHot(Utils.INDEX_MASTER);
		}

		csService.update(p);

		setTipMsg("操作成功！");

		forwardAction("/admin/");

	}

	public void recommendpicasmaster(String id) { // 推荐该作品

		vailT(id);

		List<Pic> p = csService.getPicByPicGraphUrl(id);

		vailList(p);

		boolean hot = p.get(0).getPicHot() != null && p.get(0).getPicHot() == Utils.INDEX_MASTER;

		if (hot) {
			for (Pic pic : p) {
				pic.setPicHot(Utils.INDEX_DEFAULT);
				pic.setPicDates(new Date());
				csService.update(pic);
			}
		} else {
			for (Pic pic : p) {
				pic.setPicHot(Utils.INDEX_MASTER);
				pic.setPicDates(new Date());
				csService.update(pic);
			}
		}

		setTipMsg("操作成功！");

		forwardAction("/admin/ipic");

	}

	public void recommendpartsasmaster(Integer id) { // 推荐该作品

		vailId(id);

		Parts p = csService.getPartsById(id);

		boolean hot = p.getPartsHot() != null && p.getPartsHot() == Utils.INDEX_MASTER;

		if (hot) {
			p.setPartsHot(Utils.INDEX_DEFAULT);
		} else {
			p.setPartsHot(Utils.INDEX_MASTER);
		}

		csService.update(p);

		setTipMsg("操作成功！");

		forwardAction("/admin/parts");

	}

	public void saveipic(String uuid, String types, String auto) {

		String[] pic = getParaValues("pic");

		Pic p = new Pic();

		p.setPicAuto(auto);
		p.setPicTypeName(types);
		p.setPicHtmlGraphUrl(uuid);
		p.setPicDates(new Date());
		p.setPicAuthor(getUser().getUserId());
		p.setPicHot(Utils.INDEX_DEFAULT);

		if (pic != null && pic.length > 0) {

			for (String str : pic) {

				String file = Utils.INSTANCE.getFilePath(uuid);

				if (Utils.INSTANCE.base64ToImage(str, file)) {

					if (p.getPicId() != null) {
						p.setPicId(null);

					}

					p.setPicFileUrl(file);

					csService.save(p);
				}

			}
		}

		setTipMsg("操作成功！");

		forwardAction("/admin/ipic");

	}

	public void addipic() {

		setTitle("新建精图");

		setAttr("uuid", StrKit.getRandomUUID());

		render("addipic.html");

	}

	public void deletepost(Integer id) { // 删除帖子

		vailId(id);

		Post p = csService.getPostById(id);

		vailT(p);

		if (csService.delete(p)) {
			setTipMsg("删除成功！");
		} else {
			setTipMsg("删除失败！");
		}

		if (p.getPostHot() == Utils.INDEX_GOOD || p.getPostHot() == Utils.INDEX_MASTER) {
			forwardAction("/admin");
		} else {
			forwardAction("/admin/post");
		}

	}

	public void deletepic(String id) {

		vailT(id);

		List<Pic> pic = csService.getPicByPicGraphUrl(id);

		vailList(pic);

		for (Pic p : pic) {

			csService.delete(p);

		}

		setTipMsg("操作成功！");

		forwardAction("/admin/ipic");

	}

	public void deleteparts(Integer id) { // 删除帖子

		vailId(id);

		Parts p = csService.getPartsById(id);

		vailT(p);

		if (csService.delete(p)) {
			setTipMsg("删除成功！");
		} else {
			setTipMsg("删除失败！");
		}

		forwardAction("/admin/parts");

	}

	public void postdetail(Integer id) { // 帖子详细

		vailId(id);

		Post p = csService.getPostById(id);

		vailT(p);

		setAttr("post", p);

		setTitle("浏览  " + p.getPostTitle());

		render("postdetail.html");

	}

	public void post(Integer pages) { // 帖子列表

		setTitle("帖子管理");

		pages = getDefaultPages(pages);

		Page<Post> list = csService.getPostByPlateIsPost(pages);

		setAttr("list", list);
		setPages(pages);
		render("post.html");

	}

	public void user(String what, Integer pages) { // 用户管理

		setTitle("用户管理");

		pages = getDefaultPages(pages);

		Page<User> list = csService.getUser(what, pages);

		setAttr("list", list);
		setAttr("what", what);

		render("user.html");

	}

	public void info(Integer pages) { // 资讯管理

		setTitle("资讯管理");

		pages = getDefaultPages(pages);

		Page<Info> list = csService.getInfoAndUser(pages, 20);

		setAttr("list", list);

		setPages(pages);

		render("info.html");

	}

	public void showinfo(Integer id) { // 查看或者新增资讯

		if (id == null) {
			setTitle("新增资讯");
		} else {

			Info i = csService.getInfoById(id);

			setTitle("查看：" + i.getInfoTitle());

			setAttr("info", i);

		}

		render("addinfo.html");

	}

	public void uploadimg() { // 上传图片 返回图片地址

		UploadFile file = getFile("file");

		if (file != null) {

			renderText(getRequest().getContextPath() + "/upload/temp/" + file.getFileName());

		}

	}

	public void ipic(Integer pages) {

		pages = getDefaultPages(pages);

		setPages(pages);

		setTitle("精选图赏");

		setAttr("list", csService.getPic(pages));

		render("ipic.html");

	}

	public void saveinfo() { // 保存资讯

		Info i = getModel(Info.class);

		User u = getUser();

		i.setInfoAuthor(u.getUserId());
		i.setInfoDates(new Date());
		i.setInfoPlate(Utils.PLATEININFO);

		if (StrKit.notBlank(getPara("cronimg"))) {

			String file = Utils.INSTANCE.getFilePath(u);

			i.setInfoImg(file);

			Utils.INSTANCE.base64ToImage(getPara("cronimg"), file);
		}

		boolean flag = false;

		if (i.getInfoId() == null) {
			flag = i.save();
		} else {
			flag = i.update();
		}

		if (flag) {
			setTipMsg("操作成功！");
		} else {
			setTipMsg("操作失败！");
		}

		forwardAction("/admin/info");

	}

	public void infodetail(Integer id) { // 查看资讯

		if (id == null) {
			renderError(404);
			return;
		}

		Info i = csService.getInfoById(id);

		vailT(i);

		setAttr("info", i);

		setTitle(i.getInfoTitle());

		render("infodetail.html");

	}

	public void addparts(Integer id) { // 新增 或者 查看广告

		if (id == null) {
			setTitle("新增广告");
		} else {

			Parts i = csService.getPartsById(id);

			setTitle("查看：" + i.getPartsTitle());

			setAttr("parts", i);

		}

		render("addparts.html");

	}

	public void saveparts() { // 保存广告

		Parts i = getModel(Parts.class);

		User u = getUser();

		i.setPartsAuthor(u.getUserId());
		i.setPartsDates(new Date());
		i.setPartsPlate(Utils.PLATEINPARTS);
		i.setPartsHot(Utils.INDEX_DEFAULT);

		if (StrKit.notBlank(getPara("cronimg"))) {

			String file = Utils.INSTANCE.getFilePath(u);

			i.setPartsPicUrl(file);

			Utils.INSTANCE.base64ToImage(getPara("cronimg"), file);
		}

		boolean flag = false;

		if (i.getPartsId() == null) {
			flag = i.save();
		} else {
			flag = i.update();
		}

		if (flag) {
			setTipMsg("操作成功！");
		} else {
			setTipMsg("操作失败！");
		}

		forwardAction("/admin/parts");

	}

	public void addetail(Integer id) { // 查看一个广告

		vailId(id);

		Info i = csService.getInfoById(id);

		vailT(i);

		setAttr("ad", i);

		setTitle(i.getInfoTitle());

		render("addetail.html");

	}

	public void addelete(Integer id) { // 删除广告

		vailId(id);

		if (csService.delete(new Info().setInfoId(id))) {
			setTipMsg("删除成功！");
		} else {
			setTipMsg("删除失败！");
		}

		forwardAction("/ad");

	}

	public void infodelete(Integer id) { // 删除资讯

		vailId(id);

		if (csService.deleteInfo(id)) {
			setTipMsg("删除成功！");
		} else {
			setTipMsg("删除失败！");
		}

		forwardAction("/admin/info");

	}

	public void parts(Integer pages) { // 广告管理

		setTitle("广告");

		pages = getDefaultPages(pages);

		setAttr("list", csService.getParts(pages));

		setPages(pages);

		render("parts.html");
	}

	public void lookuser(Integer id) { // 查看用户

		vailId(id);

		User u = csService.getUserById(id);

		setTitle("用户：" + u.getUserNickname());

		setAttr("u", u);

		render("lookuser.html");

	}

	public void delete(Integer id) { // 删除用户

		if (id == null) {
			renderError(404);
			return;
		}

		User u = csService.getUserById(id);

		vailT(u);

		if (u.delete()) {
			setTipMsg("删除用户成功！");
		} else {
			setTipMsg("删除用户失败！");
		}

		forwardAction("/admin/user");

	}

	public void modifyuserasadmin(Integer id) { // 成为 取消管理员身份

		vailId(id);

		User u = csService.getUserById(id);

		vailT(u);

		boolean flag = u.getUserAuth() != null && u.getUserAuth() == Utils.ASADMIN;

		if (flag) {
			u.setUserAuth(Utils.ASUSER);
		} else {
			u.setUserAuth(Utils.ASADMIN);
		}

		u.update();

		setTipMsg("修改成功！");

		forwardAction("/admin/user");

	}

	public void modifyuser() { // 修改用户信息

		User u = getModel(User.class);

		if (StrKit.notBlank(getPara("cronimg"))) {

			String file = Utils.INSTANCE.getFilePath(u);

			u.setUserPic(file);

			Utils.INSTANCE.base64ToImage(getPara("cronimg"), file);
		}

		if (StrKit.isBlank(getPara("state"))) {
			u.setUserAuth(Utils.ASUSER);
		}

		u = csService.saveUserAndReturnUpdate(u);

		if (u != null) {
			if (isNowUser(u)) {
				setUser(u);
			}

			setTipMsg("修改成功！");

		} else {
			setTipMsg("修改失败！");
		}

		forwardAction("/admin/lookuser");

	}

	private boolean isNowUser(User u) { // 检查是否为当前用户

		User user = getUser();

		return (user.getUserId() == u.getUserId());

	}

	public void getpic() { // 获取当前用户头像

		User u = getUser();

		if (StrKit.isBlank(u.getUserPic())) {
			redirect("/admin/assets/img/user04.png");
			return;
		} else {
			redirect("/upload/temp/" + u.getUserPic());
			return;

		}

	}

	public void getpicbyid(Integer id) { // 获取用户头像 通过id

		if (id == null)
			return;

		User u = csService.getUserById(id);

		if (StrKit.isBlank(u.getUserPic())) {
			redirect("/admin/assets/img/user04.png");
			return;
		} else {
			redirect("/upload/temp/" + u.getUserPic());
			return;

		}

	}

}
