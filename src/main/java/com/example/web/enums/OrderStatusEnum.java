package com.example.web.enums;

import java.util.HashMap;

/**
  *订单状态枚举
  */
public enum OrderStatusEnum 
 {
   /**
    * 待支付
    */
   待支付(0),

   /**
    * 待发货
    */
   待发货(1),

   /**
    * 待收货
    */
   待收货(2),

   /**
    * 待评价
    */
   待评价(3),

   /**
    * 售后中
    */
   售后中(4),

   /**
    * 已退款
    */
   已退款(5),

   /**
    * 完成
    */
   完成(6),

   /**
    * 订单关闭
    */
   订单关闭(7),

   /**
    * 待自提
    */
   待自提(8);


   private final int index;

   OrderStatusEnum(int index)
   {
     this.index = index;
   }

   public int index() {
     return index;
   }
      private static final HashMap<Integer,OrderStatusEnum> MY_MAP = new HashMap<Integer,OrderStatusEnum>();
    static {
           for (OrderStatusEnum myEnum : values()) {
               MY_MAP.put(myEnum.index(), myEnum);
           }
     }
    public static OrderStatusEnum GetEnum(Integer v)
       {
          if(v==null){
               return MY_MAP.values().stream().findFirst().get();
           }
           return MY_MAP.get(v);
       }



}
