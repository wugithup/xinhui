package psdi.face.app.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import psdi.face.app.todo.dto.OaTodoDto;
import psdi.face.app.todo.dto.OaTodoListDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnetOaTodoUtil {
    public OaTodoDto getOaTodo(String username, String password) throws IOException, DocumentException {
        String strURL = "http://xhpoa.gdyd.com/names.nsf?login&redirectto=/todo/checkusertodo.nsf/suPrintTodoXML?openagent&username=" + username + "&password=" + password + "";
        URL url;
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        Document doc;
        OaTodoDto oadto = new OaTodoDto();
        List<OaTodoListDTO> list = new ArrayList<>();
        url = new URL(strURL);
        URLConnection httpConn = url.openConnection();
        httpConn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpConn.getInputStream()));
        String line;
        StringBuilder buffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        String xml = buffer.toString();
        // 将字符串转为XML
        doc = DocumentHelper.parseText(xml);
        // 获取根节点
        Element rootElt = doc.getRootElement();

        oadto.setRecoardcount(rootElt.elementText("recoardcount"));
        oadto.setPagecount(rootElt.elementText("pagecount"));
        oadto.setPageindexs(rootElt.elementText("pageindexs"));

        Iterator iter = rootElt.elementIterator("Row");
        while (iter.hasNext()) {
            OaTodoListDTO oaList = new OaTodoListDTO();
            Element recordEle = (Element) iter.next();
            String suid = recordEle.elementTextTrim("SUID");
            oaList.setSuid(suid);
            String jinji = recordEle.elementTextTrim("jinji");
            oaList.setJinji(jinji);
            String bz = recordEle.elementTextTrim("BZ");
            oaList.setBz(bz);
            String sdate = recordEle.elementTextTrim("SDATE");
            oaList.setSdate(sdate);
            String flmc = recordEle.elementTextTrim("FLMC");
            oaList.setFlmc(flmc);
            String strUrl = recordEle.elementTextTrim("URL");
            oaList.setUrl(strUrl);
            String strfrom = recordEle.elementTextTrim("strfrom");
            oaList.setStrfrom(strfrom);
            String state = recordEle.elementTextTrim("state");
            oaList.setState(state);
            String fldmodule = recordEle.elementTextTrim("fldmodule");
            oaList.setFldmodule(fldmodule);
            list.add(oaList);
        }
        oadto.setTodoList(list);
        return oadto;
    }
}
