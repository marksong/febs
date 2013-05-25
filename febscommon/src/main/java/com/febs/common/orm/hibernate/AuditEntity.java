package com.febs.common.orm.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * 包含创建、修改、删除的记录
 * @author Mark Song
 *
 */
@MappedSuperclass
public class AuditEntity extends IdEntity {
	
	private static final long serialVersionUID = 8124049795716174082L;
	
	// 发布人id
	protected String creater;
	// 发布时间
	protected Date createTime;
	// 修改人id
	protected String modifier;
	// 修改时间
	protected Date modifyTime;
	protected Boolean deleteFlag = false;	//删除标记，默认未删除
	// 删除人id
	protected String deleter;
	// 删除时间
	protected Date deleteTime;

	@Column(name = "MODIFIER")
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "MODIFY_TIME")
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "CREATER")
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "DELETE_FLAG")
	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "DELETER")
	public String getDeleter() {
		return deleter;
	}

	public void setDeleter(String deleter) {
		this.deleter = deleter;
	}

	@Column(name = "DELETE_TIME")
	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

}
