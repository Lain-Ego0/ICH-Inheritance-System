package com.example.web.enums;

import java.util.HashMap;

/**
  *话题审核枚举
  */
public enum TopicAuditStatusEnum 
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
    * 审核不通过
    */
   审核不通过(3);


   private final int index;

   TopicAuditStatusEnum(int index)
   {
     this.index = index;
   }

   public int index() {
     return index;
   }
      private static final HashMap<Integer,TopicAuditStatusEnum> MY_MAP = new HashMap<Integer,TopicAuditStatusEnum>();
    static {
           for (TopicAuditStatusEnum myEnum : values()) {
               MY_MAP.put(myEnum.index(), myEnum);
           }
     }
    public static TopicAuditStatusEnum GetEnum(Integer v)
       {
          if(v==null){
               return MY_MAP.values().stream().findFirst().get();
           }
           return MY_MAP.get(v);
       }



}
