package com.shuto.mam.crontask.stpi.download;

import java.util.Map;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;

public abstract class PiDownload {
	public abstract Map<String,String> download(MboRemote taskMbo,MboSetRemote areaSet,MboSetRemote itemSet,MboSetRemote userSet);
}
