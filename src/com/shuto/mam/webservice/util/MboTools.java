package com.shuto.mam.webservice.util;

import java.rmi.RemoteException;

import com.google.gson.Gson;

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class MboTools {
	public static MboSetRemote getMboSet(String name) throws RemoteException, MXException {
		return MXServer.getMXServer().getMboSet(name, MXServer.getMXServer().getSystemUserInfo());
	}

	public static String log(String key, Object value) {
		String msg = new Gson().toJson(value);
		System.out.println(key + ":" + msg);
		return msg;
	}
}