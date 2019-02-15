package com.shuto.mam.util;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttributeUtil {
    public void copyAttribute(String paramString, MboRemote paramMboRemote1, MboRemote paramMboRemote2)
            throws RemoteException, MXException {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str1 = paramString.toUpperCase();

        StringBuffer localStringBuffer = new StringBuffer();

        String str2 = null;
        String str3 = null;

        String str4 = null;

        long l = -1L;

        String str5 = "";

        String str6 = null;
        boolean bool = false;
        double d = 0.0D;
        Date localDate = null;
        int i = 0;

        String str7 = null;

        MboRemote localMboRemote = null;

        String str8 = localStringBuffer.append("appname = '").append(str1).append("' and targetobject = '").append(paramMboRemote2.getName()).append("' ").toString();

        MboSetRemote localMboSetRemote = paramMboRemote1.getMboSet("$copyattribute", "copyattribute", str8);
        if ((localMboSetRemote == null) || (localMboSetRemote.count() <= 0)) {
            return;
        }
        for (int j = 0; j < localMboSetRemote.count(); ++j) {
            localMboRemote = localMboSetRemote.getMbo(j);

            str2 = localMboRemote.getString("currentattribute");

            str4 = localMboRemote.getString("attributetyp");

            str3 = localMboRemote.getString("targetattribute");

            str7 = localMboRemote.getString("setvalue");

            str5 = localMboRemote.getString("accessModifier");
            if ((str5 != null) && (!"".equals(str5))) {
                l = Long.parseLong(localMboRemote.getString("accessModifier"));
            }

            if (l != -1L) {
                if ((!"".equals(str7)) && (str7 != null)) {
                    if ("字符".equals(str4)) {
                        paramMboRemote2.setValue(str3, str7, l);
                        continue;
                    }
                    if ("布尔".equals(str4)) {
                        if ("true".equals(str7))
                            bool = true;
                        else {
                            bool = false;
                        }

                        paramMboRemote2.setValue(str3, bool, l);
                        continue;
                    }
                    if ("小数".equals(str4)) {
                        d = Double.parseDouble(str7);

                        paramMboRemote2.setValue(str3, d, l);
                        continue;
                    }
                    if ("整数".equals(str4)) {
                        i = Integer.parseInt(str7);

                        paramMboRemote2.setValue(str3, i, l);
                        continue;
                    }
                    if ("日期".equals(str4)) {
                        try {
                            localDate = localSimpleDateFormat.parse(str7);
                        } catch (ParseException localParseException1) {
                            localParseException1.printStackTrace();
                        }

                        paramMboRemote2.setValue(str3, localDate, l);
                        continue;
                    }
                    throw new MXApplicationException("#", "类型无效，需更新代码！");
                }

                if ("字符".equals(str4)) {
                    str6 = paramMboRemote1.getString(str2);

                    paramMboRemote2.setValue(str3, str6, l);
                    continue;
                }
                if ("布尔".equals(str4)) {
                    bool = paramMboRemote1.getBoolean(str2);

                    paramMboRemote2.setValue(str3, bool, l);
                    continue;
                }
                if ("小数".equals(str4)) {
                    d = paramMboRemote1.getDouble(str2);

                    paramMboRemote2.setValue(str3, d, l);
                    continue;
                }
                if ("整数".equals(str4)) {
                    i = paramMboRemote1.getInt(str2);

                    paramMboRemote2.setValue(str3, i, l);
                    continue;
                }
                if ("日期".equals(str4)) {
                    localDate = paramMboRemote1.getDate(str2);

                    paramMboRemote2.setValue(str3, localDate, l);
                    continue;
                }
                throw new MXApplicationException("#", "类型无效，需更新代码！");
            }

            if ((!"".equals(str7)) && (str7 != null)) {
                if ("字符".equals(str4)) {
                    paramMboRemote2.setValue(str3, str7);
                    continue;
                }
                if ("布尔".equals(str4)) {
                    if ("true".equals(str7))
                        bool = true;
                    else {
                        bool = false;
                    }

                    paramMboRemote2.setValue(str3, bool);
                    continue;
                }
                if ("小数".equals(str4)) {
                    d = Double.parseDouble(str7);

                    paramMboRemote2.setValue(str3, d);
                    continue;
                }
                if ("整数".equals(str4)) {
                    i = Integer.parseInt(str7);

                    paramMboRemote2.setValue(str3, i);
                    continue;
                }
                if ("日期".equals(str4)) {
                    try {
                        localDate = localSimpleDateFormat.parse(str7);
                    } catch (ParseException localParseException2) {
                        localParseException2.printStackTrace();
                    }

                    paramMboRemote2.setValue(str3, localDate);
                    continue;
                }
                throw new MXApplicationException("", "类型无效，需更新代码！");
            }

            if ("字符".equals(str4)) {
                str6 = paramMboRemote1.getString(str2);

                paramMboRemote2.setValue(str3, str6);
            } else if ("布尔".equals(str4)) {
                bool = paramMboRemote1.getBoolean(str2);

                paramMboRemote2.setValue(str3, bool);
            } else if ("小数".equals(str4)) {
                d = paramMboRemote1.getDouble(str2);

                paramMboRemote2.setValue(str3, d);
            } else if ("整数".equals(str4)) {
                i = paramMboRemote1.getInt(str2);

                paramMboRemote2.setValue(str3, i);
            } else if ("日期".equals(str4)) {
                localDate = paramMboRemote1.getDate(str2);

                paramMboRemote2.setValue(str3, localDate);
            } else {
                throw new MXApplicationException("", "类型无效，需更新代码！");
            }
        }
    }

    public void changeAttributeSet(String paramString1, MboRemote paramMboRemote, String paramString2, Object paramObject, String paramString3)
            throws RemoteException, MXException {
        StringBuffer localStringBuffer = new StringBuffer();

        String str1 = paramString1.toUpperCase();

        String str2 = localStringBuffer.append("appname = '").append(str1).append("' and methodname = '").append(paramString3).append("' and changeval = '").append(paramObject.toString()).append("' and changeatt ='").append(paramString2).append("'").toString();

        MboSetRemote localMboSetRemote = paramMboRemote.getMboSet("$changeattset", "changeattset", str2);

        MboRemote localMboRemote = null;

        Object localObject1 = null;

        Object localObject2 = null;

        Object localObject3 = null;

        if ((localMboSetRemote != null) && (localMboSetRemote.count() > 0)) {
            for (int i = 0; i < localMboSetRemote.count(); ++i) {
                localMboRemote = localMboSetRemote.getMbo(i);
                changeSetValue(paramMboRemote, localMboRemote);
            }
        }

        localMboSetRemote.close();
    }

    private void changeSetValue(MboRemote paramMboRemote1, MboRemote paramMboRemote2)
            throws RemoteException, MXException {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str1 = paramMboRemote2.getString("setatt");

        String str2 = paramMboRemote2.getString("setatttype");

        String str3 = paramMboRemote2.getString("attsource");

        String str4 = paramMboRemote2.getString("relationship");

        MboSetRemote localMboSetRemote = paramMboRemote1.getMboSet(str4);

        MboRemote localMboRemote = null;

        String str5 = paramMboRemote2.getString("setval");

        String str6 = null;

        boolean bool = false;

        int i = 0;

        double d = 0.0D;

        Date localDate = null;
        if (("".equals(str4)) || (str4 == null)) {
            if ("值".equals(str3)) {
                if ("字符".equals(str2)) {
                    str6 = str5;
                    paramMboRemote1.setValue(str1, str6, 11L);
                    return;
                }
                if ("布尔".equals(str2)) {
                    if ("true".equals(str5))
                        bool = true;
                    else {
                        bool = false;
                    }
                    paramMboRemote1.setValue(str1, bool, 11L);
                    return;
                }
                if ("小数".equals(str2)) {
                    d = Double.parseDouble(str5);

                    paramMboRemote1.setValue(str1, d, 11L);
                    return;
                }
                if ("整数".equals(str2)) {
                    i = Integer.parseInt(str5);

                    paramMboRemote1.setValue(str1, i, 11L);
                    return;
                }
                if ("日期".equals(str2)) {
                    if ("SYSDATE".equalsIgnoreCase(str5)) {
                        paramMboRemote1.setValue(str1, new Date());
                        return;
                    }
                    try {
                        localDate = localSimpleDateFormat.parse(str5);
                    } catch (ParseException localParseException1) {
                        localParseException1.printStackTrace();
                    }

                    paramMboRemote1.setValue(str1, localDate, 11L);
                    return;
                }

                throw new MXApplicationException("", "类型无效，需更新代码！");
            }
            if ("属性".equals(str3)) {
                return;
            }
            throw new MXApplicationException("", "类型无效，需更新代码！");
        }

        if ((localMboSetRemote != null) && (localMboSetRemote.count() > 0)) {
            for (int j = 0; j < localMboSetRemote.count(); ++j) {
                localMboRemote = localMboSetRemote.getMbo(j);
                if ("值".equals(str3)) {
                    if ("字符".equals(str2)) {
                        str6 = str5;
                        localMboRemote.setValue(str1, str6, 11L);
                        continue;
                    }
                    if ("布尔".equals(str2)) {
                        if ("true".equals(str5))
                            bool = true;
                        else {
                            bool = false;
                        }
                        localMboRemote.setValue(str1, bool, 11L);
                        continue;
                    }
                    if ("小数".equals(str2)) {
                        d = Double.parseDouble(str5);

                        localMboRemote.setValue(str1, d, 11L);
                        continue;
                    }
                    if ("整数".equals(str2)) {
                        i = Integer.parseInt(str5);

                        localMboRemote.setValue(str1, i, 11L);
                        continue;
                    }
                    if ("日期".equals(str2)) {
                        if ("SYSDATE".equalsIgnoreCase(str5)) {
                            localMboRemote.setValue(str1, new Date());
                            continue;
                        }
                        try {
                            localDate = localSimpleDateFormat.parse(str5);
                        } catch (ParseException localParseException2) {
                            localParseException2.printStackTrace();
                        }

                        localMboRemote.setValue(str1, localDate, 11L);
                        continue;
                    }

                    throw new MXApplicationException("", "类型无效，需更新代码！");
                }
                if ("属性".equals(str3)) {
                    continue;
                }
                throw new MXApplicationException("", "类型无效，需更新代码！");
            }
        }
    }
}