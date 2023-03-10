package com.itheima.common.constants;

/**
 * @author Arthurocky
 */
public interface BusinessConstants {
    /**
     * 实名认证相关
     */
   public static class ApUserRealnameConstants{
        /**
         * 创建中
         */
        public static final Integer SHENHE_ING=0;

        /**
         * 待审核
         */
        public static final Integer SHENHE_WAITING=1;

        /**
         * 审核失败
         */
        public static final Integer SHENHE_FALSE=2;

        /**
         * 审核通过
         */
        public static final Integer SHENHE_SUCCESS=9;
    }

}