package com.bilyoner.assignment.couponapi.client.balance;

import com.bilyoner.assignment.couponapi.configuration.FeignClientConfiguration;
import com.bilyoner.assignment.couponapi.model.UpdateBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "balance-client", url = "${balance.client.url}", configuration = FeignClientConfiguration.class)
public interface BalanceClient {
    @PutMapping(produces = "application/json", consumes = "application/json")
    void updateBalance(@Valid @RequestBody UpdateBalanceRequest updateBalanceRequest);
}
