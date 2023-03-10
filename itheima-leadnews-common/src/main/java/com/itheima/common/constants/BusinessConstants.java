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

    /**
     * 注册为自媒体账号
     */
    public static class WmUserConstants{

        /**
         * 有效
         */
        public static final Integer WM_USER_OK= 9;

        /**
         * 冻结
         */
        public static final Integer WM_USER_LOCKED= 0;

        /**
         * 永久失效
         */
        public static final Integer WM_USER_INVALID= 1;
    }


    public static class ApAuthorConstants{
        /**
         * 平台自媒体人
         */
        public static final Integer A_MEDIA_USER= 2;

        /**
         * 合作商
         */
        public static final Integer A_MEDIA_SELLER= 1;

        /**
         * 普通作者
         */
        public static final Integer A_MEDIA_ZERO= 0;
    }

}