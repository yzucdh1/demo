package com.example.demo.controller;

import com.example.demo.service.DemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/getTemperature")
    public Optional<Integer> getTemperature(@RequestParam String province, @RequestParam String city, @RequestParam String county) {
        if (StringUtils.isBlank(province) || StringUtils.isBlank(city) || StringUtils.isBlank(county)) {
            return Optional.of(0);
        }
        return demoService.getTemperature(province, city, county);
    }
}
