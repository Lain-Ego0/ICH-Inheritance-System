package com.example.web.enums;

import java.util.HashMap;

/**
 * 活动状态枚举
 */
public enum ActivityStatusEnum {
  /**
   * 待进行
   */
  待进行(1),

  /**
   * 报名中
   */
  报名中(2),

  /**
   * 报名结束
   */
  报名结束(3),

  /**
   * 活动进行中
   */
  活动进行中(4),

  /**
   * 活动结束
   */
  活动结束(5),

  /**
   * 活动取消
   */
  活动取消(6);

  private final int index;

  ActivityStatusEnum(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  private static final HashMap<Integer, ActivityStatusEnum> MY_MAP = new HashMap<Integer, ActivityStatusEnum>();
  static {
    for (ActivityStatusEnum myEnum : values()) {
      MY_MAP.put(myEnum.index(), myEnum);
    }
  }

  public static ActivityStatusEnum GetEnum(Integer v) {
    if (v == null) {
      return MY_MAP.values().stream().findFirst().get();
    }
    return MY_MAP.get(v);
  }

}
