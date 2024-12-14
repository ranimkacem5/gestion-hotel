package com.example.hotelversion2.Business.Servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelversion2.Business.Services.Customerservice;
import com.example.hotelversion2.DAO.Repository.CustommerRepository;
import com.example.hotelversion2.DAO.entites.Customer;
@Service("Customerimpl")
public class Customerimpl implements Customerservice{
@Autowired
CustommerRepository custommerrepository ;
    @Override
    public List<Customer> getallcustommers() {
      return  custommerrepository.findAll();
    }

    @Override
    public Customer getCustomerbyId(Long id) {
        if(id==null)
        {
            return null;
        }
       return this.custommerrepository.findById( id).get();
    }

    @Override
    public Customer add(Customer customer) {
        if(customer==null)
        {
        return null; 
         }
          return this.custommerrepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        if(customer==null)
        {return null;}
        return this.custommerrepository.save(customer);
    }

    @Override
    public void remove(Customer customer) {
       this.custommerrepository.delete(customer);
    }
   
}
