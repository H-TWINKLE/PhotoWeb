package com.ccc.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseInfo<M extends BaseInfo<M>> extends Model<M> implements IBean {

	public M setInfoId(java.lang.Integer infoId) {
		set("info_id", infoId);
		return (M)this;
	}
	
	public java.lang.Integer getInfoId() {
		return getInt("info_id");
	}

	public M setInfoType(java.lang.Integer infoType) {
		set("info_type", infoType);
		return (M)this;
	}
	
	public java.lang.Integer getInfoType() {
		return getInt("info_type");
	}

	public M setInfoPlate(java.lang.Integer infoPlate) {
		set("info_plate", infoPlate);
		return (M)this;
	}
	
	public java.lang.Integer getInfoPlate() {
		return getInt("info_plate");
	}

	public M setInfoTitle(java.lang.String infoTitle) {
		set("info_title", infoTitle);
		return (M)this;
	}
	
	public java.lang.String getInfoTitle() {
		return getStr("info_title");
	}

	public M setInfoHot(java.lang.Integer infoHot) {
		set("info_hot", infoHot);
		return (M)this;
	}
	
	public java.lang.Integer getInfoHot() {
		return getInt("info_hot");
	}

	public M setInfoAuthor(java.lang.Integer infoAuthor) {
		set("info_author", infoAuthor);
		return (M)this;
	}
	
	public java.lang.Integer getInfoAuthor() {
		return getInt("info_author");
	}

	public M setInfoImg(java.lang.String infoImg) {
		set("info_img", infoImg);
		return (M)this;
	}
	
	public java.lang.String getInfoImg() {
		return getStr("info_img");
	}

	public M setInfoAuto(java.lang.String infoAuto) {
		set("info_auto", infoAuto);
		return (M)this;
	}
	
	public java.lang.String getInfoAuto() {
		return getStr("info_auto");
	}

	public M setInfoUrl(java.lang.String infoUrl) {
		set("info_url", infoUrl);
		return (M)this;
	}
	
	public java.lang.String getInfoUrl() {
		return getStr("info_url");
	}

	public M setInfoContent(java.lang.String infoContent) {
		set("info_content", infoContent);
		return (M)this;
	}
	
	public java.lang.String getInfoContent() {
		return getStr("info_content");
	}

	public M setInfoDates(java.util.Date infoDates) {
		set("info_dates", infoDates);
		return (M)this;
	}
	
	public java.util.Date getInfoDates() {
		return get("info_dates");
	}

}
