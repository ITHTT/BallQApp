package com.tysci.ballq.networks;

/**
 * Created by Administrator on 2016/3/2.
 */
public class HttpUrls {
    /**
     * 圈子服务主机地址
     */
    public static final String CIRCLE_HOST_URL = "http://int.ballq.cn:8003/ballq/api/v1/";

    /**
     * 其他接口主机地址
     */
    public static final String HOST_URL = "http://int.ballq.cn:8004";

    public static final String HOST_URL_V1 = HOST_URL + "/api/v1/";
    public static final String HOST_URL_V2 = HOST_URL + "/api/v2/";
    public static final String HOST_URL_V3 = HOST_URL + "/api/v3/";
    public static final String HOST_URL_V5 = HOST_URL + "/api/v5/";
    public static final String HOST_URL_V6 = HOST_URL + "/api/v6/";

    /**
     * 图片主机地址
     */
    public static final String IMAGE_HOST_URL = "http://static-cdn.ballq.cn/";
    /**
     * 首页热门圈子列表URL
     */
    public static final String HOT_CIRCLE_LIST_URL = CIRCLE_HOST_URL + "bbs/topic/hots";
    /**
     * 球经列表URL
     */
    public static final String BALLQ_INFO_LIST_URL = HOST_URL_V5 + "articles/";
}
