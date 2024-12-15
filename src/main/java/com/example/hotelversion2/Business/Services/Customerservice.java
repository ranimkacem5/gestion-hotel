package com.example.hotelversion2.Business.Services;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.hotelversion2.DAO.entites.Customer;
import com.example.hotelversion2.DAO.entites.Room;
@Service("Customerservice")
public interface Customerservice {
    List<Customer> getallcustommers();
    Customer  getCustomerbyId(Long id);
Customer add(Customer customer);
Customer update(Customer customer );
void  remove(Customer customer);
Page<Customer> getCustomerSortedByEmailPagination(String order,Pageable pageable);
Page<Customer>getAllCustomerPagination(Pageable pageable);
Page<Customer>rechercherbyemail(String email ,Pageable pageable);
List<Customer>getCustomersortedByEmail(String order);


    
}
