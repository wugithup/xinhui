package com.shuto.mam.app.reports;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**  
com.shuto.mam.app.reports.ReportsDataMbo 华东大区 阜阳项目 报表自动计算定时任务
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:44:24
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ReportsDataMbo extends Mbo implements ReportsDataMboRemote{

	public ReportsDataMbo(MboSet ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws MXException {
		// TODO Auto-generated method stub
		super.init();
//		try {
//			if(!getMboSet("reportszbdata").isEmpty()){
//				setFieldFlag(new String[]{"REPORTSID","MKDATE"}, MboConstants.READONLY, true);
//			} 
//			if(!isNew()){
//				if((getBoolean("ISLOCKED"))&&!"MAXADMIN".equals(getUserInfo().getPersonId())){
//					setFlag(MboConstants.READONLY, true);
//				}
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void delete() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		if(getBoolean("ISLOCKED")){
			throw new MXApplicationException("", "该报表数据已经锁定,不能进行删除操作!");
		}
		super.delete();
	}

	/**
	 * @param write	true:设置VALUE字段的值,false:不设置VALUE字段的值
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void makeReport(boolean write) throws RemoteException, MXException {
		// TODO Auto-generated method stub
		if(getBoolean("ISLOCKED")){
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date mkdate = getDate("MKDATE");
		if(mkdate==null||isNull("REPORTSID")){
			return;
		}
		String reportsid = getString("REPORTSID");
		String tongjiType = getString("REPORTSNAME.TONGJITYPE");
		
		ZhibiaoTuuyou zt=new ZhibiaoTuuyou();
		mkdate = zt.formatDate(mkdate, tongjiType,null);
		
		if(hasReport()){
			throw new MXApplicationException("", "该日期报表已存在,不能重复创建!");
		}
		
		SqlFormat sql=null;	
		MboSetRemote rSet = getMboSet("REPORTSNAME");
		MboSetRemote rzbSet = rSet.getMbo(0).getMboSet("reportszb");
		rzbSet.setOrderBy("seqnum desc");
		
		rzbSet.reset();
		for(int i=0;i<rzbSet.count();i++){
			ReportsZBMboRemote rzb = (ReportsZBMboRemote) rzbSet.getMbo(i);
			sql = new SqlFormat( "REPORTSDATAID=:1 and REPORTSZBID=:2");
			sql.setObject(1, "REPORTSZBDATA", "REPORTSDATAID", getString("REPORTSDATAID"));
			sql.setObject(2, "REPORTSZBDATA", "REPORTSZBID", rzb.getString("reportszbid"));
			MboSetRemote rzbdSet = getMboSet("$REPORTSZBDATA","REPORTSZBDATA",sql.format());
			MboRemote rzbd = null;
			if(rzbdSet.count()==0){
				rzbd=rzbdSet.add();
				rzbd.setValue("reportszbid",rzb.getString("reportszbid"));
				rzbd.setValue("REPORTSDATAID",getString("REPORTSDATAID"));
			}else{
				rzbd=rzbdSet.getMbo(0);
			}
			if((!rzbd.getBoolean("ISLOCKED")&&!rzb.isNull("FORMULA"))||!rzb.isNull("SQLSTR")){
				String jisuanValue = rzb.jisuanValue(mkdate);
				
//				System.out.println("jisuanValue="+jisuanValue);
				if("".equals(jisuanValue)){
					rzbd.setValueNull("VALUE",DataBean.ATTR_READONLY);
				}else{
					if(write){
						if("NaN".equals(jisuanValue)
						||"Infinity".equals(jisuanValue)
						||"-Infinity".equals(jisuanValue)){
							rzbd.setValue("VALUE","0",DataBean.ATTR_READONLY);
							
						}else{
							rzbd.setValue("VALUE",jisuanValue,DataBean.ATTR_READONLY);
						}
						
					}
				}
			}
			rzbdSet.save();
			rzbdSet.close();
		}
		rzbSet.close();
		rSet.save();
		rSet.close();
		
		if("日".equals(tongjiType)){
		}else if("月".equals(tongjiType)){
			
		}else if("年".equals(tongjiType)){
			
		}
		
		
//		rz
		
	}

	public boolean hasReport() throws RemoteException, MXException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Mbo mbo = this;
		ZhibiaoTuuyou zt=new ZhibiaoTuuyou();
		Date mkdate = mbo.getDate("MKDATE");
		if(mkdate==null||mbo.isNull("REPORTSID")){
			return false;
		}
		String tongjiType = mbo.getString("REPORTSNAME.TONGJITYPE");
		mkdate = zt.formatDate(mkdate, tongjiType,null);
		SqlFormat sql = new SqlFormat( "REPORTSID=:1 and MKDATE=:2 and REPORTSDATAID!=:3");
		sql.setObject(1, "REPORTSDATA", "REPORTSID", mbo.getString("REPORTSID"));
		sql.setObject(2, "REPORTSDATA", "MKDATE", sdf.format(mkdate));
		sql.setObject(3, "REPORTSDATA", "REPORTSDATAID", mbo.getString("REPORTSDATAID"));
		MboSetRemote rdSet = mbo.getMboSet("$REPORTSDATA", "REPORTSDATA", sql.format());
		if(rdSet.count()>0){
			rdSet.close();
			return true;
		}
		rdSet.close();
		return false;
	}
}
