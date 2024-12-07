package com.example.hotelversion2.Business.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelversion2.DAO.entites.Customer;
@Service("Customerservice")
public interface Customerservice {
    List<Customer> getallcustommers();
    Customer  getCustomerbyId(Long id);
Customer add(Customer customer);
Customer update(Customer customer );
void  remove(Customer customer);

    
}
