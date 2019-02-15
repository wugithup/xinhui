package com.shuto.mam.webclient.beans.operation.tz;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 掺配煤方案      TZ_CPMTZ
 com.shuto.mam.webclient.beans.operation.tz.CpmtzAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:43:13
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class CpmtzAppBean extends CAppBean {

	public int INSERT() throws MXException, RemoteException {
		return super.INSERT();
	}
	
	public int SAVE() throws MXException, RemoteException {
		
		// 获得当前Mbo
		MboRemote mbo = this.app.getAppBean().getMbo();

		MboSetRemote optzSet = mbo.getMboSet("$cpmtz","CPMTZ","CPMTZTYPE='TZ_CPMTZ' and rownum=1 order by cpmtzid desc ");
		// 获得结果集#1炉
		MboSetRemote optzline1Set = mbo.getMboSet("TZ_CPMTZ");//#1炉
		MboSetRemote optzline2Set = mbo.getMboSet("TZ_CPMTZ1");//#2炉
		MboSetRemote optzline3Set = mbo.getMboSet("TZ_CPMTZ2");//#3炉
		MboSetRemote optzline4Set = mbo.getMboSet("TZ_CPMTZ3");//#4炉
		MboSetRemote optzline5Set = mbo.getMboSet("TZ_CPMTZ4");//#5炉
		DataBean resultsBean1 = this.app.getDataBean("table_optzline");
		MboSetRemote mboset1 = resultsBean1.getMboSet();//#1炉
		DataBean resultsBean2 = this.app.getDataBean("table2_optzline");
		MboSetRemote mboset2 = resultsBean2.getMboSet();//#2炉
		DataBean resultsBean3 = this.app.getDataBean("table3_optzline");
		MboSetRemote mboset3 = resultsBean3.getMboSet();//#3炉
		DataBean resultsBean4 = this.app.getDataBean("table4_optzline");
		MboSetRemote mboset4 = resultsBean4.getMboSet();//#4炉
		DataBean resultsBean5 = this.app.getDataBean("table5_optzline");
		MboSetRemote mboset5 = resultsBean5.getMboSet();//#5炉
		MboRemote mbo1 = null;
		MboRemote mbo2 = null;
		MboRemote mbo3 = null;
		MboRemote mbo4 = null;
		MboRemote mbo5 = null;
	
		double zb_dec01 = mboset1.sum("DEC01");
		double zb_dec02 = mboset1.sum("DEC02");
		double zb_dec03 = mboset1.sum("DEC03");
		double zb_dec04 = mboset1.sum("DEC04");
		double zb_dec05 = mboset1.sum("DEC05");
		
		double zb_dec21 = mboset2.sum("DEC01");
		double zb_dec22 = mboset2.sum("DEC02");
		double zb_dec23 = mboset2.sum("DEC03");
		double zb_dec24 = mboset2.sum("DEC04");
		double zb_dec25 = mboset2.sum("DEC05");
		
		double zb_dec31 = mboset3.sum("DEC01");
		double zb_dec32 = mboset3.sum("DEC02");
		double zb_dec33 = mboset3.sum("DEC03");
		double zb_dec34 = mboset3.sum("DEC04");
		double zb_dec35 = mboset3.sum("DEC05");
		
		double zb_dec41 = mboset4.sum("DEC01");
		double zb_dec42 = mboset4.sum("DEC02");
		double zb_dec43 = mboset4.sum("DEC03");
		double zb_dec44 = mboset4.sum("DEC04");
		double zb_dec45 = mboset4.sum("DEC05");
		
		double zb_dec51 = mboset5.sum("DEC01");
		double zb_dec52 = mboset5.sum("DEC02");
		double zb_dec53 = mboset5.sum("DEC03");
		double zb_dec54 = mboset5.sum("DEC04");
		double zb_dec55 = mboset5.sum("DEC05");
		double hj1 = 0;
		double hj2 = 0;
		double hj3 = 0;
		double hj4 = 0;
		double hj5 = 0;
		double hjfrl = 0;
		double hjlf = 0;
		double hjhff = 0;
		double hjsf = 0;
		//一号炉
		for (int i=0;i<mboset1.count();i++) {
			mbo1 = mboset1.getMbo(i);
			if(mbo1.toBeDeleted()){
				continue;
			}
			zb_dec01 = mbo1.getDouble("dec01");
			zb_dec02 = mbo1.getDouble("dec02");
			zb_dec03 = mbo1.getDouble("dec03");
			zb_dec04 = mbo1.getDouble("dec04");
			zb_dec05 = mbo1.getDouble("dec05");
			hj1 = hj1+(zb_dec01*zb_dec05);
			hj3 = hj3+(zb_dec02*zb_dec05);
			hj4 = hj4+(zb_dec03*zb_dec05);
			hj5 = hj5+(zb_dec04*zb_dec05);
			hj2 = hj2+zb_dec05;
		}
		if(hj2==0){
		 hjfrl = 0;
		 hjlf  = 0;
		 hjhff = 0;
		 hjsf  = 0;	
		}
		else{
		hjfrl=hj1/hj2;
		hjlf=hj3/hj2;
		hjhff=hj4/hj2;
		hjsf=hj5/hj2;
		}
		//2号炉
		double hj21 = 0;
		double hj22 = 0;
		double hj23 = 0;
		double hj24 = 0;
		double hj25 = 0;
		double hjfrl2 = 0;
		double hjlf2 = 0;
		double hjhff2 = 0;
		double hjsf2 = 0;
		for (int i=0;i<mboset2.count();i++) {
			mbo2 = mboset2.getMbo(i);
			if(mbo2.toBeDeleted()){
				continue;
			}
			zb_dec21 = mbo2.getDouble("dec01");
			zb_dec22 = mbo2.getDouble("dec02");
			zb_dec23 = mbo2.getDouble("dec03");
			zb_dec24 = mbo2.getDouble("dec04");
			zb_dec25 = mbo2.getDouble("dec05");
			hj21 = hj21+(zb_dec21*zb_dec25);
			hj23 = hj23+(zb_dec22*zb_dec25);
			hj24 = hj24+(zb_dec23*zb_dec25);
			hj25 = hj25+(zb_dec24*zb_dec25);
			hj22 = hj22+zb_dec25;
		}
		if(hj22==0){
		 hjfrl2 = 0;
		 hjlf2  = 0;
		 hjhff2 = 0;
		 hjsf2  = 0;	
		}
		else{
		hjfrl2=hj21/hj22;
		hjlf2=hj23/hj22;
		hjhff2=hj24/hj22;
		hjsf2=hj25/hj22;
		}
		
		//3号炉
		double hj31 = 0;
		double hj32 = 0;
		double hj33 = 0;
		double hj34 = 0;
		double hj35 = 0;
		double hjfrl3= 0;
		double hjlf3 = 0;
		double hjhff3 = 0;
		double hjsf3 = 0;
		for (int i=0;i<mboset3.count();i++) {
			mbo3 = mboset3.getMbo(i);
			if(mbo3.toBeDeleted()){
				continue;
			}
			zb_dec31 = mbo3.getDouble("dec01");
			zb_dec32 = mbo3.getDouble("dec02");
			zb_dec33 = mbo3.getDouble("dec03");
			zb_dec34 = mbo3.getDouble("dec04");
			zb_dec35 = mbo3.getDouble("dec05");
			hj31 = hj31+(zb_dec31*zb_dec35);
			hj33 = hj33+(zb_dec32*zb_dec35);
			hj34 = hj34+(zb_dec33*zb_dec35);
			hj35 = hj35+(zb_dec34*zb_dec35);
			hj32 = hj32+zb_dec35;
		}
		if(hj32==0){
		 hjfrl3 = 0;
		 hjlf3 = 0;
		 hjhff3 = 0;
		 hjsf3  = 0;	
		}
		else{
		hjfrl3=hj31/hj32;
		hjlf3=hj33/hj32;
		hjhff3=hj34/hj32;
		hjsf3=hj35/hj32;
		}
		//4号炉
		double hj41 = 0;
		double hj42 = 0;
		double hj43 = 0;
		double hj44 = 0;
		double hj45 = 0;
		double hjfrl4= 0;
		double hjlf4 = 0;
		double hjhff4 = 0;
		double hjsf4 = 0;
		for (int i=0;i<mboset4.count();i++) {
			mbo4 = mboset4.getMbo(i);
			if(mbo4.toBeDeleted()){
				continue;
			}
			
			zb_dec41 = mbo4.getDouble("dec01");
			zb_dec42 = mbo4.getDouble("dec02");
			zb_dec43 = mbo4.getDouble("dec03");
			zb_dec44 = mbo4.getDouble("dec04");
			zb_dec45 = mbo4.getDouble("dec05");
			hj41 = hj41+(zb_dec41*zb_dec45);
			hj43 = hj43+(zb_dec42*zb_dec45);
			hj44 = hj44+(zb_dec43*zb_dec45);
			hj45 = hj45+(zb_dec44*zb_dec45);
			hj42 = hj42+zb_dec45;
		}
		if(hj42==0){
		 hjfrl4 = 0;
		 hjlf4 = 0;
		 hjhff4 = 0;
		 hjsf4  = 0;	
		}
		else{
		hjfrl4=hj41/hj42;
		hjlf4=hj43/hj42;
		hjhff4=hj44/hj42;
		hjsf4=hj45/hj42;
		}
		//5号炉
		double hj51 = 0;
		double hj52 = 0;
		double hj53 = 0;
		double hj54 = 0;
		double hj55 = 0;
		double hjfrl5= 0;
		double hjlf5 = 0;
		double hjhff5 = 0;
		double hjsf5 = 0;
		for (int i=0;i<mboset5.count();i++) {
			mbo5= mboset5.getMbo(i);
			if(mbo5.toBeDeleted()){
				continue;
			}
			zb_dec51 = mbo5.getDouble("dec01");
			zb_dec52 = mbo5.getDouble("dec02");
			zb_dec53 = mbo5.getDouble("dec03");
			zb_dec54 = mbo5.getDouble("dec04");
			zb_dec55 = mbo5.getDouble("dec05");
			hj51 = hj51+(zb_dec51*zb_dec55);
			hj53 = hj53+(zb_dec52*zb_dec55);
			hj54 = hj54+(zb_dec53*zb_dec55);
			hj55 = hj55+(zb_dec54*zb_dec55);
			hj52 = hj52+zb_dec55;
		}
		if(hj52==0){
		 hjfrl5 = 0;
		 hjlf5 = 0;
		 hjhff5 = 0;
		 hjsf5  = 0;	
		}
		else{
		hjfrl5=hj51/hj52;
		hjlf5=hj53/hj52;
		hjhff5=hj54/hj52;
		hjsf5=hj55/hj52;
		}
		//删除合计行.
		MboSetRemote hjSet = mbo.getMboSet("$tz_cpmtz","TZ_CPMTZ","cpmtzid="+mbo.getLong("cpmtzid")+" and VC01='掺配后数据' order by cpmtzid desc ");
		hjSet.deleteAll();
		hjSet.getOrderBy();
		//#1炉掺配后数据
		if(!mboset1.isEmpty()){
			MboRemote mbohj1 = optzline1Set.add();
			mbohj1.setValue("cpmtzid", mbo.getLong("cpmtzid"));
			mbohj1.setValue("into01", 5);
			mbohj1.setValue("VC01", "掺配后数据");
			mbohj1.setValue("DEC01", hjfrl);
			mbohj1.setValue("DEC02", hjlf);
			mbohj1.setValue("DEC03", hjhff);
			mbohj1.setValue("DEC04", hjsf);	
			}
		
		//#2炉掺配后数据
		if(!mboset2.isEmpty()){
		MboRemote mbohj2 = optzline2Set.add();
		mbohj2.setValue("cpmtzid", mbo.getLong("cpmtzid"));
		mbohj2.setValue("into01", 1);
		mbohj2.setValue("VC01", "掺配后数据");
		mbohj2.setValue("DEC01", hjfrl2);
		mbohj2.setValue("DEC02", hjlf2);
		mbohj2.setValue("DEC03", hjhff2);
		mbohj2.setValue("DEC04", hjsf2);
		}
		//#3炉掺配后数据
		if(!mboset3.isEmpty()){
		MboRemote mbohj3 = optzline3Set.add();
		mbohj3.setValue("cpmtzid", mbo.getLong("cpmtzid"));
		mbohj3.setValue("into01", 2);
		mbohj3.setValue("VC01", "掺配后数据");
		mbohj3.setValue("DEC01", hjfrl3);
		mbohj3.setValue("DEC02", hjlf3);
		mbohj3.setValue("DEC03", hjhff3);
		mbohj3.setValue("DEC04", hjsf3);
		}
		//#4炉掺配后数据
		if(!mboset4.isEmpty()){
		MboRemote mbohj4 = optzline4Set.add();
		mbohj4.setValue("cpmtzid", mbo.getLong("cpmtzid"));
		mbohj4.setValue("into01", 3);
		mbohj4.setValue("VC01", "掺配后数据");
		mbohj4.setValue("DEC01", hjfrl4);
		mbohj4.setValue("DEC02", hjlf4);
		mbohj4.setValue("DEC03", hjhff4);
		mbohj4.setValue("DEC04", hjsf4);
		}
		//#5炉掺配后数据
		if(!mboset5.isEmpty()){
		MboRemote mbohj5 = optzline5Set.add();
		mbohj5.setValue("cpmtzid", mbo.getLong("cpmtzid"));
		mbohj5.setValue("into01", 4);
		mbohj5.setValue("VC01", "掺配后数据");
		mbohj5.setValue("DEC01", hjfrl5);
		mbohj5.setValue("DEC02", hjlf5);
		mbohj5.setValue("DEC03", hjhff5);
		mbohj5.setValue("DEC04", hjsf5);
		}
		return super.SAVE();
		
	}
	
}
