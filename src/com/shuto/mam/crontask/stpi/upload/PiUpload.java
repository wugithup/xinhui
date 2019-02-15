package com.shuto.mam.crontask.stpi.upload;

import psdi.mbo.MboRemote;

public abstract class PiUpload {
	public abstract int[] upload(MboRemote dbMbo);
}
