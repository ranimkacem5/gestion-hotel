package com.example.hotelversion2.Business.Servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Customer> rechercherbyemail(String email, Pageable pageable) {
      return custommerrepository.findByEmail(email,pageable);
    }

    @Override
    public Page<Customer> getCustomerSortedByEmailPagination(String order, Pageable pageable) {
      if(pageable ==null){
        return null;
    }  
    Sort.Direction direction= Sort.Direction.ASC;
    if("desc".equalsIgnoreCase(order)){
        direction= Sort.Direction.DESC;
    }

    Pageable sortedPageable=PageRequest.of(
      pageable.getPageNumber(),
      pageable.getPageSize(),
      Sort.by(direction,"Email")
  );
  return (custommerrepository.findAll(sortedPageable));
    }

    @Override
    public Page<Customer> getAllCustomerPagination(Pageable pageable) {
      return custommerrepository.findAll(pageable);
    }

    @Override
    public List<Customer> getCustomersortedByEmail(String order) {
      Sort.Direction direction= Sort.Direction.ASC;
      if("desc".equalsIgnoreCase(order)){

        direction= Sort.Direction.DESC;
   
}
return custommerrepository.findAll(Sort.by(direction,"Email"));
    }

   
}
