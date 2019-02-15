package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/** com.shuto.mam.app.reports.ReportsZBMboSet
 * @author 蒋カイセキ    Japan-Tokyo  2013-1-10
 * @ブログ http://shoukaiseki.blog.163.com/
 * @E-メール jiang28555@Gmail.com
 */
/**  
com.shuto.mam.app.reports.ReportsZBMboSet 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午11:09:17
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ReportsZBMboSet extends MboSet implements ReportsZBMboSetRemote{

	public ReportsZBMboSet(MboServerInterface ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Mbo getMboInstance(MboSet mboset) throws MXException,
			RemoteException {
		// TODO Auto-generated method stub
		return new ReportsZBMbo(mboset);
	}

}
