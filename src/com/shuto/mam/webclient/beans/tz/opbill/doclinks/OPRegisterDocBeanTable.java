package com.shuto.mam.webclient.beans.tz.opbill.doclinks;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Vector;

import psdi.app.doclink.DoclinkServiceRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.mbo.Translate;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.runtime.WebClientRuntime;

/**
 * @author lzq
 * @date 创建时间：2017-3-24 上午9:31:27
 * @since 原华南台账相关类
 */
public class OPRegisterDocBeanTable extends DataBean {
	String ownername = "";
	MboRemote ownerMbo = null;

	public OPRegisterDocBeanTable() throws RemoteException, MXException {
	}

	@Override
	public void initialize() throws MXException, RemoteException {
		try {
			getMboSet().checkMethodAccess("add");
		} catch (MXException e) {
			this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), e);
			this.app.popPage(this.app.getCurrentPage(), true);
		}
		super.initialize();

		this.ownerMbo = getMboSetRemote().getOwner();
		if (this.ownerMbo != null) {
			this.ownername = this.ownerMbo.getName();
			this.ownerMbo.getMboSet("DOCLINKS");
		}
		insert();
	}

	@Override
	public synchronized void insert() throws MXException, RemoteException {
		MboSetRemote list = null;
		MboRemote mbo = null;
		super.insert();
		setValue("APP", this.app.getId().toUpperCase());
		if (((list = getList("doctype")) == null) || ((mbo = list.getMbo(0)) == null))
        {
            return;
        }

		setValue("doctype", mbo.getString("doctype"));
	}

	@Override
	public int execute() throws MXException, RemoteException {
		UploadFile df = null;
		String delFileName = "";
		try {
			SqlFormat sqlf = new SqlFormat(getMboSetRemote().getUserInfo(), "app = :1");
			sqlf.setObject(1, "MAXAPPS", "APP", this.app.getId());
			MboSetRemote mboSet = getMbo().getMboSet("$MAXAPP", "MAXAPPS", sqlf.format());
			String originalAppName = mboSet.getMbo(0).getString("originalapp");
			Translate tr = MXServer.getMXServer().getMaximoDD().getTranslator();
			String urltype = tr.toInternalString("URLTYPE", getString("urltype"));

			urltype = "FILE";

			df = (UploadFile) this.app.get("doclinkfile");

			if (df == null) {
				return 1;
			}
			String absFileName = df.getAbsoluteFileName();
			String fullFileName = df.getFullFileName();
			String fileName = df.getFileName();
			String invalidCharacters = null;
			String[] invalidChars = null;
			String invalidLeadingCharacters = null;
			String[] invalidLeadingChars = null;
			try {
				invalidCharacters = getConfigValue("mxe.opdoclink.filename.invalidCharacters");
				invalidLeadingCharacters = getConfigValue("mxe.opdoclink.filename.invalidLeadingCharacters");
			} catch (Exception e) {
				System.out.println("++++ File invalidAttachmentFilename.properties doesn't exist ");
				e.printStackTrace();
			}

			if (invalidCharacters != null) {
				invalidChars = invalidCharacters.split(",");
				for (int i = 0; i < invalidChars.length; ++i) {
					if (fileName.indexOf(invalidChars[i].trim()) == -1)
						continue;
					Object[] params = { invalidChars[i] };
					throw new MXApplicationException("doclink", "errorattachdocinvalidchars", params);
				}
			}

			if (invalidLeadingCharacters != null) {
				invalidLeadingChars = invalidLeadingCharacters.split(",");
				for (int i = 0; i < invalidLeadingChars.length; ++i) {
					if (!fileName.startsWith(invalidLeadingChars[i].trim()))
                    {
                        continue;
                    }

					Object[] params = { invalidLeadingChars[i] };
					throw new MXApplicationException("doclink", "errordoclinkinvalidleadingchars", params);
				}

			}

			String fileExtension = "";
			Vector printableList = new Vector();
			String invalidLeadingType = getConfigValue("mxe.opdoclink.filename.invalidLeadingType");

			for (String str : invalidLeadingType.split(",")) {
				printableList.add(str);
			}

			boolean isAllowedtype = false;
			if (fileName.lastIndexOf(".") != -1) {
				fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			}
			if (fileExtension.equals(""))
            {
                isAllowedtype = true;
            }

			int j = 1;
			getString("printthrulinkdflt");
			getString("printthrulink");
			j = ((!getString("printthrulink").equals("Y")) && (!getString("printthrulinkdflt").equals("Y"))) ? 0 : 1;
			if ((j != 0) && (!fileName.equals("unknown")) && (!printableList.contains(fileExtension))) {
				String appname = this.app.getId();
				if ((!appname.equalsIgnoreCase("createsr")) && (!appname.equalsIgnoreCase("viewsr"))
						&& (!this.ownername.equalsIgnoreCase("commlog"))) {
					throw new MXApplicationException("doclink", "errorprintthrulink");
				}

			}

			MXSession mxs = this.clientSession.getMXSession();
			DoclinkServiceRemote doclinkService = (DoclinkServiceRemote) mxs.getMXServerRemote().lookup("DOCLINK");

			String path = getConfigValue("mxe.opdoclink.Folder");
			String reportdep = this.ownerMbo.getString("REPORTDEP");
			String lnReportdep = getConfigValue(reportdep.trim());

			if (lnReportdep != null) {
				reportdep = lnReportdep;
			}

			String dirName = path + "/" + reportdep;
			if (reportdep.isEmpty()) {
				throw new MXApplicationException("opupfile", "noReportdep");
			}
			try {
				df.setDirectoryName(dirName);

				df.writeToDisk();
				absFileName = df.getAbsoluteFileName();
				delFileName = absFileName;

				if (!WebClientRuntime.isNull(absFileName)) {
					df.save();
				}

				absFileName = absFileName.replace("\\", "/");
				setValue("REPORTDEP", reportdep, 2L);
				if (getString("DOCNAME").isEmpty()) {
					setValue("DOCNAME", fullFileName.substring(0, fullFileName.lastIndexOf(".")), 2L);
				}
				setValue("DOCTYPE", absFileName.substring(path.length()), 2L);
				setValue("OWNERTABLE", this.ownerMbo.getName(), 2L);
				getMbo().setValue("UPLOADDATE", MXServer.getMXServer().getDate(), 2L);
				setValue("ownerid", this.ownerMbo.getString("OPUPLOADFILEID"), 2L);
				setValue("sambaurl", this.clientSession.getContextPath(), 2L);

				this.ownerMbo.setValue("DESCRIPTION", lnReportdep, 2L);
				setValue("urlname", fullFileName);
				setValue("newurlname", absFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.app.remove("doclinkfile");
		} catch (MXException e) {
			if (df != null) {
				File file = new File(delFileName);
				file.delete();
			}
			throw e;
		}
		return 1;
	}

	public String getConfigValue(String propname) throws RemoteException, MXException {
		MboSetRemote msrConfig = MXServer.getMXServer().getMboSet("OPUPLOADCONFIGU", getMboSetRemote().getUserInfo());
		String value = null;

		if (msrConfig != null) {
			msrConfig.setWhere("propname='" + propname + "'");
			if (msrConfig.count() == 0) {
				value = null;
			} else {
				value = msrConfig.getMbo(0).getString("VALUE");

				if (value.isEmpty()) {
					value = null;
				}
			}
		}
		msrConfig.close();
		return value;
	}
}