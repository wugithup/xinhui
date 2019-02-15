/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.safety;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class YhImpDataBean extends DataBean {

    private static String nullStrToEmpty(Object str) {

        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {

        app.getAppBean().save();
        MboRemote mainMbo = app.getAppBean().getMbo();

        // 得到上传的文件
        UploadFile file = (UploadFile) (this.app.get("importfile"));
        // 得到文件名
        String fileName = file.getFileName();
        if (fileName.endsWith(".xlsx")) {
            ByteArrayOutputStream os = file.getFileOutputStream();
            try {
                InputStream stream = new ByteArrayInputStream(os.toByteArray());
                // 获取Excel文件对象
                Workbook wb = new XSSFWorkbook(stream);
                // 获取文件的指定工作表 默认的第一个
                XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);

                // 导入数据
                impContent(sheet, mainMbo.getString("safejctbnum"));

                // 导入完成后刷新
                app.getDataBean("SMAWLINE2").reloadTable();

            } catch (Exception e) {
                e.printStackTrace();
                throw new MXApplicationException("文件读取错误", "");
            }
        } else {
            throw new MXApplicationException("文件类型错误,只允许导入2007格式的Excel文件", "");
        }
        return super.execute();
    }

    private void impContent(Sheet sheet, String safejctbnum) throws Exception {

        MXServer mxserver = MXServer.getMXServer();
        String url = mxserver.getProperty("mxe.db.url");
        String username = mxserver.getProperty("mxe.db.user");
        String password = mxserver.getProperty("mxe.db.password");
        // 创建数据库链接
        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        try {
            // 行数(表头的目录不需要，从1开始)
            for (int x = 1; x <= sheet.getLastRowNum(); x++) {

                Row row = sheet.getRow(x);
                // 得到部门/单位
                String deptDes = nullStrToEmpty(row.getCell(0));
                // 得到隐患类别
                String yhlb = nullStrToEmpty(row.getCell(1));
                // 得到专业
                String zhuanye = nullStrToEmpty(row.getCell(2));
                // 得到要求日期
                String dateStr = new SimpleDateFormat("yyyy-MM-dd")
                        .format(row.getCell(3).getDateCellValue());

                // 得到整改项目
                String robject = nullStrToEmpty(row.getCell(4));
                // 得到整改内容
                String zgnr = nullStrToEmpty(row.getCell(5));
                // 得到整改要求
                String zgyq = nullStrToEmpty(row.getCell(6));

                String sql =
                        "insert into smawline(smawlineid,sn,applyid,app,hasld,description,yhlb ,zhuanye,REQUESTDATE,robject,zgnr,zgyq,applydept )  " +
                                " select smawlineidseq.nextval , " + x + " ,'" + safejctbnum +
                                "','SAFEYHZGX' ,0,'" + deptDes + "','" + yhlb + "','" + zhuanye +
                                "',to_date('" + dateStr + "','yyyy-mm-dd'),'" + robject + "','" +
                                zgnr + "','" + zgyq + "'," +
                                "(select depnum from department where description='" + deptDes +
                                "' and rownum  =1 ) from dual  ";
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        }
    }
}
