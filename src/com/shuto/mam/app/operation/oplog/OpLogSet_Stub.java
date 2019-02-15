package com.shuto.mam.app.operation.oplog;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import psdi.common.erm.ERMEntity;
import psdi.mbo.MaxMessage;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetData;
import psdi.mbo.MboSetInfo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboSetRetainMboPositionData;
import psdi.mbo.MboSetRetainMboPositionInfo;
import psdi.mbo.MboValueData;
import psdi.mbo.MboValueInfoStatic;
import psdi.security.ProfileRemote;
import psdi.security.UserInfo;
import psdi.txn.MXTransaction;
import psdi.util.BitFlag;
import psdi.util.MXException;

public final class OpLogSet_Stub extends RemoteStub
  implements OpLogSetRemote, MboSetRemote
{
  private static final long serialVersionUID = 2L;
  private static Method $method_abortSql_0;
  private static Method $method_add_1;
  private static Method $method_add_2;
  private static Method $method_addAtEnd_3;
  private static Method $method_addAtEnd_4;
  private static Method $method_addAtIndex_5;
  private static Method $method_addAtIndex_6;
  private static Method $method_addFakeAtEnd_7;
  private static Method $method_addSubQbe_8;
  private static Method $method_addSubQbe_9;
  private static Method $method_addSubQbe_10;
  private static Method $method_addSubQbe_11;
  private static Method $method_addWarning_12;
  private static Method $method_addWarnings_13;
  private static Method $method_checkMethodAccess_14;
  private static Method $method_cleanup_15;
  private static Method $method_clear_16;
  private static Method $method_clearLongOpPipe_17;
  private static Method $method_close_18;
  private static Method $method_commit_19;
  private static Method $method_commitTransaction_20;
  private static Method $method_copy_21;
  private static Method $method_copy_22;
  private static Method $method_copyForDM_23;
  private static Method $method_count_24;
  private static Method $method_count_25;
  private static Method $method_deleteAll_26;
  private static Method $method_deleteAll_27;
  private static Method $method_deleteAndRemove_28;
  private static Method $method_deleteAndRemove_29;
  private static Method $method_deleteAndRemove_30;
  private static Method $method_deleteAndRemove_31;
  private static Method $method_deleteAndRemove_32;
  private static Method $method_deleteAndRemoveAll_33;
  private static Method $method_deleteAndRemoveAll_34;
  private static Method $method_determineRequiredFieldsFromERM_35;
  private static Method $method_earliestDate_36;
  private static Method $method_fetchNext_37;
  private static Method $method_findAllNullRequiredFields_38;
  private static Method $method_findByIntegrationKey_39;
  private static Method $method_findKey_40;
  private static Method $method_fireEventsAfterDB_41;
  private static Method $method_fireEventsAfterDBCommit_42;
  private static Method $method_fireEventsBeforeDB_43;
  private static Method $method_getApp_44;
  private static Method $method_getAppAlwaysFieldFlags_45;
  private static Method $method_getAppWhere_46;
  private static Method $method_getBoolean_47;
  private static Method $method_getByte_48;
  private static Method $method_getBytes_49;
  private static Method $method_getCompleteWhere_50;
  private static Method $method_getCurrentPosition_51;
  private static Method $method_getDBFetchMaxRows_52;
  private static Method $method_getDate_53;
  private static Method $method_getDefaultValue_54;
  private static Method $method_getDouble_55;
  private static Method $method_getERMEntity_56;
  private static Method $method_getESigTransactionId_57;
  private static Method $method_getExcludeMeFromPropagation_58;
  private static Method $method_getFlags_59;
  private static Method $method_getFloat_60;
  private static Method $method_getInt_61;
  private static Method $method_getKeyAttributes_62;
  private static Method $method_getList_63;
  private static Method $method_getList_64;
  private static Method $method_getLong_65;
  private static Method $method_getMLFromClause_66;
  private static Method $method_getMXTransaction_67;
  private static Method $method_getMaxMessage_68;
  private static Method $method_getMbo_69;
  private static Method $method_getMbo_70;
  private static Method $method_getMboForUniqueId_71;
  private static Method $method_getMboSetData_72;
  private static Method $method_getMboSetData_73;
  private static Method $method_getMboSetInfo_74;
  private static Method $method_getMboSetRetainMboPositionData_75;
  private static Method $method_getMboSetRetainMboPositionInfo_76;
  private static Method $method_getMboSetValueData_77;
  private static Method $method_getMboValueData_78;
  private static Method $method_getMboValueData_79;
  private static Method $method_getMboValueData_80;
  private static Method $method_getMboValueInfoStatic_81;
  private static Method $method_getMboValueInfoStatic_82;
  private static Method $method_getMessage_83;
  private static Method $method_getMessage_84;
  private static Method $method_getMessage_85;
  private static Method $method_getMessage_86;
  private static Method $method_getName_87;
  private static Method $method_getOrderBy_88;
  private static Method $method_getOwner_89;
  private static Method $method_getParentApp_90;
  private static Method $method_getProfile_91;
  private static Method $method_getQbe_92;
  private static Method $method_getQbe_93;
  private static Method $method_getQbe_94;
  private static Method $method_getQueryTimeout_95;
  private static Method $method_getRelationName_96;
  private static Method $method_getRelationship_97;
  private static Method $method_getSQLOptions_98;
  private static Method $method_getSelection_99;
  private static Method $method_getSelectionWhere_100;
  private static Method $method_getSize_101;
  private static Method $method_getString_102;
  private static Method $method_getTxnPropertyMap_103;
  private static Method $method_getUserAndQbeWhere_104;
  private static Method $method_getUserInfo_105;
  private static Method $method_getUserName_106;
  private static Method $method_getUserWhere_107;
  private static Method $method_getWarnings_108;
  private static Method $method_getWhere_109;
  private static Method $method_getZombie_110;
  private static Method $method_hasMLQbe_111;
  private static Method $method_hasQbe_112;
  private static Method $method_hasWarnings_113;
  private static Method $method_ignoreQbeExactMatchSet_114;
  private static Method $method_incrementDeletedCount_115;
  private static Method $method_init_116;
  private static Method $method_isBasedOn_117;
  private static Method $method_isDMDeploySet_118;
  private static Method $method_isDMSkipFieldValidation_119;
  private static Method $method_isESigNeeded_120;
  private static Method $method_isEmpty_121;
  private static Method $method_isFlagSet_122;
  private static Method $method_isNull_123;
  private static Method $method_isQbeCaseSensitive_124;
  private static Method $method_isQbeExactMatch_125;
  private static Method $method_isRetainMboPosition_126;
  private static Method $method_latestDate_127;
  private static Method $method_locateMbo_128;
  private static Method $method_logESigVerification_129;
  private static Method $method_max_130;
  private static Method $method_min_131;
  private static Method $method_moveFirst_132;
  private static Method $method_moveLast_133;
  private static Method $method_moveNext_134;
  private static Method $method_movePrev_135;
  private static Method $method_moveTo_136;
  private static Method $method_notExist_137;
  private static Method $method_positionState_138;
  private static Method $method_processML_139;
  private static Method $method_remove_140;
  private static Method $method_remove_141;
  private static Method $method_remove_142;
  private static Method $method_reset_143;
  private static Method $method_resetQbe_144;
  private static Method $method_resetWithSelection_145;
  private static Method $method_rollback_146;
  private static Method $method_rollbackToCheckpoint_147;
  private static Method $method_rollbackToCheckpoint_148;
  private static Method $method_rollbackTransaction_149;
  private static Method $method_save_150;
  private static Method $method_save_151;
  private static Method $method_saveTransaction_152;
  private static Method $method_select_153;
  private static Method $method_select_154;
  private static Method $method_select_155;
  private static Method $method_selectAll_156;
  private static Method $method_setAllowQualifiedRestriction_157;
  private static Method $method_setApp_158;
  private static Method $method_setAppAlwaysFieldFlag_159;
  private static Method $method_setAppWhere_160;
  private static Method $method_setAutoKeyFlag_161;
  private static Method $method_setDBFetchMaxRows_162;
  private static Method $method_setDMDeploySet_163;
  private static Method $method_setDMSkipFieldValidation_164;
  private static Method $method_setDefaultOrderBy_165;
  private static Method $method_setDefaultValue_166;
  private static Method $method_setDefaultValue_167;
  private static Method $method_setDefaultValues_168;
  private static Method $method_setERMEntity_169;
  private static Method $method_setESigFieldModified_170;
  private static Method $method_setExcludeMeFromPropagation_171;
  private static Method $method_setFlag_172;
  private static Method $method_setFlag_173;
  private static Method $method_setFlags_174;
  private static Method $method_setInsertCompanySet_175;
  private static Method $method_setInsertItemSet_176;
  private static Method $method_setInsertOrg_177;
  private static Method $method_setInsertSite_178;
  private static Method $method_setLastESigTransId_179;
  private static Method $method_setLogLargFetchResultDisabled_180;
  private static Method $method_setMXTransaction_181;
  private static Method $method_setMboSetInfo_182;
  private static Method $method_setNoNeedtoFetchFromDB_183;
  private static Method $method_setOrderBy_184;
  private static Method $method_setOwner_185;
  private static Method $method_setQbe_186;
  private static Method $method_setQbe_187;
  private static Method $method_setQbe_188;
  private static Method $method_setQbe_189;
  private static Method $method_setQbe_190;
  private static Method $method_setQbeCaseSensitive_191;
  private static Method $method_setQbeCaseSensitive_192;
  private static Method $method_setQbeExactMatch_193;
  private static Method $method_setQbeExactMatch_194;
  private static Method $method_setQbeOperatorOr_195;
  private static Method $method_setQueryBySiteQbe_196;
  private static Method $method_setQueryTimeout_197;
  private static Method $method_setRelationName_198;
  private static Method $method_setRelationship_199;
  private static Method $method_setRequiedFlagsFromERM_200;
  private static Method $method_setRetainMboPosition_201;
  private static Method $method_setSQLOptions_202;
  private static Method $method_setTableDomainLookup_203;
  private static Method $method_setTxnPropertyMap_204;
  private static Method $method_setUserWhere_205;
  private static Method $method_setUserWhereAfterParse_206;
  private static Method $method_setValue_207;
  private static Method $method_setValue_208;
  private static Method $method_setValue_209;
  private static Method $method_setValue_210;
  private static Method $method_setValue_211;
  private static Method $method_setValue_212;
  private static Method $method_setValue_213;
  private static Method $method_setValue_214;
  private static Method $method_setValue_215;
  private static Method $method_setValue_216;
  private static Method $method_setValue_217;
  private static Method $method_setValue_218;
  private static Method $method_setValue_219;
  private static Method $method_setValue_220;
  private static Method $method_setValue_221;
  private static Method $method_setValue_222;
  private static Method $method_setValue_223;
  private static Method $method_setValue_224;
  private static Method $method_setValue_225;
  private static Method $method_setValue_226;
  private static Method $method_setValueNull_227;
  private static Method $method_setValueNull_228;
  private static Method $method_setWhere_229;
  private static Method $method_setWhereQbe_230;
  private static Method $method_setupLongOpPipe_231;
  private static Method $method_smartFill_232;
  private static Method $method_smartFill_233;
  private static Method $method_smartFind_234;
  private static Method $method_smartFind_235;
  private static Method $method_startCheckpoint_236;
  private static Method $method_startCheckpoint_237;
  private static Method $method_sum_238;
  private static Method $method_toBeSaved_239;
  private static Method $method_undeleteAll_240;
  private static Method $method_undoTransaction_241;
  private static Method $method_unselect_242;
  private static Method $method_unselect_243;
  private static Method $method_unselect_244;
  private static Method $method_unselectAll_245;
  private static Method $method_useStoredQuery_246;
  private static Method $method_validate_247;
  private static Method $method_validateTransaction_248;
  private static Method $method_verifyESig_249;
  static Class array$Ljava$lang$String;
  static Class array$Lpsdi$util$MXException;
  static Class array$Ljava$lang$Object;
  static Class array$B;

  // ERROR //
  static
  {
 
  }

  public OpLogSet_Stub(RemoteRef ref)
  {
    super(ref);
  }

  public void abortSql()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_abortSql_0, null, -7838268418889321589L);
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

  public MboRemote add()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_add_1, null, -3066705374630471138L);
      return (MboRemote)$result;
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

  public MboRemote add(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_add_2, new Object[] { new Long($param_long_1) }, -4781561932342219587L);
      return (MboRemote)$result;
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

  public MboRemote addAtEnd()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_addAtEnd_3, null, 195274362947297798L);
      return (MboRemote)$result;
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

  public MboRemote addAtEnd(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_addAtEnd_4, new Object[] { new Long($param_long_1) }, 6921395039880217317L);
      return (MboRemote)$result;
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

  public MboRemote addAtIndex(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_addAtIndex_5, new Object[] { new Integer($param_int_1) }, -651694666862096163L);
      return (MboRemote)$result;
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

  public MboRemote addAtIndex(long $param_long_1, int $param_int_2)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_addAtIndex_6, new Object[] { new Long($param_long_1), new Integer($param_int_2) }, 647785868130954428L);
      return (MboRemote)$result;
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

  public MboRemote addFakeAtEnd()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_addFakeAtEnd_7, null, -2259915494540129010L);
      return (MboRemote)$result;
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

  public void addSubQbe(String $param_String_1, String $param_String_2, String[] $param_arrayOf_String_3, String $param_String_4)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_addSubQbe_8, new Object[] { $param_String_1, $param_String_2, $param_arrayOf_String_3, $param_String_4 }, -1363903634389208836L);
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

  public void addSubQbe(String $param_String_1, String $param_String_2, String[] $param_arrayOf_String_3, String $param_String_4, boolean $param_boolean_5)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_addSubQbe_9, new Object[] { $param_String_1, $param_String_2, $param_arrayOf_String_3, $param_String_4, ($param_boolean_5) ? Boolean.TRUE : Boolean.FALSE }, -4616100831476509347L);
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

  public void addSubQbe(String $param_String_1, String[] $param_arrayOf_String_2, String $param_String_3)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_addSubQbe_10, new Object[] { $param_String_1, $param_arrayOf_String_2, $param_String_3 }, 8856088974585881521L);
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

  public void addSubQbe(String $param_String_1, String[] $param_arrayOf_String_2, String $param_String_3, boolean $param_boolean_4)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_addSubQbe_11, new Object[] { $param_String_1, $param_arrayOf_String_2, $param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE }, 3910060578001859834L);
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

  public void addWarning(MXException $param_MXException_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_addWarning_12, new Object[] { $param_MXException_1 }, 6877762596046011488L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void addWarnings(MXException[] $param_arrayOf_MXException_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_addWarnings_13, new Object[] { $param_arrayOf_MXException_1 }, 3693476214781041099L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void checkMethodAccess(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_checkMethodAccess_14, new Object[] { $param_String_1 }, 8770342446443124381L);
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

  public void cleanup()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_cleanup_15, null, -5060879735199558936L);
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

  public void clear()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_clear_16, null, -7475254351993695499L);
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

  public void clearLongOpPipe()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_clearLongOpPipe_17, null, 8659227281629351838L);
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

  public void close()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_close_18, null, -4742752445160157748L);
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

  public void commit()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_commit_19, null, 8461082169793485964L);
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

  public void commitTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_commitTransaction_20, new Object[] { $param_MXTransaction_1 }, 5526751948342117649L);
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

  public void copy(MboSetRemote $param_MboSetRemote_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_copy_21, new Object[] { $param_MboSetRemote_1 }, -4068451441676654316L);
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

  public void copy(MboSetRemote $param_MboSetRemote_1, String[] $param_arrayOf_String_2, String[] $param_arrayOf_String_3)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_copy_22, new Object[] { $param_MboSetRemote_1, $param_arrayOf_String_2, $param_arrayOf_String_3 }, 259840801264490387L);
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

  public void copyForDM(MboSetRemote $param_MboSetRemote_1, int $param_int_2, int $param_int_3)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_copyForDM_23, new Object[] { $param_MboSetRemote_1, new Integer($param_int_2), new Integer($param_int_3) }, 4139655675866814170L);
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

  public int count()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_count_24, null, -6275967665373233420L);
      return ((Integer)$result).intValue();
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

  public int count(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_count_25, new Object[] { new Integer($param_int_1) }, 6057223631155861379L);
      return ((Integer)$result).intValue();
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

  public void deleteAll()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAll_26, null, 1047866983005709604L);
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

  public void deleteAll(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAll_27, new Object[] { new Long($param_long_1) }, 7428141354626732966L);
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

  public void deleteAndRemove()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemove_28, null, 108455117932777006L);
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

  public void deleteAndRemove(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemove_29, new Object[] { new Integer($param_int_1) }, 7058265410369616733L);
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

  public void deleteAndRemove(int $param_int_1, long $param_long_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemove_30, new Object[] { new Integer($param_int_1), new Long($param_long_2) }, -57466441867056035L);
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

  public void deleteAndRemove(MboRemote $param_MboRemote_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemove_31, new Object[] { $param_MboRemote_1 }, 8049976903218966811L);
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

  public void deleteAndRemove(MboRemote $param_MboRemote_1, long $param_long_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemove_32, new Object[] { $param_MboRemote_1, new Long($param_long_2) }, -2460759163543663366L);
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

  public void deleteAndRemoveAll()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemoveAll_33, null, -9171735664440166110L);
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

  public void deleteAndRemoveAll(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_deleteAndRemoveAll_34, new Object[] { new Long($param_long_1) }, -2086032524462602434L);
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

  public List determineRequiredFieldsFromERM()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_determineRequiredFieldsFromERM_35, null, 6249625157320251888L);
      return (List)$result;
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

  public Date earliestDate(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_earliestDate_36, new Object[] { $param_String_1 }, 319619818021671105L);
      return (Date)$result;
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

  public MboRemote fetchNext()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_fetchNext_37, null, -2842604447245051608L);
      return (MboRemote)$result;
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

  public List findAllNullRequiredFields()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_findAllNullRequiredFields_38, null, -8395847474787730044L);
      return (List)$result;
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

  public MboRemote findByIntegrationKey(String[] $param_arrayOf_String_1, String[] $param_arrayOf_String_2)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_findByIntegrationKey_39, new Object[] { $param_arrayOf_String_1, $param_arrayOf_String_2 }, -5188950366980953895L);
      return (MboRemote)$result;
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

  public MboRemote findKey(Object $param_Object_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_findKey_40, new Object[] { $param_Object_1 }, -4143602837382961813L);
      return (MboRemote)$result;
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

  public void fireEventsAfterDB(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_fireEventsAfterDB_41, new Object[] { $param_MXTransaction_1 }, 2018614941210383773L);
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

  public void fireEventsAfterDBCommit(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_fireEventsAfterDBCommit_42, new Object[] { $param_MXTransaction_1 }, 539352431787368469L);
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

  public void fireEventsBeforeDB(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_fireEventsBeforeDB_43, new Object[] { $param_MXTransaction_1 }, -1896937679177330251L);
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

  public String getApp()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getApp_44, null, -5367863973791977394L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public BitFlag getAppAlwaysFieldFlags(String $param_String_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getAppAlwaysFieldFlags_45, new Object[] { $param_String_1 }, 4725972791458588808L);
      return (BitFlag)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getAppWhere()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getAppWhere_46, null, -6411027332061535922L);
      return (String)$result;
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

  public boolean getBoolean(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getBoolean_47, new Object[] { $param_String_1 }, -1640992992330807345L);
      return ((Boolean)$result).booleanValue();
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

  public byte getByte(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getByte_48, new Object[] { $param_String_1 }, 3166015741238752943L);
      return ((Byte)$result).byteValue();
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

  public byte[] getBytes(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getBytes_49, new Object[] { $param_String_1 }, -3054736941581443291L);
      return (byte[])$result;
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

  public String getCompleteWhere()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getCompleteWhere_50, null, 8091544845542593075L);
      return (String)$result;
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

  public int getCurrentPosition()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getCurrentPosition_51, null, -5631123019493404510L);
      return ((Integer)$result).intValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public int getDBFetchMaxRows()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getDBFetchMaxRows_52, null, -6910065472471089755L);
      return ((Integer)$result).intValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public Date getDate(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getDate_53, new Object[] { $param_String_1 }, 25358525752956448L);
      return (Date)$result;
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

  public String getDefaultValue(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getDefaultValue_54, new Object[] { $param_String_1 }, 681247189211209370L);
      return (String)$result;
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

  public double getDouble(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getDouble_55, new Object[] { $param_String_1 }, -7136627451769557504L);
      return ((Double)$result).doubleValue();
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

  public ERMEntity getERMEntity()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getERMEntity_56, null, 5554976065811350171L);
      return (ERMEntity)$result;
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

  public String getESigTransactionId()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getESigTransactionId_57, null, -6797157010545199227L);
      return (String)$result;
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

  public boolean getExcludeMeFromPropagation()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getExcludeMeFromPropagation_58, null, 439917228953926900L);
      return ((Boolean)$result).booleanValue();
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

  public long getFlags()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getFlags_59, null, 8881435422980061864L);
      return ((Long)$result).longValue();
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

  public float getFloat(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getFloat_60, new Object[] { $param_String_1 }, -4592236820643884030L);
      return ((Float)$result).floatValue();
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

  public int getInt(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getInt_61, new Object[] { $param_String_1 }, 6551869032578983177L);
      return ((Integer)$result).intValue();
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

  public String[] getKeyAttributes()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getKeyAttributes_62, null, -7392337040539157066L);
      return (String[])$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboSetRemote getList(int $param_int_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getList_63, new Object[] { new Integer($param_int_1), $param_String_2 }, 5124730839289391840L);
      return (MboSetRemote)$result;
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

  public MboSetRemote getList(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getList_64, new Object[] { $param_String_1 }, -1226607622080901807L);
      return (MboSetRemote)$result;
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

  public long getLong(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getLong_65, new Object[] { $param_String_1 }, 1123300209586097136L);
      return ((Long)$result).longValue();
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

  public StringBuffer getMLFromClause(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMLFromClause_66, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 8102666457792494928L);
      return (StringBuffer)$result;
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

  public MXTransaction getMXTransaction()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMXTransaction_67, null, 5626709230336731958L);
      return (MXTransaction)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MaxMessage getMaxMessage(String $param_String_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMaxMessage_68, new Object[] { $param_String_1, $param_String_2 }, -1770727576702508461L);
      return (MaxMessage)$result;
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

  public MboRemote getMbo()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMbo_69, null, 1451139922529636344L);
      return (MboRemote)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboRemote getMbo(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMbo_70, new Object[] { new Integer($param_int_1) }, -7465904525414218295L);
      return (MboRemote)$result;
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

  public MboRemote getMboForUniqueId(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboForUniqueId_71, new Object[] { new Long($param_long_1) }, -6104400636357324029L);
      return (MboRemote)$result;
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

  public MboSetData getMboSetData(int $param_int_1, int $param_int_2, String[] $param_arrayOf_String_3)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetData_72, new Object[] { new Integer($param_int_1), new Integer($param_int_2), $param_arrayOf_String_3 }, 958102828713360553L);
      return (MboSetData)$result;
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

  public MboSetData getMboSetData(String[] $param_arrayOf_String_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetData_73, new Object[] { $param_arrayOf_String_1 }, -5237504902278352384L);
      return (MboSetData)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboSetInfo getMboSetInfo()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetInfo_74, null, -6397823119298298567L);
      return (MboSetInfo)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboSetRetainMboPositionData getMboSetRetainMboPositionData()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetRetainMboPositionData_75, null, -2888342383150444573L);
      return (MboSetRetainMboPositionData)$result;
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

  public MboSetRetainMboPositionInfo getMboSetRetainMboPositionInfo()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetRetainMboPositionInfo_76, null, 6887134552328187054L);
      return (MboSetRetainMboPositionInfo)$result;
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

  public MboValueData[] getMboSetValueData(String[] $param_arrayOf_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboSetValueData_77, new Object[] { $param_arrayOf_String_1 }, 9086922193006277312L);
      return (MboValueData[])$result;
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

  public MboValueData[][] getMboValueData(int $param_int_1, int $param_int_2, String[] $param_arrayOf_String_3)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboValueData_78, new Object[] { new Integer($param_int_1), new Integer($param_int_2), $param_arrayOf_String_3 }, 2271011067994553524L);
      return (MboValueData[][])$result;
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

  public MboValueData getMboValueData(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboValueData_79, new Object[] { $param_String_1 }, -2193850169204155020L);
      return (MboValueData)$result;
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

  public MboValueData[] getMboValueData(String[] $param_arrayOf_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboValueData_80, new Object[] { $param_arrayOf_String_1 }, -3046682349766384472L);
      return (MboValueData[])$result;
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

  public MboValueInfoStatic getMboValueInfoStatic(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboValueInfoStatic_81, new Object[] { $param_String_1 }, -4328088463610638087L);
      return (MboValueInfoStatic)$result;
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
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMboValueInfoStatic_82, new Object[] { $param_arrayOf_String_1 }, -169869964566830779L);
      return (MboValueInfoStatic[])$result;
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

  public String getMessage(String $param_String_1, String $param_String_2)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMessage_83, new Object[] { $param_String_1, $param_String_2 }, -5117172076054138989L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getMessage(String $param_String_1, String $param_String_2, Object $param_Object_3)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMessage_84, new Object[] { $param_String_1, $param_String_2, $param_Object_3 }, 5002469433788530020L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getMessage(String $param_String_1, String $param_String_2, Object[] $param_arrayOf_Object_3)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMessage_85, new Object[] { $param_String_1, $param_String_2, $param_arrayOf_Object_3 }, -5220667813980826248L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getMessage(MXException $param_MXException_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getMessage_86, new Object[] { $param_MXException_1 }, -4392176690452392965L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getName()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getName_87, null, 6317137956467216454L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getOrderBy()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getOrderBy_88, null, 1663304414241879155L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboRemote getOwner()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getOwner_89, null, 2290236231147060375L);
      return (MboRemote)$result;
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

  public String getParentApp()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getParentApp_90, null, -848219904041595449L);
      return (String)$result;
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

  public ProfileRemote getProfile()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getProfile_91, null, 8741482772666955520L);
      return (ProfileRemote)$result;
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

  public String[][] getQbe()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getQbe_92, null, 3570030357530510418L);
      return (String[][])$result;
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

  public String getQbe(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getQbe_93, new Object[] { $param_String_1 }, -7363965097830124081L);
      return (String)$result;
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

  public String[] getQbe(String[] $param_arrayOf_String_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getQbe_94, new Object[] { $param_arrayOf_String_1 }, 2281028707015845434L);
      return (String[])$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public int getQueryTimeout()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getQueryTimeout_95, null, -5292570273248889913L);
      return ((Integer)$result).intValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getRelationName()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getRelationName_96, null, 3242433746877981586L);
      return (String)$result;
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

  public String getRelationship()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getRelationship_97, null, 3854992974262284809L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getSQLOptions()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getSQLOptions_98, null, -9169659528589608885L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public Vector getSelection()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getSelection_99, null, -548806503353428924L);
      return (Vector)$result;
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

  public String getSelectionWhere()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getSelectionWhere_100, null, 6668519946243860304L);
      return (String)$result;
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

  public int getSize()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getSize_101, null, -4419516886758165304L);
      return ((Integer)$result).intValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getString(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getString_102, new Object[] { $param_String_1 }, 5066930371966209369L);
      return (String)$result;
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

  public Map getTxnPropertyMap()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getTxnPropertyMap_103, null, 4210328555318117463L);
      return (Map)$result;
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

  public String getUserAndQbeWhere()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getUserAndQbeWhere_104, null, -1907962377797080291L);
      return (String)$result;
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

  public UserInfo getUserInfo()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getUserInfo_105, null, -6594617694786131693L);
      return (UserInfo)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getUserName()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getUserName_106, null, 483502017080265922L);
      return (String)$result;
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

  public String getUserWhere()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getUserWhere_107, null, 2823502905349228475L);
      return (String)$result;
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

  public MXException[] getWarnings()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getWarnings_108, null, -4202679921961755174L);
      return (MXException[])$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public String getWhere()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getWhere_109, null, 4589423418485775302L);
      return (String)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public MboRemote getZombie()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getZombie_110, null, 6079358383459206381L);
      return (MboRemote)$result;
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public boolean hasMLQbe()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_hasMLQbe_111, null, 8505476428782976049L);
      return ((Boolean)$result).booleanValue();
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

  public boolean hasQbe()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_hasQbe_112, null, 1019854811266524678L);
      return ((Boolean)$result).booleanValue();
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

  public boolean hasWarnings()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_hasWarnings_113, null, 9219748662690981686L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void ignoreQbeExactMatchSet(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_ignoreQbeExactMatchSet_114, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 3970162173842621208L);
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

  public void incrementDeletedCount(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_incrementDeletedCount_115, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 5145123422414524021L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void init(UserInfo $param_UserInfo_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_init_116, new Object[] { $param_UserInfo_1 }, -8222637788779956097L);
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

  public boolean isBasedOn(String $param_String_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isBasedOn_117, new Object[] { $param_String_1 }, 6201297079127551930L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public boolean isDMDeploySet()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isDMDeploySet_118, null, -2989902975530919438L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isDMSkipFieldValidation()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isDMSkipFieldValidation_119, null, -8931532007432595343L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isESigNeeded(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isESigNeeded_120, new Object[] { $param_String_1 }, 5150239072674528451L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isEmpty()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isEmpty_121, null, 9136275027625107786L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isFlagSet(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isFlagSet_122, new Object[] { new Long($param_long_1) }, -7088243327149326417L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isNull(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isNull_123, new Object[] { $param_String_1 }, -4712365544638525211L);
      return ((Boolean)$result).booleanValue();
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

  public boolean isQbeCaseSensitive()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isQbeCaseSensitive_124, null, -4288819605394887311L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public boolean isQbeExactMatch()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isQbeExactMatch_125, null, -1905721130618516539L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public boolean isRetainMboPosition()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_isRetainMboPosition_126, null, -1715589879025131382L);
      return ((Boolean)$result).booleanValue();
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

  public Date latestDate(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_latestDate_127, new Object[] { $param_String_1 }, 6770058323197509039L);
      return (Date)$result;
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

  public MboRemote locateMbo(String[] $param_arrayOf_String_1, String[] $param_arrayOf_String_2, int $param_int_3)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_locateMbo_128, new Object[] { $param_arrayOf_String_1, $param_arrayOf_String_2, new Integer($param_int_3) }, 3620969173800395703L);
      return (MboRemote)$result;
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

  public void logESigVerification(String $param_String_1, String $param_String_2, boolean $param_boolean_3)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_logESigVerification_129, new Object[] { $param_String_1, $param_String_2, ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, -2562018672569833918L);
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

  public double max(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_max_130, new Object[] { $param_String_1 }, 6406270657459925090L);
      return ((Double)$result).doubleValue();
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

  public double min(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_min_131, new Object[] { $param_String_1 }, 3076694027348187184L);
      return ((Double)$result).doubleValue();
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

  public MboRemote moveFirst()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_moveFirst_132, null, 4153861272894462535L);
      return (MboRemote)$result;
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

  public MboRemote moveLast()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_moveLast_133, null, -8547641780575967093L);
      return (MboRemote)$result;
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

  public MboRemote moveNext()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_moveNext_134, null, 373441726928335219L);
      return (MboRemote)$result;
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

  public MboRemote movePrev()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_movePrev_135, null, 2948763279973544906L);
      return (MboRemote)$result;
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

  public MboRemote moveTo(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_moveTo_136, new Object[] { new Integer($param_int_1) }, 5197759255074189960L);
      return (MboRemote)$result;
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

  public boolean notExist()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_notExist_137, null, -6457193471361750411L);
      return ((Boolean)$result).booleanValue();
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

  public void positionState()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_positionState_138, null, -446753277631831422L);
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

  public boolean processML()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_processML_139, null, 2055730368118779090L);
      return ((Boolean)$result).booleanValue();
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

  public void remove()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_remove_140, null, -5013858639939630501L);
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

  public void remove(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_remove_141, new Object[] { new Integer($param_int_1) }, 6274393861135366882L);
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

  public void remove(MboRemote $param_MboRemote_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_remove_142, new Object[] { $param_MboRemote_1 }, 7940608372793014621L);
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

  public void reset()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_reset_143, null, 7419395615006395270L);
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

  public void resetQbe()
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_resetQbe_144, null, -6889841924411579277L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void resetWithSelection()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_resetWithSelection_145, null, -7244786475224824748L);
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

  public void rollback()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_rollback_146, null, -2202008398766919932L);
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

  public void rollbackToCheckpoint()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_rollbackToCheckpoint_147, null, 4883480516303419745L);
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

  public void rollbackToCheckpoint(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_rollbackToCheckpoint_148, new Object[] { new Integer($param_int_1) }, -2850573153969533130L);
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

  public void rollbackTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_rollbackTransaction_149, new Object[] { $param_MXTransaction_1 }, 4659038437979813513L);
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

  public void save()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_save_150, null, -4949911113651036540L);
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

  public void save(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_save_151, new Object[] { new Long($param_long_1) }, 2056927562915037624L);
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

  public void saveTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_saveTransaction_152, new Object[] { $param_MXTransaction_1 }, -1187549220824616016L);
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

  public void select(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_select_153, new Object[] { new Integer($param_int_1) }, -7084434404722646542L);
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

  public void select(int $param_int_1, int $param_int_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_select_154, new Object[] { new Integer($param_int_1), new Integer($param_int_2) }, -1518362863281228118L);
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

  public void select(Vector $param_Vector_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_select_155, new Object[] { $param_Vector_1 }, -5402499589263984416L);
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

  public void selectAll()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_selectAll_156, null, 6479496206148187827L);
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

  public void setAllowQualifiedRestriction(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setAllowQualifiedRestriction_157, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 1411411564601082656L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setApp(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setApp_158, new Object[] { $param_String_1 }, 5371987469511591378L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setAppAlwaysFieldFlag(String $param_String_1, long $param_long_2, boolean $param_boolean_3)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setAppAlwaysFieldFlag_159, new Object[] { $param_String_1, new Long($param_long_2), ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, 552379019196936441L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setAppWhere(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setAppWhere_160, new Object[] { $param_String_1 }, 4005592618565017356L);
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

  public boolean setAutoKeyFlag(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_setAutoKeyFlag_161, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -6411490009216971397L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setDBFetchMaxRows(int $param_int_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setDBFetchMaxRows_162, new Object[] { new Integer($param_int_1) }, 4377403422813114536L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setDMDeploySet(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDMDeploySet_163, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -8700165215753881909L);
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

  public void setDMSkipFieldValidation(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDMSkipFieldValidation_164, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 2741223569988620111L);
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

  public void setDefaultOrderBy()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDefaultOrderBy_165, null, -8212896781643474852L);
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

  public void setDefaultValue(String $param_String_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDefaultValue_166, new Object[] { $param_String_1, $param_String_2 }, -936210876334662358L);
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

  public void setDefaultValue(String $param_String_1, MboRemote $param_MboRemote_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDefaultValue_167, new Object[] { $param_String_1, $param_MboRemote_2 }, -180348208905173394L);
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

  public void setDefaultValues(String[] $param_arrayOf_String_1, String[] $param_arrayOf_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setDefaultValues_168, new Object[] { $param_arrayOf_String_1, $param_arrayOf_String_2 }, -1114393929898813763L);
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

  public void setERMEntity(ERMEntity $param_ERMEntity_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setERMEntity_169, new Object[] { $param_ERMEntity_1 }, -6308566533719683739L);
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

  public void setESigFieldModified(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setESigFieldModified_170, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -4983321710710401682L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setExcludeMeFromPropagation(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setExcludeMeFromPropagation_171, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -3045041172404102890L);
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

  public void setFlag(long $param_long_1, boolean $param_boolean_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setFlag_172, new Object[] { new Long($param_long_1), ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE }, 8152726795599941974L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setFlag_173, new Object[] { new Long($param_long_1), ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE, $param_MXException_3 }, -568127893371775973L);
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

  public void setFlags(long $param_long_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setFlags_174, new Object[] { new Long($param_long_1) }, 8574959450838984319L);
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

  public void setInsertCompanySet(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setInsertCompanySet_175, new Object[] { $param_String_1 }, -609403328939477490L);
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

  public void setInsertItemSet(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setInsertItemSet_176, new Object[] { $param_String_1 }, 4151646420973302027L);
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

  public void setInsertOrg(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setInsertOrg_177, new Object[] { $param_String_1 }, -839209712096664132L);
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

  public void setInsertSite(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setInsertSite_178, new Object[] { $param_String_1 }, -638193148575279788L);
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

  public void setLastESigTransId(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setLastESigTransId_179, new Object[] { $param_String_1 }, 1279421509078450704L);
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

  public boolean setLogLargFetchResultDisabled(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_setLogLargFetchResultDisabled_180, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 3897291742764671947L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setMXTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setMXTransaction_181, new Object[] { $param_MXTransaction_1 }, -2372782663100921321L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setMboSetInfo(MboSetInfo $param_MboSetInfo_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setMboSetInfo_182, new Object[] { $param_MboSetInfo_1 }, 6202755735166296117L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setNoNeedtoFetchFromDB(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setNoNeedtoFetchFromDB_183, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, 6012739660060509436L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setOrderBy(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setOrderBy_184, new Object[] { $param_String_1 }, -19578588874132793L);
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

  public void setOwner(MboRemote $param_MboRemote_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setOwner_185, new Object[] { $param_MboRemote_1 }, -2850778315764919277L);
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

  public void setQbe(String $param_String_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbe_186, new Object[] { $param_String_1, $param_String_2 }, 7622233883727162149L);
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

  public void setQbe(String $param_String_1, MboSetRemote $param_MboSetRemote_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbe_187, new Object[] { $param_String_1, $param_MboSetRemote_2 }, -2542034319729990883L);
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

  public void setQbe(String $param_String_1, String[] $param_arrayOf_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbe_188, new Object[] { $param_String_1, $param_arrayOf_String_2 }, -4169193863648280634L);
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

  public void setQbe(String[] $param_arrayOf_String_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbe_189, new Object[] { $param_arrayOf_String_1, $param_String_2 }, -7314228440572543961L);
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

  public void setQbe(String[] $param_arrayOf_String_1, String[] $param_arrayOf_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbe_190, new Object[] { $param_arrayOf_String_1, $param_arrayOf_String_2 }, -5410129375908299038L);
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

  public void setQbeCaseSensitive(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setQbeCaseSensitive_191, new Object[] { $param_String_1 }, 2927902194828070027L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setQbeCaseSensitive(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setQbeCaseSensitive_192, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -8126387353665598841L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setQbeExactMatch(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setQbeExactMatch_193, new Object[] { $param_String_1 }, -2374994778322609016L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setQbeExactMatch(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setQbeExactMatch_194, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -1928150863985358656L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setQbeOperatorOr()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQbeOperatorOr_195, null, 1236983592463789350L);
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

  public void setQueryBySiteQbe()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setQueryBySiteQbe_196, null, 2214818104601513936L);
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

  public void setQueryTimeout(int $param_int_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setQueryTimeout_197, new Object[] { new Integer($param_int_1) }, -6751336869551275110L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setRelationName(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setRelationName_198, new Object[] { $param_String_1 }, -2792563086294606747L);
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

  public void setRelationship(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setRelationship_199, new Object[] { $param_String_1 }, -2732266161082627950L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setRequiedFlagsFromERM()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setRequiedFlagsFromERM_200, null, -4359710921395673979L);
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

  public void setRetainMboPosition(boolean $param_boolean_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setRetainMboPosition_201, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -8750933503245042647L);
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

  public void setSQLOptions(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setSQLOptions_202, new Object[] { $param_String_1 }, 845750341850299746L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setTableDomainLookup(boolean $param_boolean_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setTableDomainLookup_203, new Object[] { ($param_boolean_1) ? Boolean.TRUE : Boolean.FALSE }, -3578067273387914142L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setTxnPropertyMap(Map $param_Map_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setTxnPropertyMap_204, new Object[] { $param_Map_1 }, -244954862634426529L);
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

  public void setUserWhere(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setUserWhere_205, new Object[] { $param_String_1 }, 7423908367736230769L);
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

  public void setUserWhereAfterParse(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setUserWhereAfterParse_206, new Object[] { $param_String_1 }, 8727387906196481794L);
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

  public void setValue(String $param_String_1, byte $param_byte_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_207, new Object[] { $param_String_1, new Byte($param_byte_2) }, 3270551574198177870L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_208, new Object[] { $param_String_1, new Byte($param_byte_2), new Long($param_long_3) }, -243985487831981328L);
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

  public void setValue(String $param_String_1, double $param_double_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_209, new Object[] { $param_String_1, new Double($param_double_2) }, -7524981934498388763L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_210, new Object[] { $param_String_1, new Double($param_double_2), new Long($param_long_3) }, -168439541455018744L);
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

  public void setValue(String $param_String_1, float $param_float_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_211, new Object[] { $param_String_1, new Float($param_float_2) }, -2815589486362369060L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_212, new Object[] { $param_String_1, new Float($param_float_2), new Long($param_long_3) }, 7169252791071186101L);
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

  public void setValue(String $param_String_1, int $param_int_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_213, new Object[] { $param_String_1, new Integer($param_int_2) }, 8850354658795100389L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_214, new Object[] { $param_String_1, new Integer($param_int_2), new Long($param_long_3) }, 3993773668554685290L);
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

  public void setValue(String $param_String_1, long $param_long_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_215, new Object[] { $param_String_1, new Long($param_long_2) }, 9210802592731375364L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_216, new Object[] { $param_String_1, new Long($param_long_2), new Long($param_long_3) }, 6848715728568018278L);
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

  public void setValue(String $param_String_1, String $param_String_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_217, new Object[] { $param_String_1, $param_String_2 }, -2811644617196606099L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_218, new Object[] { $param_String_1, $param_String_2, new Long($param_long_3) }, -4261472768839578905L);
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

  public void setValue(String $param_String_1, Date $param_Date_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_219, new Object[] { $param_String_1, $param_Date_2 }, -2630749704591450137L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_220, new Object[] { $param_String_1, $param_Date_2, new Long($param_long_3) }, 7971076697990243292L);
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

  public void setValue(String $param_String_1, short $param_short_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_221, new Object[] { $param_String_1, new Short($param_short_2) }, -592203831455696145L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_222, new Object[] { $param_String_1, new Short($param_short_2), new Long($param_long_3) }, -6261639766806276381L);
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

  public void setValue(String $param_String_1, boolean $param_boolean_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_223, new Object[] { $param_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE }, 4990140584423208903L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_224, new Object[] { $param_String_1, ($param_boolean_2) ? Boolean.TRUE : Boolean.FALSE, new Long($param_long_3) }, 8236575036597348343L);
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

  public void setValue(String $param_String_1, byte[] $param_arrayOf_byte_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_225, new Object[] { $param_String_1, $param_arrayOf_byte_2 }, -5271144966979799580L);
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
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValue_226, new Object[] { $param_String_1, $param_arrayOf_byte_2, new Long($param_long_3) }, 1093725565992944082L);
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

  public void setValueNull(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValueNull_227, new Object[] { $param_String_1 }, -362562597341262986L);
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

  public void setValueNull(String $param_String_1, long $param_long_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setValueNull_228, new Object[] { $param_String_1, new Long($param_long_2) }, 5998575739150575662L);
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

  public void setWhere(String $param_String_1)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_setWhere_229, new Object[] { $param_String_1 }, 3716158265074302952L);
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void setWhereQbe(String $param_String_1, String $param_String_2, String $param_String_3)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_setWhereQbe_230, new Object[] { $param_String_1, $param_String_2, $param_String_3 }, -3908674513352925281L);
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

  public InputStream setupLongOpPipe()
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_setupLongOpPipe_231, null, -5292144304387380232L);
      return (InputStream)$result;
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

  public MboSetRemote smartFill(int $param_int_1, String $param_String_2, String $param_String_3, boolean $param_boolean_4)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_smartFill_232, new Object[] { new Integer($param_int_1), $param_String_2, $param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE }, -4986550395298731157L);
      return (MboSetRemote)$result;
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
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_smartFill_233, new Object[] { $param_String_1, $param_String_2, ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, -935282078909453374L);
      return (MboSetRemote)$result;
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

  public MboSetRemote smartFind(String $param_String_1, String $param_String_2, String $param_String_3, boolean $param_boolean_4)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_smartFind_234, new Object[] { $param_String_1, $param_String_2, $param_String_3, ($param_boolean_4) ? Boolean.TRUE : Boolean.FALSE }, -1456117861212734379L);
      return (MboSetRemote)$result;
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
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_smartFind_235, new Object[] { $param_String_1, $param_String_2, ($param_boolean_3) ? Boolean.TRUE : Boolean.FALSE }, 615902001724753702L);
      return (MboSetRemote)$result;
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

  public void startCheckpoint()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_startCheckpoint_236, null, 8105257734697951775L);
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

  public void startCheckpoint(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_startCheckpoint_237, new Object[] { new Integer($param_int_1) }, 9212833876695667882L);
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

  public double sum(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_sum_238, new Object[] { $param_String_1 }, -4482925876510413120L);
      return ((Double)$result).doubleValue();
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

  public boolean toBeSaved()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_toBeSaved_239, null, -4334682600408332364L);
      return ((Boolean)$result).booleanValue();
    } catch (RuntimeException e) {
      throw e;
    } catch (RemoteException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }

  public void undeleteAll()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_undeleteAll_240, null, -6036829916884967034L);
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

  public void undoTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_undoTransaction_241, new Object[] { $param_MXTransaction_1 }, -123437101032274917L);
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

  public void unselect(int $param_int_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_unselect_242, new Object[] { new Integer($param_int_1) }, 8493332929890330251L);
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

  public void unselect(int $param_int_1, int $param_int_2)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_unselect_243, new Object[] { new Integer($param_int_1), new Integer($param_int_2) }, -1568029375769882413L);
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

  public void unselect(Vector $param_Vector_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_unselect_244, new Object[] { $param_Vector_1 }, -279594486889853003L);
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

  public void unselectAll()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_unselectAll_245, null, 6955628763468650662L);
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

  public void useStoredQuery(String $param_String_1)
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_useStoredQuery_246, new Object[] { $param_String_1 }, 566357811834720575L);
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

  public void validate()
    throws RemoteException, MXException
  {
    try
    {
      this.ref.invoke(this, $method_validate_247, null, -8368415688081130249L);
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

  public boolean validateTransaction(MXTransaction $param_MXTransaction_1)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_validateTransaction_248, new Object[] { $param_MXTransaction_1 }, 8811760484326804411L);
      return ((Boolean)$result).booleanValue();
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

  public boolean verifyESig(String $param_String_1, String $param_String_2, String $param_String_3)
    throws RemoteException, MXException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_verifyESig_249, new Object[] { $param_String_1, $param_String_2, $param_String_3 }, 4263616896083742816L);
      return ((Boolean)$result).booleanValue();
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

@Override
public String getQbeWhere() throws MXException, RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getSetOrderByForUI() throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setSetOrderByForUI(String arg0) throws RemoteException {
	// TODO Auto-generated method stub
	
}

//@Override
//public void setTxnPropertyMap(Map<Object, Object> arg0) throws MXException,
//		RemoteException {
//	// TODO Auto-generated method stub
//	
//}
}