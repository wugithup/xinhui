package com.shuto.mam.webclient.beans.doclinks;

public class ASEntryDoc {
	public  String docId;
	public  String docName;
	public  String docType;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		result = prime * result + ((docName == null) ? 0 : docName.hashCode());
		result = prime * result + ((docType == null) ? 0 : docType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ASEntryDoc other = (ASEntryDoc) obj;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		if (docName == null) {
			if (other.docName != null)
				return false;
		} else if (!docName.equals(other.docName))
			return false;
		if (docType == null) {
			if (other.docType != null)
				return false;
		} else if (!docType.equals(other.docType))
			return false;
		return true;
	}
}
