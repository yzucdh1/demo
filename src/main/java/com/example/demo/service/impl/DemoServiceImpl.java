package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.feign.DemoFeign;
import com.example.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoFeign demoFeign;

    @Override
    @Retryable(value = ConnectException.class, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public Optional<Integer> getTemperature(String province, String city, String county) {
        log.info("省份信息:{},城市信息:{},县信息:{}", province, city, county);
        String provinceCodes = demoFeign.getProvinceCode();
        log.info("获取的省份信息:{}", provinceCodes);
        if (StringUtils.isNotBlank(provinceCodes)) {
            Map<String, String> provinceMap = JSONObject.parseObject(provinceCodes, Map.class);
            if (Objects.nonNull(provinceMap) && !provinceMap.isEmpty() && provinceMap.containsKey(province)) {
                String cityCodes = demoFeign.getCityCode(province);
                log.info("获取的城市信息:{}", cityCodes);
                if (StringUtils.isNotBlank(cityCodes)) {
                    Map<String, String> cityMap = JSONObject.parseObject(cityCodes, Map.class);
                    if (Objects.nonNull(cityMap) && !cityMap.isEmpty() && cityMap.containsKey(city)) {
                        String countyCodes = demoFeign.getCountyCode(province + city);
                        log.info("获取的县信息:{}", countyCodes);
                        if (StringUtils.isNotBlank(countyCodes)) {
                            Map<String, String> countyMap = JSONObject.parseObject(countyCodes, Map.class);
                            if (Objects.nonNull(countyMap) && !countyMap.isEmpty() && countyMap.containsKey(county)) {
                                String weatherInfo = demoFeign.getTemperature(province + city + county);
                                log.info("获取的温度信息:{}", weatherInfo);
                                if (StringUtils.isNotBlank(weatherInfo)) {
                                    JSONObject parse = (JSONObject) JSONObject.parse(weatherInfo);
                                    if (Objects.nonNull(parse)) {
                                        JSONObject weatherinfo = (JSONObject) parse.get("weatherinfo");
                                        if (Objects.nonNull(weatherinfo)) {
                                            String temp = (String) weatherinfo.get("temp");
                                            log.info("具体的温度值为:{}", temp);
                                            if (StringUtils.isNotBlank(temp)) {
                                                return Optional.of((int) Float.parseFloat(temp));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("地区信息不存在,默认返回0!");
        return Optional.of(0);
    }

    @Recover
    public Optional<Integer> recover(ConnectException e) {
        log.error("连接超时!");
        return Optional.of(0);
    }

}
