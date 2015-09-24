package com.eshopping.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.eshopping.model.po.ESCategory;
import com.eshopping.model.po.ESCategoryItem;
import com.eshopping.model.po.ESCatgoryItemSpecialType;
import com.eshopping.model.po.ESComboItem;
import com.eshopping.model.po.ESImage;
import com.eshopping.model.po.ESItem;
import com.eshopping.model.po.EShoppingItemConfigDetail;
import com.eshopping.model.po.EStemSpecialTypeMapping;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.CategoryItemSpecialType;
import com.eshopping.model.vo.ComboShoppingItem;
import com.eshopping.model.vo.Image;
import com.eshopping.model.vo.SingleShoppingItem;

public class EShoppingService extends BaseService {
	
	public void addShoppingItem(AbsShoppingItem item) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		if (item.getType() == AbsShoppingItem.TYPE_SINGLE) {
			ESItem esitem = new ESItem();
			esitem.setDescription(item.getDescription());
			esitem.setName(item.getName());
			esitem.setPrice(item.getPrice());
			esitem.setPicUrl(((SingleShoppingItem)item).getPicUrl());
			session.saveOrUpdate(esitem);
			
			item.setId(esitem.getId());
		} else if (item.getType() == AbsShoppingItem.TYPE_COMBO) {
			ComboShoppingItem   comboShoppingItem = (ComboShoppingItem)item;
			ESComboItem comboItem = new ESComboItem();
			comboItem.setDescription(item.getDescription());
			comboItem.setName(item.getName());
			comboItem.setPrice(item.getPrice());
			
			List<SingleShoppingItem> list = comboShoppingItem.getItemList();
			for (SingleShoppingItem si : list) {
				ESItem esi = new ESItem();
				esi.setId(si.getId());
				comboItem.addItem(esi);
			}
			
			session.saveOrUpdate(comboItem);
			item.setId(comboItem.getId());
		}
		
		List<Image> images = item.getImageList();
		for(Image ig : images) {
			ESImage esi = new ESImage();
			esi.setUri(ig.getUrl());
			esi.setItemType(ig.getType());
			esi.setItemID(item.getId());
			esi.setItemType(item.getType());
			session.save(esi);
		}
		
