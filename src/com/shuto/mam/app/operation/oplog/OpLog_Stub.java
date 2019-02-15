/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.operation.oplog;

import psdi.mbo.KeyValue;
import psdi.mbo.MaxMessage;
import psdi.mbo.Mbo;
import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValueData;
import psdi.mbo.MboValueInfoStatic;
import psdi.security.UserInfo;
import psdi.txn.MXTransaction;
import psdi.util.ApplicationError;
import psdi.util.MXException;
import psdi.util.MaxType;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

public final class OpLog_Stub extends RemoteStub implements OpLogRemote, MboRemote {
	private static final long serialVersionUID = 2L;
	private static Method $method_add_0;
	private static Method $method_addMboSetForRequiredCheck_1;
	private static Method $method_addToDeleteForInsertList_2;
	private static Method $method_blindCopy_3;
	private static Method $method_checkMethodAccess_4;
	private static Method $method_clear_5;
	private static Method $method_copy_6;
	private static Method $method_copy_7;
	private static Method $method_copy_8;
	private static Method $method_copyFake_9;
	private static Method $method_copyValue_10;
	private static Method $method_copyValue_11;
	private static Method $method_createComm_12;
	private static Method $method_delete_13;
	private static Method $method_delete_14;
	private static Method $method_duplicate_15;
	private static Method $method_evaluateCondition_16;
	private static Method $method_evaluateCtrlConditions_17;
	private static Method $method_evaluateCtrlConditions_18;
	private static Method $method_excludeObjectForPropagate_19;
	private static Method $method_generateAutoKey_20;
	private static Method $method_getBoolean_21;
	private static Method $method_getByte_22;
	private static Method $method_getBytes_23;
	private static Method $method_getCommLogOwnerNameAndUniqueId_24;
	private static Method $method_getDatabaseValue_25;
	private static Method $method_getDate_26;
	private static Method $method_getDeleteForInsertList_27;
	private static Method $method_getDocLinksCount_28;
	private static Method $method_getDomainIDs_29;
	private static Method $method_getDouble_30;
	private static Method $method_getExistingMboSet_31;
	private static Method $method_getFlags_32;
	private static Method $method_getFloat_33;
	private static Method $method_getInitialValue_34;
	private static Method $method_getInsertCompanySetId_35;
	private static Method $method_getInsertItemSetId_36;
	private static Method $method_getInsertOrganization_37;
	private static Method $method_getInsertSite_38;
	private static Method $method_getInt_39;
	private static Method $method_getKeyValue_40;
	private static Method $method_getLinesRelationship_41;
	private static Method $method_getList_42;
	private static Method $method_getLong_43;
	private static Method $method_getMXTransaction_44;
	private static Method $method_getMatchingAttr_45;
	private static Method $method_getMatchingAttr_46;
	private static Method $method_getMatchingAttrs_47;
	private static Method $method_getMaxMessage_48;
	private static Method $method_getMboData_49;
	private static Method $method_getMboDataSet_50;
	private static Method $method_getMboInitialValue_51;
	private static Method $method_getMboList_52;
	private static Method $method_getMboSet_53;
	private static Method $method_getMboSet_54;
	private static Method $method_getMboSet_55;
	private static Method $method_getMboValueData_56;
	private static Method $method_getMboValueData_57;
	private static Method $method_getMboValueData_58;
	private static Method $method_getMboValueInfoStatic_59;
	private static Method $method_getMboValueInfoStatic_60;
	private static Method $method_getMessage_61;
	private static Method $method_getMessage_62;
	private static Method $method_getMessage_63;
	private static Method $method_getMessage_64;
	private static Method $method_getName_65;
	private static Method $method_getOrgForGL_66;
	private static Method $method_getOrgSiteForMaxvar_67;
	private static Method $method_getOwner_68;
	private static Method $method_getPropagateKeyFlag_69;
	private static Method $method_getRecordIdentifer_70;
	private static Method $method_getSiteOrg_71;
	private static Method $method_getString_72;
	private static Method $method_getString_73;
	private static Method $method_getStringInBaseLanguage_74;
	private static Method $method_getStringInSpecificLocale_75;
	private static Method $method_getStringTransparent_76;
	private static Method $method_getThisMboSet_77;
	private static Method $method_getUniqueIDName_78;
	private static Method $method_getUniqueIDValue_79;
	private static Method $method_getUserInfo_80;
	private static Method $method_getUserName_81;
	private static Method $method_hasHierarchyLink_82;
	private static Method $method_isAutoKeyed_83;
	private static Method $method_isBasedOn_84;
	private static Method $method_isFlagSet_85;
	private static Method $method_isForDM_86;
	private static Method $method_isModified_87;
	private static Method $method_isModified_88;
	private static Method $method_isNew_89;
	private static Method $method_isNull_90;
	private static Method $method_isSelected_91;
	private static Method $method_isZombie_92;
	private static Method $method_propagateKeyValue_93;
	private static Method $method_rollbackToCheckpoint_94;
	private static Method $method_select_95;
	private static Method $method_setApplicationError_96;
	private static Method $method_setApplicationRequired_97;
	private static Method $method_setCopyDefaults_98;
	private static Method $method_setDeleted_99;
	private static Method $method_setESigFieldModified_100;
	private static Method $method_setFieldFlag_101;
	private static Method $method_setFieldFlag_102;
	private static Method $method_setFieldFlag_103;
	private static Method $method_setFieldFlag_104;
	private static Method $method_setFieldFlag_105;
	private static Method $method_setFieldFlag_106;
	private static Method $method_setFieldFlags_107;
	private static Method $method_setFlag_108;
	private static Method $method_setFlag_109;
	private static Method $method_setFlags_110;
	private static Method $method_setForDM_111;
	private static Method $method_setMLValue_112;
	private static Method $method_setModified_113;
	private static Method $method_setNewMbo_114;
	private static Method $method_setPropagateKeyFlag_115;
	private static Method $method_setPropagateKeyFlag_116;
	private static Method $method_setReferencedMbo_117;
	private static Method $method_setValue_118;
	private static Method $method_setValue_119;
	private static Method $method_setValue_120;
	private static Method $method_setValue_121;
	private static Method $method_setValue_122;
	private static Method $method_setValue_123;
	private static Method $method_setValue_124;
	private static Method $method_setValue_125;
	private static Method $method_setValue_126;
	private static Method $method_setValue_127;
	private static Method $method_setValue_128;
	private static Method $method_setValue_129;
	private static Method $method_setValue_130;
	private static Method $method_setValue_131;
	private static Method $method_setValue_132;
	private static Method $method_setValue_133;
	private static Method $method_setValue_134;
	private static Method $method_setValue_135;
	private static Method $method_setValue_136;
	private static Method $method_setValue_137;
	private static Method $method_setValue_138;
	private static Method $method_setValue_139;
	private static Method $method_setValue_140;
	private static Method $method_setValueNull_141;
	private static Method $method_setValueNull_142;
	private static Method $method_sigOptionAccessAuthorized_143;
	private static Method $method_sigopGranted_144;
	private static Method $method_sigopGranted_145;
	private static Method $method_sigopGranted_146;
	private static Method $method_smartFill_147;
	private static Method $method_smartFind_148;
	private static Method $method_smartFind_149;
	private static Method $method_smartFindByObjectName_150;
	private static Method $method_smartFindByObjectName_151;
	private static Method $method_smartFindByObjectNameDirect_152;
	private static Method $method_startCheckpoint_153;
	private static Method $method_thisToBeUpdated_154;
	private static Method $method_toBeAdded_155;
	private static Method $method_toBeDeleted_156;
	private static Method $method_toBeSaved_157;
	private static Method $method_toBeUpdated_158;
	private static Method $method_toBeValidated_159;
	private static Method $method_undelete_160;
	private static Method $method_unselect_161;
	private static Method $method_validate_162;
	private static Method $method_validateAttributes_163;
	static Class array$Ljava$lang$String;
	static Class array$Ljava$lang$Object;
	static Class array$B;
	static Class array$$Ljava$lang$String;

