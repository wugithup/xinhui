package psdi.face.app.util;

import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-10-29 15:31
 * @desc
 * @class psdi.face.app.util.ConnUrlInterfaceUtil
 * @Copyright: 2018 Shuto版权所有
 **/

public class ConnUrlInterfaceUtil {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("rest.application");

    public String returnStr(String urlPath) {

        String returnStr = "";
        URL url;
        try {
            url = new URL(urlPath);
            URLConnection httpConn = url.openConnection();
            httpConn.connect();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            returnStr = buffer.toString();
        } catch (IOException e) {
            MX_LOGGER.error(e);
        }
        return returnStr;
    }
}
