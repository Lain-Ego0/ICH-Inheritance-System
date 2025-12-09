package com.example.web.enums;

import java.util.HashMap;

/**
 * 课程订单状态
 */
public enum CouseOrderStatusEnum {
    /**
     * 待支付
     */
    待支付(1),

    /**
     * 已支付
     */
    已支付(2),

    /**
     * 订单关闭
     */
    订单关闭(3),

    /**
     * 已退款
     */
    已退款(4);

    private final int index;

    CouseOrderStatusEnum(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    private static final HashMap<Integer, CouseOrderStatusEnum> MY_MAP = new HashMap<Integer, CouseOrderStatusEnum>();
    static {
        for (CouseOrderStatusEnum myEnum : values()) {
            MY_MAP.put(myEnum.index(), myEnum);
        }
    }

    public static CouseOrderStatusEnum GetEnum(Integer v) {
        if (v == null) {
            return MY_MAP.values().stream().findFirst().get();
        }
        return MY_MAP.get(v);
    }

}