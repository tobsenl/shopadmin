package com.sunchin.shop.admin.coupon.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.sunchin.shop.admin.dict.FlagEnum;
import com.sunchin.shop.admin.pojo.ScCoupon;
import com.sunchin.shop.admin.pojo.ScDictionary;
import com.sunchin.shop.admin.pojo.ScUserCoupon;

import framework.bean.PageBean;
import framework.db.DBUtil;
import framework.db.PageDAO;
import framework.db.pojo.TXtSysCode;

@Repository("couponDAO")
public class CouponDAO extends PageDAO{

	public int queryCouponCount(PageBean pageBean) {
		List<String> params = new ArrayList<String>();
		params.add(FlagEnum.ACT.getCode());
		params.add("COUPON_TYPE");
		String sql = this.buildWhereSql(pageBean, params);
		return DBUtil.getInstance().queryCountBySQL(sql, params);
	}

	@SuppressWarnings("unchecked")
	public List<ScCoupon> queryCouponPagination(PageBean pageBean) {
		List<String> params = new ArrayList<String>();
		params.add(FlagEnum.ACT.getCode());
		params.add("COUPON_TYPE");
		String sql = this.buildWhereSql(pageBean, params);
		return this.query(sql, params, DBUtil.getInstance(), pageBean);
	}
	private String buildWhereSql(PageBean pageBean, List<String> params) {
		// 拼接查询条件
		StringBuffer sql = new StringBuffer(" select t1.id,t1.coupon_name,t1.coupon_zs_balance,t1.coupon_blance, ");
		sql.append(" t1.coupon_xf_balance,t1.coupon_expiry_date,decode(t1.coupon_status,'0','有效','1','无效') coupon_status, ");
		sql.append(" t1.coupon_remark,to_char(t1.create_time,'yyyy-mm-dd') create_time,t2.name ");
		sql.append(" from sc_coupon t1 ");
		sql.append(" left join sc_dictionary t2 on t2.code=t1.coupon_type ");
		sql.append(" where t1.coupon_flag=?");
		sql.append(" and t2.type=?");
		if (pageBean.getQueryParams() != null && !pageBean.getQueryParams().isEmpty()) {
			String couponName = pageBean.getQueryParams().get("couponName");
			if (StringUtils.isNotBlank(couponName)){
				params.add("%"+couponName+"%");
				sql.append(" and t1.coupon_name like ? ");
			}
			String expiryDate = pageBean.getQueryParams().get("expiryDate");
			if (StringUtils.isNotBlank(expiryDate)){
				params.add(expiryDate);
				sql.append(" and t1.coupon_expiry_date=? ");
			}
			String couponType = pageBean.getQueryParams().get("couponType");
			if (StringUtils.isNotBlank(couponType)&&!"-1".equals(couponType)){
				params.add(couponType);
				sql.append(" and t2.code=? ");
			}
			String startTime = pageBean.getQueryParams().get("startRegTime");
			if (StringUtils.isNotBlank(startTime)){
				params.add(startTime);
				sql.append(" and t1.create_time >= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			}
			String endTime = pageBean.getQueryParams().get("endRegTime");
			if (StringUtils.isNotBlank(endTime)){
				params.add(endTime+" 23:59:59 ");
				sql.append(" and t1.create_time <= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			}
		}
		sql.append(" order by t1.create_time desc ");
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	public List<ScDictionary> queryCouponType(String type) throws Exception {
		DBUtil db = DBUtil.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("flag", FlagEnum.ACT.getCode());
		return db.queryByPojo(ScDictionary.class, params);
	}
}