	// ERROR //
	static {
		// Byte code:
		// 0: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3: ifnull +9 -> 12
		// 6: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9: goto +12 -> 21
		// 12: ldc 91
		// 14: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 17: dup
		// 18: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 21: ldc 5
		// 23: iconst_0
		// 24: anewarray 143 java/lang/Class
		// 27: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 30: putstatic 181 com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_add_0
		// Ljava/lang/reflect/Method;
		// 33: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 36: ifnull +9 -> 45
		// 39: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 42: goto +12 -> 54
		// 45: ldc 91
		// 47: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 50: dup
		// 51: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 54: ldc 6
		// 56: iconst_1
		// 57: anewarray 143 java/lang/Class
		// 60: dup
		// 61: iconst_0
		// 62: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 65: ifnull +9 -> 74
		// 68: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 71: goto +12 -> 83
		// 74: ldc 92
		// 76: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 79: dup
		// 80: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 83: aastore
		// 84: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 87: putstatic 179
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_addMboSetForRequiredCheck_1
		// Ljava/lang/reflect/Method;
		// 90: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 93: ifnull +9 -> 102
		// 96: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 99: goto +12 -> 111
		// 102: ldc 91
		// 104: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 107: dup
		// 108: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 111: ldc 7
		// 113: iconst_1
		// 114: anewarray 143 java/lang/Class
		// 117: dup
		// 118: iconst_0
		// 119: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 122: ifnull +9 -> 131
		// 125: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 128: goto +12 -> 140
		// 131: ldc 83
		// 133: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 136: dup
		// 137: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 140: aastore
		// 141: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 144: putstatic 180
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_addToDeleteForInsertList_2
		// Ljava/lang/reflect/Method;
		// 147: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 150: ifnull +9 -> 159
		// 153: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 156: goto +12 -> 168
		// 159: ldc 91
		// 161: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 164: dup
		// 165: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 168: ldc 8
		// 170: iconst_1
		// 171: anewarray 143 java/lang/Class
		// 174: dup
		// 175: iconst_0
		// 176: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 179: ifnull +9 -> 188
		// 182: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 185: goto +12 -> 197
		// 188: ldc 92
		// 190: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 193: dup
		// 194: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 197: aastore
		// 198: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 201: putstatic 182
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_blindCopy_3
		// Ljava/lang/reflect/Method;
		// 204: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 207: ifnull +9 -> 216
		// 210: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 213: goto +12 -> 225
		// 216: ldc 91
		// 218: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 221: dup
		// 222: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 225: ldc 9
		// 227: iconst_1
		// 228: anewarray 143 java/lang/Class
		// 231: dup
		// 232: iconst_0
		// 233: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 236: ifnull +9 -> 245
		// 239: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 242: goto +12 -> 254
		// 245: ldc 83
		// 247: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 250: dup
		// 251: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 254: aastore
		// 255: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 258: putstatic 183
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_checkMethodAccess_4
		// Ljava/lang/reflect/Method;
		// 261: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 264: ifnull +9 -> 273
		// 267: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 270: goto +12 -> 282
		// 273: ldc 91
		// 275: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 278: dup
		// 279: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 282: ldc 10
		// 284: iconst_0
		// 285: anewarray 143 java/lang/Class
		// 288: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 291: putstatic 184
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_clear_5
		// Ljava/lang/reflect/Method;
		// 294: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 297: ifnull +9 -> 306
		// 300: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 303: goto +12 -> 315
		// 306: ldc 91
		// 308: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 311: dup
		// 312: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 315: ldc 11
		// 317: iconst_0
		// 318: anewarray 143 java/lang/Class
		// 321: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 324: putstatic 188
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copy_6
		// Ljava/lang/reflect/Method;
		// 327: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 330: ifnull +9 -> 339
		// 333: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 336: goto +12 -> 348
		// 339: ldc 91
		// 341: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 344: dup
		// 345: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 348: ldc 11
		// 350: iconst_1
		// 351: anewarray 143 java/lang/Class
		// 354: dup
		// 355: iconst_0
		// 356: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 359: ifnull +9 -> 368
		// 362: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 365: goto +12 -> 377
		// 368: ldc 92
		// 370: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 373: dup
		// 374: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 377: aastore
		// 378: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 381: putstatic 189
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copy_7
		// Ljava/lang/reflect/Method;
		// 384: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 387: ifnull +9 -> 396
		// 390: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 393: goto +12 -> 405
		// 396: ldc 91
		// 398: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 401: dup
		// 402: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 405: ldc 11
		// 407: iconst_2
		// 408: anewarray 143 java/lang/Class
		// 411: dup
		// 412: iconst_0
		// 413: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 416: ifnull +9 -> 425
		// 419: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 422: goto +12 -> 434
		// 425: ldc 92
		// 427: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 430: dup
		// 431: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 434: aastore
		// 435: dup
		// 436: iconst_1
		// 437: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 440: aastore
		// 441: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 444: putstatic 190
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copy_8
		// Ljava/lang/reflect/Method;
		// 447: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 450: ifnull +9 -> 459
		// 453: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 456: goto +12 -> 468
		// 459: ldc 91
		// 461: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 464: dup
		// 465: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 468: ldc 12
		// 470: iconst_1
		// 471: anewarray 143 java/lang/Class
		// 474: dup
		// 475: iconst_0
		// 476: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 479: ifnull +9 -> 488
		// 482: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 485: goto +12 -> 497
		// 488: ldc 92
		// 490: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 493: dup
		// 494: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 497: aastore
		// 498: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 501: putstatic 185
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copyFake_9
		// Ljava/lang/reflect/Method;
		// 504: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 507: ifnull +9 -> 516
		// 510: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 513: goto +12 -> 525
		// 516: ldc 91
		// 518: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 521: dup
		// 522: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 525: ldc 13
		// 527: iconst_4
		// 528: anewarray 143 java/lang/Class
		// 531: dup
		// 532: iconst_0
		// 533: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 536: ifnull +9 -> 545
		// 539: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 542: goto +12 -> 554
		// 545: ldc 91
		// 547: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 550: dup
		// 551: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 554: aastore
		// 555: dup
		// 556: iconst_1
		// 557: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 560: ifnull +9 -> 569
		// 563: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 566: goto +12 -> 578
		// 569: ldc 83
		// 571: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 574: dup
		// 575: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 578: aastore
		// 579: dup
		// 580: iconst_2
		// 581: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 584: ifnull +9 -> 593
		// 587: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 590: goto +12 -> 602
		// 593: ldc 83
		// 595: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 598: dup
		// 599: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 602: aastore
		// 603: dup
		// 604: iconst_3
		// 605: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 608: aastore
		// 609: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 612: putstatic 186
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copyValue_10
		// Ljava/lang/reflect/Method;
		// 615: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 618: ifnull +9 -> 627
		// 621: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 624: goto +12 -> 636
		// 627: ldc 91
		// 629: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 632: dup
		// 633: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 636: ldc 13
		// 638: iconst_4
		// 639: anewarray 143 java/lang/Class
		// 642: dup
		// 643: iconst_0
		// 644: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 647: ifnull +9 -> 656
		// 650: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 653: goto +12 -> 665
		// 656: ldc 91
		// 658: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 661: dup
		// 662: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 665: aastore
		// 666: dup
		// 667: iconst_1
		// 668: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 671: ifnull +9 -> 680
		// 674: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 677: goto +12 -> 689
		// 680: ldc 3
		// 682: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 685: dup
		// 686: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 689: aastore
		// 690: dup
		// 691: iconst_2
		// 692: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 695: ifnull +9 -> 704
		// 698: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 701: goto +12 -> 713
		// 704: ldc 3
		// 706: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 709: dup
		// 710: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 713: aastore
		// 714: dup
		// 715: iconst_3
		// 716: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 719: aastore
		// 720: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 723: putstatic 187
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_copyValue_11
		// Ljava/lang/reflect/Method;
		// 726: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 729: ifnull +9 -> 738
		// 732: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 735: goto +12 -> 747
		// 738: ldc 91
		// 740: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 743: dup
		// 744: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 747: ldc 14
		// 749: iconst_0
		// 750: anewarray 143 java/lang/Class
		// 753: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 756: putstatic 191
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_createComm_12
		// Ljava/lang/reflect/Method;
		// 759: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 762: ifnull +9 -> 771
		// 765: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 768: goto +12 -> 780
		// 771: ldc 91
		// 773: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 776: dup
		// 777: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 780: ldc 15
		// 782: iconst_0
		// 783: anewarray 143 java/lang/Class
		// 786: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 789: putstatic 192
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_delete_13
		// Ljava/lang/reflect/Method;
		// 792: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 795: ifnull +9 -> 804
		// 798: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 801: goto +12 -> 813
		// 804: ldc 91
		// 806: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 809: dup
		// 810: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 813: ldc 15
		// 815: iconst_1
		// 816: anewarray 143 java/lang/Class
		// 819: dup
		// 820: iconst_0
		// 821: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 824: aastore
		// 825: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 828: putstatic 193
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_delete_14
		// Ljava/lang/reflect/Method;
		// 831: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 834: ifnull +9 -> 843
		// 837: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 840: goto +12 -> 852
		// 843: ldc 91
		// 845: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 848: dup
		// 849: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 852: ldc 16
		// 854: iconst_0
		// 855: anewarray 143 java/lang/Class
		// 858: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 861: putstatic 194
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_duplicate_15
		// Ljava/lang/reflect/Method;
		// 864: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 867: ifnull +9 -> 876
		// 870: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 873: goto +12 -> 885
		// 876: ldc 91
		// 878: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 881: dup
		// 882: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 885: ldc 17
		// 887: iconst_1
		// 888: anewarray 143 java/lang/Class
		// 891: dup
		// 892: iconst_0
		// 893: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 896: ifnull +9 -> 905
		// 899: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 902: goto +12 -> 914
		// 905: ldc 83
		// 907: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 910: dup
		// 911: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 914: aastore
		// 915: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 918: putstatic 195
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_evaluateCondition_16
		// Ljava/lang/reflect/Method;
		// 921: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 924: ifnull +9 -> 933
		// 927: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 930: goto +12 -> 942
		// 933: ldc 91
		// 935: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 938: dup
		// 939: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 942: ldc 18
		// 944: iconst_1
		// 945: anewarray 143 java/lang/Class
		// 948: dup
		// 949: iconst_0
		// 950: getstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 953: ifnull +9 -> 962
		// 956: getstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 959: goto +12 -> 971
		// 962: ldc 85
		// 964: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 967: dup
		// 968: putstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 971: aastore
		// 972: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 975: putstatic 196
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_evaluateCtrlConditions_17
		// Ljava/lang/reflect/Method;
		// 978: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 981: ifnull +9 -> 990
		// 984: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 987: goto +12 -> 999
		// 990: ldc 91
		// 992: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 995: dup
		// 996: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 999: ldc 18
		// 1001: iconst_2
		// 1002: anewarray 143 java/lang/Class
		// 1005: dup
		// 1006: iconst_0
		// 1007: getstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 1010: ifnull +9 -> 1019
		// 1013: getstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 1016: goto +12 -> 1028
		// 1019: ldc 85
		// 1021: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1024: dup
		// 1025: putstatic 372
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$HashSet
		// Ljava/lang/Class;
		// 1028: aastore
		// 1029: dup
		// 1030: iconst_1
		// 1031: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1034: ifnull +9 -> 1043
		// 1037: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1040: goto +12 -> 1052
		// 1043: ldc 83
		// 1045: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1048: dup
		// 1049: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1052: aastore
		// 1053: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1056: putstatic 197
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_evaluateCtrlConditions_18
		// Ljava/lang/reflect/Method;
		// 1059: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1062: ifnull +9 -> 1071
		// 1065: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1068: goto +12 -> 1080
		// 1071: ldc 91
		// 1073: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1076: dup
		// 1077: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1080: ldc 19
		// 1082: iconst_1
		// 1083: anewarray 143 java/lang/Class
		// 1086: dup
		// 1087: iconst_0
		// 1088: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1091: ifnull +9 -> 1100
		// 1094: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1097: goto +12 -> 1109
		// 1100: ldc 83
		// 1102: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1105: dup
		// 1106: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1109: aastore
		// 1110: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1113: putstatic 198
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_excludeObjectForPropagate_19
		// Ljava/lang/reflect/Method;
		// 1116: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1119: ifnull +9 -> 1128
		// 1122: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1125: goto +12 -> 1137
		// 1128: ldc 91
		// 1130: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1133: dup
		// 1134: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1137: ldc 20
		// 1139: iconst_0
		// 1140: anewarray 143 java/lang/Class
		// 1143: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1146: putstatic 199
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_generateAutoKey_20
		// Ljava/lang/reflect/Method;
		// 1149: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1152: ifnull +9 -> 1161
		// 1155: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1158: goto +12 -> 1170
		// 1161: ldc 91
		// 1163: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1166: dup
		// 1167: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1170: ldc 21
		// 1172: iconst_1
		// 1173: anewarray 143 java/lang/Class
		// 1176: dup
		// 1177: iconst_0
		// 1178: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1181: ifnull +9 -> 1190
		// 1184: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1187: goto +12 -> 1199
		// 1190: ldc 83
		// 1192: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1195: dup
		// 1196: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1199: aastore
		// 1200: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1203: putstatic 200
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getBoolean_21
		// Ljava/lang/reflect/Method;
		// 1206: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1209: ifnull +9 -> 1218
		// 1212: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1215: goto +12 -> 1227
		// 1218: ldc 91
		// 1220: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1223: dup
		// 1224: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1227: ldc 22
		// 1229: iconst_1
		// 1230: anewarray 143 java/lang/Class
		// 1233: dup
		// 1234: iconst_0
		// 1235: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1238: ifnull +9 -> 1247
		// 1241: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1244: goto +12 -> 1256
		// 1247: ldc 83
		// 1249: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1252: dup
		// 1253: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1256: aastore
		// 1257: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1260: putstatic 201
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getByte_22
		// Ljava/lang/reflect/Method;
		// 1263: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1266: ifnull +9 -> 1275
		// 1269: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1272: goto +12 -> 1284
		// 1275: ldc 91
		// 1277: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1280: dup
		// 1281: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1284: ldc 23
		// 1286: iconst_1
		// 1287: anewarray 143 java/lang/Class
		// 1290: dup
		// 1291: iconst_0
		// 1292: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1295: ifnull +9 -> 1304
		// 1298: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1301: goto +12 -> 1313
		// 1304: ldc 83
		// 1306: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1309: dup
		// 1310: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1313: aastore
		// 1314: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1317: putstatic 202
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getBytes_23
		// Ljava/lang/reflect/Method;
		// 1320: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1323: ifnull +9 -> 1332
		// 1326: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1329: goto +12 -> 1341
		// 1332: ldc 91
		// 1334: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1337: dup
		// 1338: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1341: ldc 24
		// 1343: iconst_0
		// 1344: anewarray 143 java/lang/Class
		// 1347: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1350: putstatic 203
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getCommLogOwnerNameAndUniqueId_24
		// Ljava/lang/reflect/Method;
		// 1353: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1356: ifnull +9 -> 1365
		// 1359: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1362: goto +12 -> 1374
		// 1365: ldc 91
		// 1367: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1370: dup
		// 1371: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1374: ldc 25
		// 1376: iconst_1
		// 1377: anewarray 143 java/lang/Class
		// 1380: dup
		// 1381: iconst_0
		// 1382: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1385: ifnull +9 -> 1394
		// 1388: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1391: goto +12 -> 1403
		// 1394: ldc 83
		// 1396: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1399: dup
		// 1400: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1403: aastore
		// 1404: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1407: putstatic 204
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDatabaseValue_25
		// Ljava/lang/reflect/Method;
		// 1410: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1413: ifnull +9 -> 1422
		// 1416: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1419: goto +12 -> 1431
		// 1422: ldc 91
		// 1424: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1427: dup
		// 1428: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1431: ldc 26
		// 1433: iconst_1
		// 1434: anewarray 143 java/lang/Class
		// 1437: dup
		// 1438: iconst_0
		// 1439: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1442: ifnull +9 -> 1451
		// 1445: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1448: goto +12 -> 1460
		// 1451: ldc 83
		// 1453: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1456: dup
		// 1457: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1460: aastore
		// 1461: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1464: putstatic 205
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDate_26
		// Ljava/lang/reflect/Method;
		// 1467: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1470: ifnull +9 -> 1479
		// 1473: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1476: goto +12 -> 1488
		// 1479: ldc 91
		// 1481: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1484: dup
		// 1485: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1488: ldc 27
		// 1490: iconst_0
		// 1491: anewarray 143 java/lang/Class
		// 1494: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1497: putstatic 206
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDeleteForInsertList_27
		// Ljava/lang/reflect/Method;
		// 1500: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1503: ifnull +9 -> 1512
		// 1506: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1509: goto +12 -> 1521
		// 1512: ldc 91
		// 1514: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1517: dup
		// 1518: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1521: ldc 28
		// 1523: iconst_0
		// 1524: anewarray 143 java/lang/Class
		// 1527: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1530: putstatic 207
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDocLinksCount_28
		// Ljava/lang/reflect/Method;
		// 1533: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1536: ifnull +9 -> 1545
		// 1539: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1542: goto +12 -> 1554
		// 1545: ldc 91
		// 1547: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1550: dup
		// 1551: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1554: ldc 29
		// 1556: iconst_1
		// 1557: anewarray 143 java/lang/Class
		// 1560: dup
		// 1561: iconst_0
		// 1562: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1565: ifnull +9 -> 1574
		// 1568: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1571: goto +12 -> 1583
		// 1574: ldc 83
		// 1576: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1579: dup
		// 1580: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1583: aastore
		// 1584: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1587: putstatic 208
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDomainIDs_29
		// Ljava/lang/reflect/Method;
		// 1590: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1593: ifnull +9 -> 1602
		// 1596: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1599: goto +12 -> 1611
		// 1602: ldc 91
		// 1604: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1607: dup
		// 1608: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1611: ldc 30
		// 1613: iconst_1
		// 1614: anewarray 143 java/lang/Class
		// 1617: dup
		// 1618: iconst_0
		// 1619: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1622: ifnull +9 -> 1631
		// 1625: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1628: goto +12 -> 1640
		// 1631: ldc 83
		// 1633: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1636: dup
		// 1637: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1640: aastore
		// 1641: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1644: putstatic 209
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getDouble_30
		// Ljava/lang/reflect/Method;
		// 1647: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1650: ifnull +9 -> 1659
		// 1653: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1656: goto +12 -> 1668
		// 1659: ldc 91
		// 1661: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1664: dup
		// 1665: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1668: ldc 31
		// 1670: iconst_1
		// 1671: anewarray 143 java/lang/Class
		// 1674: dup
		// 1675: iconst_0
		// 1676: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1679: ifnull +9 -> 1688
		// 1682: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1685: goto +12 -> 1697
		// 1688: ldc 83
		// 1690: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1693: dup
		// 1694: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1697: aastore
		// 1698: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1701: putstatic 210
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getExistingMboSet_31
		// Ljava/lang/reflect/Method;
		// 1704: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1707: ifnull +9 -> 1716
		// 1710: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1713: goto +12 -> 1725
		// 1716: ldc 91
		// 1718: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1721: dup
		// 1722: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1725: ldc 32
		// 1727: iconst_0
		// 1728: anewarray 143 java/lang/Class
		// 1731: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1734: putstatic 211
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getFlags_32
		// Ljava/lang/reflect/Method;
		// 1737: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1740: ifnull +9 -> 1749
		// 1743: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1746: goto +12 -> 1758
		// 1749: ldc 91
		// 1751: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1754: dup
		// 1755: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1758: ldc 33
		// 1760: iconst_1
		// 1761: anewarray 143 java/lang/Class
		// 1764: dup
		// 1765: iconst_0
		// 1766: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1769: ifnull +9 -> 1778
		// 1772: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1775: goto +12 -> 1787
		// 1778: ldc 83
		// 1780: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1783: dup
		// 1784: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1787: aastore
		// 1788: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1791: putstatic 212
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getFloat_33
		// Ljava/lang/reflect/Method;
		// 1794: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1797: ifnull +9 -> 1806
		// 1800: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1803: goto +12 -> 1815
		// 1806: ldc 91
		// 1808: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1811: dup
		// 1812: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1815: ldc 34
		// 1817: iconst_1
		// 1818: anewarray 143 java/lang/Class
		// 1821: dup
		// 1822: iconst_0
		// 1823: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1826: ifnull +9 -> 1835
		// 1829: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1832: goto +12 -> 1844
		// 1835: ldc 83
		// 1837: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1840: dup
		// 1841: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 1844: aastore
		// 1845: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1848: putstatic 213
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInitialValue_34
		// Ljava/lang/reflect/Method;
		// 1851: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1854: ifnull +9 -> 1863
		// 1857: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1860: goto +12 -> 1872
		// 1863: ldc 91
		// 1865: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1868: dup
		// 1869: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1872: ldc 35
		// 1874: iconst_0
		// 1875: anewarray 143 java/lang/Class
		// 1878: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1881: putstatic 214
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInsertCompanySetId_35
		// Ljava/lang/reflect/Method;
		// 1884: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1887: ifnull +9 -> 1896
		// 1890: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1893: goto +12 -> 1905
		// 1896: ldc 91
		// 1898: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1901: dup
		// 1902: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1905: ldc 36
		// 1907: iconst_0
		// 1908: anewarray 143 java/lang/Class
		// 1911: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1914: putstatic 215
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInsertItemSetId_36
		// Ljava/lang/reflect/Method;
		// 1917: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1920: ifnull +9 -> 1929
		// 1923: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1926: goto +12 -> 1938
		// 1929: ldc 91
		// 1931: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1934: dup
		// 1935: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1938: ldc 37
		// 1940: iconst_0
		// 1941: anewarray 143 java/lang/Class
		// 1944: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1947: putstatic 216
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInsertOrganization_37
		// Ljava/lang/reflect/Method;
		// 1950: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1953: ifnull +9 -> 1962
		// 1956: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1959: goto +12 -> 1971
		// 1962: ldc 91
		// 1964: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 1967: dup
		// 1968: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1971: ldc 38
		// 1973: iconst_0
		// 1974: anewarray 143 java/lang/Class
		// 1977: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 1980: putstatic 217
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInsertSite_38
		// Ljava/lang/reflect/Method;
		// 1983: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1986: ifnull +9 -> 1995
		// 1989: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 1992: goto +12 -> 2004
		// 1995: ldc 91
		// 1997: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2000: dup
		// 2001: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2004: ldc 39
		// 2006: iconst_1
		// 2007: anewarray 143 java/lang/Class
		// 2010: dup
		// 2011: iconst_0
		// 2012: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2015: ifnull +9 -> 2024
		// 2018: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2021: goto +12 -> 2033
		// 2024: ldc 83
		// 2026: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2029: dup
		// 2030: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2033: aastore
		// 2034: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2037: putstatic 218
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getInt_39
		// Ljava/lang/reflect/Method;
		// 2040: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2043: ifnull +9 -> 2052
		// 2046: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2049: goto +12 -> 2061
		// 2052: ldc 91
		// 2054: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2057: dup
		// 2058: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2061: ldc 40
		// 2063: iconst_0
		// 2064: anewarray 143 java/lang/Class
		// 2067: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2070: putstatic 219
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getKeyValue_40
		// Ljava/lang/reflect/Method;
		// 2073: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2076: ifnull +9 -> 2085
		// 2079: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2082: goto +12 -> 2094
		// 2085: ldc 91
		// 2087: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2090: dup
		// 2091: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2094: ldc 41
		// 2096: iconst_0
		// 2097: anewarray 143 java/lang/Class
		// 2100: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2103: putstatic 220
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getLinesRelationship_41
		// Ljava/lang/reflect/Method;
		// 2106: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2109: ifnull +9 -> 2118
		// 2112: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2115: goto +12 -> 2127
		// 2118: ldc 91
		// 2120: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2123: dup
		// 2124: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2127: ldc 42
		// 2129: iconst_1
		// 2130: anewarray 143 java/lang/Class
		// 2133: dup
		// 2134: iconst_0
		// 2135: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2138: ifnull +9 -> 2147
		// 2141: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2144: goto +12 -> 2156
		// 2147: ldc 83
		// 2149: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2152: dup
		// 2153: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2156: aastore
		// 2157: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2160: putstatic 221
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getList_42
		// Ljava/lang/reflect/Method;
		// 2163: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2166: ifnull +9 -> 2175
		// 2169: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2172: goto +12 -> 2184
		// 2175: ldc 91
		// 2177: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2180: dup
		// 2181: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2184: ldc 43
		// 2186: iconst_1
		// 2187: anewarray 143 java/lang/Class
		// 2190: dup
		// 2191: iconst_0
		// 2192: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2195: ifnull +9 -> 2204
		// 2198: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2201: goto +12 -> 2213
		// 2204: ldc 83
		// 2206: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2209: dup
		// 2210: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2213: aastore
		// 2214: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2217: putstatic 222
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getLong_43
		// Ljava/lang/reflect/Method;
		// 2220: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2223: ifnull +9 -> 2232
		// 2226: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2229: goto +12 -> 2241
		// 2232: ldc 91
		// 2234: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2237: dup
		// 2238: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2241: ldc 44
		// 2243: iconst_0
		// 2244: anewarray 143 java/lang/Class
		// 2247: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2250: putstatic 223
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMXTransaction_44
		// Ljava/lang/reflect/Method;
		// 2253: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2256: ifnull +9 -> 2265
		// 2259: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2262: goto +12 -> 2274
		// 2265: ldc 91
		// 2267: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2270: dup
		// 2271: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2274: ldc 45
		// 2276: iconst_1
		// 2277: anewarray 143 java/lang/Class
		// 2280: dup
		// 2281: iconst_0
		// 2282: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2285: ifnull +9 -> 2294
		// 2288: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2291: goto +12 -> 2303
		// 2294: ldc 83
		// 2296: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2299: dup
		// 2300: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2303: aastore
		// 2304: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2307: putstatic 224
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMatchingAttr_45
		// Ljava/lang/reflect/Method;
		// 2310: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2313: ifnull +9 -> 2322
		// 2316: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2319: goto +12 -> 2331
		// 2322: ldc 91
		// 2324: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2327: dup
		// 2328: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2331: ldc 45
		// 2333: iconst_2
		// 2334: anewarray 143 java/lang/Class
		// 2337: dup
		// 2338: iconst_0
		// 2339: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2342: ifnull +9 -> 2351
		// 2345: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2348: goto +12 -> 2360
		// 2351: ldc 83
		// 2353: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2356: dup
		// 2357: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2360: aastore
		// 2361: dup
		// 2362: iconst_1
		// 2363: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2366: ifnull +9 -> 2375
		// 2369: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2372: goto +12 -> 2384
		// 2375: ldc 83
		// 2377: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2380: dup
		// 2381: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2384: aastore
		// 2385: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2388: putstatic 225
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMatchingAttr_46
		// Ljava/lang/reflect/Method;
		// 2391: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2394: ifnull +9 -> 2403
		// 2397: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2400: goto +12 -> 2412
		// 2403: ldc 91
		// 2405: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2408: dup
		// 2409: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2412: ldc 46
		// 2414: iconst_2
		// 2415: anewarray 143 java/lang/Class
		// 2418: dup
		// 2419: iconst_0
		// 2420: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2423: ifnull +9 -> 2432
		// 2426: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2429: goto +12 -> 2441
		// 2432: ldc 83
		// 2434: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2437: dup
		// 2438: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2441: aastore
		// 2442: dup
		// 2443: iconst_1
		// 2444: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2447: ifnull +9 -> 2456
		// 2450: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2453: goto +12 -> 2465
		// 2456: ldc 83
		// 2458: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2461: dup
		// 2462: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2465: aastore
		// 2466: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2469: putstatic 226
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMatchingAttrs_47
		// Ljava/lang/reflect/Method;
		// 2472: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2475: ifnull +9 -> 2484
		// 2478: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2481: goto +12 -> 2493
		// 2484: ldc 91
		// 2486: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2489: dup
		// 2490: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2493: ldc 47
		// 2495: iconst_2
		// 2496: anewarray 143 java/lang/Class
		// 2499: dup
		// 2500: iconst_0
		// 2501: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2504: ifnull +9 -> 2513
		// 2507: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2510: goto +12 -> 2522
		// 2513: ldc 83
		// 2515: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2518: dup
		// 2519: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2522: aastore
		// 2523: dup
		// 2524: iconst_1
		// 2525: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2528: ifnull +9 -> 2537
		// 2531: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2534: goto +12 -> 2546
		// 2537: ldc 83
		// 2539: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2542: dup
		// 2543: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2546: aastore
		// 2547: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2550: putstatic 227
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMaxMessage_48
		// Ljava/lang/reflect/Method;
		// 2553: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2556: ifnull +9 -> 2565
		// 2559: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2562: goto +12 -> 2574
		// 2565: ldc 91
		// 2567: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2570: dup
		// 2571: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2574: ldc 48
		// 2576: iconst_1
		// 2577: anewarray 143 java/lang/Class
		// 2580: dup
		// 2581: iconst_0
		// 2582: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 2585: ifnull +9 -> 2594
		// 2588: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 2591: goto +12 -> 2603
		// 2594: ldc 3
		// 2596: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2599: dup
		// 2600: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 2603: aastore
		// 2604: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2607: putstatic 229
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboData_49
		// Ljava/lang/reflect/Method;
		// 2610: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2613: ifnull +9 -> 2622
		// 2616: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2619: goto +12 -> 2631
		// 2622: ldc 91
		// 2624: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2627: dup
		// 2628: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2631: ldc 49
		// 2633: iconst_1
		// 2634: anewarray 143 java/lang/Class
		// 2637: dup
		// 2638: iconst_0
		// 2639: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2642: ifnull +9 -> 2651
		// 2645: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2648: goto +12 -> 2660
		// 2651: ldc 83
		// 2653: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2656: dup
		// 2657: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2660: aastore
		// 2661: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2664: putstatic 228
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboDataSet_50
		// Ljava/lang/reflect/Method;
		// 2667: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2670: ifnull +9 -> 2679
		// 2673: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2676: goto +12 -> 2688
		// 2679: ldc 91
		// 2681: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2684: dup
		// 2685: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2688: ldc 50
		// 2690: iconst_1
		// 2691: anewarray 143 java/lang/Class
		// 2694: dup
		// 2695: iconst_0
		// 2696: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2699: ifnull +9 -> 2708
		// 2702: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2705: goto +12 -> 2717
		// 2708: ldc 83
		// 2710: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2713: dup
		// 2714: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2717: aastore
		// 2718: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2721: putstatic 230
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboInitialValue_51
		// Ljava/lang/reflect/Method;
		// 2724: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2727: ifnull +9 -> 2736
		// 2730: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2733: goto +12 -> 2745
		// 2736: ldc 91
		// 2738: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2741: dup
		// 2742: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2745: ldc 51
		// 2747: iconst_1
		// 2748: anewarray 143 java/lang/Class
		// 2751: dup
		// 2752: iconst_0
		// 2753: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2756: ifnull +9 -> 2765
		// 2759: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2762: goto +12 -> 2774
		// 2765: ldc 83
		// 2767: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2770: dup
		// 2771: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2774: aastore
		// 2775: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2778: putstatic 231
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboList_52
		// Ljava/lang/reflect/Method;
		// 2781: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2784: ifnull +9 -> 2793
		// 2787: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2790: goto +12 -> 2802
		// 2793: ldc 91
		// 2795: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2798: dup
		// 2799: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2802: ldc 52
		// 2804: iconst_1
		// 2805: anewarray 143 java/lang/Class
		// 2808: dup
		// 2809: iconst_0
		// 2810: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2813: ifnull +9 -> 2822
		// 2816: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2819: goto +12 -> 2831
		// 2822: ldc 83
		// 2824: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2827: dup
		// 2828: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2831: aastore
		// 2832: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2835: putstatic 232
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboSet_53
		// Ljava/lang/reflect/Method;
		// 2838: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2841: ifnull +9 -> 2850
		// 2844: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2847: goto +12 -> 2859
		// 2850: ldc 91
		// 2852: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2855: dup
		// 2856: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2859: ldc 52
		// 2861: iconst_2
		// 2862: anewarray 143 java/lang/Class
		// 2865: dup
		// 2866: iconst_0
		// 2867: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2870: ifnull +9 -> 2879
		// 2873: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2876: goto +12 -> 2888
		// 2879: ldc 83
		// 2881: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2884: dup
		// 2885: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2888: aastore
		// 2889: dup
		// 2890: iconst_1
		// 2891: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2894: ifnull +9 -> 2903
		// 2897: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2900: goto +12 -> 2912
		// 2903: ldc 83
		// 2905: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2908: dup
		// 2909: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2912: aastore
		// 2913: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 2916: putstatic 233
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboSet_54
		// Ljava/lang/reflect/Method;
		// 2919: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2922: ifnull +9 -> 2931
		// 2925: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2928: goto +12 -> 2940
		// 2931: ldc 91
		// 2933: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2936: dup
		// 2937: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 2940: ldc 52
		// 2942: iconst_3
		// 2943: anewarray 143 java/lang/Class
		// 2946: dup
		// 2947: iconst_0
		// 2948: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2951: ifnull +9 -> 2960
		// 2954: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2957: goto +12 -> 2969
		// 2960: ldc 83
		// 2962: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2965: dup
		// 2966: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2969: aastore
		// 2970: dup
		// 2971: iconst_1
		// 2972: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2975: ifnull +9 -> 2984
		// 2978: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2981: goto +12 -> 2993
		// 2984: ldc 83
		// 2986: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 2989: dup
		// 2990: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2993: aastore
		// 2994: dup
		// 2995: iconst_2
		// 2996: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 2999: ifnull +9 -> 3008
		// 3002: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3005: goto +12 -> 3017
		// 3008: ldc 83
		// 3010: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3013: dup
		// 3014: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3017: aastore
		// 3018: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3021: putstatic 234
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboSet_55
		// Ljava/lang/reflect/Method;
		// 3024: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3027: ifnull +9 -> 3036
		// 3030: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3033: goto +12 -> 3045
		// 3036: ldc 91
		// 3038: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3041: dup
		// 3042: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3045: ldc 53
		// 3047: iconst_1
		// 3048: anewarray 143 java/lang/Class
		// 3051: dup
		// 3052: iconst_0
		// 3053: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3056: ifnull +9 -> 3065
		// 3059: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3062: goto +12 -> 3074
		// 3065: ldc 83
		// 3067: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3070: dup
		// 3071: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3074: aastore
		// 3075: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3078: putstatic 235
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboValueData_56
		// Ljava/lang/reflect/Method;
		// 3081: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3084: ifnull +9 -> 3093
		// 3087: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3090: goto +12 -> 3102
		// 3093: ldc 91
		// 3095: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3098: dup
		// 3099: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3102: ldc 53
		// 3104: iconst_2
		// 3105: anewarray 143 java/lang/Class
		// 3108: dup
		// 3109: iconst_0
		// 3110: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3113: ifnull +9 -> 3122
		// 3116: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3119: goto +12 -> 3131
		// 3122: ldc 83
		// 3124: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3127: dup
		// 3128: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3131: aastore
		// 3132: dup
		// 3133: iconst_1
		// 3134: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 3137: aastore
		// 3138: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3141: putstatic 236
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboValueData_57
		// Ljava/lang/reflect/Method;
		// 3144: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3147: ifnull +9 -> 3156
		// 3150: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3153: goto +12 -> 3165
		// 3156: ldc 91
		// 3158: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3161: dup
		// 3162: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3165: ldc 53
		// 3167: iconst_1
		// 3168: anewarray 143 java/lang/Class
		// 3171: dup
		// 3172: iconst_0
		// 3173: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3176: ifnull +9 -> 3185
		// 3179: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3182: goto +12 -> 3194
		// 3185: ldc 3
		// 3187: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3190: dup
		// 3191: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3194: aastore
		// 3195: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3198: putstatic 237
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboValueData_58
		// Ljava/lang/reflect/Method;
		// 3201: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3204: ifnull +9 -> 3213
		// 3207: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3210: goto +12 -> 3222
		// 3213: ldc 91
		// 3215: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3218: dup
		// 3219: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3222: ldc 54
		// 3224: iconst_1
		// 3225: anewarray 143 java/lang/Class
		// 3228: dup
		// 3229: iconst_0
		// 3230: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3233: ifnull +9 -> 3242
		// 3236: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3239: goto +12 -> 3251
		// 3242: ldc 83
		// 3244: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3247: dup
		// 3248: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3251: aastore
		// 3252: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3255: putstatic 238
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboValueInfoStatic_59
		// Ljava/lang/reflect/Method;
		// 3258: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3261: ifnull +9 -> 3270
		// 3264: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3267: goto +12 -> 3279
		// 3270: ldc 91
		// 3272: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3275: dup
		// 3276: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3279: ldc 54
		// 3281: iconst_1
		// 3282: anewarray 143 java/lang/Class
		// 3285: dup
		// 3286: iconst_0
		// 3287: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3290: ifnull +9 -> 3299
		// 3293: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3296: goto +12 -> 3308
		// 3299: ldc 3
		// 3301: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3304: dup
		// 3305: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 3308: aastore
		// 3309: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3312: putstatic 239
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMboValueInfoStatic_60
		// Ljava/lang/reflect/Method;
		// 3315: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3318: ifnull +9 -> 3327
		// 3321: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3324: goto +12 -> 3336
		// 3327: ldc 91
		// 3329: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3332: dup
		// 3333: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3336: ldc 55
		// 3338: iconst_2
		// 3339: anewarray 143 java/lang/Class
		// 3342: dup
		// 3343: iconst_0
		// 3344: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3347: ifnull +9 -> 3356
		// 3350: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3353: goto +12 -> 3365
		// 3356: ldc 83
		// 3358: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3361: dup
		// 3362: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3365: aastore
		// 3366: dup
		// 3367: iconst_1
		// 3368: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3371: ifnull +9 -> 3380
		// 3374: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3377: goto +12 -> 3389
		// 3380: ldc 83
		// 3382: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3385: dup
		// 3386: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3389: aastore
		// 3390: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3393: putstatic 240
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMessage_61
		// Ljava/lang/reflect/Method;
		// 3396: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3399: ifnull +9 -> 3408
		// 3402: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3405: goto +12 -> 3417
		// 3408: ldc 91
		// 3410: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3413: dup
		// 3414: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3417: ldc 55
		// 3419: iconst_3
		// 3420: anewarray 143 java/lang/Class
		// 3423: dup
		// 3424: iconst_0
		// 3425: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3428: ifnull +9 -> 3437
		// 3431: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3434: goto +12 -> 3446
		// 3437: ldc 83
		// 3439: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3442: dup
		// 3443: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3446: aastore
		// 3447: dup
		// 3448: iconst_1
		// 3449: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3452: ifnull +9 -> 3461
		// 3455: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3458: goto +12 -> 3470
		// 3461: ldc 83
		// 3463: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3466: dup
		// 3467: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3470: aastore
		// 3471: dup
		// 3472: iconst_2
		// 3473: getstatic 369
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$Object
		// Ljava/lang/Class;
		// 3476: ifnull +9 -> 3485
		// 3479: getstatic 369
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$Object
		// Ljava/lang/Class;
		// 3482: goto +12 -> 3494
		// 3485: ldc 82
		// 3487: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3490: dup
		// 3491: putstatic 369
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$Object
		// Ljava/lang/Class;
		// 3494: aastore
		// 3495: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3498: putstatic 241
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMessage_62
		// Ljava/lang/reflect/Method;
		// 3501: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3504: ifnull +9 -> 3513
		// 3507: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3510: goto +12 -> 3522
		// 3513: ldc 91
		// 3515: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3518: dup
		// 3519: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3522: ldc 55
		// 3524: iconst_3
		// 3525: anewarray 143 java/lang/Class
		// 3528: dup
		// 3529: iconst_0
		// 3530: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3533: ifnull +9 -> 3542
		// 3536: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3539: goto +12 -> 3551
		// 3542: ldc 83
		// 3544: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3547: dup
		// 3548: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3551: aastore
		// 3552: dup
		// 3553: iconst_1
		// 3554: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3557: ifnull +9 -> 3566
		// 3560: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3563: goto +12 -> 3575
		// 3566: ldc 83
		// 3568: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3571: dup
		// 3572: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3575: aastore
		// 3576: dup
		// 3577: iconst_2
		// 3578: getstatic 364
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$Object
		// Ljava/lang/Class;
		// 3581: ifnull +9 -> 3590
		// 3584: getstatic 364
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$Object
		// Ljava/lang/Class;
		// 3587: goto +12 -> 3599
		// 3590: ldc 2
		// 3592: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3595: dup
		// 3596: putstatic 364
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$Object
		// Ljava/lang/Class;
		// 3599: aastore
		// 3600: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3603: putstatic 242
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMessage_63
		// Ljava/lang/reflect/Method;
		// 3606: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3609: ifnull +9 -> 3618
		// 3612: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3615: goto +12 -> 3627
		// 3618: ldc 91
		// 3620: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3623: dup
		// 3624: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3627: ldc 55
		// 3629: iconst_1
		// 3630: anewarray 143 java/lang/Class
		// 3633: dup
		// 3634: iconst_0
		// 3635: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 3638: ifnull +9 -> 3647
		// 3641: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 3644: goto +12 -> 3656
		// 3647: ldc 94
		// 3649: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3652: dup
		// 3653: putstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 3656: aastore
		// 3657: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3660: putstatic 243
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getMessage_64
		// Ljava/lang/reflect/Method;
		// 3663: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3666: ifnull +9 -> 3675
		// 3669: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3672: goto +12 -> 3684
		// 3675: ldc 91
		// 3677: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3680: dup
		// 3681: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3684: ldc 56
		// 3686: iconst_0
		// 3687: anewarray 143 java/lang/Class
		// 3690: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3693: putstatic 244
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getName_65
		// Ljava/lang/reflect/Method;
		// 3696: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3699: ifnull +9 -> 3708
		// 3702: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3705: goto +12 -> 3717
		// 3708: ldc 91
		// 3710: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3713: dup
		// 3714: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3717: ldc 57
		// 3719: iconst_1
		// 3720: anewarray 143 java/lang/Class
		// 3723: dup
		// 3724: iconst_0
		// 3725: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3728: ifnull +9 -> 3737
		// 3731: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3734: goto +12 -> 3746
		// 3737: ldc 83
		// 3739: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3742: dup
		// 3743: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3746: aastore
		// 3747: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3750: putstatic 245
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getOrgForGL_66
		// Ljava/lang/reflect/Method;
		// 3753: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3756: ifnull +9 -> 3765
		// 3759: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3762: goto +12 -> 3774
		// 3765: ldc 91
		// 3767: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3770: dup
		// 3771: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3774: ldc 58
		// 3776: iconst_1
		// 3777: anewarray 143 java/lang/Class
		// 3780: dup
		// 3781: iconst_0
		// 3782: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3785: ifnull +9 -> 3794
		// 3788: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3791: goto +12 -> 3803
		// 3794: ldc 83
		// 3796: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3799: dup
		// 3800: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3803: aastore
		// 3804: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3807: putstatic 246
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getOrgSiteForMaxvar_67
		// Ljava/lang/reflect/Method;
		// 3810: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3813: ifnull +9 -> 3822
		// 3816: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3819: goto +12 -> 3831
		// 3822: ldc 91
		// 3824: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3827: dup
		// 3828: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3831: ldc 59
		// 3833: iconst_0
		// 3834: anewarray 143 java/lang/Class
		// 3837: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3840: putstatic 247
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getOwner_68
		// Ljava/lang/reflect/Method;
		// 3843: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3846: ifnull +9 -> 3855
		// 3849: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3852: goto +12 -> 3864
		// 3855: ldc 91
		// 3857: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3860: dup
		// 3861: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3864: ldc 60
		// 3866: iconst_0
		// 3867: anewarray 143 java/lang/Class
		// 3870: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3873: putstatic 248
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getPropagateKeyFlag_69
		// Ljava/lang/reflect/Method;
		// 3876: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3879: ifnull +9 -> 3888
		// 3882: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3885: goto +12 -> 3897
		// 3888: ldc 91
		// 3890: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3893: dup
		// 3894: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3897: ldc 61
		// 3899: iconst_0
		// 3900: anewarray 143 java/lang/Class
		// 3903: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3906: putstatic 249
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getRecordIdentifer_70
		// Ljava/lang/reflect/Method;
		// 3909: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3912: ifnull +9 -> 3921
		// 3915: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3918: goto +12 -> 3930
		// 3921: ldc 91
		// 3923: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3926: dup
		// 3927: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3930: ldc 62
		// 3932: iconst_0
		// 3933: anewarray 143 java/lang/Class
		// 3936: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3939: putstatic 250
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getSiteOrg_71
		// Ljava/lang/reflect/Method;
		// 3942: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3945: ifnull +9 -> 3954
		// 3948: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3951: goto +12 -> 3963
		// 3954: ldc 91
		// 3956: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3959: dup
		// 3960: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 3963: ldc 63
		// 3965: iconst_1
		// 3966: anewarray 143 java/lang/Class
		// 3969: dup
		// 3970: iconst_0
		// 3971: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3974: ifnull +9 -> 3983
		// 3977: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3980: goto +12 -> 3992
		// 3983: ldc 83
		// 3985: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 3988: dup
		// 3989: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 3992: aastore
		// 3993: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 3996: putstatic 254
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getString_72
		// Ljava/lang/reflect/Method;
		// 3999: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4002: ifnull +9 -> 4011
		// 4005: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4008: goto +12 -> 4020
		// 4011: ldc 91
		// 4013: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4016: dup
		// 4017: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4020: ldc 63
		// 4022: iconst_2
		// 4023: anewarray 143 java/lang/Class
		// 4026: dup
		// 4027: iconst_0
		// 4028: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4031: ifnull +9 -> 4040
		// 4034: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4037: goto +12 -> 4049
		// 4040: ldc 83
		// 4042: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4045: dup
		// 4046: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4049: aastore
		// 4050: dup
		// 4051: iconst_1
		// 4052: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4055: ifnull +9 -> 4064
		// 4058: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4061: goto +12 -> 4073
		// 4064: ldc 83
		// 4066: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4069: dup
		// 4070: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4073: aastore
		// 4074: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4077: putstatic 255
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getString_73
		// Ljava/lang/reflect/Method;
		// 4080: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4083: ifnull +9 -> 4092
		// 4086: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4089: goto +12 -> 4101
		// 4092: ldc 91
		// 4094: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4097: dup
		// 4098: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4101: ldc 64
		// 4103: iconst_1
		// 4104: anewarray 143 java/lang/Class
		// 4107: dup
		// 4108: iconst_0
		// 4109: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4112: ifnull +9 -> 4121
		// 4115: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4118: goto +12 -> 4130
		// 4121: ldc 83
		// 4123: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4126: dup
		// 4127: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4130: aastore
		// 4131: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4134: putstatic 251
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getStringInBaseLanguage_74
		// Ljava/lang/reflect/Method;
		// 4137: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4140: ifnull +9 -> 4149
		// 4143: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4146: goto +12 -> 4158
		// 4149: ldc 91
		// 4151: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4154: dup
		// 4155: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4158: ldc 65
		// 4160: iconst_3
		// 4161: anewarray 143 java/lang/Class
		// 4164: dup
		// 4165: iconst_0
		// 4166: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4169: ifnull +9 -> 4178
		// 4172: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4175: goto +12 -> 4187
		// 4178: ldc 83
		// 4180: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4183: dup
		// 4184: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4187: aastore
		// 4188: dup
		// 4189: iconst_1
		// 4190: getstatic 373
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Locale
		// Ljava/lang/Class;
		// 4193: ifnull +9 -> 4202
		// 4196: getstatic 373
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Locale
		// Ljava/lang/Class;
		// 4199: goto +12 -> 4211
		// 4202: ldc 86
		// 4204: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4207: dup
		// 4208: putstatic 373
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Locale
		// Ljava/lang/Class;
		// 4211: aastore
		// 4212: dup
		// 4213: iconst_2
		// 4214: getstatic 375
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$TimeZone
		// Ljava/lang/Class;
		// 4217: ifnull +9 -> 4226
		// 4220: getstatic 375
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$TimeZone
		// Ljava/lang/Class;
		// 4223: goto +12 -> 4235
		// 4226: ldc 88
		// 4228: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4231: dup
		// 4232: putstatic 375
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$TimeZone
		// Ljava/lang/Class;
		// 4235: aastore
		// 4236: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4239: putstatic 252
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getStringInSpecificLocale_75
		// Ljava/lang/reflect/Method;
		// 4242: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4245: ifnull +9 -> 4254
		// 4248: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4251: goto +12 -> 4263
		// 4254: ldc 91
		// 4256: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4259: dup
		// 4260: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4263: ldc 66
		// 4265: iconst_2
		// 4266: anewarray 143 java/lang/Class
		// 4269: dup
		// 4270: iconst_0
		// 4271: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4274: ifnull +9 -> 4283
		// 4277: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4280: goto +12 -> 4292
		// 4283: ldc 83
		// 4285: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4288: dup
		// 4289: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4292: aastore
		// 4293: dup
		// 4294: iconst_1
		// 4295: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4298: ifnull +9 -> 4307
		// 4301: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4304: goto +12 -> 4316
		// 4307: ldc 83
		// 4309: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4312: dup
		// 4313: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4316: aastore
		// 4317: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4320: putstatic 253
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getStringTransparent_76
		// Ljava/lang/reflect/Method;
		// 4323: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4326: ifnull +9 -> 4335
		// 4329: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4332: goto +12 -> 4344
		// 4335: ldc 91
		// 4337: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4340: dup
		// 4341: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4344: ldc 67
		// 4346: iconst_0
		// 4347: anewarray 143 java/lang/Class
		// 4350: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4353: putstatic 256
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getThisMboSet_77
		// Ljava/lang/reflect/Method;
		// 4356: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4359: ifnull +9 -> 4368
		// 4362: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4365: goto +12 -> 4377
		// 4368: ldc 91
		// 4370: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4373: dup
		// 4374: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4377: ldc 68
		// 4379: iconst_0
		// 4380: anewarray 143 java/lang/Class
		// 4383: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4386: putstatic 257
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getUniqueIDName_78
		// Ljava/lang/reflect/Method;
		// 4389: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4392: ifnull +9 -> 4401
		// 4395: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4398: goto +12 -> 4410
		// 4401: ldc 91
		// 4403: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4406: dup
		// 4407: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4410: ldc 69
		// 4412: iconst_0
		// 4413: anewarray 143 java/lang/Class
		// 4416: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4419: putstatic 258
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getUniqueIDValue_79
		// Ljava/lang/reflect/Method;
		// 4422: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4425: ifnull +9 -> 4434
		// 4428: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4431: goto +12 -> 4443
		// 4434: ldc 91
		// 4436: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4439: dup
		// 4440: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4443: ldc 70
		// 4445: iconst_0
		// 4446: anewarray 143 java/lang/Class
		// 4449: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4452: putstatic 259
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getUserInfo_80
		// Ljava/lang/reflect/Method;
		// 4455: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4458: ifnull +9 -> 4467
		// 4461: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4464: goto +12 -> 4476
		// 4467: ldc 91
		// 4469: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4472: dup
		// 4473: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4476: ldc 71
		// 4478: iconst_0
		// 4479: anewarray 143 java/lang/Class
		// 4482: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4485: putstatic 260
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_getUserName_81
		// Ljava/lang/reflect/Method;
		// 4488: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4491: ifnull +9 -> 4500
		// 4494: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4497: goto +12 -> 4509
		// 4500: ldc 91
		// 4502: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4505: dup
		// 4506: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4509: ldc 72
		// 4511: iconst_0
		// 4512: anewarray 143 java/lang/Class
		// 4515: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4518: putstatic 261
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_hasHierarchyLink_82
		// Ljava/lang/reflect/Method;
		// 4521: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4524: ifnull +9 -> 4533
		// 4527: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4530: goto +12 -> 4542
		// 4533: ldc 91
		// 4535: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4538: dup
		// 4539: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4542: ldc 73
		// 4544: iconst_1
		// 4545: anewarray 143 java/lang/Class
		// 4548: dup
		// 4549: iconst_0
		// 4550: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4553: ifnull +9 -> 4562
		// 4556: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4559: goto +12 -> 4571
		// 4562: ldc 83
		// 4564: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4567: dup
		// 4568: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4571: aastore
		// 4572: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4575: putstatic 262
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isAutoKeyed_83
		// Ljava/lang/reflect/Method;
		// 4578: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4581: ifnull +9 -> 4590
		// 4584: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4587: goto +12 -> 4599
		// 4590: ldc 91
		// 4592: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4595: dup
		// 4596: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4599: ldc 74
		// 4601: iconst_1
		// 4602: anewarray 143 java/lang/Class
		// 4605: dup
		// 4606: iconst_0
		// 4607: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4610: ifnull +9 -> 4619
		// 4613: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4616: goto +12 -> 4628
		// 4619: ldc 83
		// 4621: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4624: dup
		// 4625: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4628: aastore
		// 4629: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4632: putstatic 263
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isBasedOn_84
		// Ljava/lang/reflect/Method;
		// 4635: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4638: ifnull +9 -> 4647
		// 4641: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4644: goto +12 -> 4656
		// 4647: ldc 91
		// 4649: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4652: dup
		// 4653: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4656: ldc 75
		// 4658: iconst_1
		// 4659: anewarray 143 java/lang/Class
		// 4662: dup
		// 4663: iconst_0
		// 4664: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 4667: aastore
		// 4668: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4671: putstatic 264
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isFlagSet_85
		// Ljava/lang/reflect/Method;
		// 4674: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4677: ifnull +9 -> 4686
		// 4680: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4683: goto +12 -> 4695
		// 4686: ldc 91
		// 4688: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4691: dup
		// 4692: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4695: ldc 76
		// 4697: iconst_0
		// 4698: anewarray 143 java/lang/Class
		// 4701: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4704: putstatic 265
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isForDM_86
		// Ljava/lang/reflect/Method;
		// 4707: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4710: ifnull +9 -> 4719
		// 4713: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4716: goto +12 -> 4728
		// 4719: ldc 91
		// 4721: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4724: dup
		// 4725: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4728: ldc 77
		// 4730: iconst_0
		// 4731: anewarray 143 java/lang/Class
		// 4734: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4737: putstatic 266
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isModified_87
		// Ljava/lang/reflect/Method;
		// 4740: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4743: ifnull +9 -> 4752
		// 4746: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4749: goto +12 -> 4761
		// 4752: ldc 91
		// 4754: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4757: dup
		// 4758: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4761: ldc 77
		// 4763: iconst_1
		// 4764: anewarray 143 java/lang/Class
		// 4767: dup
		// 4768: iconst_0
		// 4769: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4772: ifnull +9 -> 4781
		// 4775: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4778: goto +12 -> 4790
		// 4781: ldc 83
		// 4783: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4786: dup
		// 4787: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4790: aastore
		// 4791: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4794: putstatic 267
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isModified_88
		// Ljava/lang/reflect/Method;
		// 4797: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4800: ifnull +9 -> 4809
		// 4803: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4806: goto +12 -> 4818
		// 4809: ldc 91
		// 4811: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4814: dup
		// 4815: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4818: ldc 78
		// 4820: iconst_0
		// 4821: anewarray 143 java/lang/Class
		// 4824: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4827: putstatic 268
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isNew_89
		// Ljava/lang/reflect/Method;
		// 4830: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4833: ifnull +9 -> 4842
		// 4836: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4839: goto +12 -> 4851
		// 4842: ldc 91
		// 4844: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4847: dup
		// 4848: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4851: ldc 79
		// 4853: iconst_1
		// 4854: anewarray 143 java/lang/Class
		// 4857: dup
		// 4858: iconst_0
		// 4859: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4862: ifnull +9 -> 4871
		// 4865: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4868: goto +12 -> 4880
		// 4871: ldc 83
		// 4873: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4876: dup
		// 4877: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4880: aastore
		// 4881: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4884: putstatic 269
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isNull_90
		// Ljava/lang/reflect/Method;
		// 4887: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4890: ifnull +9 -> 4899
		// 4893: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4896: goto +12 -> 4908
		// 4899: ldc 91
		// 4901: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4904: dup
		// 4905: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4908: ldc 80
		// 4910: iconst_0
		// 4911: anewarray 143 java/lang/Class
		// 4914: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4917: putstatic 270
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isSelected_91
		// Ljava/lang/reflect/Method;
		// 4920: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4923: ifnull +9 -> 4932
		// 4926: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4929: goto +12 -> 4941
		// 4932: ldc 91
		// 4934: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4937: dup
		// 4938: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4941: ldc 81
		// 4943: iconst_0
		// 4944: anewarray 143 java/lang/Class
		// 4947: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 4950: putstatic 271
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_isZombie_92
		// Ljava/lang/reflect/Method;
		// 4953: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4956: ifnull +9 -> 4965
		// 4959: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4962: goto +12 -> 4974
		// 4965: ldc 91
		// 4967: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4970: dup
		// 4971: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 4974: ldc 89
		// 4976: iconst_2
		// 4977: anewarray 143 java/lang/Class
		// 4980: dup
		// 4981: iconst_0
		// 4982: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4985: ifnull +9 -> 4994
		// 4988: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 4991: goto +12 -> 5003
		// 4994: ldc 83
		// 4996: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 4999: dup
		// 5000: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5003: aastore
		// 5004: dup
		// 5005: iconst_1
		// 5006: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5009: ifnull +9 -> 5018
		// 5012: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5015: goto +12 -> 5027
		// 5018: ldc 83
		// 5020: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5023: dup
		// 5024: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5027: aastore
		// 5028: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5031: putstatic 272
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_propagateKeyValue_93
		// Ljava/lang/reflect/Method;
		// 5034: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5037: ifnull +9 -> 5046
		// 5040: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5043: goto +12 -> 5055
		// 5046: ldc 91
		// 5048: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5051: dup
		// 5052: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5055: ldc 96
		// 5057: iconst_0
		// 5058: anewarray 143 java/lang/Class
		// 5061: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5064: putstatic 273
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_rollbackToCheckpoint_94
		// Ljava/lang/reflect/Method;
		// 5067: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5070: ifnull +9 -> 5079
		// 5073: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5076: goto +12 -> 5088
		// 5079: ldc 91
		// 5081: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5084: dup
		// 5085: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5088: ldc 97
		// 5090: iconst_0
		// 5091: anewarray 143 java/lang/Class
		// 5094: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5097: putstatic 274
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_select_95
		// Ljava/lang/reflect/Method;
		// 5100: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5103: ifnull +9 -> 5112
		// 5106: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5109: goto +12 -> 5121
		// 5112: ldc 91
		// 5114: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5117: dup
		// 5118: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5121: ldc 98
		// 5123: iconst_2
		// 5124: anewarray 143 java/lang/Class
		// 5127: dup
		// 5128: iconst_0
		// 5129: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5132: ifnull +9 -> 5141
		// 5135: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5138: goto +12 -> 5150
		// 5141: ldc 83
		// 5143: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5146: dup
		// 5147: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5150: aastore
		// 5151: dup
		// 5152: iconst_1
		// 5153: getstatic 379
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$ApplicationError
		// Ljava/lang/Class;
		// 5156: ifnull +9 -> 5165
		// 5159: getstatic 379
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$ApplicationError
		// Ljava/lang/Class;
		// 5162: goto +12 -> 5174
		// 5165: ldc 93
		// 5167: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5170: dup
		// 5171: putstatic 379
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$ApplicationError
		// Ljava/lang/Class;
		// 5174: aastore
		// 5175: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5178: putstatic 275
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setApplicationError_96
		// Ljava/lang/reflect/Method;
		// 5181: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5184: ifnull +9 -> 5193
		// 5187: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5190: goto +12 -> 5202
		// 5193: ldc 91
		// 5195: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5198: dup
		// 5199: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5202: ldc 99
		// 5204: iconst_2
		// 5205: anewarray 143 java/lang/Class
		// 5208: dup
		// 5209: iconst_0
		// 5210: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5213: ifnull +9 -> 5222
		// 5216: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5219: goto +12 -> 5231
		// 5222: ldc 83
		// 5224: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5227: dup
		// 5228: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5231: aastore
		// 5232: dup
		// 5233: iconst_1
		// 5234: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5237: aastore
		// 5238: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5241: putstatic 276
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setApplicationRequired_97
		// Ljava/lang/reflect/Method;
		// 5244: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5247: ifnull +9 -> 5256
		// 5250: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5253: goto +12 -> 5265
		// 5256: ldc 91
		// 5258: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5261: dup
		// 5262: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5265: ldc 100
		// 5267: iconst_0
		// 5268: anewarray 143 java/lang/Class
		// 5271: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5274: putstatic 277
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setCopyDefaults_98
		// Ljava/lang/reflect/Method;
		// 5277: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5280: ifnull +9 -> 5289
		// 5283: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5286: goto +12 -> 5298
		// 5289: ldc 91
		// 5291: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5294: dup
		// 5295: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5298: ldc 101
		// 5300: iconst_1
		// 5301: anewarray 143 java/lang/Class
		// 5304: dup
		// 5305: iconst_0
		// 5306: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5309: aastore
		// 5310: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5313: putstatic 278
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setDeleted_99
		// Ljava/lang/reflect/Method;
		// 5316: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5319: ifnull +9 -> 5328
		// 5322: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5325: goto +12 -> 5337
		// 5328: ldc 91
		// 5330: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5333: dup
		// 5334: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5337: ldc 102
		// 5339: iconst_1
		// 5340: anewarray 143 java/lang/Class
		// 5343: dup
		// 5344: iconst_0
		// 5345: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5348: aastore
		// 5349: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5352: putstatic 279
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setESigFieldModified_100
		// Ljava/lang/reflect/Method;
		// 5355: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5358: ifnull +9 -> 5367
		// 5361: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5364: goto +12 -> 5376
		// 5367: ldc 91
		// 5369: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5372: dup
		// 5373: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5376: ldc 103
		// 5378: iconst_3
		// 5379: anewarray 143 java/lang/Class
		// 5382: dup
		// 5383: iconst_0
		// 5384: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5387: ifnull +9 -> 5396
		// 5390: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5393: goto +12 -> 5405
		// 5396: ldc 83
		// 5398: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5401: dup
		// 5402: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5405: aastore
		// 5406: dup
		// 5407: iconst_1
		// 5408: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5411: aastore
		// 5412: dup
		// 5413: iconst_2
		// 5414: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5417: aastore
		// 5418: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5421: putstatic 280
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_101
		// Ljava/lang/reflect/Method;
		// 5424: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5427: ifnull +9 -> 5436
		// 5430: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5433: goto +12 -> 5445
		// 5436: ldc 91
		// 5438: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5441: dup
		// 5442: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5445: ldc 103
		// 5447: iconst_4
		// 5448: anewarray 143 java/lang/Class
		// 5451: dup
		// 5452: iconst_0
		// 5453: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5456: ifnull +9 -> 5465
		// 5459: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5462: goto +12 -> 5474
		// 5465: ldc 83
		// 5467: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5470: dup
		// 5471: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5474: aastore
		// 5475: dup
		// 5476: iconst_1
		// 5477: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5480: aastore
		// 5481: dup
		// 5482: iconst_2
		// 5483: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5486: aastore
		// 5487: dup
		// 5488: iconst_3
		// 5489: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5492: ifnull +9 -> 5501
		// 5495: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5498: goto +12 -> 5510
		// 5501: ldc 94
		// 5503: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5506: dup
		// 5507: putstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5510: aastore
		// 5511: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5514: putstatic 281
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_102
		// Ljava/lang/reflect/Method;
		// 5517: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5520: ifnull +9 -> 5529
		// 5523: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5526: goto +12 -> 5538
		// 5529: ldc 91
		// 5531: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5534: dup
		// 5535: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5538: ldc 103
		// 5540: iconst_3
		// 5541: anewarray 143 java/lang/Class
		// 5544: dup
		// 5545: iconst_0
		// 5546: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5549: ifnull +9 -> 5558
		// 5552: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5555: goto +12 -> 5567
		// 5558: ldc 3
		// 5560: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5563: dup
		// 5564: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5567: aastore
		// 5568: dup
		// 5569: iconst_1
		// 5570: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5573: aastore
		// 5574: dup
		// 5575: iconst_2
		// 5576: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5579: aastore
		// 5580: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5583: putstatic 282
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_103
		// Ljava/lang/reflect/Method;
		// 5586: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5589: ifnull +9 -> 5598
		// 5592: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5595: goto +12 -> 5607
		// 5598: ldc 91
		// 5600: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5603: dup
		// 5604: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5607: ldc 103
		// 5609: iconst_4
		// 5610: anewarray 143 java/lang/Class
		// 5613: dup
		// 5614: iconst_0
		// 5615: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5618: ifnull +9 -> 5627
		// 5621: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5624: goto +12 -> 5636
		// 5627: ldc 3
		// 5629: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5632: dup
		// 5633: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5636: aastore
		// 5637: dup
		// 5638: iconst_1
		// 5639: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5642: aastore
		// 5643: dup
		// 5644: iconst_2
		// 5645: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5648: aastore
		// 5649: dup
		// 5650: iconst_3
		// 5651: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5654: ifnull +9 -> 5663
		// 5657: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5660: goto +12 -> 5672
		// 5663: ldc 94
		// 5665: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5668: dup
		// 5669: putstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5672: aastore
		// 5673: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5676: putstatic 283
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_104
		// Ljava/lang/reflect/Method;
		// 5679: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5682: ifnull +9 -> 5691
		// 5685: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5688: goto +12 -> 5700
		// 5691: ldc 91
		// 5693: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5696: dup
		// 5697: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5700: ldc 103
		// 5702: iconst_4
		// 5703: anewarray 143 java/lang/Class
		// 5706: dup
		// 5707: iconst_0
		// 5708: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5711: ifnull +9 -> 5720
		// 5714: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5717: goto +12 -> 5729
		// 5720: ldc 3
		// 5722: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5725: dup
		// 5726: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5729: aastore
		// 5730: dup
		// 5731: iconst_1
		// 5732: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5735: aastore
		// 5736: dup
		// 5737: iconst_2
		// 5738: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5741: aastore
		// 5742: dup
		// 5743: iconst_3
		// 5744: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5747: aastore
		// 5748: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5751: putstatic 284
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_105
		// Ljava/lang/reflect/Method;
		// 5754: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5757: ifnull +9 -> 5766
		// 5760: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5763: goto +12 -> 5775
		// 5766: ldc 91
		// 5768: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5771: dup
		// 5772: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5775: ldc 103
		// 5777: iconst_5
		// 5778: anewarray 143 java/lang/Class
		// 5781: dup
		// 5782: iconst_0
		// 5783: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5786: ifnull +9 -> 5795
		// 5789: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5792: goto +12 -> 5804
		// 5795: ldc 3
		// 5797: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5800: dup
		// 5801: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 5804: aastore
		// 5805: dup
		// 5806: iconst_1
		// 5807: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5810: aastore
		// 5811: dup
		// 5812: iconst_2
		// 5813: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5816: aastore
		// 5817: dup
		// 5818: iconst_3
		// 5819: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5822: aastore
		// 5823: dup
		// 5824: iconst_4
		// 5825: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5828: ifnull +9 -> 5837
		// 5831: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5834: goto +12 -> 5846
		// 5837: ldc 94
		// 5839: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5842: dup
		// 5843: putstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 5846: aastore
		// 5847: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5850: putstatic 285
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlag_106
		// Ljava/lang/reflect/Method;
		// 5853: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5856: ifnull +9 -> 5865
		// 5859: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5862: goto +12 -> 5874
		// 5865: ldc 91
		// 5867: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5870: dup
		// 5871: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5874: ldc 104
		// 5876: iconst_2
		// 5877: anewarray 143 java/lang/Class
		// 5880: dup
		// 5881: iconst_0
		// 5882: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5885: ifnull +9 -> 5894
		// 5888: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5891: goto +12 -> 5903
		// 5894: ldc 83
		// 5896: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5899: dup
		// 5900: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 5903: aastore
		// 5904: dup
		// 5905: iconst_1
		// 5906: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5909: aastore
		// 5910: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5913: putstatic 286
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFieldFlags_107
		// Ljava/lang/reflect/Method;
		// 5916: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5919: ifnull +9 -> 5928
		// 5922: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5925: goto +12 -> 5937
		// 5928: ldc 91
		// 5930: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5933: dup
		// 5934: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5937: ldc 105
		// 5939: iconst_2
		// 5940: anewarray 143 java/lang/Class
		// 5943: dup
		// 5944: iconst_0
		// 5945: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5948: aastore
		// 5949: dup
		// 5950: iconst_1
		// 5951: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5954: aastore
		// 5955: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 5958: putstatic 287
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFlag_108
		// Ljava/lang/reflect/Method;
		// 5961: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5964: ifnull +9 -> 5973
		// 5967: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5970: goto +12 -> 5982
		// 5973: ldc 91
		// 5975: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 5978: dup
		// 5979: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 5982: ldc 105
		// 5984: iconst_3
		// 5985: anewarray 143 java/lang/Class
		// 5988: dup
		// 5989: iconst_0
		// 5990: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 5993: aastore
		// 5994: dup
		// 5995: iconst_1
		// 5996: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 5999: aastore
		// 6000: dup
		// 6001: iconst_2
		// 6002: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 6005: ifnull +9 -> 6014
		// 6008: getstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 6011: goto +12 -> 6023
		// 6014: ldc 94
		// 6016: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6019: dup
		// 6020: putstatic 380
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MXException
		// Ljava/lang/Class;
		// 6023: aastore
		// 6024: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6027: putstatic 288
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFlag_109
		// Ljava/lang/reflect/Method;
		// 6030: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6033: ifnull +9 -> 6042
		// 6036: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6039: goto +12 -> 6051
		// 6042: ldc 91
		// 6044: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6047: dup
		// 6048: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6051: ldc 106
		// 6053: iconst_1
		// 6054: anewarray 143 java/lang/Class
		// 6057: dup
		// 6058: iconst_0
		// 6059: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 6062: aastore
		// 6063: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6066: putstatic 289
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setFlags_110
		// Ljava/lang/reflect/Method;
		// 6069: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6072: ifnull +9 -> 6081
		// 6075: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6078: goto +12 -> 6090
		// 6081: ldc 91
		// 6083: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6086: dup
		// 6087: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6090: ldc 107
		// 6092: iconst_1
		// 6093: anewarray 143 java/lang/Class
		// 6096: dup
		// 6097: iconst_0
		// 6098: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 6101: aastore
		// 6102: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6105: putstatic 290
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setForDM_111
		// Ljava/lang/reflect/Method;
		// 6108: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6111: ifnull +9 -> 6120
		// 6114: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6117: goto +12 -> 6129
		// 6120: ldc 91
		// 6122: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6125: dup
		// 6126: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6129: ldc 108
		// 6131: iconst_4
		// 6132: anewarray 143 java/lang/Class
		// 6135: dup
		// 6136: iconst_0
		// 6137: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6140: ifnull +9 -> 6149
		// 6143: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6146: goto +12 -> 6158
		// 6149: ldc 83
		// 6151: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6154: dup
		// 6155: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6158: aastore
		// 6159: dup
		// 6160: iconst_1
		// 6161: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6164: ifnull +9 -> 6173
		// 6167: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6170: goto +12 -> 6182
		// 6173: ldc 83
		// 6175: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6178: dup
		// 6179: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6182: aastore
		// 6183: dup
		// 6184: iconst_2
		// 6185: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6188: ifnull +9 -> 6197
		// 6191: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6194: goto +12 -> 6206
		// 6197: ldc 83
		// 6199: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6202: dup
		// 6203: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6206: aastore
		// 6207: dup
		// 6208: iconst_3
		// 6209: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 6212: aastore
		// 6213: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6216: putstatic 291
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setMLValue_112
		// Ljava/lang/reflect/Method;
		// 6219: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6222: ifnull +9 -> 6231
		// 6225: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6228: goto +12 -> 6240
		// 6231: ldc 91
		// 6233: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6236: dup
		// 6237: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6240: ldc 109
		// 6242: iconst_1
		// 6243: anewarray 143 java/lang/Class
		// 6246: dup
		// 6247: iconst_0
		// 6248: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 6251: aastore
		// 6252: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6255: putstatic 292
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setModified_113
		// Ljava/lang/reflect/Method;
		// 6258: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6261: ifnull +9 -> 6270
		// 6264: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6267: goto +12 -> 6279
		// 6270: ldc 91
		// 6272: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6275: dup
		// 6276: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6279: ldc 110
		// 6281: iconst_1
		// 6282: anewarray 143 java/lang/Class
		// 6285: dup
		// 6286: iconst_0
		// 6287: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 6290: aastore
		// 6291: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6294: putstatic 293
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setNewMbo_114
		// Ljava/lang/reflect/Method;
		// 6297: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6300: ifnull +9 -> 6309
		// 6303: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6306: goto +12 -> 6318
		// 6309: ldc 91
		// 6311: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6314: dup
		// 6315: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6318: ldc 111
		// 6320: iconst_1
		// 6321: anewarray 143 java/lang/Class
		// 6324: dup
		// 6325: iconst_0
		// 6326: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 6329: aastore
		// 6330: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6333: putstatic 294
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setPropagateKeyFlag_115
		// Ljava/lang/reflect/Method;
		// 6336: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6339: ifnull +9 -> 6348
		// 6342: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6345: goto +12 -> 6357
		// 6348: ldc 91
		// 6350: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6353: dup
		// 6354: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6357: ldc 111
		// 6359: iconst_2
		// 6360: anewarray 143 java/lang/Class
		// 6363: dup
		// 6364: iconst_0
		// 6365: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 6368: ifnull +9 -> 6377
		// 6371: getstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 6374: goto +12 -> 6386
		// 6377: ldc 3
		// 6379: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6382: dup
		// 6383: putstatic 365
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$Ljava$lang$String
		// Ljava/lang/Class;
		// 6386: aastore
		// 6387: dup
		// 6388: iconst_1
		// 6389: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 6392: aastore
		// 6393: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6396: putstatic 295
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setPropagateKeyFlag_116
		// Ljava/lang/reflect/Method;
		// 6399: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6402: ifnull +9 -> 6411
		// 6405: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6408: goto +12 -> 6420
		// 6411: ldc 91
		// 6413: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6416: dup
		// 6417: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6420: ldc 112
		// 6422: iconst_2
		// 6423: anewarray 143 java/lang/Class
		// 6426: dup
		// 6427: iconst_0
		// 6428: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6431: ifnull +9 -> 6440
		// 6434: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6437: goto +12 -> 6449
		// 6440: ldc 83
		// 6442: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6445: dup
		// 6446: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6449: aastore
		// 6450: dup
		// 6451: iconst_1
		// 6452: getstatic 376
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$Mbo
		// Ljava/lang/Class;
		// 6455: ifnull +9 -> 6464
		// 6458: getstatic 376
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$Mbo
		// Ljava/lang/Class;
		// 6461: goto +12 -> 6473
		// 6464: ldc 90
		// 6466: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6469: dup
		// 6470: putstatic 376
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$Mbo
		// Ljava/lang/Class;
		// 6473: aastore
		// 6474: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6477: putstatic 296
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setReferencedMbo_117
		// Ljava/lang/reflect/Method;
		// 6480: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6483: ifnull +9 -> 6492
		// 6486: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6489: goto +12 -> 6501
		// 6492: ldc 91
		// 6494: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6497: dup
		// 6498: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6501: ldc 113
		// 6503: iconst_2
		// 6504: anewarray 143 java/lang/Class
		// 6507: dup
		// 6508: iconst_0
		// 6509: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6512: ifnull +9 -> 6521
		// 6515: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6518: goto +12 -> 6530
		// 6521: ldc 83
		// 6523: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6526: dup
		// 6527: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6530: aastore
		// 6531: dup
		// 6532: iconst_1
		// 6533: getstatic 356 java/lang/Byte:TYPE Ljava/lang/Class;
		// 6536: aastore
		// 6537: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6540: putstatic 299
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_118
		// Ljava/lang/reflect/Method;
		// 6543: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6546: ifnull +9 -> 6555
		// 6549: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6552: goto +12 -> 6564
		// 6555: ldc 91
		// 6557: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6560: dup
		// 6561: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6564: ldc 113
		// 6566: iconst_3
		// 6567: anewarray 143 java/lang/Class
		// 6570: dup
		// 6571: iconst_0
		// 6572: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6575: ifnull +9 -> 6584
		// 6578: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6581: goto +12 -> 6593
		// 6584: ldc 83
		// 6586: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6589: dup
		// 6590: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6593: aastore
		// 6594: dup
		// 6595: iconst_1
		// 6596: getstatic 356 java/lang/Byte:TYPE Ljava/lang/Class;
		// 6599: aastore
		// 6600: dup
		// 6601: iconst_2
		// 6602: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 6605: aastore
		// 6606: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6609: putstatic 300
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_119
		// Ljava/lang/reflect/Method;
		// 6612: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6615: ifnull +9 -> 6624
		// 6618: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6621: goto +12 -> 6633
		// 6624: ldc 91
		// 6626: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6629: dup
		// 6630: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6633: ldc 113
		// 6635: iconst_2
		// 6636: anewarray 143 java/lang/Class
		// 6639: dup
		// 6640: iconst_0
		// 6641: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6644: ifnull +9 -> 6653
		// 6647: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6650: goto +12 -> 6662
		// 6653: ldc 83
		// 6655: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6658: dup
		// 6659: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6662: aastore
		// 6663: dup
		// 6664: iconst_1
		// 6665: getstatic 357 java/lang/Double:TYPE Ljava/lang/Class;
		// 6668: aastore
		// 6669: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6672: putstatic 301
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_120
		// Ljava/lang/reflect/Method;
		// 6675: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6678: ifnull +9 -> 6687
		// 6681: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6684: goto +12 -> 6696
		// 6687: ldc 91
		// 6689: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6692: dup
		// 6693: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6696: ldc 113
		// 6698: iconst_3
		// 6699: anewarray 143 java/lang/Class
		// 6702: dup
		// 6703: iconst_0
		// 6704: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6707: ifnull +9 -> 6716
		// 6710: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6713: goto +12 -> 6725
		// 6716: ldc 83
		// 6718: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6721: dup
		// 6722: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6725: aastore
		// 6726: dup
		// 6727: iconst_1
		// 6728: getstatic 357 java/lang/Double:TYPE Ljava/lang/Class;
		// 6731: aastore
		// 6732: dup
		// 6733: iconst_2
		// 6734: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 6737: aastore
		// 6738: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6741: putstatic 302
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_121
		// Ljava/lang/reflect/Method;
		// 6744: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6747: ifnull +9 -> 6756
		// 6750: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6753: goto +12 -> 6765
		// 6756: ldc 91
		// 6758: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6761: dup
		// 6762: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6765: ldc 113
		// 6767: iconst_2
		// 6768: anewarray 143 java/lang/Class
		// 6771: dup
		// 6772: iconst_0
		// 6773: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6776: ifnull +9 -> 6785
		// 6779: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6782: goto +12 -> 6794
		// 6785: ldc 83
		// 6787: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6790: dup
		// 6791: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6794: aastore
		// 6795: dup
		// 6796: iconst_1
		// 6797: getstatic 358 java/lang/Float:TYPE Ljava/lang/Class;
		// 6800: aastore
		// 6801: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6804: putstatic 303
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_122
		// Ljava/lang/reflect/Method;
		// 6807: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6810: ifnull +9 -> 6819
		// 6813: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6816: goto +12 -> 6828
		// 6819: ldc 91
		// 6821: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6824: dup
		// 6825: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6828: ldc 113
		// 6830: iconst_3
		// 6831: anewarray 143 java/lang/Class
		// 6834: dup
		// 6835: iconst_0
		// 6836: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6839: ifnull +9 -> 6848
		// 6842: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6845: goto +12 -> 6857
		// 6848: ldc 83
		// 6850: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6853: dup
		// 6854: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6857: aastore
		// 6858: dup
		// 6859: iconst_1
		// 6860: getstatic 358 java/lang/Float:TYPE Ljava/lang/Class;
		// 6863: aastore
		// 6864: dup
		// 6865: iconst_2
		// 6866: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 6869: aastore
		// 6870: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6873: putstatic 304
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_123
		// Ljava/lang/reflect/Method;
		// 6876: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6879: ifnull +9 -> 6888
		// 6882: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6885: goto +12 -> 6897
		// 6888: ldc 91
		// 6890: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6893: dup
		// 6894: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6897: ldc 113
		// 6899: iconst_2
		// 6900: anewarray 143 java/lang/Class
		// 6903: dup
		// 6904: iconst_0
		// 6905: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6908: ifnull +9 -> 6917
		// 6911: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6914: goto +12 -> 6926
		// 6917: ldc 83
		// 6919: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6922: dup
		// 6923: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6926: aastore
		// 6927: dup
		// 6928: iconst_1
		// 6929: getstatic 359 java/lang/Integer:TYPE Ljava/lang/Class;
		// 6932: aastore
		// 6933: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 6936: putstatic 305
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_124
		// Ljava/lang/reflect/Method;
		// 6939: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6942: ifnull +9 -> 6951
		// 6945: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6948: goto +12 -> 6960
		// 6951: ldc 91
		// 6953: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6956: dup
		// 6957: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 6960: ldc 113
		// 6962: iconst_3
		// 6963: anewarray 143 java/lang/Class
		// 6966: dup
		// 6967: iconst_0
		// 6968: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6971: ifnull +9 -> 6980
		// 6974: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6977: goto +12 -> 6989
		// 6980: ldc 83
		// 6982: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 6985: dup
		// 6986: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 6989: aastore
		// 6990: dup
		// 6991: iconst_1
		// 6992: getstatic 359 java/lang/Integer:TYPE Ljava/lang/Class;
		// 6995: aastore
		// 6996: dup
		// 6997: iconst_2
		// 6998: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7001: aastore
		// 7002: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7005: putstatic 306
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_125
		// Ljava/lang/reflect/Method;
		// 7008: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7011: ifnull +9 -> 7020
		// 7014: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7017: goto +12 -> 7029
		// 7020: ldc 91
		// 7022: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7025: dup
		// 7026: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7029: ldc 113
		// 7031: iconst_2
		// 7032: anewarray 143 java/lang/Class
		// 7035: dup
		// 7036: iconst_0
		// 7037: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7040: ifnull +9 -> 7049
		// 7043: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7046: goto +12 -> 7058
		// 7049: ldc 83
		// 7051: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7054: dup
		// 7055: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7058: aastore
		// 7059: dup
		// 7060: iconst_1
		// 7061: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7064: aastore
		// 7065: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7068: putstatic 307
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_126
		// Ljava/lang/reflect/Method;
		// 7071: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7074: ifnull +9 -> 7083
		// 7077: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7080: goto +12 -> 7092
		// 7083: ldc 91
		// 7085: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7088: dup
		// 7089: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7092: ldc 113
		// 7094: iconst_3
		// 7095: anewarray 143 java/lang/Class
		// 7098: dup
		// 7099: iconst_0
		// 7100: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7103: ifnull +9 -> 7112
		// 7106: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7109: goto +12 -> 7121
		// 7112: ldc 83
		// 7114: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7117: dup
		// 7118: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7121: aastore
		// 7122: dup
		// 7123: iconst_1
		// 7124: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7127: aastore
		// 7128: dup
		// 7129: iconst_2
		// 7130: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7133: aastore
		// 7134: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7137: putstatic 308
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_127
		// Ljava/lang/reflect/Method;
		// 7140: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7143: ifnull +9 -> 7152
		// 7146: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7149: goto +12 -> 7161
		// 7152: ldc 91
		// 7154: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7157: dup
		// 7158: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7161: ldc 113
		// 7163: iconst_2
		// 7164: anewarray 143 java/lang/Class
		// 7167: dup
		// 7168: iconst_0
		// 7169: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7172: ifnull +9 -> 7181
		// 7175: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7178: goto +12 -> 7190
		// 7181: ldc 83
		// 7183: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7186: dup
		// 7187: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7190: aastore
		// 7191: dup
		// 7192: iconst_1
		// 7193: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7196: ifnull +9 -> 7205
		// 7199: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7202: goto +12 -> 7214
		// 7205: ldc 83
		// 7207: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7210: dup
		// 7211: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7214: aastore
		// 7215: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7218: putstatic 309
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_128
		// Ljava/lang/reflect/Method;
		// 7221: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7224: ifnull +9 -> 7233
		// 7227: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7230: goto +12 -> 7242
		// 7233: ldc 91
		// 7235: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7238: dup
		// 7239: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7242: ldc 113
		// 7244: iconst_3
		// 7245: anewarray 143 java/lang/Class
		// 7248: dup
		// 7249: iconst_0
		// 7250: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7253: ifnull +9 -> 7262
		// 7256: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7259: goto +12 -> 7271
		// 7262: ldc 83
		// 7264: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7267: dup
		// 7268: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7271: aastore
		// 7272: dup
		// 7273: iconst_1
		// 7274: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7277: ifnull +9 -> 7286
		// 7280: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7283: goto +12 -> 7295
		// 7286: ldc 83
		// 7288: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7291: dup
		// 7292: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7295: aastore
		// 7296: dup
		// 7297: iconst_2
		// 7298: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7301: aastore
		// 7302: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7305: putstatic 310
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_129
		// Ljava/lang/reflect/Method;
		// 7308: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7311: ifnull +9 -> 7320
		// 7314: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7317: goto +12 -> 7329
		// 7320: ldc 91
		// 7322: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7325: dup
		// 7326: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7329: ldc 113
		// 7331: iconst_2
		// 7332: anewarray 143 java/lang/Class
		// 7335: dup
		// 7336: iconst_0
		// 7337: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7340: ifnull +9 -> 7349
		// 7343: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7346: goto +12 -> 7358
		// 7349: ldc 83
		// 7351: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7354: dup
		// 7355: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7358: aastore
		// 7359: dup
		// 7360: iconst_1
		// 7361: getstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7364: ifnull +9 -> 7373
		// 7367: getstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7370: goto +12 -> 7382
		// 7373: ldc 84
		// 7375: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7378: dup
		// 7379: putstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7382: aastore
		// 7383: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7386: putstatic 311
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_130
		// Ljava/lang/reflect/Method;
		// 7389: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7392: ifnull +9 -> 7401
		// 7395: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7398: goto +12 -> 7410
		// 7401: ldc 91
		// 7403: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7406: dup
		// 7407: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7410: ldc 113
		// 7412: iconst_3
		// 7413: anewarray 143 java/lang/Class
		// 7416: dup
		// 7417: iconst_0
		// 7418: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7421: ifnull +9 -> 7430
		// 7424: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7427: goto +12 -> 7439
		// 7430: ldc 83
		// 7432: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7435: dup
		// 7436: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7439: aastore
		// 7440: dup
		// 7441: iconst_1
		// 7442: getstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7445: ifnull +9 -> 7454
		// 7448: getstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7451: goto +12 -> 7463
		// 7454: ldc 84
		// 7456: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7459: dup
		// 7460: putstatic 371
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Date
		// Ljava/lang/Class;
		// 7463: aastore
		// 7464: dup
		// 7465: iconst_2
		// 7466: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7469: aastore
		// 7470: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7473: putstatic 312
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_131
		// Ljava/lang/reflect/Method;
		// 7476: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7479: ifnull +9 -> 7488
		// 7482: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7485: goto +12 -> 7497
		// 7488: ldc 91
		// 7490: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7493: dup
		// 7494: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7497: ldc 113
		// 7499: iconst_2
		// 7500: anewarray 143 java/lang/Class
		// 7503: dup
		// 7504: iconst_0
		// 7505: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7508: ifnull +9 -> 7517
		// 7511: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7514: goto +12 -> 7526
		// 7517: ldc 83
		// 7519: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7522: dup
		// 7523: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7526: aastore
		// 7527: dup
		// 7528: iconst_1
		// 7529: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7532: ifnull +9 -> 7541
		// 7535: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7538: goto +12 -> 7550
		// 7541: ldc 91
		// 7543: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7546: dup
		// 7547: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7550: aastore
		// 7551: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7554: putstatic 313
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_132
		// Ljava/lang/reflect/Method;
		// 7557: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7560: ifnull +9 -> 7569
		// 7563: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7566: goto +12 -> 7578
		// 7569: ldc 91
		// 7571: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7574: dup
		// 7575: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7578: ldc 113
		// 7580: iconst_2
		// 7581: anewarray 143 java/lang/Class
		// 7584: dup
		// 7585: iconst_0
		// 7586: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7589: ifnull +9 -> 7598
		// 7592: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7595: goto +12 -> 7607
		// 7598: ldc 83
		// 7600: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7603: dup
		// 7604: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7607: aastore
		// 7608: dup
		// 7609: iconst_1
		// 7610: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 7613: ifnull +9 -> 7622
		// 7616: getstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 7619: goto +12 -> 7631
		// 7622: ldc 92
		// 7624: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7627: dup
		// 7628: putstatic 378
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboSetRemote
		// Ljava/lang/Class;
		// 7631: aastore
		// 7632: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7635: putstatic 314
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_133
		// Ljava/lang/reflect/Method;
		// 7638: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7641: ifnull +9 -> 7650
		// 7644: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7647: goto +12 -> 7659
		// 7650: ldc 91
		// 7652: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7655: dup
		// 7656: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7659: ldc 113
		// 7661: iconst_3
		// 7662: anewarray 143 java/lang/Class
		// 7665: dup
		// 7666: iconst_0
		// 7667: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7670: ifnull +9 -> 7679
		// 7673: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7676: goto +12 -> 7688
		// 7679: ldc 83
		// 7681: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7684: dup
		// 7685: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7688: aastore
		// 7689: dup
		// 7690: iconst_1
		// 7691: getstatic 381
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MaxType
		// Ljava/lang/Class;
		// 7694: ifnull +9 -> 7703
		// 7697: getstatic 381
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MaxType
		// Ljava/lang/Class;
		// 7700: goto +12 -> 7712
		// 7703: ldc 95
		// 7705: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7708: dup
		// 7709: putstatic 381
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$util$MaxType
		// Ljava/lang/Class;
		// 7712: aastore
		// 7713: dup
		// 7714: iconst_2
		// 7715: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7718: aastore
		// 7719: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7722: putstatic 315
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_134
		// Ljava/lang/reflect/Method;
		// 7725: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7728: ifnull +9 -> 7737
		// 7731: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7734: goto +12 -> 7746
		// 7737: ldc 91
		// 7739: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7742: dup
		// 7743: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7746: ldc 113
		// 7748: iconst_2
		// 7749: anewarray 143 java/lang/Class
		// 7752: dup
		// 7753: iconst_0
		// 7754: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7757: ifnull +9 -> 7766
		// 7760: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7763: goto +12 -> 7775
		// 7766: ldc 83
		// 7768: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7771: dup
		// 7772: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7775: aastore
		// 7776: dup
		// 7777: iconst_1
		// 7778: getstatic 361 java/lang/Short:TYPE Ljava/lang/Class;
		// 7781: aastore
		// 7782: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7785: putstatic 316
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_135
		// Ljava/lang/reflect/Method;
		// 7788: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7791: ifnull +9 -> 7800
		// 7794: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7797: goto +12 -> 7809
		// 7800: ldc 91
		// 7802: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7805: dup
		// 7806: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7809: ldc 113
		// 7811: iconst_3
		// 7812: anewarray 143 java/lang/Class
		// 7815: dup
		// 7816: iconst_0
		// 7817: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7820: ifnull +9 -> 7829
		// 7823: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7826: goto +12 -> 7838
		// 7829: ldc 83
		// 7831: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7834: dup
		// 7835: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7838: aastore
		// 7839: dup
		// 7840: iconst_1
		// 7841: getstatic 361 java/lang/Short:TYPE Ljava/lang/Class;
		// 7844: aastore
		// 7845: dup
		// 7846: iconst_2
		// 7847: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7850: aastore
		// 7851: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7854: putstatic 317
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_136
		// Ljava/lang/reflect/Method;
		// 7857: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7860: ifnull +9 -> 7869
		// 7863: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7866: goto +12 -> 7878
		// 7869: ldc 91
		// 7871: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7874: dup
		// 7875: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7878: ldc 113
		// 7880: iconst_2
		// 7881: anewarray 143 java/lang/Class
		// 7884: dup
		// 7885: iconst_0
		// 7886: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7889: ifnull +9 -> 7898
		// 7892: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7895: goto +12 -> 7907
		// 7898: ldc 83
		// 7900: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7903: dup
		// 7904: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7907: aastore
		// 7908: dup
		// 7909: iconst_1
		// 7910: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 7913: aastore
		// 7914: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7917: putstatic 318
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_137
		// Ljava/lang/reflect/Method;
		// 7920: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7923: ifnull +9 -> 7932
		// 7926: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7929: goto +12 -> 7941
		// 7932: ldc 91
		// 7934: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7937: dup
		// 7938: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7941: ldc 113
		// 7943: iconst_3
		// 7944: anewarray 143 java/lang/Class
		// 7947: dup
		// 7948: iconst_0
		// 7949: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7952: ifnull +9 -> 7961
		// 7955: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7958: goto +12 -> 7970
		// 7961: ldc 83
		// 7963: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 7966: dup
		// 7967: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 7970: aastore
		// 7971: dup
		// 7972: iconst_1
		// 7973: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 7976: aastore
		// 7977: dup
		// 7978: iconst_2
		// 7979: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 7982: aastore
		// 7983: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 7986: putstatic 319
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_138
		// Ljava/lang/reflect/Method;
		// 7989: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7992: ifnull +9 -> 8001
		// 7995: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 7998: goto +12 -> 8010
		// 8001: ldc 91
		// 8003: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8006: dup
		// 8007: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8010: ldc 113
		// 8012: iconst_2
		// 8013: anewarray 143 java/lang/Class
		// 8016: dup
		// 8017: iconst_0
		// 8018: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8021: ifnull +9 -> 8030
		// 8024: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8027: goto +12 -> 8039
		// 8030: ldc 83
		// 8032: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8035: dup
		// 8036: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8039: aastore
		// 8040: dup
		// 8041: iconst_1
        // 8042: getstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8045: ifnull +9 -> 8054
        // 8048: getstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8051: goto +12 -> 8063
		// 8054: ldc 1
		// 8056: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8059: dup
        // 8060: putstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8063: aastore
		// 8064: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8067: putstatic 320
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_139
		// Ljava/lang/reflect/Method;
		// 8070: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8073: ifnull +9 -> 8082
		// 8076: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8079: goto +12 -> 8091
		// 8082: ldc 91
		// 8084: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8087: dup
		// 8088: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8091: ldc 113
		// 8093: iconst_3
		// 8094: anewarray 143 java/lang/Class
		// 8097: dup
		// 8098: iconst_0
		// 8099: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8102: ifnull +9 -> 8111
		// 8105: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8108: goto +12 -> 8120
		// 8111: ldc 83
		// 8113: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8116: dup
		// 8117: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8120: aastore
		// 8121: dup
		// 8122: iconst_1
        // 8123: getstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8126: ifnull +9 -> 8135
        // 8129: getstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8132: goto +12 -> 8144
		// 8135: ldc 1
		// 8137: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8140: dup
        // 8141: putstatic 363 com/shuto/mam/app/operation/oplog/OpLog_Stub:array$changeBig
		// Ljava/lang/Class;
		// 8144: aastore
		// 8145: dup
		// 8146: iconst_2
		// 8147: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 8150: aastore
		// 8151: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8154: putstatic 321
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValue_140
		// Ljava/lang/reflect/Method;
		// 8157: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8160: ifnull +9 -> 8169
		// 8163: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8166: goto +12 -> 8178
		// 8169: ldc 91
		// 8171: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8174: dup
		// 8175: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8178: ldc 114
		// 8180: iconst_1
		// 8181: anewarray 143 java/lang/Class
		// 8184: dup
		// 8185: iconst_0
		// 8186: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8189: ifnull +9 -> 8198
		// 8192: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8195: goto +12 -> 8207
		// 8198: ldc 83
		// 8200: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8203: dup
		// 8204: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8207: aastore
		// 8208: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8211: putstatic 297
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValueNull_141
		// Ljava/lang/reflect/Method;
		// 8214: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8217: ifnull +9 -> 8226
		// 8220: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8223: goto +12 -> 8235
		// 8226: ldc 91
		// 8228: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8231: dup
		// 8232: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8235: ldc 114
		// 8237: iconst_2
		// 8238: anewarray 143 java/lang/Class
		// 8241: dup
		// 8242: iconst_0
		// 8243: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8246: ifnull +9 -> 8255
		// 8249: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8252: goto +12 -> 8264
		// 8255: ldc 83
		// 8257: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8260: dup
		// 8261: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8264: aastore
		// 8265: dup
		// 8266: iconst_1
		// 8267: getstatic 360 java/lang/Long:TYPE Ljava/lang/Class;
		// 8270: aastore
		// 8271: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8274: putstatic 298
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_setValueNull_142
		// Ljava/lang/reflect/Method;
		// 8277: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8280: ifnull +9 -> 8289
		// 8283: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8286: goto +12 -> 8298
		// 8289: ldc 91
		// 8291: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8294: dup
		// 8295: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8298: ldc 115
		// 8300: iconst_1
		// 8301: anewarray 143 java/lang/Class
		// 8304: dup
		// 8305: iconst_0
		// 8306: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8309: ifnull +9 -> 8318
		// 8312: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8315: goto +12 -> 8327
		// 8318: ldc 83
		// 8320: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8323: dup
		// 8324: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8327: aastore
		// 8328: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8331: putstatic 322
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_sigOptionAccessAuthorized_143
		// Ljava/lang/reflect/Method;
		// 8334: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8337: ifnull +9 -> 8346
		// 8340: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8343: goto +12 -> 8355
		// 8346: ldc 91
		// 8348: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8351: dup
		// 8352: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8355: ldc 116
		// 8357: iconst_1
		// 8358: anewarray 143 java/lang/Class
		// 8361: dup
		// 8362: iconst_0
		// 8363: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8366: ifnull +9 -> 8375
		// 8369: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8372: goto +12 -> 8384
		// 8375: ldc 83
		// 8377: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8380: dup
		// 8381: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8384: aastore
		// 8385: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8388: putstatic 323
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_sigopGranted_144
		// Ljava/lang/reflect/Method;
		// 8391: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8394: ifnull +9 -> 8403
		// 8397: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8400: goto +12 -> 8412
		// 8403: ldc 91
		// 8405: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8408: dup
		// 8409: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8412: ldc 116
		// 8414: iconst_2
		// 8415: anewarray 143 java/lang/Class
		// 8418: dup
		// 8419: iconst_0
		// 8420: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8423: ifnull +9 -> 8432
		// 8426: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8429: goto +12 -> 8441
		// 8432: ldc 83
		// 8434: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8437: dup
		// 8438: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8441: aastore
		// 8442: dup
		// 8443: iconst_1
		// 8444: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8447: ifnull +9 -> 8456
		// 8450: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8453: goto +12 -> 8465
		// 8456: ldc 83
		// 8458: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8461: dup
		// 8462: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8465: aastore
		// 8466: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8469: putstatic 324
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_sigopGranted_145
		// Ljava/lang/reflect/Method;
		// 8472: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8475: ifnull +9 -> 8484
		// 8478: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8481: goto +12 -> 8493
		// 8484: ldc 91
		// 8486: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8489: dup
		// 8490: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8493: ldc 116
		// 8495: iconst_1
		// 8496: anewarray 143 java/lang/Class
		// 8499: dup
		// 8500: iconst_0
		// 8501: getstatic 374
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Set
		// Ljava/lang/Class;
		// 8504: ifnull +9 -> 8513
		// 8507: getstatic 374
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Set
		// Ljava/lang/Class;
		// 8510: goto +12 -> 8522
		// 8513: ldc 87
		// 8515: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8518: dup
		// 8519: putstatic 374
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$util$Set
		// Ljava/lang/Class;
		// 8522: aastore
		// 8523: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8526: putstatic 325
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_sigopGranted_146
		// Ljava/lang/reflect/Method;
		// 8529: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8532: ifnull +9 -> 8541
		// 8535: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8538: goto +12 -> 8550
		// 8541: ldc 91
		// 8543: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8546: dup
		// 8547: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8550: ldc 117
		// 8552: iconst_3
		// 8553: anewarray 143 java/lang/Class
		// 8556: dup
		// 8557: iconst_0
		// 8558: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8561: ifnull +9 -> 8570
		// 8564: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8567: goto +12 -> 8579
		// 8570: ldc 83
		// 8572: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8575: dup
		// 8576: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8579: aastore
		// 8580: dup
		// 8581: iconst_1
		// 8582: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8585: ifnull +9 -> 8594
		// 8588: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8591: goto +12 -> 8603
		// 8594: ldc 83
		// 8596: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8599: dup
		// 8600: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8603: aastore
		// 8604: dup
		// 8605: iconst_2
		// 8606: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 8609: aastore
		// 8610: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8613: putstatic 326
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFill_147
		// Ljava/lang/reflect/Method;
		// 8616: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8619: ifnull +9 -> 8628
		// 8622: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8625: goto +12 -> 8637
		// 8628: ldc 91
		// 8630: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8633: dup
		// 8634: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8637: ldc 118
		// 8639: iconst_4
		// 8640: anewarray 143 java/lang/Class
		// 8643: dup
		// 8644: iconst_0
		// 8645: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8648: ifnull +9 -> 8657
		// 8651: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8654: goto +12 -> 8666
		// 8657: ldc 83
		// 8659: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8662: dup
		// 8663: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8666: aastore
		// 8667: dup
		// 8668: iconst_1
		// 8669: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8672: ifnull +9 -> 8681
		// 8675: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8678: goto +12 -> 8690
		// 8681: ldc 83
		// 8683: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8686: dup
		// 8687: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8690: aastore
		// 8691: dup
		// 8692: iconst_2
		// 8693: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8696: ifnull +9 -> 8705
		// 8699: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8702: goto +12 -> 8714
		// 8705: ldc 83
		// 8707: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8710: dup
		// 8711: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8714: aastore
		// 8715: dup
		// 8716: iconst_3
		// 8717: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 8720: aastore
		// 8721: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8724: putstatic 330
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFind_148
		// Ljava/lang/reflect/Method;
		// 8727: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8730: ifnull +9 -> 8739
		// 8733: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8736: goto +12 -> 8748
		// 8739: ldc 91
		// 8741: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8744: dup
		// 8745: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8748: ldc 118
		// 8750: iconst_3
		// 8751: anewarray 143 java/lang/Class
		// 8754: dup
		// 8755: iconst_0
		// 8756: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8759: ifnull +9 -> 8768
		// 8762: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8765: goto +12 -> 8777
		// 8768: ldc 83
		// 8770: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8773: dup
		// 8774: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8777: aastore
		// 8778: dup
		// 8779: iconst_1
		// 8780: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8783: ifnull +9 -> 8792
		// 8786: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8789: goto +12 -> 8801
		// 8792: ldc 83
		// 8794: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8797: dup
		// 8798: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8801: aastore
		// 8802: dup
		// 8803: iconst_2
		// 8804: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 8807: aastore
		// 8808: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8811: putstatic 331
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFind_149
		// Ljava/lang/reflect/Method;
		// 8814: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8817: ifnull +9 -> 8826
		// 8820: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8823: goto +12 -> 8835
		// 8826: ldc 91
		// 8828: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8831: dup
		// 8832: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8835: ldc 119
		// 8837: iconst_4
		// 8838: anewarray 143 java/lang/Class
		// 8841: dup
		// 8842: iconst_0
		// 8843: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8846: ifnull +9 -> 8855
		// 8849: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8852: goto +12 -> 8864
		// 8855: ldc 83
		// 8857: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8860: dup
		// 8861: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8864: aastore
		// 8865: dup
		// 8866: iconst_1
		// 8867: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8870: ifnull +9 -> 8879
		// 8873: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8876: goto +12 -> 8888
		// 8879: ldc 83
		// 8881: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8884: dup
		// 8885: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8888: aastore
		// 8889: dup
		// 8890: iconst_2
		// 8891: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8894: ifnull +9 -> 8903
		// 8897: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8900: goto +12 -> 8912
		// 8903: ldc 83
		// 8905: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8908: dup
		// 8909: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8912: aastore
		// 8913: dup
		// 8914: iconst_3
		// 8915: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 8918: aastore
		// 8919: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 8922: putstatic 328
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFindByObjectName_150
		// Ljava/lang/reflect/Method;
		// 8925: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8928: ifnull +9 -> 8937
		// 8931: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8934: goto +12 -> 8946
		// 8937: ldc 91
		// 8939: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8942: dup
		// 8943: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 8946: ldc 119
		// 8948: iconst_5
		// 8949: anewarray 143 java/lang/Class
		// 8952: dup
		// 8953: iconst_0
		// 8954: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8957: ifnull +9 -> 8966
		// 8960: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8963: goto +12 -> 8975
		// 8966: ldc 83
		// 8968: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8971: dup
		// 8972: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8975: aastore
		// 8976: dup
		// 8977: iconst_1
		// 8978: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8981: ifnull +9 -> 8990
		// 8984: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8987: goto +12 -> 8999
		// 8990: ldc 83
		// 8992: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 8995: dup
		// 8996: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 8999: aastore
		// 9000: dup
		// 9001: iconst_2
		// 9002: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9005: ifnull +9 -> 9014
		// 9008: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9011: goto +12 -> 9023
		// 9014: ldc 83
		// 9016: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9019: dup
		// 9020: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9023: aastore
		// 9024: dup
		// 9025: iconst_3
		// 9026: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 9029: aastore
		// 9030: dup
		// 9031: iconst_4
		// 9032: getstatic 362
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$$Ljava$lang$String
		// Ljava/lang/Class;
		// 9035: ifnull +9 -> 9044
		// 9038: getstatic 362
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$$Ljava$lang$String
		// Ljava/lang/Class;
		// 9041: goto +12 -> 9053
		// 9044: ldc 4
		// 9046: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9049: dup
		// 9050: putstatic 362
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:array$$Ljava$lang$String
		// Ljava/lang/Class;
		// 9053: aastore
		// 9054: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9057: putstatic 329
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFindByObjectName_151
		// Ljava/lang/reflect/Method;
		// 9060: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9063: ifnull +9 -> 9072
		// 9066: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9069: goto +12 -> 9081
		// 9072: ldc 91
		// 9074: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9077: dup
		// 9078: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9081: ldc 120
		// 9083: iconst_4
		// 9084: anewarray 143 java/lang/Class
		// 9087: dup
		// 9088: iconst_0
		// 9089: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9092: ifnull +9 -> 9101
		// 9095: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9098: goto +12 -> 9110
		// 9101: ldc 83
		// 9103: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9106: dup
		// 9107: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9110: aastore
		// 9111: dup
		// 9112: iconst_1
		// 9113: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9116: ifnull +9 -> 9125
		// 9119: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9122: goto +12 -> 9134
		// 9125: ldc 83
		// 9127: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9130: dup
		// 9131: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9134: aastore
		// 9135: dup
		// 9136: iconst_2
		// 9137: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9140: ifnull +9 -> 9149
		// 9143: getstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9146: goto +12 -> 9158
		// 9149: ldc 83
		// 9151: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9154: dup
		// 9155: putstatic 370
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$java$lang$String
		// Ljava/lang/Class;
		// 9158: aastore
		// 9159: dup
		// 9160: iconst_3
		// 9161: getstatic 355 java/lang/Boolean:TYPE Ljava/lang/Class;
		// 9164: aastore
		// 9165: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9168: putstatic 327
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_smartFindByObjectNameDirect_152
		// Ljava/lang/reflect/Method;
		// 9171: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9174: ifnull +9 -> 9183
		// 9177: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9180: goto +12 -> 9192
		// 9183: ldc 91
		// 9185: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9188: dup
		// 9189: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9192: ldc 121
		// 9194: iconst_0
		// 9195: anewarray 143 java/lang/Class
		// 9198: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9201: putstatic 332
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_startCheckpoint_153
		// Ljava/lang/reflect/Method;
		// 9204: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9207: ifnull +9 -> 9216
		// 9210: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9213: goto +12 -> 9225
		// 9216: ldc 91
		// 9218: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9221: dup
		// 9222: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9225: ldc 123
		// 9227: iconst_0
		// 9228: anewarray 143 java/lang/Class
		// 9231: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9234: putstatic 333
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_thisToBeUpdated_154
		// Ljava/lang/reflect/Method;
		// 9237: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9240: ifnull +9 -> 9249
		// 9243: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9246: goto +12 -> 9258
		// 9249: ldc 91
		// 9251: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9254: dup
		// 9255: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9258: ldc 124
		// 9260: iconst_0
		// 9261: anewarray 143 java/lang/Class
		// 9264: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9267: putstatic 334
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_toBeAdded_155
		// Ljava/lang/reflect/Method;
		// 9270: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9273: ifnull +9 -> 9282
		// 9276: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9279: goto +12 -> 9291
		// 9282: ldc 91
		// 9284: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9287: dup
		// 9288: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9291: ldc 125
		// 9293: iconst_0
		// 9294: anewarray 143 java/lang/Class
		// 9297: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9300: putstatic 335
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_toBeDeleted_156
		// Ljava/lang/reflect/Method;
		// 9303: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9306: ifnull +9 -> 9315
		// 9309: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9312: goto +12 -> 9324
		// 9315: ldc 91
		// 9317: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9320: dup
		// 9321: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9324: ldc 126
		// 9326: iconst_0
		// 9327: anewarray 143 java/lang/Class
		// 9330: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9333: putstatic 336
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_toBeSaved_157
		// Ljava/lang/reflect/Method;
		// 9336: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9339: ifnull +9 -> 9348
		// 9342: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9345: goto +12 -> 9357
		// 9348: ldc 91
		// 9350: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9353: dup
		// 9354: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9357: ldc 127
		// 9359: iconst_0
		// 9360: anewarray 143 java/lang/Class
		// 9363: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9366: putstatic 337
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_toBeUpdated_158
		// Ljava/lang/reflect/Method;
		// 9369: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9372: ifnull +9 -> 9381
		// 9375: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9378: goto +12 -> 9390
		// 9381: ldc 91
		// 9383: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9386: dup
		// 9387: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9390: ldc 128
		// 9392: iconst_0
		// 9393: anewarray 143 java/lang/Class
		// 9396: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9399: putstatic 338
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_toBeValidated_159
		// Ljava/lang/reflect/Method;
		// 9402: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9405: ifnull +9 -> 9414
		// 9408: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9411: goto +12 -> 9423
		// 9414: ldc 91
		// 9416: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9419: dup
		// 9420: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9423: ldc 130
		// 9425: iconst_0
		// 9426: anewarray 143 java/lang/Class
		// 9429: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9432: putstatic 339
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_undelete_160
		// Ljava/lang/reflect/Method;
		// 9435: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9438: ifnull +9 -> 9447
		// 9441: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9444: goto +12 -> 9456
		// 9447: ldc 91
		// 9449: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9452: dup
		// 9453: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9456: ldc 131
		// 9458: iconst_0
		// 9459: anewarray 143 java/lang/Class
		// 9462: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9465: putstatic 340
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_unselect_161
		// Ljava/lang/reflect/Method;
		// 9468: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9471: ifnull +9 -> 9480
		// 9474: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9477: goto +12 -> 9489
		// 9480: ldc 91
		// 9482: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9485: dup
		// 9486: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9489: ldc 132
		// 9491: iconst_0
		// 9492: anewarray 143 java/lang/Class
		// 9495: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9498: putstatic 342
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_validate_162
		// Ljava/lang/reflect/Method;
		// 9501: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9504: ifnull +9 -> 9513
		// 9507: getstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9510: goto +12 -> 9522
		// 9513: ldc 91
		// 9515: invokestatic 368 com/shuto/mam/app/operation/oplog/OpLog_Stub:class$
		// (Ljava/lang/String;)Ljava/lang/Class;
		// 9518: dup
		// 9519: putstatic 377
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:class$psdi$mbo$MboRemote
		// Ljava/lang/Class;
		// 9522: ldc 133
		// 9524: iconst_0
		// 9525: anewarray 143 java/lang/Class
		// 9528: invokevirtual 386 java/lang/Class:getMethod
		// (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
		// 9531: putstatic 341
		// com/shuto/mam/app/operation/oplog/OpLog_Stub:$method_validateAttributes_163
		// Ljava/lang/reflect/Method;
		// 9534: goto +14 -> 9548
		// 9537: pop
		// 9538: new 151 java/lang/NoSuchMethodError
		// 9541: dup
		// 9542: ldc 122
		// 9544: invokespecial 349 java/lang/NoSuchMethodError:<init>
		// (Ljava/lang/String;)V
		// 9547: athrow
		// 9548: return
		//
		// Exception table:
		// from to target type
		// 0 9534 9537 java/lang/NoSuchMethodException
	}

