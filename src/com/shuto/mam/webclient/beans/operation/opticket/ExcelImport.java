package com.shuto.mam.webclient.beans.operation.opticket;

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

public class ExcelImport extends DataBean {
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
				MboRemote czpmbo = this.app.getAppBean().getMbo();
				// 操作票编号
				String opticketNum = czpmbo.getString("OPTICKETNUM");
				if ((sheetname.equalsIgnoreCase("操作项目")) && (i == 0)) {
					row = 0;
					MboSetRemote cznrmboset = czpmbo.getMboSet("OPTICKETLINE");
					System.out
							.println("-----------------------------------------"
									+ cznrmboset.getName());
					cznrmboset.deleteAll();
					cznrmboset.setOwner(czpmbo);
					for (int j = 1; j < rowCount; j++) {
						row = j;
						MboRemote opp = cznrmboset.addAtEnd();
						System.out
								.println("row--------------------------------"
										+ row);
						opp.setValue("OPTICKETNUM",
								czpmbo.getString("OPTICKETNUM"));
						System.out.println("OPTICKETNUM--------------"
								+ czpmbo.getString("OPTICKETNUM"));
						// 序号
						opp.setValue("ORDERNUM", sheet.getCell(0, row)
								.getContents().trim());
						System.out.println("ordernum-----------------------"
								+ sheet.getCell(0, row).getContents());
						// 操作项目
						opp.setValue("OPTICKETPROJECT", sheet.getCell(1, row)
								.getContents().trim());
						opp.setValue("ISOP", "0");
						opp.setValue("OPTICKETNUM", opticketNum);

					}
					cznrmboset.save();
					cznrmboset.close();
					super.save();
				}
			}
			book.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"ExcelImportCzpBean", "工作表:" + sheetname + "第" + row
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