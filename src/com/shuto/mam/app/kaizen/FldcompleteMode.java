package com.shuto.mam.app.kaizen;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * KAIZEN项目提案       YX_REGISTR
 com.shuto.mam.app.kaizen.FldcompleteMode 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:16:53
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldcompleteMode extends MboValueAdapter {

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo  = getMboValue().getMbo(); //获得当前MBO
		String mode = mbo.getString("COMPLETEMODE"); //获取当前的完成形式
		int score = 0 ; //默认得分为0 
		Boolean  flag  =mbo.getBoolean("EFFECTIVE"); //查看是否有效 无效得0分
		String status  = mbo.getString("status"); //查看当前状态
		if("完成".equals(status)){
			if(!flag){
				score = 0 ; 
			}
			if("本人".equals(mode)){
				score = 2 ; 
			}else if("他人".equals(mode)){
				score =1 ; 
			}
			mbo.setValue("SCORE", score,2L);
		}else{
			mbo.setValue("SCORE", 0,2L);
		}
		
	}

	public FldcompleteMode() {
		super();
	}

	public FldcompleteMode(MboValue mbv) {
		super(mbv);
	}
}
