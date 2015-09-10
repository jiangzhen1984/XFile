package com.eshopping.model.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ES_CATEGORY")
public class ESCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(100)")
	protected String name;
	
	@Column(name = "SEQ_NUMBER", columnDefinition = "NUMERIC(2)")
	protected int seq;
	
	@Column(name = "PARENT_ID", columnDefinition = "NUMERIC(20)")
	protected long parentId;
	
	
	
	protected ESCategory parent;
	protected List<ESCategory> subCategory;
	
	
	

	public ESCategory() {
		subCategory = new ArrayList<ESCategory>();
	}
	
	public void addSubCategory(ESCategory cate) {
		if (cate == null) {
			throw new RuntimeException(" category is null");
		}
		cate.updateParent(this);
		subCategory.add(cate);
	}
	
	public boolean removeSubCategory(ESCategory subCate) {
		return this.subCategory.remove(subCate);
	}
	
	public void updateParent(ESCategory parent) {
		this.parent = parent;
	}

	
	
	public ESCategory getParent() {
		return parent;
	}

	public List<ESCategory> getSubCategory() {
		return subCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		ESCategory other = (ESCategory) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	

}
