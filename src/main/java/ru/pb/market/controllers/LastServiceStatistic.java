package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.config.AppLoggingAspect;
import ru.pb.market.dto.LastServiceStatisticDto;

import java.util.Map;


@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class LastServiceStatistic {


    @GetMapping("")
    public LastServiceStatisticDto getStatistic() {
        return new LastServiceStatisticDto(AppLoggingAspect.serviceName, AppLoggingAspect.serviceTime, AppLoggingAspect.userName);
    }
}
