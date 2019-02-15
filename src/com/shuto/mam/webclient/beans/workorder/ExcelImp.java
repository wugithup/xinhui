package com.shuto.mam.webclient.beans.workorder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import jxl.Sheet;
import jxl.Workbook;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;

public class ExcelImp extends DataBean {
	public void deleteXLS(UploadFile uploadfile) {
		uploadfile.delete();
		uploadfile.save();
	}

	public int execute() throws MXException, RemoteException {
		UploadFile uploadfile = (UploadFile) this.app.get("doclinkfile");
		boolean flag = false;

		String s = uploadfile.getFullFileName();
		String s1 = uploadfile.getAbsoluteFileName();
		String s2 = uploadfile.getFullFileName();

		boolean flag2 = (s != null) && (s.length() > 0);
		flag = (s2 != null) && (s2.length() > 0);
		if ((!flag) && (!flag2)) {
			throw new MXApplicationException("designer", "noimportfile");
		}

		Properties properties = getDocLinkProperties();
		String s3 = properties.getProperty("mxe.doclink.doctypes.defpath");

		String strFile = "";
		int ret = 0;
		try {
			uploadfile.setDirectoryName(s3.trim());
			uploadfile.writeToDisk();
			strFile = uploadfile.getAbsoluteFileName();

			if (!Utility.isNull(s1)) {
				uploadfile.save();
			}

			ret = readXls(strFile);
			System.out.println("---------------------------" + strFile);
			deleteXLS(uploadfile);
		} catch (IOException e) {
			e.printStackTrace();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"ExcelImport", e.toString(), 2);
			deleteXLS(uploadfile);
		} finally {
			if (ret >= 0)
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
						"ExcelImport", "Excel文件中的所有数据均已成功导入!", 2);
			else {
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
						"ExcelImport", "Excel文件中的数据导入出错，请重新导入！", 2);
			}

		}

		this.app.remove("doclinkfile");
		this.app.getAppBean().fireStructureChangedEvent();
		this.app.getAppBean().fireDataChangedEvent();
		this.app.getAppBean().reloadTable();
		this.sessionContext.queueRefreshEvent();
		return 1;
	}

	public int readXls(String fileName) {
		int row = 0;
		String sheetname = "";
		try {
			File xlsFile = new File(fileName);

			Workbook book = Workbook.getWorkbook(xlsFile);
			Sheet[] arrysheet = book.getSheets();
			for (int i = 0; i < arrysheet.length; i++) {
				Sheet sheet = book.getSheet(i);
				int rowCount = sheet.getRows();
				System.out.println("-----------------------------------"
						+ rowCount);
				sheetname = sheet.getName();
				System.out.println("-----------------------------------"
						+ sheetname);
				MboRemote gzpmbo = this.app.getAppBean().getMbo();
				if ((sheetname.equalsIgnoreCase("运行必须采取安措"))) {
					row = 0;
					MboSetRemote yxacmboset = gzpmbo.getMboSet("WOSAFETYLINK001");
					System.out
							.println("-----------------------------------------"
									+ yxacmboset.getName());
					yxacmboset.deleteAll();
					yxacmboset.setOwner(gzpmbo);
					for (int j = 1; j < rowCount; j++) {
						row = j;
						MboRemote opp = yxacmboset.addAtEnd();
						System.out
								.println("row--------------------------------"
										+ row);
						// 执行顺序
						opp.setValue("APPLYSEQ", sheet.getCell(0, row)
								.getContents().trim());
						// 隔离点
						opp.setValue("TAGOUTID", sheet.getCell(1, row)
								.getContents().trim());
						//隔离点描述
						opp.setValue("TAGOUTDESCRIPTION2", sheet.getCell(2, row)
								.getContents().trim());
						//钥匙编号
						opp.setValue("YSBH", sheet.getCell(3, row)
								.getContents().trim());
						//隔离方法
						opp.setValue("TAGMETHOD", sheet.getCell(2, row)
								.getContents().trim());
						
						opp.setValue("HAZARDID", "YXBXAC");
						opp.setValue("WOSAFETYDATASOURCE", "WO");
						opp.setValue("ORGID", "XHXM");
						opp.setValue("SITEID", "XHDC");
						opp.setValue("S_ISZX", 0);
						opp.setValue("ISKG", 0);
						opp.setValue("ISDX", 0);
						opp.setValue("ISBSP", 0);
						opp.setValue("S_ISHF", 0);
						opp.setValue("ISGLCT", 0);
						opp.setValue("LANGCODE", "ZH");
						opp.setValue("C_ISZX", 0);
						opp.setValue("C_JXZL", 0);
						opp.setValue("HASLD", 0);
						
					}
					yxacmboset.save();
					yxacmboset.close();
					super.save();
				}else if((sheetname.equalsIgnoreCase("检修必须采取安措"))){
					row = 0;
					MboSetRemote yxacmboset = gzpmbo.getMboSet("WOSAFETYLINK002");
					System.out
							.println("-----------------------------------------"
									+ yxacmboset.getName());
					yxacmboset.deleteAll();
					yxacmboset.setOwner(gzpmbo);
					for (int j = 1; j < rowCount; j++) {
						row = j;
						MboRemote opp = yxacmboset.addAtEnd();
						System.out
								.println("row--------------------------------"
										+ row);
						// 执行顺序
						opp.setValue("APPLYSEQ", sheet.getCell(0, row)
								.getContents().trim());
						// 隔离点
						opp.setValue("TAGOUTID", sheet.getCell(1, row)
								.getContents().trim());
						//隔离点描述
						opp.setValue("TAGOUTDESCRIPTION2", sheet.getCell(2, row)
								.getContents().trim());
						//钥匙编号
						opp.setValue("YSBH", sheet.getCell(3, row)
								.getContents().trim());
						//隔离方法
						opp.setValue("TAGMETHOD", sheet.getCell(2, row)
								.getContents().trim());
						
						opp.setValue("HAZARDID", "JXBXAC");
						opp.setValue("WOSAFETYDATASOURCE", "WO");
						opp.setValue("ORGID", "XHXM");
						opp.setValue("SITEID", "XHDC");
						opp.setValue("S_ISZX", 0);
						opp.setValue("ISKG", 0);
						opp.setValue("ISDX", 0);
						opp.setValue("ISBSP", 0);
						opp.setValue("S_ISHF", 0);
						opp.setValue("ISGLCT", 0);
						opp.setValue("LANGCODE", "ZH");
						opp.setValue("C_ISZX", 0);
						opp.setValue("C_JXZL", 0);
						opp.setValue("HASLD", 0);
					}
					yxacmboset.save();
					yxacmboset.close();
					super.save();
				}else if((sheetname.equalsIgnoreCase("补充安全措施"))){
					row = 0;
					MboSetRemote yxacmboset = gzpmbo.getMboSet("WOSAFETYLINK003");
					System.out
							.println("-----------------------------------------"
									+ yxacmboset.getName());
					yxacmboset.deleteAll();
					yxacmboset.setOwner(gzpmbo);
					for (int j = 1; j < rowCount; j++) {
						row = j;
						MboRemote opp = yxacmboset.addAtEnd();
						System.out
								.println("row--------------------------------"
										+ row);
						// 执行顺序
						opp.setValue("APPLYSEQ", sheet.getCell(0, row)
								.getContents().trim());
						// 隔离点
						opp.setValue("TAGOUTID", sheet.getCell(1, row)
								.getContents().trim());
						//隔离点描述
						opp.setValue("TAGOUTDESCRIPTION2", sheet.getCell(2, row)
								.getContents().trim());
						//钥匙编号
						opp.setValue("YSBH", sheet.getCell(3, row)
								.getContents().trim());
						//隔离方法
						opp.setValue("TAGMETHOD", sheet.getCell(2, row)
								.getContents().trim());
						
						opp.setValue("HAZARDID", "BCAC");
						opp.setValue("WOSAFETYDATASOURCE", "WO");
						opp.setValue("ORGID", "XHXM");
						opp.setValue("SITEID", "XHDC");
						opp.setValue("S_ISZX", 0);
						opp.setValue("ISKG", 0);
						opp.setValue("ISDX", 0);
						opp.setValue("ISBSP", 0);
						opp.setValue("S_ISHF", 0);
						opp.setValue("ISGLCT", 0);
						opp.setValue("LANGCODE", "ZH");
						opp.setValue("C_ISZX", 0);
						opp.setValue("C_JXZL", 0);
						opp.setValue("HASLD", 0);
					}
					yxacmboset.save();
					yxacmboset.close();
					super.save();
				}else if((sheetname.equalsIgnoreCase("应装接地线"))){
					row = 0;
					MboSetRemote yxacmboset = gzpmbo.getMboSet("WOSAFETYLINK004");
					System.out
							.println("-----------------------------------------"
									+ yxacmboset.getName());
					yxacmboset.deleteAll();
					yxacmboset.setOwner(gzpmbo);
					for (int j = 1; j < rowCount; j++) {
						row = j;
						MboRemote opp = yxacmboset.addAtEnd();
						// 执行顺序
						opp.setValue("APPLYSEQ", sheet.getCell(0, row)
								.getContents().trim());
						// 隔离点
						opp.setValue("TAGOUTID", sheet.getCell(1, row)
								.getContents().trim());
						//隔离点描述
						opp.setValue("TAGOUTDESCRIPTION2", sheet.getCell(2, row)
								.getContents().trim());
						//钥匙编号
						opp.setValue("YSBH", sheet.getCell(3, row)
								.getContents().trim());
						//隔离方法
						opp.setValue("TAGMETHOD", sheet.getCell(2, row)
								.getContents().trim());
						
						opp.setValue("HAZARDID", "YJDX");
						opp.setValue("WOSAFETYDATASOURCE", "WO");
						opp.setValue("ORGID", "XHXM");
						opp.setValue("SITEID", "XHDC");
						opp.setValue("S_ISZX", 0);
						opp.setValue("ISKG", 0);
						opp.setValue("ISDX", 0);
						opp.setValue("ISBSP", 0);
						opp.setValue("S_ISHF", 0);
						opp.setValue("ISGLCT", 0);
						opp.setValue("LANGCODE", "ZH");
						opp.setValue("C_ISZX", 0);
						opp.setValue("C_JXZL", 0);
						opp.setValue("HASLD", 0);
					}
					yxacmboset.save();
					yxacmboset.close();
					super.save();
				}
			}
			book.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"ExcelImportGzpBean", "工作表:" + sheetname + "第" + row
							+ "行出现错误" + e.toString(), 2);
		}
		return -1;
	}

	public Properties getDocLinkProperties() {
		Properties properties = new Properties();
		try {
			InputStream inputstream = getClass().getResourceAsStream(
					"/doclink.properties");
			properties.load(inputstream);
			inputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return properties;
	}
}
