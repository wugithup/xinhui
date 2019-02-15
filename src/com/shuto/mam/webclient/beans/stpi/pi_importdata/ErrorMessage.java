package com.shuto.mam.webclient.beans.stpi.pi_importdata;


public class ErrorMessage {
	private static ErrorMessage errorMessage = null;
//	private static Map<String, String> errorMessageMap = new HashMap<String, String>();

//	public int pushErrorMessage(String messageId, String errorMessage) {
//		if (errorMessageMap.containsKey(messageId)) {
//			errorMessageMap.put(messageId, errorMessageMap.get(messageId)
//					+ errorMessage);
//		}else{
//			errorMessageMap.put(messageId, errorMessage);
//		}
//		return 1;
//	}
//
//	public int clearErrorMessage(String messageId) {
//		errorMessageMap.remove(messageId);
//		return 1;
//	}
//	
//	public String getErrorMessage(String messageId) {
//		return errorMessageMap.get(messageId);
//	}
	private ErrorMessage() {

	}

	// private static class getErrorMessage{
	// private final static ErrorMessage instance=new ErrorMessage();
	// }
	public static ErrorMessage getInstance() {
		if (errorMessage == null) {
			errorMessage = new ErrorMessage();
		}
		return errorMessage;
	}

	private StringBuffer resultAll = new StringBuffer("");

	public void setResult(String result) {
		resultAll.append(result);
	}

	public String getResult() {
		return resultAll.toString();
	}
	
	public void setEmpty() {
		resultAll = new StringBuffer("");
	}

}