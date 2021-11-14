package com.example.demo.service;

import java.util.Optional;

public interface DemoService {

    /**
     * 获取温度接口
     *
     * @param province
     * @param city
     * @param county
     * @return
     */
    Optional<Integer> getTemperature(String province, String city, String county);
}
