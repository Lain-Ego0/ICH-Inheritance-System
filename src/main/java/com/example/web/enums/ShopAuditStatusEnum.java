package com.example.web.enums;

import java.util.HashMap;

/**
  *店铺审核状态枚举
  */
public enum ShopAuditStatusEnum 
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

   ShopAuditStatusEnum(int index)
   {
     this.index = index;
   }

   public int index() {
     return index;
   }
      private static final HashMap<Integer,ShopAuditStatusEnum> MY_MAP = new HashMap<Integer,ShopAuditStatusEnum>();
    static {
           for (ShopAuditStatusEnum myEnum : values()) {
               MY_MAP.put(myEnum.index(), myEnum);
           }
     }
    public static ShopAuditStatusEnum GetEnum(Integer v)
       {
          if(v==null){
               return MY_MAP.values().stream().findFirst().get();
           }
           return MY_MAP.get(v);
       }



}