		t.commit();
		session.close();
	}
	
	
	public void addShoppingItem(AbsShoppingItem item, List<Category> belongs) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		if (item.getType() == AbsShoppingItem.TYPE_SINGLE) {
			ESItem esitem = new ESItem();
			esitem.setDescription(item.getDescription());
			esitem.setName(item.getName());
			esitem.setPrice(item.getPrice());
			esitem.setPicUrl(((SingleShoppingItem)item).getPicUrl());
			session.saveOrUpdate(esitem);
			
			item.setId(esitem.getId());
		} else if (item.getType() == AbsShoppingItem.TYPE_COMBO) {
			ComboShoppingItem   comboShoppingItem = (ComboShoppingItem)item;
			ESComboItem comboItem = new ESComboItem();
			comboItem.setDescription(item.getDescription());
			comboItem.setName(item.getName());
			comboItem.setPrice(item.getPrice());
			
			List<SingleShoppingItem> list = comboShoppingItem.getItemList();
			for (SingleShoppingItem si : list) {
				ESItem esi = new ESItem();
				esi.setId(si.getId());
				comboItem.addItem(esi);
			}
			
			session.saveOrUpdate(comboItem);
			item.setId(comboItem.getId());
		}
		
		
		for(Category ct : belongs) {
			ESCategoryItem ect = new ESCategoryItem();
			ect.setCategoryId(ct.getId());
			ect.setItemId(item.getId());
			ect.setType(item.getType());
			session.save(ect);
		}
		
		List<Image> images = item.getImageList();
		for(Image ig : images) {
			ESImage esi = new ESImage();
			esi.setUri(ig.getUrl());
			esi.setItemType(ig.getType());
			esi.setItemID(item.getId());
			esi.setItemType(item.getType());
			session.save(esi);
		}
		
		t.commit();
		session.flush();
		session.close();
	}
	
	
	
	public void deleteShoppingItem(AbsShoppingItem item) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		if (item.getType() == AbsShoppingItem.TYPE_SINGLE) {
			ESItem esitem = new ESItem();
			esitem.setId(item.getId());
			session.delete(esitem);
		} else if (item.getType() == AbsShoppingItem.TYPE_COMBO) {
			ESComboItem eci = new ESComboItem();
			eci.setId(item.getId());
			session.delete(eci);
		}
		t.commit();
		session.close();
	}
	
	
	public void addCategory(Category cate) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCategory  escate = new ESCategory();
		escate.setName(cate.getName());
		escate.setSeq(cate.getSeq());
		escate.setParentId(cate.getParentId());
		session.saveOrUpdate(escate);
		cate.setId(escate.getId());
		t.commit();
		session.close();
	}
	
	
	public void updateCategory(Category cate) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCategory  escate = (ESCategory)session.load(ESCategory.class, cate.getId());
		escate.setName(cate.getName());
		escate.setSeq(cate.getSeq());
		escate.setParentId(cate.getParentId());
		session.saveOrUpdate(escate);
		cate.setId(escate.getId());
		t.commit();
		session.close();
	}
	
	
	public void deleteCategory(Category cate) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCategory  escate = (ESCategory)session.load(ESCategory.class, cate.getId());
		session.delete(escate);
		t.commit();
		session.close();
	}
	
	
	
	public List<Category> queryCategory(int start, int fetchCount) {
		Session sess = openSession();
		List<Category> queryList =  null;
			Query query = sess.createQuery(" from ESCategory order by parentId ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size(); 
			if (size > 0) {
				queryList = new ArrayList<Category>(size);
				for (int i = 0; i < size; i++) {
					ESCategory ei = (ESCategory) list.get(i);
					queryList.add(new Category(ei));
				}
			}
		
		sess.close();
		return queryList;
	}
	
	
	
	public List<AbsShoppingItem> queryItemList(int type, int start, int fetchCount) {
		
		Session sess = openSession();
		List<AbsShoppingItem> queryList =  null;
		if (type == AbsShoppingItem.TYPE_SINGLE) {
			Query query = sess.createQuery(" from ESItem ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					ESItem ei = (ESItem) list.get(i);
					queryList.add(new SingleShoppingItem(ei));
				}
			}
			
		} else if (type == AbsShoppingItem.TYPE_COMBO) {
			Query query = sess.createQuery(" from ESComboItem ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					ESComboItem ei = (ESComboItem) list.get(i);
					queryList.add(new ComboShoppingItem(ei));
				}
			}
		}
		
		sess.close();
		return queryList;
	}
	
	
	
	
	public List<AbsShoppingItem> queryItemList(long cateId, int start, int fetchCount) {
		
		Session sess = openSession();
		List<AbsShoppingItem> queryList =  null;
		
		String sql = " select eci.es_category_id, eci.es_item_id, eci.item_type, esi.name as iname, esi.price as iprice, esi.pic_path as ipic_path, esci.name as ciname, esci.price as ciprice, esci.pic_path as cipic_path "+
					 " from ES_CATEGORY_ITEM eci " +
					 " left join ES_ITEM esi on eci.es_item_id = esi.id "+
					 " left join ES_Combo_Item esci on esci.id = eci.es_item_id" +
					 " where eci.es_category_id =:cid";
			Query query = sess.createSQLQuery(sql);
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			query.setLong("cid", cateId);
			
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					Object[] key = (Object[]) list.get(i);
					int type = ((BigDecimal)key[2]).intValue();
					if (type == AbsShoppingItem.TYPE_SINGLE) {
						SingleShoppingItem ss = new SingleShoppingItem();
						ss.setId(((BigInteger)key[1]).longValue());
						ss.setName((String)key[3]);
						ss.setPrice(((BigDecimal)key[4]).floatValue());
						ss.setPicUrl((String)key[5]);
						queryList.add(ss);
					} else if (type == AbsShoppingItem.TYPE_COMBO) {
						ComboShoppingItem combo = new ComboShoppingItem();
						combo.setId(((BigInteger)key[1]).longValue());
						combo.setName((String)key[6]);
						combo.setPrice(((BigDecimal)key[7]).floatValue());
						queryList.add(combo);
					}
				}
			}
		
		sess.close();
		return queryList;
	}
	
	
	public List<AbsShoppingItem> queryItemListLazy(long cateId, int start, int fetchCount) {
		
		Session sess = openSession();
		List<AbsShoppingItem> queryList =  null;
		
		String hql = "   from ESCategoryItem " + 
					 " where categoryId =:cid";
			Query query = sess.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			query.setLong("cid", cateId);
			
			List<ESCategoryItem> list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					ESCategoryItem cateItem = (ESCategoryItem) list.get(i);
					int type = cateItem.getType();
					if (type == AbsShoppingItem.TYPE_SINGLE) {
						SingleShoppingItem ss = new SingleShoppingItem();
						ss.setId(cateItem.getItemId());
						queryList.add(ss);
					} else if (type == AbsShoppingItem.TYPE_COMBO) {
						ComboShoppingItem combo = new ComboShoppingItem();
						combo.setId(cateItem.getItemId());
						queryList.add(combo);
					}
				}
			}
		
		sess.close();
		return queryList;
	}
	
	
	public List<EShoppingItemConfigDetail>  getRecommandationList(int start, int fetchCount) {
		return getConfigDetailList(1, start, fetchCount);
	}
	
	
	public List<EShoppingItemConfigDetail>  getNewList(int start, int fetchCount) {
		return getConfigDetailList(2, start, fetchCount);
	}
	
	public List<EShoppingItemConfigDetail>  getHotList(int start, int fetchCount) {
		return getConfigDetailList(3, start, fetchCount);
	}
	
	public List<EShoppingItemConfigDetail>  getSaleList(int start, int fetchCount) {
		return getConfigDetailList(4, start, fetchCount);
	}
	
	private List<EShoppingItemConfigDetail> getConfigDetailList(int type, int start, int fetchCount) {
		Session sess = openSession();
		List<EShoppingItemConfigDetail> queryList =  null;
		StringBuffer hql = new StringBuffer("from EShoppingItemConfigDetail ");
		switch (type) {
		case 1:
			hql.append(" where isRecommand = true");
			break;
		case 2:
			hql.append(" where isNew = true");
			break;
		case 3:
			hql.append(" where isHot = true");
			break;
		case 4:
			hql.append(" where isSale = true");
			break;
			
		}
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(start);
		query.setMaxResults(fetchCount);
		List<EShoppingItemConfigDetail>  list = query.list();
		int size = list.size();
		if (size > 0) {
			queryList = new ArrayList<EShoppingItemConfigDetail>(size);
			for (int i = 0; i < size; i++) {
				EShoppingItemConfigDetail configDetail = (EShoppingItemConfigDetail) list.get(i);
				EShoppingItemConfigDetail copyDetail = new EShoppingItemConfigDetail();
				copyDetail.setId(configDetail.getId());
				copyDetail.setHot(configDetail.isHot());
				copyDetail.setSale(configDetail.isSale());
				copyDetail.setRecommand(configDetail.isRecommand());
				copyDetail.setNew(configDetail.isNew());
				copyDetail.setSaledCounts(configDetail.getSaledCounts());
				copyDetail.setRank(configDetail.getRank());
				copyDetail.setStockDate(configDetail.getStockDate());
				copyDetail.setItemType(configDetail.getItemType());
				queryList.add(copyDetail);
			}
		}
		sess.close();
		return queryList;
	}
	
	
	
	public List<CategoryItemSpecialType> getCategorySpecialType(Category cate) {
		if (cate == null) {
			return null;
		}
		
		List<CategoryItemSpecialType> list = null;
		Session sess = openSession();
		Query query = sess.createQuery(" from ESCatgoryItemSpecialType st where st.categoryId =:ci order by type_group ");
		query.setLong("ci", cate.getId());
		List<ESCatgoryItemSpecialType> queryList = query.list();
		if (queryList.size() > 0) {
			list = new ArrayList<CategoryItemSpecialType>(queryList.size());
			for (ESCatgoryItemSpecialType escit : queryList) {
				CategoryItemSpecialType cit = new CategoryItemSpecialType(escit);
				cit.setCategory(cate);
				cate.addType(cit);
				list.add(cit);
			}
		}
		
		cate.setLoadCategorySpecificalType(true);
		
		sess.close();
		
		return list;
	}
	
	
	public void addESCatgoryItemSpecialType(CategoryItemSpecialType  type) {
		if (type == null || type.getCategory() == null) {
			return;
		}
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCatgoryItemSpecialType esType = new ESCatgoryItemSpecialType();
		esType.setCategoryId(type.getCategory().getId());
		esType.setGroup(type.getGroup());
		esType.setGroupName(type.getGroupName());
		esType.setName(type.getName());
		esType.setShow(type.isShow());
		
		session.saveOrUpdate(esType);
		type.setId(esType.getId());
		t.commit();
		session.close();
	}
	
	public void deleteESCatgoryItemSpecialType(CategoryItemSpecialType type) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCatgoryItemSpecialType esitem = new ESCatgoryItemSpecialType();
		esitem.setId(type.getId());
		session.delete(esitem);
		t.commit();
		session.close();
	}
	
	
	public List<EStemSpecialTypeMapping> querySpecialTypeMapping(Category cate, int start, int fetchCount) {
		Session sess = openSession();
		List<EStemSpecialTypeMapping> queryList =  null;
		
		String hql = "   from EStemSpecialTypeMapping " + 
					 " where categoryId =:cid";
			Query query = sess.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			query.setLong("cid", cate.getId());
			
			List<EStemSpecialTypeMapping> list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<EStemSpecialTypeMapping>(size);
				for (int i = 0; i < size; i++) {
					EStemSpecialTypeMapping mapping = (EStemSpecialTypeMapping) list.get(i);
					EStemSpecialTypeMapping esiTypeMapping = new EStemSpecialTypeMapping();
					esiTypeMapping.setTypeId(mapping.getTypeId());
					esiTypeMapping.setCategoryId(mapping.getCategoryId());
					esiTypeMapping.setItemId(mapping.getItemId());
					esiTypeMapping.setItemType(mapping.getItemType());
					queryList.add(esiTypeMapping);
				}
			}
		
		sess.close();
		return queryList;
	}
	
	
	public List<Image> queryImageList(AbsShoppingItem item) {
		Session sess = openSession();
		List<Image> queryList =  null;
		
		String hql = "   from ESImage " + 
					 " where itemId =:itemid and itemType =:itemType ";
			Query query = sess.createQuery(hql);
			query.setLong("itemid", item.getId());
			query.setInteger("itemType", item.getType());
			
			List<ESImage> list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<Image>(size);
				for (int i = 0; i < size; i++) {
					ESImage esimage = (ESImage) list.get(i);
					Image newImage = new Image(esimage.getId(), esimage.getUri(), esimage.getImageType());
					queryList.add(newImage);
					item.addImage(newImage);
				}
			}
			item.setLoadImages(true);
		
		sess.close();
		return queryList;
	}

}
