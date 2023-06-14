package com.duykhanhHotel.domain.hotelOperating.customer.controller;

import com.duykhanhHotel.domain.hotelOperating.customer.dto.CustomerDto;
import com.duykhanhHotel.domain.hotelOperating.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/rooms/{fullName}/customer")
@CrossOrigin
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public CustomerDto getCustomer(@PathVariable("fullName") String fullName){
        return customerService.getCustomer(fullName);
    }

    @PutMapping
    public CustomerDto updateCustomer(@PathVariable("fullName") String fullName,
                                      @RequestBody CustomerDto dto) {
        return customerService.updateCustomer(fullName, dto);
    }

}
