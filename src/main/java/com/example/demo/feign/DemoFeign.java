package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 远程调用接口
 */
@FeignClient(name = "demoFeign", url = "http://www.weather.com.cn/data")
public interface DemoFeign {

    /**
     * 获取省份信息
     *
     * @return
     */
    @RequestMapping("/city3jdata/china.html")
    String getProvinceCode();

    /**
     * 根据省份信息获取城市编号
     *
     * @param provinceCode
     * @return
     */
    @RequestMapping("/city3jdata/provshi/{provinceCode}.html")
    String getCityCode(@PathVariable String provinceCode);

    /**
     * 根据城市编号获取县编号
     *
     * @param cityCode
     * @return
     */
    @RequestMapping("/city3jdata/station/{cityCode}.html")
    String getCountyCode(@PathVariable String cityCode);

    /**
     * 根据县编号获取温度信息
     *
     * @param countyCode
     * @return
     */
    @RequestMapping("/sk/{countyCode}.html")
    String getTemperature(@PathVariable String countyCode);
}
