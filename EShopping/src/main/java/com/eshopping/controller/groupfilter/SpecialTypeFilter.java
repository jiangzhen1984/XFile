package com.eshopping.controller.groupfilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.commons.collections.Criteria;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.CategoryItemSpecialType;

public class SpecialTypeFilter implements Criteria<AbsShoppingItem> {
	
	private List<Integer> typeLIst;
	
	public SpecialTypeFilter() {
		typeLIst = new ArrayList<Integer>();
	}

	@Override
	public boolean evaluate(AbsShoppingItem t) {
		List<CategoryItemSpecialType> tTypeList = t.getSpecialTypeList() ;
		for (Iterator<Integer> it = typeLIst.iterator(); it.hasNext();) {
			Integer integer = it.next();
			for (Iterator<CategoryItemSpecialType> itm = tTypeList.iterator(); itm.hasNext();) {
				if (integer == itm.next().getId()) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	public void addFilterTypeId(Integer typeId) {
		typeLIst.add(typeId);
	}
	
	public void removeFilterTypeId(Integer typeId) {
		typeLIst.remove(typeId);
	}
	
	public void addFilterTypeId(long[] ids) {
		for (long l : ids) {
			typeLIst.add(Integer.valueOf((int)l));
		}
	}

	
}
