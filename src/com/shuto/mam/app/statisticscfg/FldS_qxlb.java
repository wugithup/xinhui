package com.shuto.mam.app.statisticscfg;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * 缺陷统计规则维护
 * 
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月12日 下午9:16:24
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldS_qxlb extends MAXTableDomain {
	public FldS_qxlb(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		// 获取数据表名｜关系(一般不会在此设置关系都用1=1，因为getList设置关系更灵活)
		setRelationship("srtype", "1=1");
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "type" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if (getMboValue().isNull()) {
			return;
		}
		MboSetRemote list = getList();
		SqlFormat sql = new SqlFormat("type=:1");
		sql.setObject(1, "srtype", "type", getMboValue().getString());
		String appWhere = list.getAppWhere();
		list.setAppWhere(sql.format());
		list.reset();
		int count = list.count();
		list.setAppWhere(appWhere);
		list.reset();
		if (count == 0) {
			throw new MXApplicationException("该值在选择列表中不存在", "");
		}
	}
}
