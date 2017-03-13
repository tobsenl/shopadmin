package com.sunchin.shop.admin.propValue.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.Action;
import com.sunchin.shop.admin.pojo.ScPropValue;
import com.sunchin.shop.admin.pojo.ScProperty;
import com.sunchin.shop.admin.propValue.service.PropValueService;
import com.sunchin.shop.admin.property.service.PropertyService;

import framework.action.PageAction;
import framework.bean.PageBean;

public class PropValueAction extends PageAction {
	
	@Resource(name="propValueService")
	private PropValueService propValueService; 
	
	private ScPropValue propValue;
	
	/**
	 * 查询
	 * @return
	 */
	public String query() {
		try {
			PageBean resultData = propValueService.queryPropertyList(this.getPageBean());
			this.setTotal(resultData.getTotal());
			this.setDataRows(resultData.getPageData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete() {
		try {
			propValueService.delProperty(propValue.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	/**
	 * 修改
	 * @return
	 */
	public String updateProperty() {
		try {
			propValueService.updateProperty(propValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 新增
	 * @return
	 */
	public String addProperty() {
		try {
			propValueService.addProperty(propValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 修改的查询
	 * @return
	 */
	public String queryPropertyById() {
		try {
			propValue = propValueService.queryProperty(propValue.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public ScPropValue getPropValue() {
		return propValue;
	}

	public void setPropValue(ScPropValue propValue) {
		this.propValue = propValue;
	}

}