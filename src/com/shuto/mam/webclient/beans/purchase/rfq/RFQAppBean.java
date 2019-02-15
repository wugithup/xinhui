package com.shuto.mam.webclient.beans.purchase.rfq;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import psdi.app.common.TaxUtility;
import psdi.app.contract.purch.PurchView;
import psdi.app.po.PO;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;

import com.shuto.mam.util.ValidateUtil;

public class RFQAppBean extends psdi.webclient.beans.rfq.RFQAppBean {
	private Hashtable table = new Hashtable();

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		MboRemote mainMbo = getMbo();
		MboSetRemote terms = mainMbo.getMboSet("rfqterm");
		MboSetRemote stdterms = mainMbo.getMboSet("$stdterms", "term",
				"type='RFQ' and orgid='" + mainMbo.getString("orgid") + "'");

		MboRemote term = null;
		for (int i = 0; i < stdterms.count(); i++) {
			term = terms.add();
			term.setValue("termid", stdterms.getMbo(i).getString("termid"));
		}
		stdterms.close();
		return 1;
	}

	public int ROUTEWF() throws MXException, RemoteException {
		MboRemote mainMbo = getMbo();
		ValidateUtil vu = new ValidateUtil();
		vu.valideate(this.appName, getClass().getSimpleName(),
				new java.lang.Exception().getStackTrace()[0].getMethodName(),
				mainMbo);
		return super.ROUTEWF();
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote mainMbo = getMbo();
		return super.SAVE();
	}

	public void awardLowestUnitcost() throws MXException, RemoteException {
		MboRemote mainMbo = getMbo();

		MboSetRemote quotelines = mainMbo.getMboSet("quotationline");
		for (int i = 0; i < quotelines.count(); i++) {
			quotelines.getMbo(i).setValue("isawarded", false, 2L);
		}
		quotelines.save();
		quotelines.close();

		MboSetRemote rfqlines = mainMbo.getMboSet("rfqline");
		MboRemote rfqline = null;
		MboSetRemote quotations = null;
		double aaa = 0.0D;
		for (int i = 0; i < rfqlines.count(); i++) {
			rfqline = rfqlines.getMbo(i);
			String rfqlinenum = rfqline.getString("rfqlinenum");

			quotations = rfqline.getMboSet("quotationitem");
			quotations.setWhere("unitcost >0");
			quotations.setOrderBy("unitcost");

			if ((quotations.count() <= 0)
					|| (quotations.getMbo(0).getDouble("unitcost") <= 0.0D))
				continue;
			aaa = quotations.getMbo(0).getDouble("unitcost");
			quotations.getMbo(0).setValue("isawarded", true, 2L);
		}

		this.app.getAppBean().save();
		this.app.getDataBean().reloadTable();
		if (aaa == 0.0D)

			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "rfq",
					"gyswbj",null);
		else {
			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "rfq",
					"sywc", null);
		}

		// this.sessionContext.queueRefreshEvent();
	}

	public void awardLowestCost() throws MXException, RemoteException {
		MboRemote rfq = getMbo();
		MboSetRemote qlines = rfq.getMboSet("quotationline");
		for (int i = 0; i < qlines.count(); i++)
			qlines.getMbo(i).setValue("isawarded", false, 2L);
		qlines.save();
		qlines.close();
		double cost = 0.0D;
		double zero = 0.0D;
		String vendor = null;
		MboSetRemote rfqvendors = rfq.getMboSet("rfqvendor");
		for (int i = 0; i < rfqvendors.count(); i++) {
			MboRemote rfqvendor = rfqvendors.getMbo(i);
			MboSetRemote quotelines = rfqvendor
					.getMboSet("quotationlinevendor");
			if ((i == 0) && (quotelines.sum("linecost") > 0.0D)) {
				cost = quotelines.sum("linecost");
				vendor = rfqvendor.getString("vendor");
			} else if ((quotelines.sum("linecost") < cost)
					&& (quotelines.sum("linecost") > 0.0D)) {
				cost = quotelines.sum("linecost");
				vendor = rfqvendor.getString("vendor");
			}
		}

		if (cost == 0.0D) {
			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "rfq",
					"gyswbj ", null);
		}

		MboSetRemote lines = rfq.getMboSet(
				"$vendorquoteline",
				"quotationline",
				"rfqnum='" + rfq.getString("rfqnum") + "' and siteid='"
						+ rfq.getString("siteid") + "' and vendor='" + vendor
						+ "'");
		for (int j = 0; j < lines.count(); j++) {
			lines.getMbo(j).setValue("isawarded", true, 2L);
		}
		lines.save();

		this.app.getDataBean().reloadTable();
		Utility.showMessageBox(this.clientSession.getCurrentEvent(), "rfq",
				"sywc ", null);
	}

	public int DELETE() throws MXException, RemoteException {
		MboSetRemote rfqlines = getMbo().getMboSet("rfqline");
		for (int i = 0; i < rfqlines.count(); i++) {
			MboRemote line = rfqlines.getMbo(i);

			MboSetRemote prlines = line.getMboSet("prline");
			if (prlines.count() > 0) {
				for (int j = 0; j < prlines.count(); j++) {
					prlines.getMbo(j).setValueNull("rfqnum", 2L);
					prlines.getMbo(j).setValueNull("rfqlinenum", 2L);
					prlines.getMbo(j).setValueNull("rfqlineid", 2L);
					prlines.getMbo(j).setValueNull("rfqorg", 2L);
				}
			}
			prlines.save();
			line.getMboSet("quotationline").deleteAll(2L);
		}
		return super.DELETE();
	}

	private boolean canGenCont() throws RemoteException, MXException {
		MboRemote rfq = getMbo();

		MboSetRemote rfqls = rfq.getMboSet("rfqline");
		rfqls.setWhere("polinenum is null or contractnum is null");
		if (rfqls.count() == 0) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"rfq", "nolinetocreate", null);
			return false;
		}

		if (!rfq.getString("enterby").equals(rfq.getUserName())) {
			throw new MXApplicationException("rfq", "notrfqenterby");
		}
		return true;
	}

	public void createAllcont() throws MXException, RemoteException {
		this.app.getDataBean().save();
		if (!canGenCont()) {
			return;
		}

		MboRemote rfq = getMbo();
		MboSetRemote lines = rfq.getMboSet("$prlinevendor", "prlinevendor",
				"rfqorg='" + rfq.getString("orgid") + "' " + "and rfqnum='"
						+ rfq.getString("rfqnum") + "' "
						+ "and ponum is null and contractnum is null");

		System.out.println("rfqorg='" + rfq.getString("orgid") + "' "
				+ "and rfqnum='" + rfq.getString("rfqnum") + "' "
				+ "and ponum is null and contractnum is null");
		lines.setOrderBy("prorg, vendor, rfqlinenum");

		String orgid = null;
		String vendor = null;
		String okconts = "";
		MboRemote contract = null;
		MboSetRemote newContractSet = rfq.getMboSet("$newcont", "PURCHVIEW",
				"1=2");
		System.out.println("linecount()===============" + lines.count());
		for (int i = 0; i < lines.count(); i++) {
			MboRemote line = lines.getMbo(i);
			if (line.isNull("prnum")) {
				if (!line.getString("rfqline.orgid").equals(orgid)) {
					orgid = line.getString("rfqline.orgid");
					vendor = line.getString("vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				} else if (!line.getString("vendor").equals(vendor)) {
					vendor = line.getString("vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				}
				contract.setValue("createrel", true);

				MboSetRemote contlines = contract.getMboSet("contractline");
				MboRemote contline = copyRFQLineToContract(line, contlines);
				contline.setValue("S_TAXUNITCOST", line.getDouble("cost"));
				if (contline.isNull("orderunit"))
					contline.setValue("orderunit", "个");
				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(contline.getInt("contractlineid")));
			} else {
				MboSetRemote prlines = line.getMboSet("prline");
				MboRemote prline = prlines.getMbo(0);

				if (!prline.getString("orgid").equals(orgid)) {
					orgid = prline.getString("orgid");
					vendor = prline.getString("rfqline.vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				} else if (!prline.getString("rfqline.vendor").equals(vendor)) {
					vendor = prline.getString("rfqline.vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				}
				contract.setValue("createrel", true);

				MboSetRemote contlines = contract.getMboSet("contractline");
				MboRemote contline = ((PurchView) contract)
						.copyPRLineToContract(prline, contlines);

				contline.setValue("S_TAXUNITCOST", line.getDouble("cost"));
				if (contline.isNull("orderunit")) {
					contline.setValue("orderunit", "个");
				}

				MboRemote rfqline = line.getMboSet("rfqline").getMbo(0);
				MboRemote quoteline = line.getMboSet("quotationline").getMbo(0);
				contline.setValue("modelnum", rfqline.getString("modelnum"));

				if ((rfqline.getDouble("orderqty") == prline
						.getDouble("orderqty"))
						&& (quoteline.getDouble("orderqty") != rfqline
								.getDouble("orderqty"))) {
					contline.setValue("orderqty",
							quoteline.getDouble("orderqty"), 11L);
				} else if (quoteline.getDouble("orderqty") != rfqline
						.getDouble("orderqty")) {
					Utility.showMessageBox(
							this.sessionContext.getCurrentEvent(), "rfq",
							"quoteqtycont",
							new String[] { contline.getString("contractnum") });
				}
				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(contline.getInt("contractlineid")));
				contlines.save();

				prline.setValue("contractid", contract.getInt("contractid"), 2L);
				prline.setValue("contractnum",
						contract.getString("contractnum"), 2L);
				prline.setValue("contractrev", contract.getInt("revisionnum"),
						2L);
				prline.setValue("contractlinenum",
						contline.getInt("contractlinenum"), 2L);
				prline.setValue("contractlineid",
						contline.getInt("contractlineid"), 2L);
				prline.setValue("contractorg", rfq.getString("orgid"), 2L);
				prlines.save();
			}
		}
		newContractSet.save();

		if (okconts.length() == 0) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"rfq", "nocontcreated", null);
			return;
		}

		StringTokenizer st = new StringTokenizer(okconts, ",");
		while (st.hasMoreTokens()) {
			String contnum = st.nextToken();
			updateContractCost(contnum);
		}

		Iterator iter = this.table.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object rfqlineid = entry.getKey();
			Object contractlineid = entry.getValue();
			updateRFQLineForCont(((Integer) rfqlineid).intValue(),
					((Integer) contractlineid).intValue());
		}

		StringTokenizer st1 = new StringTokenizer(okconts, ",");
		while (st1.hasMoreTokens()) {
			String contnum = st1.nextToken();
			createContractTerm(contnum);
		}

		okconts = "【" + okconts.substring(0, okconts.length() - 1) + "】";
		Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "rfq",
				"createcontok", new String[] { okconts });

		this.app.getDataBean().reloadTable();
		this.sessionContext.queueRefreshEvent();
	}

	private boolean canGenPO() throws RemoteException, MXException {
		MboRemote rfq = getMbo();

		MboSetRemote rfqls = rfq.getMboSet("rfqline");
		rfqls.setWhere("polinenum is null or contractnum is null");
		if (rfqls.count() == 0) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"rfq", "nolinetocreate", null);
			return false;
		}

		if (!rfq.getString("enterby").equals(rfq.getUserName())) {
			throw new MXApplicationException("rfq", "notrfqenterby");
		}

		rfqls.reset();

		for (int i = 0; i < rfqls.count(); i++) {
			if (rfqls.getMbo(i).isNull("vendor.tax1code")) {
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
						"rfq", "nulltaxcode", null);
				return false;
			}
		}
		return true;
	}

	public void createAllpo() throws MXException, RemoteException {
		this.app.getDataBean().save();

		if (!canGenPO()) {
			return;
		}

		MboRemote rfq = getMbo();

		MboSetRemote lines = rfq.getMboSet("$prlinevendor", "prlinevendor",
				"rfqorg='" + rfq.getString("orgid") + "' " + "and rfqnum='"
						+ rfq.getString("rfqnum") + "' "
						+ "and ponum is null and contractnum is null");
		System.out.println("line=======================:rfqorg='"
				+ rfq.getString("orgid") + "' " + "and rfqnum='"
				+ rfq.getString("rfqnum") + "' "
				+ "and ponum is null and contractnum is null");
		lines.setOrderBy("prsite, vendor, rfqlinenum");

		String orgid = null;
		String siteid = null;
		String vendor = null;
		String okpos = "";
		MboRemote po = null;
		MboSetRemote newPOSet = rfq.getMboSet("$newpo", "po", "1=2");

		for (int i = 0; i < lines.count(); i++) {
			MboRemote line = lines.getMbo(i);
			if (line.isNull("prnum")) {
				if (!line.getString("rfqline.siteid").equals(siteid)) {
					siteid = line.getString("rfqline.siteid");
					orgid = line.getString("rfqline.orgid");
					vendor = line.getString("vendor");
					po = createPOHeader(newPOSet, orgid, siteid, vendor);
					okpos = okpos + po.getString("ponum") + ",";
				} else if (!line.getString("vendor").equals(vendor)) {
					vendor = line.getString("vendor");
					po = createPOHeader(newPOSet, orgid, siteid, vendor);
					okpos = okpos + po.getString("ponum") + ",";
				}

				MboSetRemote polines = po.getMboSet("poline");

				MboRemote poline = copyRFQLineToPO(line, po, polines);
				poline.setValue("tax1code", line.getString("tax1code"), 2L);

				poline.setValue("s_taxunitcost", line.getDouble("cost"), 2L);
				poline.setValue("INSPECTIONREQUIRED", "1", 2L);

				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(poline.getInt("polineid")));
			} else {
				MboSetRemote prlines = line.getMboSet("prline");
				MboRemote prline = prlines.getMbo(0);

				if (!prline.getString("siteid").equals(siteid)) {
					siteid = prline.getString("siteid");
					orgid = prline.getString("orgid");
					vendor = prline.getString("rfqline.vendor");
					po = createPOHeader(newPOSet, orgid, siteid, vendor);
					okpos = okpos + po.getString("ponum") + ",";
				} else if (!prline.getString("rfqline.vendor").equals(vendor)) {
					vendor = prline.getString("rfqline.vendor");
					po = createPOHeader(newPOSet, orgid, siteid, vendor);
					okpos = okpos + po.getString("ponum") + ",";
				}

				MboRemote pr = prline.getMboSet("pr").getMbo(0);
				MboSetRemote polines = po.getMboSet("poline");

				MboRemote poline = ((PO) po).createPOLineFromPR(pr, prline,
						polines);

				poline.setValue("tax1code", line.getString("tax1code"));

				poline.setValue("S_TAXUNITCOST", line.getDouble("cost"));
				poline.setValue("INSPECTIONREQUIRED", "1", 2L);
				MboRemote rfqline = line.getMboSet("rfqline").getMbo(0);
				MboRemote quoteline = line.getMboSet("quotationline").getMbo(0);
				poline.setValue("modelnum", rfqline.getString("modelnum"));

				if (quoteline.getDouble("orderqty") == rfqline
						.getDouble("orderqty")) {
					poline.setValue("orderqty",
							quoteline.getDouble("orderqty"), 11L);
				} else if (quoteline.getDouble("orderqty") != rfqline
						.getDouble("orderqty")) {
					Utility.showMessageBox(
							this.sessionContext.getCurrentEvent(), "rfq",
							"quoteqtypo",
							new String[] { po.getString("ponum") });
				}
				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(poline.getInt("polineid")));

				prline.setValue("poorg", rfq.getString("orgid"), 2L);
				prlines.save();
			}
		}
		newPOSet.save();

		if (okpos.length() == 0) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"rfq", "nopotocreate", null);
			return;
		}

		StringTokenizer st = new StringTokenizer(okpos, ",");
		while (st.hasMoreTokens()) {
			String ponum = st.nextToken();
			updatePOCost(ponum);
		}

		Iterator iter = this.table.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object rfqlineid = entry.getKey();
			Object polineid = entry.getValue();
			updateRFQLineForPO(((Integer) rfqlineid).intValue(),
					((Integer) polineid).intValue());
		}

		StringTokenizer st1 = new StringTokenizer(okpos, ",");
		while (st1.hasMoreTokens()) {
			String ponum = st1.nextToken();
			updatePOTerm(ponum);
		}

		okpos = "【" + okpos.substring(0, okpos.length() - 1) + "】";
		Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "rfq",
				"createpook", new String[] { okpos });

		this.app.getDataBean().reloadTable();
		this.sessionContext.queueRefreshEvent();
	}

	private void updatePOTerm(String ponum) throws RemoteException, MXException {
		MboSetRemote newpos = getMbo().getMboSet("$newpos", "po",
				"ponum='" + ponum + "'");
		if (newpos.count() == 0)
			return;
		MboRemote newpo = newpos.getMbo(0);

		MboSetRemote terms = newpo.getMboSet("$poterm", "poterm", "ponum='"
				+ newpo.getString("ponum") + "' and orgid='"
				+ getMbo().getString("orgid") + "'");
		for (int j = 0; j < terms.count(); j++) {
			terms.getMbo(j)
					.setValue(
							"seqnum",
							terms.getMbo(j).getMboSet("term").getMbo(0)
									.getInt("seqno"), 2L);
			terms.getMbo(j).setValue("orgid", newpo.getString("orgid"), 2L);
			terms.getMbo(j).setValue("siteid", newpo.getString("siteid"), 2L);
		}
		terms.save();
		terms.close();
	}

	private void updateRFQLineForPO(int rfqlineid, int polineid)
			throws RemoteException, MXException {
		MboSetRemote rfqlines = getMbo().getMboSet("$rfqline", "rfqline",
				"rfqlineid=" + Integer.valueOf(rfqlineid));
		MboRemote rfqline = rfqlines.getMbo(0);

		MboSetRemote polines = getMbo().getMboSet("$poline", "poline",
				"polineid=" + Integer.valueOf(polineid));
		MboRemote poline = polines.getMbo(0);

		rfqline.setValue("ponum", poline.getString("ponum"), 2L);
		rfqline.setValue("porevisionnum", poline.getInt("revisionnum"), 2L);
		rfqline.setValue("polinenum", poline.getInt("polinenum"), 2L);
		rfqline.setValue("polineid", poline.getInt("polineid"), 2L);
		rfqlines.save();
		rfqlines.close();
	}

	private void updatePOCost(String ponum) throws RemoteException, MXException {
		MboSetRemote newpos = getMbo().getMboSet("$newpos", "po",
				"ponum='" + ponum + "'");
		if (newpos.count() == 0)
			return;
		MboRemote newpo = newpos.getMbo(0);

		double totalcost = 0.0D;
		MboSetRemote polines = newpo.getMboSet("poline");
		for (int i = 0; i < polines.count(); i++) {
			totalcost = totalcost
					+ polines.getMbo(i).getDouble("S_TAXUNITCOST")
					* polines.getMbo(i).getDouble("orderqty");
		}
		newpo.setValue("totalcost", totalcost, 2L);
		newpos.save();
		newpos.close();
	}

	public MboRemote createPOHeader(MboSetRemote newPOSet, String orgid,
			String siteid, String vendor) throws MXException, RemoteException {
		MboRemote rfq = getMbo();

		MboSetRemote rfqvendors = rfq.getMboSet("rfqvendor");
		rfqvendors.setWhere("vendor='" + vendor + "'");
		if (rfqvendors.count() == 0)
			throw new MXApplicationException("rfq", "notfoundrfqvendor");
		MboRemote rfqvendor = rfqvendors.getMbo(0);

		MboRemote newPO = newPOSet.add();
		newPO.setValue("description", rfq.getString("description"));
		newPO.setValue("vendor", vendor);
		MboRemote company = newPO.getMboSet("vendor").getMbo(0);
		if (company != null) {
			newPO.setValue("payonreceipt", company.getString("payonreceipt"));
		}

		newPO.setValue("orgid", orgid, 2L);
		newPO.setValue("siteid", siteid, 2L);

		newPO.setValue("createorg", rfq.getString("orgid"));

		newPO.setValue("buyahead", rfqvendor.getString("buyahead"), 11L);
		if (!rfq.getString("billto").equals(""))
			newPO.setValue("billto", rfq.getString("billto"), 11L);
		if (!rfq.getString("billtoattn").equals(""))
			newPO.setValue("billtoattn", rfq.getString("billtoattn"), 11L);
		newPO.setValue("contact", rfqvendor.getString("contact"), 11L);
		newPO.setValue("currencycode", rfqvendor.getString("currencycode"), 2L);
		newPO.setValue("customernum", rfqvendor.getString("customernum"), 11L);
		if (rfqvendor.getBoolean("buyahead")) {
			newPO.setValue("exchangerate", rfqvendor.getString("exchangerate"),
					11L);
			newPO.setValue("exchangedate", rfqvendor.getString("exchangedate"),
					11L);
		}
		newPO.setValue("fob", rfqvendor.getString("fob"), 11L);
		newPO.setValue("freightterms", rfqvendor.getString("freightterms"), 11L);
		newPO.setValue("freightterms_longdescription",
				rfqvendor.getString("freightterms_longdescription"), 11L);

		TaxUtility taxUtility = TaxUtility.getTaxUtility();
		taxUtility.setTaxattrValue(newPO, "INCLUSIVE", rfqvendor, 11L);
		newPO.setValue("internal", rfqvendor.getString("internal"), 11L);
		newPO.setValue("paymentterms", rfqvendor.getString("paymentterms"), 11L);
		newPO.setValue("po1", rfq.getString("rfq1"), 2L);
		newPO.setValue("po2", rfq.getString("rfq2"), 2L);
		newPO.setValue("po3", rfq.getString("rfq3"), 2L);
		newPO.setValue("po4", rfq.getString("rfq4"), 2L);
		newPO.setValue("po5", rfq.getString("rfq5"), 2L);
		newPO.setValue("po6", rfq.getString("rfq6"), 2L);
		newPO.setValue("po7", rfq.getString("rfq7"), 2L);
		newPO.setValue("po8", rfq.getString("rfq8"), 2L);
		newPO.setValue("po9", rfq.getString("rfq9"), 2L);
		newPO.setValue("po10", rfq.getString("rfq10"), 2L);
		newPO.setValue("priority", rfq.getString("priority"), 11L);
		newPO.setValue("purchaseagent", rfq.getString("purchaseagent"), 11L);
		newPO.setValue("requireddate", rfq.getString("requireddate"), 11L);
		newPO.setValue("shipvia", rfqvendor.getString("shipvia"), 11L);
		newPO.setValue("shipto", rfq.getString("shipto"), 11L);
		newPO.setValue("shiptoattn", rfq.getString("shiptoattn"), 11L);

		return newPO;
	}

	protected MboRemote createContractHeader(MboSetRemote newContractSet,
			String orgid, String vendor) throws MXException, RemoteException {
		MboRemote rfq = getMbo();

		MboSetRemote rfqvendors = rfq.getMboSet("rfqvendor");
		rfqvendors.setWhere("vendor='" + vendor + "'");
		if (rfqvendors.count() == 0)
			throw new MXApplicationException("rfq", "notfoundrfqvendor");
		MboRemote rfqvendor = rfqvendors.getMbo(0);

		MboRemote newContract = newContractSet.add();
		newContract.setValue("description", rfq.getString("description"));
		newContract.setValue("contracttype", "采购");
		newContract.setValue("vendor", vendor);
		newContract.setValue("orgid", orgid, 2L);

		newContract.setValue("buyahead", rfqvendor.getString("buyahead"));
		newContract.setValue("contact", rfqvendor.getString("contact"));
		newContract.setValue("currencycode",
				rfqvendor.getString("currencycode"));
		newContract.setValue("customernum", rfqvendor.getString("customernum"));
		newContract.setValue("exchangerate",
				rfqvendor.getString("exchangerate"));
		newContract.setValue("exchangedate",
				rfqvendor.getString("exchangedate"));
		newContract.setValue("fob", rfqvendor.getString("fob"));
		newContract.setValue("freightterms",
				rfqvendor.getString("freightterms"));

		TaxUtility taxUtility = TaxUtility.getTaxUtility();
		taxUtility.setTaxattrValue(newContract, "INCLUSIVE", rfqvendor);
		newContract.setValue("paymentterms",
				rfqvendor.getString("paymentterms"));
		newContract.setValue("purchaseagent", rfq.getString("purchaseagent"));
		newContract.setValue("shipvia", rfqvendor.getString("shipvia"));

		createContractAuth(newContract);

		return newContract;
	}

	public void createContractAuth(MboRemote newContract) throws MXException,
			RemoteException {
		MboSetRemote auths = newContract.getMboSet("contractauth");
		MboRemote auth = auths.add();
		auth.setValue("authorgid", newContract.getString("orgid"), 2L);
		auth.setValue("authsiteid", newContract.getMboSet("orgsite").getMbo(0)
				.getString("siteid"), 2L);
		auth.setValue("vendor", newContract.getString("vendor"), 2L);
	}

	public MboRemote copyRFQLineToPO(MboRemote fromViewLine, MboRemote po,
			MboSetRemote poLines) throws MXException, RemoteException {
		MboRemote rfqline = fromViewLine.getMboSet("rfqline").getMbo(0);
		MboRemote quoteline = fromViewLine.getMboSet("quotationline").getMbo(0);

		MboRemote toPOLine = poLines.add();
		toPOLine.setValue("issue", rfqline.getString("issue"), 2L);
		toPOLine.setValue("inspectionrequired",
				rfqline.getString("inspectionrequired"), 11L);
		toPOLine.setValue("receiptreqd", rfqline.getString("receiptreqd"), 11L);
		toPOLine.setValue("gldebitacct", rfqline.getString("gldebitacct"), 2L);

		toPOLine.setValue("linetype", rfqline.getString("linetype"), 2L);
		toPOLine.setValue("itemnum", rfqline.getString("itemnum"), 11L);
		toPOLine.setValue("description", rfqline.getString("description"), 11L);
		toPOLine.setValue("description_longdescription",
				rfqline.getString("description_longdescription"), 11L);
		toPOLine.setValue("modelnum", rfqline.getString("modelnum"), 11L);
		toPOLine.setValue("orderunit", rfqline.getString("orderunit"), 11L);
		System.out.println("quoteline.======"
				+ quoteline.getDouble("s_taxunitcost"));

		toPOLine.setValue("orderqty", quoteline.getDouble("orderqty"), 11L);

		toPOLine.setValue("itemsetid", rfqline.getString("itemsetid"), 11L);
		toPOLine.setValue("category", rfqline.getString("category"), 11L);
		toPOLine.setValue("chargestore", rfqline.getString("chargestore"), 11L);
		toPOLine.setValue("commodity", rfqline.getString("commodity"), 11L);
		toPOLine.setValue("commoditygroup",
				rfqline.getString("commoditygroup"), 11L);
		toPOLine.setValue("conditioncode", rfqline.getString("conditioncode"),
				11L);
		toPOLine.setValue("assetnum", rfqline.getString("assetnum"), 11L);
		toPOLine.setValue("fincntrlid", rfqline.getString("fincntrlid"), 11L);
		toPOLine.setValue("classstructureid",
				rfqline.getString("classstructureid"), 11L);
		toPOLine.setValue("conversion", rfqline.getString("conversion"), 3L);
		toPOLine.setValue("location", rfqline.getString("location"), 11L);
		toPOLine.setValue("manufacturer", rfqline.getString("manufacturer"),
				11L);
		toPOLine.setValue("proratecost", 0.0D, 11L);
		toPOLine.setValue("prorateservice",
				rfqline.getString("prorateservice"), 11L);
		toPOLine.setValue("receiptscomplete", false, 11L);
		if (toPOLine.getDouble("receivedqty") == 0.0D)
			toPOLine.setValueNull("receivedqty", 11L);
		toPOLine.setValue("receivedtotalcost", 0.0D, 11L);
		toPOLine.setValue("receivedunitcost", 0.0D, 11L);
		toPOLine.setValue("rejectedqty", 0.0D, 11L);
		toPOLine.setValue("remark", rfqline.getString("remark"), 11L);
		toPOLine.setValue("remark_longdescription",
				rfqline.getString("remark_longdescription"), 11L);
		toPOLine.setValue("reqdeliverydate",
				rfqline.getDate("reqdeliverydate"), 11L);
		toPOLine.setValue("requestedby", rfqline.getString("requestedby"), 11L);
		toPOLine.setValue("storeloc", rfqline.getString("storeloc"), 2L);
		toPOLine.setValue("supervisor", rfqline.getString("supervisor"), 11L);
		toPOLine.setValue("refwo", rfqline.getString("refwo"), 11L);
		toPOLine.setValue("enteredastask", rfqline.getBoolean("enteredastask"),
				11L);
		toPOLine.setValue("tositeid", rfqline.getString("siteid"), 11L);
		return toPOLine;
	}

	private MboRemote copyRFQLineToContract(MboRemote fromViewLine,
			MboSetRemote toContractLines) throws MXException, RemoteException {
		MboRemote rfqline = fromViewLine.getMboSet("rfqline").getMbo(0);
		MboRemote quoteline = fromViewLine.getMboSet("quotationline").getMbo(0);

		MboRemote toContractLine = toContractLines.add();
		toContractLine.setValue("commodity", rfqline.getString("commodity"),
				11L);
		toContractLine.setValue("commoditygroup",
				rfqline.getString("commoditygroup"), 11L);
		toContractLine.setValue("conditioncode",
				rfqline.getString("conditioncode"), 11L);
		toContractLine.setValue("inspectionrequired",
				rfqline.getString("inspectionrequired"), 11L);

		toContractLine.setValue("itemnum", rfqline.getString("itemnum"), 11L);
		toContractLine.setValue("itemsetid", rfqline.getString("itemsetid"),
				11L);
		if (toContractLine.getString("linetype").equals(
				rfqline.getString("linetype"))) {
			toContractLine.setValue("linetype", rfqline.getString("linetype"),
					11L);
		} else {
			toContractLine.setValue("linetype", rfqline.getString("linetype"),
					3L);
			toContractLine.setValue("description",
					rfqline.getString("description"), 11L);
		}
		toContractLine.setValue("description_longdescription",
				rfqline.getString("description_longdescription"), 11L);
		toContractLine.setValue("modelnum", rfqline.getString("modelnum"), 11L);
		toContractLine.setValue("manufacturer",
				rfqline.getString("manufacturer"), 11L);
		toContractLine.setValue("orderunit", rfqline.getString("orderunit"),
				11L);
		toContractLine.setValue("remark", rfqline.getString("remark"), 11L);
		toContractLine.setValue("S_TAXUNITCOST",
				quoteline.getDouble("S_TAXUNITCOST"), 11L);
		toContractLine.setValue("orderqty", quoteline.getDouble("orderqty"),
				11L);

		toContractLine.setValue("tax1code", quoteline.getString("tax1code"),
				11L);
		return toContractLine;
	}

	private void updateContractCost(String contnum) throws RemoteException,
			MXException {
		MboSetRemote newconts = getMbo().getMboSet("$newconts", "contract",
				"contractnum='" + contnum + "'");
		if (newconts.count() == 0)
			return;
		MboRemote newcont = newconts.getMbo(0);

		double totalcost = 0.0D;
		MboSetRemote contlines = newcont.getMboSet("contractline");
		for (int i = 0; i < contlines.count(); i++)
			totalcost += contlines.getMbo(i).getDouble("S_TAXUNITCOST")
					* contlines.getMbo(i).getDouble("orderqty");
		newcont.setValue("totalcost", totalcost, 2L);
		newconts.save();
	}

	private void updateRFQLineForCont(int rfqlineid, int contractlineid)
			throws RemoteException, MXException {
		MboSetRemote rfqlines = getMbo().getMboSet("$rfqline", "rfqline",
				"rfqlineid=" + rfqlineid);
		MboRemote rfqline = rfqlines.getMbo(0);

		MboSetRemote contractlines = getMbo().getMboSet("$contractline",
				"contractline", "contractlineid=" + contractlineid);
		MboRemote contractline = contractlines.getMbo(0);
		MboSetRemote contracts = contractline.getMboSet("contract");

		rfqline.setValue("contractid",
				contracts.getMbo(0).getString("contractid"), 2L);
		rfqline.setValue("contractrev",
				contracts.getMbo(0).getInt("revisionnum"), 2L);
		rfqline.setValue("contractnum", contractline.getString("contractnum"),
				2L);
		rfqline.setValue("contractlinenum",
				contractline.getInt("contractlinenum"), 2L);
		rfqline.setValue("contractlineid",
				contractline.getInt("contractlineid"), 2L);
		rfqlines.save();
	}

	private void createContractTerm(String contnum) throws RemoteException,
			MXException {
		MboSetRemote newconts = getMbo().getMboSet("$newconts", "purchview",
				"contractnum='" + contnum + "'");
		if (newconts.count() == 0) {
			return;
		}
		MboRemote cont = newconts.getMbo(0);
		MboSetRemote stdterms = cont.getMboSet("$stdterms", "term", "");
		stdterms.setWhere("type='CONT' and orgid='" + cont.getString("orgid")
				+ "'");

		stdterms.setOrderBy("seqno");

		MboSetRemote terms = cont.getMboSet("contractterm");
		for (int i = 0; i < stdterms.count(); i++) {
			MboRemote term = terms.add();
			term.setValue("termid", stdterms.getMbo(i).getString("termid"));
		}
		newconts.save();
	}

	public void createAllMainCont() throws MXException, RemoteException {
		this.app.getDataBean().save();

		if (!canGenCont()) {
			return;
		}

		MboRemote rfq = getMbo();
		MboSetRemote lines = rfq.getMboSet("$prlinevendor", "prlinevendor",
				"rfqorg='" + rfq.getString("orgid") + "' " + "and rfqnum='"
						+ rfq.getString("rfqnum") + "' "
						+ "and ponum is null and contractnum is null");
		lines.setOrderBy("prorg, vendor, rfqlinenum");
		System.out.println("1prlinevendor---lines=========== =   "
				+ lines.count());
		String orgid = null;
		String vendor = null;
		String okconts = "";
		MboRemote contract = null;
		MboSetRemote newContractSet = rfq.getMboSet("$newcont", "PURCHVIEW",
				"1=2");

		for (int i = 0; i < lines.count();) {
			MboRemote line = lines.getMbo(i);
			if (line.isNull("prnum")) {
				if (!line.getString("rfqline.orgid").equals(orgid)) {
					orgid = line.getString("rfqline.orgid");
					vendor = line.getString("vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				} else if (!line.getString("vendor").equals(vendor)) {
					vendor = line.getString("vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				}
				contract.setValue("createrel", true);

				MboSetRemote contlines = contract.getMboSet("contractline");
				MboRemote contline = copyRFQLineToContract(line, contlines);
				contline.setValue("S_TAXUNITCOST", line.getDouble("cost"));
				if (contline.isNull("orderunit"))
					contline.setValue("orderunit", "个");
				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(contline.getInt("contractlineid")));
			} else {
				MboSetRemote prlines = line.getMboSet("prline");
				MboRemote prline = prlines.getMbo(0);

				if (!prline.getString("orgid").equals(orgid)) {
					orgid = prline.getString("orgid");
					vendor = prline.getString("rfqline.vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				} else if (!prline.getString("rfqline.vendor").equals(vendor)) {
					vendor = prline.getString("rfqline.vendor");
					contract = createContractHeader(newContractSet, orgid,
							vendor);
					okconts = okconts + contract.getString("contractnum") + ",";
				}
				contract.setValue("createrel", true);

				MboSetRemote contlines = contract.getMboSet("contractline");
				MboRemote contline = ((PurchView) contract)
						.copyPRLineToContract(prline, contlines);

				contline.setValue("S_TAXUNITCOST", line.getDouble("cost"));
				if (contline.isNull("orderunit")) {
					contline.setValue("orderunit", "个");
				}

				MboRemote rfqline = line.getMboSet("rfqline").getMbo(0);
				MboRemote quoteline = line.getMboSet("quotationline").getMbo(0);
				contline.setValue("modelnum", rfqline.getString("modelnum"));

				if ((rfqline.getDouble("orderqty") == prline
						.getDouble("orderqty"))
						&& (quoteline.getDouble("orderqty") != rfqline
								.getDouble("orderqty"))) {
					contline.setValue("orderqty",
							quoteline.getDouble("orderqty"), 11L);
				} else if (quoteline.getDouble("orderqty") != rfqline
						.getDouble("orderqty")) {
					Utility.showMessageBox(
							this.sessionContext.getCurrentEvent(), "rfq",
							"quoteqtycont",
							new String[] { contline.getString("contractnum") });
				}
				this.table.put(Integer.valueOf(line.getInt("rfqlineid")),
						Integer.valueOf(contline.getInt("contractlineid")));
				contlines.save();

				prline.setValue("contractid", contract.getInt("contractid"), 2L);
				prline.setValue("contractnum",
						contract.getString("contractnum"), 2L);
				prline.setValue("contractrev", contract.getInt("revisionnum"),
						2L);
				prline.setValue("contractlinenum",
						contline.getInt("contractlinenum"), 2L);
				prline.setValue("contractlineid",
						contline.getInt("contractlineid"), 2L);
				prline.setValue("contractorg", rfq.getString("orgid"), 2L);
				prlines.save();
			}
		}
		newContractSet.save();

		if (okconts.length() == 0) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(),
					"rfq", "nocontcreated", null);
			return;
		}

		StringTokenizer st = new StringTokenizer(okconts, ",");
		while (st.hasMoreTokens()) {
			String contnum = st.nextToken();
			updateContractCost(contnum);
		}

		Iterator iter = this.table.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object rfqlineid = entry.getKey();
			Object contractlineid = entry.getValue();
			updateRFQLineForCont(((Integer) rfqlineid).intValue(),
					((Integer) contractlineid).intValue());
		}

		StringTokenizer st1 = new StringTokenizer(okconts, ",");
		while (st1.hasMoreTokens()) {
			String contnum = st1.nextToken();
			createContractTerm(contnum);
		}

		okconts = "【" + okconts.substring(0, okconts.length() - 1) + "】";
		Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "rfq",
				"createcontok", new String[] { okconts });

		this.app.getDataBean().reloadTable();
		this.sessionContext.queueRefreshEvent();
	}
}