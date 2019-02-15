package com.shuto.mam.webclient.beans.stpi.pi_tcfg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;


import com.shuto.mam.app.stpi.impdata.ImportPIDataHelp;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
/**
 * @user  上传数据
 * @author mabin
 * @date 2017/2/21
 *
 */
public class ImportDatasBean extends DataBean {
	public ImportDatasBean() {
		super();
	}
	
	public int execute() throws MXException, RemoteException {
		UploadFile file = (UploadFile)this.app.get("importfile");
		String fileName = file.getFullFileName();
		
		if(fileName.lastIndexOf(".")>-1){
			String fileType = fileName.substring(file.getFullFileName().lastIndexOf("."));
			if(fileType!=null&&".xls".equals(fileType)){
				ByteArrayOutputStream fileOutputStream = file.getFileOutputStream();
				InputStream ins = new ByteArrayInputStream(fileOutputStream.toByteArray());
				try {
					new ImportPIDataHelp().analysisExcelFileAndImportDataToDB(ins);
					this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), "数据导入", "数据导入成功", new String[] { file.getFullFileName() });
				} catch (Exception e) {
					e.printStackTrace();
					this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), "数据导入", "数据导入失败,请查看日志后重新导入!", new String[] { file.getFullFileName() });
				}
				
			}else{
				this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), "文件错误", "导入文件不正确，请选择EXCEL文件！", new String[] {  file.getFullFileName() });
			}
		}
		return 1;
	}

}
