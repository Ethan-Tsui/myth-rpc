package com.myth.mythrpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信息 (注册信息)
 * 封装 服务 的注册信息，包括服务名称、服务版本号、服务地址、服务分组等
 *
 * @author Ethan
 * @version 1.0
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";

    /**
     * 服务地址
     */
    private String serviceAddress;

    /**
     * 服务域名
     */
    private String serviceHost;

    /**
     * 服务端口号
     */
    private Integer servicePort;

    /**
     * 服务分组（暂未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     * 把版本号和分组都放到服务键名中，就可以在查询时根据这些参数获取对应版本和分组的服务了
     *
     * @return
     */
    public String getServiceKey() {
        // 后续可扩展服务分组
        //  return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }

}
