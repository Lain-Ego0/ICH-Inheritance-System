package com.example.web.enums;

import java.util.HashMap;

 /**
   *活动审核状态枚举
   */
public enum ActivityAuditStatusEnum 
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
    
    ActivityAuditStatusEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,ActivityAuditStatusEnum> MY_MAP = new HashMap<Integer,ActivityAuditStatusEnum>();
     static {
            for (ActivityAuditStatusEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static ActivityAuditStatusEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
