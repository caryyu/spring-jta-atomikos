package com.github.caryyu.springjtaatomikos.controller;

import com.github.caryyu.springjtaatomikos.entity.Balance;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    @RequestMapping("/sayHello")
    public String sayHello() {
        return "Hello World";
    }

    @Transactional
    @RequestMapping("/create")
    public boolean create(@RequestParam String name,@RequestParam Integer amount){
        Balance b = new Balance();
        b.setAmount(new BigDecimal(amount));
        b.setName(name);

        b.persist();
        return true;
    }
}