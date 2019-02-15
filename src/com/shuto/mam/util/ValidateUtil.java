package com.shuto.mam.util;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class ValidateUtil {
	public void valideate(String paramString1, String paramString2,
			String paramString3, MboRemote paramMboRemote)
			throws RemoteException, MXException {
		if (paramMboRemote.isNew()) {
			return;
		}

		StringBuffer localStringBuffer1 = new StringBuffer("");

		StringBuffer localStringBuffer2 = new StringBuffer("");

		StringBuffer localStringBuffer3 = new StringBuffer("");

		String str1 = paramString1.toUpperCase();

		String str2 = paramString3.toUpperCase();

		String str3 = paramString2.toUpperCase();

		String str4 = "1=1";

		String str5 = null;

		String str6 = null;

		String str7 = localStringBuffer1.append("appname = '").append(str1)
				.append("' and classname = '").append(str3)
				.append("' and methodname = '").append(str2).append("'")
				.toString();

		MboSetRemote localMboSetRemote1 = paramMboRemote.getMboSet(
				"$validatecfg", "validatecfg", str7);

		MboRemote localMboRemote = null;

		int i = 1;

		MboSetRemote localMboSetRemote2 = null;

		String[] arrayOfString = new String[1];
		if ((localMboSetRemote1 != null) && (localMboSetRemote1.count() > 0)) {
			for (int j = 0; j < localMboSetRemote1.count(); j++) {
				localMboRemote = localMboSetRemote1.getMbo(j);

				str5 = localMboRemote.getString("setwhereMbo");

				str4 = localMboRemote.getString("setwhere");

				str6 = localMboRemote.getString("valuemessage");
				localMboSetRemote2 = paramMboRemote.getMboSet(
						localStringBuffer3.append("$").append(str5).toString(),
						str5, str4);
				if (localMboSetRemote2.count() <= 0) {
					localStringBuffer2.append(i++).append("、").append(str6)
							.append("; ");
				}
			}
			arrayOfString[0] = localStringBuffer2.toString();
			if ((arrayOfString[0] != null) && (!"".equals(arrayOfString[0]))) {
				throw new MXApplicationException("valideatecfg",
						"valideatecfg", arrayOfString);
			}
		}
		if (localMboSetRemote1 != null) {
			localMboSetRemote1.close();
		}
		if (localMboSetRemote2 != null)
			localMboSetRemote2.close();
	}
}