package com.example.web.enums;

import java.util.HashMap;

/**
  *订单售后类型枚举
  */
public enum OrderReturnTypeEnum 
 {
   /**
    * 仅退款无需退货
    */
   仅退款无需退货(0),

   /**
    * 退货退款
    */
   退货退款(1),

   /**
    * 仅退货无需退款
    */
   仅退货无需退款(2);


   private final int index;

   OrderReturnTypeEnum(int index)
   {
     this.index = index;
   }

   public int index() {
     return index;
   }
      private static final HashMap<Integer,OrderReturnTypeEnum> MY_MAP = new HashMap<Integer,OrderReturnTypeEnum>();
    static {
           for (OrderReturnTypeEnum myEnum : values()) {
               MY_MAP.put(myEnum.index(), myEnum);
           }
     }
    public static OrderReturnTypeEnum GetEnum(Integer v)
       {
          if(v==null){
               return MY_MAP.values().stream().findFirst().get();
           }
           return MY_MAP.get(v);
       }



}
