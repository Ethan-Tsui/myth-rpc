package com.myth.mythrpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author Ethan
 * @version 1.0
 */
public interface LoadBalancerKeys {
    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";
}