package com.shuto.mam.app.expand;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
*com.shuto.mam.app.expand.StringExpand
* @author 蒋カイセキ Japan-Tokyo 2013-9-5
* <P>ブログ http://shoukaiseki.blog.163.com/
* <P>E-メール jiang28555@Gmail.com
*/
public class StringExpand {
	
	/**
	 * @param strs 数组,不包含null元素
	 * @param str  可以为null,为null时有空字符串代替
	 * @return 如果该数组有该字符串则返回true
	 */
	public static boolean binarySearch(String[] strs,String str){
		Arrays.sort(strs);
		return Arrays.binarySearch(strs, str==null?"":str)>=0;
	}

}
