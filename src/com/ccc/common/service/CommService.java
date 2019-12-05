package com.ccc.common.service;

import java.util.List;

import com.ccc.common.base.BaseService;
import com.ccc.common.model.Comment;
import com.ccc.common.model.Email;
import com.ccc.common.model.Info;
import com.ccc.common.model.Parts;
import com.ccc.common.model.Pic;
import com.ccc.common.model.Post;
import com.ccc.common.model.Type;
import com.ccc.common.model.User;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class CommService implements BaseService {

	@Override
	public <T extends Model<T>> T getTByTId(T t, Integer id) {
		return t.findById(id);
	}

	@Override
	public <T extends Model<T>> boolean delete(T t) {
		return t.delete();
	}

	@Override
	public <T extends Model<T>> boolean save(T t) {
		return t.save();
	}

	@Override
	public <T extends Model<T>> boolean update(T t) {
		return t.update();
	}

	public Post getPostById(Integer id) {
		return Post.dao.findById(id);
	}

	public Info getInfoById(Integer id) {
		return Info.dao.findById(id);
	}

	public User getUserById(Integer id) {
		return User.dao.findById(id);
	}

	public boolean deleteInfo(Integer id) {
		return Info.dao.deleteById(id);
	}

	public List<Pic> getPicByPicGraphUrl(String url) {

		return Pic.dao.find("select * from pic where pic_html_graph_url=? order by pic_dates desc", url);

	}

	public Parts getPartsById(Integer id) {
		return Parts.dao.findFirst("select * from parts left join user on user_id=parts_author where parts_id=?", id);
	}

	public Post getPostByIdAndUser(Integer id) {
		return Post.dao.findFirst("select * from post left join user on user_id=post_author where post_id=?", id);
	}

	public Info getInfoByIdAndUser(Integer id) {
		return Info.dao.findFirst("select * from info left join user on user_id=info_author where info_id=?", id);
	}

	public User toLogin(String account, String pass) {
		return User.dao.findFirst("select * from user where user_email=? and user_pass=?", account, pass);
	}

	public User getUserByEmail(String email) {
		return User.dao.findFirst("select * from user where user_email=?", email);
	}

	public User getUserByCode(String code) {
		return User.dao.findFirst("select * from user where user_code=?", code);
	}

	public Email getEmailByCode(String code) {
		return Email.dao.findFirst("select * from email where email_code=?", code);
	}

	public List<Type> getTypesList() {
		return Type.dao.find("select * from type group by type_name order by type_dates desc");
	}

	public Page<Post> getPostByPlateIsWorkWithType(Integer pages, Integer type) { // work is 2
		return Post.dao.paginate(pages, 0, "select *",
				"from post left join user on user.user_id=post_author left join type on type.type_id=post_type where post_plate=2 and types=? order by post_dates desc",
				type);

	}

	public Page<Post> getPostByPlateIsWorkAndGood(Integer pages) {

		return Post.dao.paginate(pages, 20, "select *",
				"from post left join user on user.user_id=post_author left join type on type.type_id=post_type where post_hot=100 or post_hot=90 order by post_dates desc");

	}

	public Page<Post> getPostByPlateIsPostWithType(Integer pages, Integer type) { // post is 1
		return Post.dao.paginate(pages, 0, "select *",
				"from post left join user on user.user_id=post_author left join type on type.type_id=post_type where post_plate=1 and types=? order by post_dates desc",
				type);

	}

	public Page<Post> getPostByPlateIsPost(Integer pages) {

		return Post.dao.paginate(pages, 10, "select *",
				"from post left join user on user.user_id=post_author where post_hot!=100 and post_hot!=90 or post_hot is null order by post_dates desc");

	}

	public Page<User> getUser(String what, Integer pages) {

		if (StrKit.isBlank(what)) {
			what = "%%";
		} else {
			what = "%" + what + "%";
		}

		return User.dao.paginate(pages, 10, "select *",
				"from user where CONCAT(IFNULL(user_email,''),IFNULL(user_nickname,'')) LIKE ?", what);

	}

	public Page<Info> getInfoAndUser(Integer pages, Integer limit) {

		return Info.dao.paginate(pages, limit, "select *",
				"from info left join user on user.user_id=info.info_author order by info_dates desc");
	}

	public Page<Parts> getPartsList(Integer pages, Integer limit) {

		return Parts.dao.paginate(pages, limit, "select *", "from parts order by parts_dates desc");

	}

	public Page<Pic> getPicList(Integer pages, Integer limit) {

		return Pic.dao.paginate(pages, limit, "select *", "from pic GROUP BY pic_auto order by pic_dates desc");

	}

	public Page<Info> getInfoList(Integer pages, Integer limit) {

		return Info.dao.paginate(pages, limit, "select *", "from info order by info_dates desc");

	}

	public Page<Post> getWorkListByPost(Integer pages, Integer limit) {
		return Post.dao.paginate(pages, limit, "select *",
				"from post where post_hot=100 or post_hot=90 order by post_dates desc");
	}

	public Page<Post> getPostListByPost(Integer pages, Integer limit) {
		return Post.dao.paginate(pages, limit, "select *",
				"from post left join user on user_id=post_author where post_plate=1 order by post_dates desc");
	}

	public Page<Post> getPostListByPostInUser(Integer pages, Integer limit, User u) {
		return Post.dao.paginate(pages, limit, "select *",
				"from post left join user on user_id=post_author where post_author=? order by post_dates desc",
				u.getUserId());
	}

	public List<Comment> getCommentByPostId(Integer id) {
		return Comment.dao.find(
				"select * from comment left join user on comment_author=user_id where comment_post=? order by comment_dates desc",
				id);
	}

	public List<Parts> getPartsListInIndex(Integer hot) {
		return Parts.dao.find("select * from parts where parts_hot=? order by parts_dates desc limit 0,3", hot);
	}

	public List<Parts> getPostListInIndex(Integer hot, Integer limit) {
		return Parts.dao.find(
				"select * from post left join user on user_id=post_author where post_hot=? order by post_dates desc limit 0,"
						+ limit,
				hot);
	}

	public List<Pic> getPicListInIndex(Integer hot, Integer limit) {
		return Pic.dao.find(
				"SELECT * FROM `pic` where pic_hot=? GROUP BY pic_auto ORDER BY pic_dates desc LIMIT 0," + limit, hot);
	}

	public User saveUserAndReturnUpdate(User u) {

		if (u.update()) {
			return User.dao.findById(u.getUserId());
		}

		return null;
	}

	public Page<Comment> getCommentByUser(Integer pages, User u) {
		return Comment.dao.paginate(pages, 10, "select *",
				"from comment left join user on comment_author=user_id left join post on post_id=comment_post where comment_author=? order by comment_dates desc",
				u.getUserId());
	}

	public Page<Comment> getCommentByPostAuthor(Integer pages, User u) {
		return Comment.dao.paginate(pages, 10, "select *",
				"from comment join user on comment_author=user_id JOIN post on comment_post=post_id AND post_author=? order by comment_dates desc",
				u.getUserId());
	}

	public Page<Parts> getParts(Integer pages) {
		return Parts.dao.paginate(pages, 20, "select *",
				"from parts left join user on parts_author=user_id ORDER BY parts_hot desc, parts_dates desc ");
	}

	public Page<Pic> getPic(Integer pages) {
		return Pic.dao.paginate(pages, 20, "select *",
				"from pic left join user on pic_author=user_id group by pic_html_graph_url ORDER BY pic_hot desc, pic_dates desc");
	}

}
