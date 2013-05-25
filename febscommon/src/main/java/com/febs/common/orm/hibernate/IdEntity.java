package com.febs.common.orm.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 定义ID及其生成策略
 * @author Mark Song
 *
 */
@MappedSuperclass
public class IdEntity implements Serializable {
	
	private static final long serialVersionUID = -7074069549461502438L;
	
	private String id;
	
	public IdEntity() {
		
	}
	
	public IdEntity(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "ID", length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "com.febs.common.orm.hibernate.UUIDGen")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdEntity other = (IdEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
