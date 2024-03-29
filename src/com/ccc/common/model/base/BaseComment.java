package com.ccc.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseComment<M extends BaseComment<M>> extends Model<M> implements IBean {

	public M setCommentId(java.lang.Integer commentId) {
		set("comment_id", commentId);
		return (M)this;
	}
	
	public java.lang.Integer getCommentId() {
		return getInt("comment_id");
	}

	public M setCommentPost(java.lang.Integer commentPost) {
		set("comment_post", commentPost);
		return (M)this;
	}
	
	public java.lang.Integer getCommentPost() {
		return getInt("comment_post");
	}

	public M setCommentAuthor(java.lang.Integer commentAuthor) {
		set("comment_author", commentAuthor);
		return (M)this;
	}
	
	public java.lang.Integer getCommentAuthor() {
		return getInt("comment_author");
	}

	public M setCommentView(java.lang.Integer commentView) {
		set("comment_view", commentView);
		return (M)this;
	}
	
	public java.lang.Integer getCommentView() {
		return getInt("comment_view");
	}

	public M setCommentLink(java.lang.Integer commentLink) {
		set("comment_link", commentLink);
		return (M)this;
	}
	
	public java.lang.Integer getCommentLink() {
		return getInt("comment_link");
	}

	public M setCommentDates(java.util.Date commentDates) {
		set("comment_dates", commentDates);
		return (M)this;
	}
	
	public java.util.Date getCommentDates() {
		return get("comment_dates");
	}

	public M setCommentContent(java.lang.String commentContent) {
		set("comment_content", commentContent);
		return (M)this;
	}
	
	public java.lang.String getCommentContent() {
		return getStr("comment_content");
	}

}
