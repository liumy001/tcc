/*
 * Copyright (c) 2016. ziroom.com.
 */
package com.eric.demo.commons.validator;

/**
 * <p>基础变量</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseConst {

    /**
     * 私有化构造
     */
    public BaseConst() {

    }

    /**
     * 通用错误消息，返回:服务错误
     */
    public static final String UNKNOWN_ERROR = "unknown.error";

    /**
     * 通用错误消息，返回的error
     */
    public static final String ERROR_CODE = "error";

    /**
     * 通用提示消息，对象不存在
     */
    public static final String NOT_FOUND = "not.found";

    /**
     * 通用提示消息，对象不存在
     */
    public static final String PARAM_NULL = "param.null";

    /**
     * 查找条件名字
     */
    public static final String SEARCH_CONDITION_NAME = "searchCondition";

    /**
     * BeanUtils.copyProperties 不copy的属性
     */
    public static final String[] NOT_COPY_FIELDS = new String[]{"id", "logicCode", "workStatus", "orderStatus", "createTime", "lastModifyTime", "orderTotalPrice"};
    /**
     * 日志
     */
    public static final String LOG_MESSAGE_FORMAT = "CLASS:[{}],METHOD:[{}],MESSAGENAME:[{}],FLAG:[{}],PARAM:[{}]";

    /**
     * 城市code全国
     */
    public static final String NATIONAL = "QG";

    public static final String YP_HOME_PAGE_INFO_KEY = "yp_home_page_info_key";

    public static final String USER_SESSION_KEY = "TCC-USER_SESSION_KEY";


    public enum isDel {
        is_Del(1, "删除"),
        no_Del(0, "未删除");
        private int code;
        private String name;

        isDel(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public isDel setCode(int code) {
            this.code = code;
            return this;
        }

        public String getName() {
            return name;
        }

        public isDel setName(String name) {
            this.name = name;
            return this;
        }
    }
}