	public OpLog_Stub(RemoteRef ref) {
		super(ref);
	}

	public void add() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_add_0, null, 7442693827464960371L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void addMboSetForRequiredCheck(MboSetRemote $param_MboSetRemote_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_addMboSetForRequiredCheck_1, new Object[] { $param_MboSetRemote_1 },
					-5338562565545028087L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void addToDeleteForInsertList(String $param_String_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_addToDeleteForInsertList_2, new Object[] { $param_String_1 },
					-6655771782122869349L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote blindCopy(MboSetRemote $param_MboSetRemote_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_blindCopy_3, new Object[] { $param_MboSetRemote_1 },
					-4018632747186293956L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void checkMethodAccess(String $param_String_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_checkMethodAccess_4, new Object[] { $param_String_1 }, 8770342446443124381L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void clear() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_clear_5, null, -7475254351993695499L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote copy() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_copy_6, null, 7357015738026087482L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote copy(MboSetRemote $param_MboSetRemote_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_copy_7, new Object[] { $param_MboSetRemote_1 },
					-4117456723192037795L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote copy(MboSetRemote $param_MboSetRemote_1, long $param_long_2) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_copy_8,
					new Object[] { $param_MboSetRemote_1, new Long($param_long_2) }, 6140987686178264144L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote copyFake(MboSetRemote $param_MboSetRemote_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_copyFake_9, new Object[] { $param_MboSetRemote_1 },
					1036720388622533370L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void copyValue(MboRemote $param_MboRemote_1, String $param_String_2, String $param_String_3,
			long $param_long_4) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_copyValue_10,
					new Object[] { $param_MboRemote_1, $param_String_2, $param_String_3, new Long($param_long_4) },
					2058941549748026920L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void copyValue(MboRemote $param_MboRemote_1, String[] $param_arrayOf_String_2,
			String[] $param_arrayOf_String_3, long $param_long_4) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_copyValue_11, new Object[] { $param_MboRemote_1, $param_arrayOf_String_2,
					$param_arrayOf_String_3, new Long($param_long_4) }, 799583690265436859L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote createComm() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_createComm_12, null, 6383061083541968967L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void delete() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_delete_13, null, 5524676105212060426L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void delete(long $param_long_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_delete_14, new Object[] { new Long($param_long_1) }, -4309379989353443610L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote duplicate() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_duplicate_15, null, 1223086467188012123L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean evaluateCondition(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_evaluateCondition_16, new Object[] { $param_String_1 },
					8089789934617172671L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public HashMap evaluateCtrlConditions(HashSet $param_HashSet_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_evaluateCtrlConditions_17, new Object[] { $param_HashSet_1 },
					-1109759550070022850L);
			return (HashMap) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public HashMap evaluateCtrlConditions(HashSet $param_HashSet_1, String $param_String_2)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_evaluateCtrlConditions_18,
					new Object[] { $param_HashSet_1, $param_String_2 }, -6655192765964905902L);
			return (HashMap) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean excludeObjectForPropagate(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_excludeObjectForPropagate_19,
					new Object[] { $param_String_1 }, 2917212447191974118L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void generateAutoKey() throws RemoteException, MXException {
		try {
			this.ref.invoke(this, $method_generateAutoKey_20, null, 2070061064054472488L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean getBoolean(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getBoolean_21, new Object[] { $param_String_1 },
					-1640992992330807345L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public byte getByte(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getByte_22, new Object[] { $param_String_1 },
					3166015741238752943L);
			return ((Byte) $result).byteValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public byte[] getBytes(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getBytes_23, new Object[] { $param_String_1 },
					-3054736941581443291L);
			return (byte[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Object[] getCommLogOwnerNameAndUniqueId() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getCommLogOwnerNameAndUniqueId_24, null,
					1610923751341104359L);
			return (Object[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Object getDatabaseValue(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDatabaseValue_25, new Object[] { $param_String_1 },
					-2505053288975065790L);
			return $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Date getDate(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDate_26, new Object[] { $param_String_1 },
					25358525752956448L);
			return (Date) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Vector getDeleteForInsertList() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDeleteForInsertList_27, null, -6605650050775173454L);
			return (Vector) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public int getDocLinksCount() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDocLinksCount_28, null, 2377991189333645900L);
			return ((Integer) $result).intValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String[] getDomainIDs(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDomainIDs_29, new Object[] { $param_String_1 },
					-5383783585694635747L);
			return (String[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public double getDouble(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getDouble_30, new Object[] { $param_String_1 },
					-7136627451769557504L);
			return ((Double) $result).doubleValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getExistingMboSet(String $param_String_1) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getExistingMboSet_31, new Object[] { $param_String_1 },
					-2344305783824064482L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public long getFlags() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getFlags_32, null, 8881435422980061864L);
			return ((Long) $result).longValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public float getFloat(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getFloat_33, new Object[] { $param_String_1 },
					-4592236820643884030L);
			return ((Float) $result).floatValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MaxType getInitialValue(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInitialValue_34, new Object[] { $param_String_1 },
					-4159234615084602283L);
			return (MaxType) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getInsertCompanySetId() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInsertCompanySetId_35, null, 5765642510693535051L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getInsertItemSetId() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInsertItemSetId_36, null, 402668792455980798L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getInsertOrganization() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInsertOrganization_37, null, 1777454063904355147L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getInsertSite() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInsertSite_38, null, 1869000665442854119L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public int getInt(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getInt_39, new Object[] { $param_String_1 },
					6551869032578983177L);
			return ((Integer) $result).intValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public KeyValue getKeyValue() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getKeyValue_40, null, 1865032822986385588L);
			return (KeyValue) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getLinesRelationship() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getLinesRelationship_41, null, 7593554042000654750L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getList(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getList_42, new Object[] { $param_String_1 },
					-1226607622080901807L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public long getLong(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getLong_43, new Object[] { $param_String_1 },
					1123300209586097136L);
			return ((Long) $result).longValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MXTransaction getMXTransaction() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMXTransaction_44, null, 5626709230336731958L);
			return (MXTransaction) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMatchingAttr(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMatchingAttr_45, new Object[] { $param_String_1 },
					-372807487548582674L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMatchingAttr(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMatchingAttr_46,
					new Object[] { $param_String_1, $param_String_2 }, 8865467643363211950L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Object[] getMatchingAttrs(String $param_String_1, String $param_String_2)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMatchingAttrs_47,
					new Object[] { $param_String_1, $param_String_2 }, -7209878759219369905L);
			return (Object[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MaxMessage getMaxMessage(String $param_String_1, String $param_String_2)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMaxMessage_48,
					new Object[] { $param_String_1, $param_String_2 }, -1770727576702508461L);
			return (MaxMessage) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboData getMboData(String[] $param_arrayOf_String_1) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboData_49, new Object[] { $param_arrayOf_String_1 },
					-5046015836519728268L);
			return (MboData) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Vector getMboDataSet(String $param_String_1) throws RemoteException, MXException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboDataSet_50, new Object[] { $param_String_1 },
					-7416455740491744025L);
			return (Vector) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MaxType getMboInitialValue(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboInitialValue_51, new Object[] { $param_String_1 },
					4229764382934053882L);
			return (MaxType) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public List getMboList(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboList_52, new Object[] { $param_String_1 },
					1631666615088706231L);
			return (List) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getMboSet(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboSet_53, new Object[] { $param_String_1 },
					4352936676464469835L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getMboSet(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboSet_54,
					new Object[] { $param_String_1, $param_String_2 }, -1016661797923200850L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getMboSet(String $param_String_1, String $param_String_2, String $param_String_3)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboSet_55,
					new Object[] { $param_String_1, $param_String_2, $param_String_3 }, -2754101075503716989L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboValueData getMboValueData(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboValueData_56, new Object[] { $param_String_1 },
					-2193850169204155020L);
			return (MboValueData) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboValueData getMboValueData(String $param_String_1, boolean $param_boolean_2)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboValueData_57,
					new Object[] { $param_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE },
					-3257167741483570332L);
			return (MboValueData) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboValueData[] getMboValueData(String[] $param_arrayOf_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboValueData_58, new Object[] { $param_arrayOf_String_1 },
					-3046682349766384472L);
			return (MboValueData[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboValueInfoStatic getMboValueInfoStatic(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboValueInfoStatic_59, new Object[] { $param_String_1 },
					-4328088463610638087L);
			return (MboValueInfoStatic) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboValueInfoStatic[] getMboValueInfoStatic(String[] $param_arrayOf_String_1)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMboValueInfoStatic_60,
					new Object[] { $param_arrayOf_String_1 }, -169869964566830779L);
			return (MboValueInfoStatic[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMessage(String $param_String_1, String $param_String_2) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMessage_61,
					new Object[] { $param_String_1, $param_String_2 }, -5117172076054138989L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMessage(String $param_String_1, String $param_String_2, Object $param_Object_3)
			throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMessage_62,
					new Object[] { $param_String_1, $param_String_2, $param_Object_3 }, 5002469433788530020L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMessage(String $param_String_1, String $param_String_2, Object[] $param_arrayOf_Object_3)
			throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMessage_63,
					new Object[] { $param_String_1, $param_String_2, $param_arrayOf_Object_3 }, -5220667813980826248L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getMessage(MXException $param_MXException_1) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getMessage_64, new Object[] { $param_MXException_1 },
					-4392176690452392965L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getName() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getName_65, null, 6317137956467216454L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getOrgForGL(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getOrgForGL_66, new Object[] { $param_String_1 },
					-297533474176735503L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getOrgSiteForMaxvar(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getOrgSiteForMaxvar_67, new Object[] { $param_String_1 },
					6081533744683337893L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboRemote getOwner() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getOwner_68, null, 2290236231147060375L);
			return (MboRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean getPropagateKeyFlag() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getPropagateKeyFlag_69, null, -5538177702501041821L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getRecordIdentifer() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getRecordIdentifer_70, null, -7011768566766147390L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String[] getSiteOrg() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getSiteOrg_71, null, 5727159326898518166L);
			return (String[]) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getString(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getString_72, new Object[] { $param_String_1 },
					5066930371966209369L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getString(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getString_73,
					new Object[] { $param_String_1, $param_String_2 }, 4681388861163595976L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getStringInBaseLanguage(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getStringInBaseLanguage_74, new Object[] { $param_String_1 },
					-1632931176936624329L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getStringInSpecificLocale(String $param_String_1, Locale $param_Locale_2, TimeZone $param_TimeZone_3)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getStringInSpecificLocale_75,
					new Object[] { $param_String_1, $param_Locale_2, $param_TimeZone_3 }, 8365760013188051278L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getStringTransparent(String $param_String_1, String $param_String_2)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getStringTransparent_76,
					new Object[] { $param_String_1, $param_String_2 }, -3695525249492534072L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote getThisMboSet() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getThisMboSet_77, null, -8653256074306703933L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getUniqueIDName() throws RemoteException, MXException {
		try {
			Object $result = this.ref.invoke(this, $method_getUniqueIDName_78, null, -4382675799323972988L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public long getUniqueIDValue() throws RemoteException, MXException {
		try {
			Object $result = this.ref.invoke(this, $method_getUniqueIDValue_79, null, 2423491830152826501L);
			return ((Long) $result).longValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public UserInfo getUserInfo() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getUserInfo_80, null, -6594617694786131693L);
			return (UserInfo) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public String getUserName() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_getUserName_81, null, 483502017080265922L);
			return (String) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean hasHierarchyLink() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_hasHierarchyLink_82, null, -5328975296699729730L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isAutoKeyed(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isAutoKeyed_83, new Object[] { $param_String_1 },
					-879194310374197922L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isBasedOn(String $param_String_1) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isBasedOn_84, new Object[] { $param_String_1 },
					6201297079127551930L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isFlagSet(long $param_long_1) throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isFlagSet_85, new Object[] { new Long($param_long_1) },
					-7088243327149326417L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isForDM() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isForDM_86, null, 2873211367698253517L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isModified() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isModified_87, null, 5708482053152154285L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isModified(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isModified_88, new Object[] { $param_String_1 },
					4585372949070100938L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isNew() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isNew_89, null, 6442781755907520873L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isNull(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isNull_90, new Object[] { $param_String_1 },
					-4712365544638525211L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isSelected() throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isSelected_91, null, 4258462717937186951L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean isZombie() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_isZombie_92, null, 3924586547093250132L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void propagateKeyValue(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_propagateKeyValue_93, new Object[] { $param_String_1, $param_String_2 },
					5838101552568681721L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void rollbackToCheckpoint() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_rollbackToCheckpoint_94, null, 4883480516303419745L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void select() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_select_95, null, -1495729093048004794L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setApplicationError(String $param_String_1, ApplicationError $param_ApplicationError_2)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setApplicationError_96,
					new Object[] { $param_String_1, $param_ApplicationError_2 }, 6332578525541894392L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setApplicationRequired(String $param_String_1, boolean $param_boolean_2)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setApplicationRequired_97,
					new Object[] { $param_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE },
					9097600827641925507L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setCopyDefaults() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setCopyDefaults_98, null, -8845229049221431625L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setDeleted(boolean $param_boolean_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setDeleted_99,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -1638088789301976208L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setESigFieldModified(boolean $param_boolean_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setESigFieldModified_100,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -4983321710710401682L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String $param_String_1, long $param_long_2, boolean $param_boolean_3)
			throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_101, new Object[] { $param_String_1, new Long($param_long_2),
					($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, -5529491389076586840L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String $param_String_1, long $param_long_2, boolean $param_boolean_3,
			MXException $param_MXException_4) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_102,
					new Object[] { $param_String_1, new Long($param_long_2),
							($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE, $param_MXException_4 },
					5770702900775330002L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String[] $param_arrayOf_String_1, long $param_long_2, boolean $param_boolean_3)
			throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_103, new Object[] { $param_arrayOf_String_1,
					new Long($param_long_2), ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE },
					-5393903062192518457L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String[] $param_arrayOf_String_1, long $param_long_2, boolean $param_boolean_3,
			MXException $param_MXException_4) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_104,
					new Object[] { $param_arrayOf_String_1, new Long($param_long_2),
							($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE, $param_MXException_4 },
					-1245260593337479812L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String[] $param_arrayOf_String_1, boolean $param_boolean_2, long $param_long_3,
			boolean $param_boolean_4) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_105,
					new Object[] { $param_arrayOf_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE,
							new Long($param_long_3), ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE },
					1472859374333820580L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlag(String[] $param_arrayOf_String_1, boolean $param_boolean_2, long $param_long_3,
			boolean $param_boolean_4, MXException $param_MXException_5) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlag_106,
					new Object[] { $param_arrayOf_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE,
							new Long($param_long_3), ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE,
							$param_MXException_5 },
					-1209563117899662500L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFieldFlags(String $param_String_1, long $param_long_2) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setFieldFlags_107, new Object[] { $param_String_1, new Long($param_long_2) },
					1591237052410980710L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFlag(long $param_long_1, boolean $param_boolean_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setFlag_108,
					new Object[] { new Long($param_long_1), ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE },
					8152726795599941974L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFlag(long $param_long_1, boolean $param_boolean_2, MXException $param_MXException_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(
					this, $method_setFlag_109, new Object[] { new Long($param_long_1),
							($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE, $param_MXException_3 },
					-568127893371775973L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setFlags(long $param_long_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setFlags_110, new Object[] { new Long($param_long_1) }, 8574959450838984319L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setForDM(boolean $param_boolean_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setForDM_111,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -37119969352629619L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setMLValue(String $param_String_1, String $param_String_2, String $param_String_3, long $param_long_4)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setMLValue_112,
					new Object[] { $param_String_1, $param_String_2, $param_String_3, new Long($param_long_4) },
					6487062711357833068L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setModified(boolean $param_boolean_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setModified_113,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -2178246973424322698L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setNewMbo(boolean $param_boolean_1) throws RemoteException {
		try {
			this.ref.invoke(this, $method_setNewMbo_114,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -8330971555555310601L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setPropagateKeyFlag(boolean $param_boolean_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setPropagateKeyFlag_115,
					new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -8309901174032264787L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setPropagateKeyFlag(String[] $param_arrayOf_String_1, boolean $param_boolean_2)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setPropagateKeyFlag_116,
					new Object[] { $param_arrayOf_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE },
					-2999468859019732148L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setReferencedMbo(String $param_String_1, Mbo $param_Mbo_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setReferencedMbo_117, new Object[] { $param_String_1, $param_Mbo_2 },
					-7091839046965254272L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, byte $param_byte_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_118, new Object[] { $param_String_1, new Byte($param_byte_2) },
					3270551574198177870L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, byte $param_byte_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_119,
					new Object[] { $param_String_1, new Byte($param_byte_2), new Long($param_long_3) },
					-243985487831981328L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, double $param_double_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_120, new Object[] { $param_String_1, new Double($param_double_2) },
					-7524981934498388763L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, double $param_double_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_121,
					new Object[] { $param_String_1, new Double($param_double_2), new Long($param_long_3) },
					-168439541455018744L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, float $param_float_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_122, new Object[] { $param_String_1, new Float($param_float_2) },
					-2815589486362369060L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, float $param_float_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_123,
					new Object[] { $param_String_1, new Float($param_float_2), new Long($param_long_3) },
					7169252791071186101L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, int $param_int_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_124, new Object[] { $param_String_1, new Integer($param_int_2) },
					8850354658795100389L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, int $param_int_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_125,
					new Object[] { $param_String_1, new Integer($param_int_2), new Long($param_long_3) },
					3993773668554685290L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, long $param_long_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_126, new Object[] { $param_String_1, new Long($param_long_2) },
					9210802592731375364L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, long $param_long_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_127,
					new Object[] { $param_String_1, new Long($param_long_2), new Long($param_long_3) },
					6848715728568018278L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_128, new Object[] { $param_String_1, $param_String_2 },
					-2811644617196606099L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, String $param_String_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_129,
					new Object[] { $param_String_1, $param_String_2, new Long($param_long_3) }, -4261472768839578905L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, Date $param_Date_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_130, new Object[] { $param_String_1, $param_Date_2 },
					-2630749704591450137L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, Date $param_Date_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_131,
					new Object[] { $param_String_1, $param_Date_2, new Long($param_long_3) }, 7971076697990243292L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, MboRemote $param_MboRemote_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_132, new Object[] { $param_String_1, $param_MboRemote_2 },
					-3620476831865796680L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, MboSetRemote $param_MboSetRemote_2)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_133, new Object[] { $param_String_1, $param_MboSetRemote_2 },
					-3537182409801315763L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, MaxType $param_MaxType_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_134,
					new Object[] { $param_String_1, $param_MaxType_2, new Long($param_long_3) }, -572289542766185319L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, short $param_short_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_135, new Object[] { $param_String_1, new Short($param_short_2) },
					-592203831455696145L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, short $param_short_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_136,
					new Object[] { $param_String_1, new Short($param_short_2), new Long($param_long_3) },
					-6261639766806276381L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, boolean $param_boolean_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_137,
					new Object[] { $param_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE },
					4990140584423208903L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, boolean $param_boolean_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(
					this, $method_setValue_138, new Object[] { $param_String_1,
							($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE, new Long($param_long_3) },
					8236575036597348343L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, byte[] $param_arrayOf_byte_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_139, new Object[] { $param_String_1, $param_arrayOf_byte_2 },
					-5271144966979799580L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValue(String $param_String_1, byte[] $param_arrayOf_byte_2, long $param_long_3)
			throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValue_140,
					new Object[] { $param_String_1, $param_arrayOf_byte_2, new Long($param_long_3) },
					1093725565992944082L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValueNull(String $param_String_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValueNull_141, new Object[] { $param_String_1 }, -362562597341262986L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void setValueNull(String $param_String_1, long $param_long_2) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_setValueNull_142, new Object[] { $param_String_1, new Long($param_long_2) },
					5998575739150575662L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void sigOptionAccessAuthorized(String $param_String_1) throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_sigOptionAccessAuthorized_143, new Object[] { $param_String_1 },
					4364214440166883643L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean sigopGranted(String $param_String_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_sigopGranted_144, new Object[] { $param_String_1 },
					2700460581989440209L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean sigopGranted(String $param_String_1, String $param_String_2) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_sigopGranted_145,
					new Object[] { $param_String_1, $param_String_2 }, 334852619251685037L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public HashMap sigopGranted(Set $param_Set_1) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_sigopGranted_146, new Object[] { $param_Set_1 },
					5831994481824058998L);
			return (HashMap) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFill(String $param_String_1, String $param_String_2, boolean $param_boolean_3)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_smartFill_147, new Object[] { $param_String_1,
					$param_String_2, ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, -935282078909453374L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFind(String $param_String_1, String $param_String_2, String $param_String_3,
			boolean $param_boolean_4) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_smartFind_148, new Object[] { $param_String_1,
					$param_String_2, $param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE },
					-1456117861212734379L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFind(String $param_String_1, String $param_String_2, boolean $param_boolean_3)
			throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_smartFind_149, new Object[] { $param_String_1,
					$param_String_2, ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, 615902001724753702L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFindByObjectName(String $param_String_1, String $param_String_2, String $param_String_3,
			boolean $param_boolean_4) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_smartFindByObjectName_150, new Object[] { $param_String_1,
					$param_String_2, $param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE },
					9174066938115694658L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFindByObjectName(String $param_String_1, String $param_String_2, String $param_String_3,
			boolean $param_boolean_4, String[][] $param_arrayOf_arrayOf_String_5) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_smartFindByObjectName_151,
					new Object[] { $param_String_1, $param_String_2, $param_String_3,
							($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE, $param_arrayOf_arrayOf_String_5 },
					-4824432416975490754L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public MboSetRemote smartFindByObjectNameDirect(String $param_String_1, String $param_String_2,
			String $param_String_3, boolean $param_boolean_4) throws MXException, RemoteException {
		try {
			Object $result = this.ref.invoke(
					this, $method_smartFindByObjectNameDirect_152, new Object[] { $param_String_1, $param_String_2,
							$param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE },
					-6639922775789924002L);
			return (MboSetRemote) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void startCheckpoint() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_startCheckpoint_153, null, 8105257734697951775L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean thisToBeUpdated() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_thisToBeUpdated_154, null, 7976169955117495941L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean toBeAdded() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_toBeAdded_155, null, -8509333918488694701L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean toBeDeleted() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_toBeDeleted_156, null, 6603782709086639129L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean toBeSaved() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_toBeSaved_157, null, -4334682600408332364L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean toBeUpdated() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_toBeUpdated_158, null, 7772394697164632407L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public boolean toBeValidated() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_toBeValidated_159, null, -6229722679165061322L);
			return ((Boolean) $result).booleanValue();
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void undelete() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_undelete_160, null, -3450598412706392512L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void unselect() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_unselect_161, null, -4036016416924417120L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public void validate() throws MXException, RemoteException {
		try {
			this.ref.invoke(this, $method_validate_162, null, -8368415688081130249L);
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (MXException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	public Hashtable validateAttributes() throws RemoteException {
		try {
			Object $result = this.ref.invoke(this, $method_validateAttributes_163, null, 6372158466213621440L);
			return (Hashtable) $result;
		} catch (RuntimeException e) {
			throw e;
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			throw new UnexpectedException("undeclared checked exception", e);
		}
	}

	@Override
    public boolean getIgnoreLockCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public String getLockedByDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean hasLockSaveRights(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean isLocked(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean isMasterTenant(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean isMboLockedByMe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean isOptionGranted(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public void lock(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
    public void setAutokeyFields() {
		// TODO Auto-generated method stub

	}

	@Override
    public void setIgnoreRecordLockCheck(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
    public void setReferencedMbo(String arg0, MboRemote arg1) {
		// TODO Auto-generated method stub

	}

	@Override
    public void unlock(boolean arg0) {
		// TODO Auto-generated method stub

	}
}