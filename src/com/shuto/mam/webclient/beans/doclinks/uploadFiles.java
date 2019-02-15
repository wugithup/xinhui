package com.shuto.mam.webclient.beans.doclinks;

import java.io.File;
import java.rmi.RemoteException;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import com.shuto.mam.webclient.beans.doclinks.ASBeginUploadResult;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;


public class uploadFiles extends uploadFileOne {
	ASBeginUploadResult result=null;
	String fileName = "";
	String typeName = "";
	String urlnames = "";
	String  url  = "";
	String docid = null;
	String doctype="";
	String  gns=null;
	@SuppressWarnings("deprecation")
	public int execute() throws MXException, RemoteException {
		int retVal = super.passSuperExecute();
		validate();
		try {
			save();
		} catch (MXException e) {
			WebClientEvent currentEvent = this.sessionContext.getCurrentEvent();
			Utility.showMessageBox(currentEvent, e);
			getMboSetRemote().reset();

			return 1;
		}
		if (this.parent != null) {
			this.parent.fireStructureChangedEvent();
		}
		return retVal;
	}

	public void save() throws MXException

	{

		if (this.mboSetRemote == null) {
			return;
		}
		try {
			boolean fromMainApp = true;
			DataBean appbean = this.app.getAppBean();
			if ((this.parent != null) && (this.parent != appbean)) {
				fromMainApp = false;
			}
			boolean parentBoundToTable = false;
			if ((this.parent != null) && (this.parent.boundToTable())) {
				parentBoundToTable = true;
			}
			if ((fromMainApp) || (!parentBoundToTable)) {
				this.mboSetRemote = this.app.getAppBean().getMboSet();
			}
			if (this.mboSetRemote == null) {
				return;
			}
			MboRemote newMbo = this.mboSetRemote.getMbo();
			if (newMbo != null) {
				if (newMbo.toBeAdded()) {
					try {

						long uniqueId = this.app.getAppBean().getUniqueIdValue();
						this.mboSetRemote.save();
						this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
						newMbo = this.app.getAppBean().getMboForUniqueId(uniqueId);
					} catch (NullPointerException e) {
					}
				} else {
					MboSetRemote mboSet = newMbo.getMboSet("DOCLINKS");
					int count = mboSet.count();
				
					for (int i = 0; i < count; i++) {
						// 获取到某一行DOCLINKS表的mbo
						MboRemote mbo = mboSet.getMbo(i);
						MboSetRemote mboSetinfo = mbo.getMboSet("DOCINFO");
						int count1 = mboSetinfo.count();
						for (int j = 0; j < count1; j++) {
							MboRemote mbos = mboSetinfo.getMbo(i);
							doctype= mbos.getString("doctype");
							System.out.println("doctype:"+doctype);
					        //获取doctypes表的mbo
					    	MboSetRemote doctypesSet = mbo.getMboSet("$doctypes" + (int) (Math.random() * 10000.0D), "doctypes",
									" doctype='" + doctype + "'");
					    	MboRemote docinfoMbo = doctypesSet.getMbo(0);
					    	gns=docinfoMbo.getString("MOENGNS");
							System.out.println("gns:"+gns);
							// 获取上传文件在docinfo表的路径信息
							urlnames = mbos.getString("URLNAME");
							// 根据路径获取到文件名称
							File file = new File(urlnames);
							fileName = file.getName();
							// 根绝docinfos表的关联关系获取到doctypes表的描述
							MboSetRemote TypeMbo = mbos.getMboSet("DOCTYPES");
							MboRemote localMboRemote2 = TypeMbo.getMbo(0);
							typeName = localMboRemote2.getString("DESCRIPTION");
							mbos.setValue("DESCRIPTION", fileName,11L);
							result = uploadName(urlnames, typeName,gns);
					        mbos.setValue("FILEGNS", result.docId , 11L);					        
							// 文件上传成功 结束循环
					        if(gns.length()==0 ) {
					        	System.out.println("result.createUrl:"+result.createUrl);
					        	docinfoMbo.setValue("moengns",result.createUrl , 11L);
					        }
							break;
						}
						// 文件上传成功 结束循环
						break;
					}
					


					
					this.mboSetRemote.save();
					this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
					if (fromMainApp) {
						newMbo = this.mboSetRemote.moveTo(this.currentRow);
					}
				}
				if (newMbo == null) {
					this.currentRecordData = null;
					this.currentRow = -1;
				} else {
					this.currentRecordData = newMbo.getMboData(this.app.getAppBean().getAttributes());
				}
				fireStructureChangedEvent();
			}
		} catch (RemoteException e) {
			handleRemoteException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	


}
