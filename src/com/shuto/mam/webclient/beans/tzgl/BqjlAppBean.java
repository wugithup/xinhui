package com.shuto.mam.webclient.beans.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class BqjlAppBean extends AppBean {

	@Override
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		// 上一条记录补氢后氢气压力
		String bcbqhyl = null;
		MboRemote mbo = this.app.getAppBean().getMbo();
		// 机组编号
		String jzbh = mbo.getString("JZBH");

		MboSetRemote msr = mbo.getMboSet("$TZ_BQJL", "TZ_BQJL",
				"lqlv is not null and jzbh='" + jzbh
						+ "' order by tz_bqjlid desc");
		MboRemote prevMbo = msr.getMbo(0);
		bcbqhyl = prevMbo.getString("bcbqhyl");
		System.out.println("---------------------" + bcbqhyl);
		mbo.setValue("BQHYL", bcbqhyl, 11L);
		this.app.getAppBean().refreshTable();
		return 1;
	}

	public void LQLJS() throws MXException, RemoteException {
		try {
			System.out.println("----------------------start------------------");

			// 上一条记录补氢后氢气压力
			String bcbqhyl = null;
			// 上一条记录补氢后平均氢温
			String bqhpjqw = null;
			MboRemote mbo = this.app.getAppBean().getMbo();

			MboSetRemote msr = mbo.getMboSet("$TZ_BQJL", "TZ_BQJL",
					"lqlv is not null order by tz_bqjlid desc");
			System.out
					.println("-------------------------------------***************");
			MboRemote prevMbo = msr.getMbo(0);
			bcbqhyl = prevMbo.getString("bcbqhyl");
			bqhpjqw = prevMbo.getString("BQHPJQW");
			// 上一条记录补氢完成时间
			long scFinishDate = prevMbo.getDate("FINISHDATE").getTime();
			// 本次补氢开始时间
			long bcStartDate = mbo.getDate("STARTDATE").getTime();
			// 发电机充气容积
			float v = Float.parseFloat(mbo.getString("FDJCQRJ"));
			// 给定状态下环境温度
			float t0 = Float.parseFloat(mbo.getString("HJWD"));
			// 给定状态下大气压力
			float p0 = Float.parseFloat(mbo.getString("DQYL"));
			// 上一次补氢后压力
			float p1 = Float.parseFloat(bcbqhyl);
			// 试验开始时大气压力
			float pB1 = Float.parseFloat(mbo.getString("STARTDQYL"));
			// 上次补氢后平均氢温
			float t1 = Float.parseFloat(bqhpjqw);
			// 补氢前氢气压力
			float p2 = Float.parseFloat(mbo.getString("BQQYL"));
			// 试验结束时的大气压力
			float pB2 = Float.parseFloat(mbo.getString("ENDDQYL"));
			// 补氢前平均氢温
			float t2 = Float.parseFloat(mbo.getString("BQQPJQW"));
			// 补氢间隔
			float hour = mbo.getFloat("BQJG");
			// 计算漏氢率
			float lqV = v * (273 + t0) / p0 * (24 / hour)
					* ((p1 + pB1) / (273 + t1) - (p2 + pB2) / (273 + t2));
			// 保留三位小数
			float lqlv = (float) (Math.round(lqV * 1000)) / 1000;
			String lqLv = Float.toString(lqlv);
			mbo.setValue("LQLV", lqLv);

			super.SAVE();
			System.out
					.println("------------------------end----------------------");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
