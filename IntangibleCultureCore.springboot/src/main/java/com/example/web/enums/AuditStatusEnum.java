package com.example.web.enums;

import java.util.HashMap;

 /**
   *审核状态枚举
   */
public enum AuditStatusEnum
  {
    /**
     * 待审核
     */    
    待审核(1),
     
    /**
     * 审核通过
     */    
    审核通过(2),
     
    /**
     * 审核失败
     */    
    审核失败(3);
     
            
    private final int index;

      AuditStatusEnum(int index)
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,AuditStatusEnum> MY_MAP = new HashMap<Integer,AuditStatusEnum>();
     static {
            for (AuditStatusEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static AuditStatusEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
