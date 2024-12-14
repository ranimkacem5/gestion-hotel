package com.example.hotelversion2.Business.Servicesimpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.Repository.RoomRepository;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomType;
@Service
public class Roomserviceimp implements RoomServices {
    private final RoomRepository roomrep;
    @Autowired
    public Roomserviceimp(RoomRepository  roomreps)
    {
        roomrep=roomreps ;
    }
    public List<Room> getAllRooms()
    {
        return roomrep.findAll(); 
    }
    @Override
    public Room addRoom(Room room) {
        if (room==null)
        {
            return null ;
        }
        roomrep.save(room);
        return room;
    }
    @Override
    public void deleteRoom(Room room) {
        roomrep.delete(room);
        
    }
    @Override
    public Room getRoomById(Long id) {
        //id must not be null 
        if(id==null)
         {return null ; }

     return this.roomrep.findById(id).get();
    }
   
    @Override
    public Room updateRoom(Room room) {
        if(room==null)
        return null ;

        return roomrep.save(room);
    }

    @Override
    public List<Room> getRoombyRoomType(RoomType roomType) {
        return roomrep.findByRoomType(roomType);
       
    }
    @Override
    public List<Room> getRoomByView(String view) {
      return roomrep.findByView(view);
    }
    @Override
    public List<Room> getRoomByCapacity(int capacity) {
      return roomrep.findByCapacity(capacity);
      
    }
    @Override
    public List<Room> getRoomByPricePerNightLessThan(double price) {
        return roomrep.findByPricePerNightLessThan(price);

    }



    @Override
    public List<Room> getRoomSortedByPrice(String order) {
        Sort.Direction direction= Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){

            direction= Sort.Direction.DESC;
       
    }
    return roomrep.findAll(Sort.by(direction,"pricePerNight"));
    
 
}
    @Override
    public Page<Room> getRoomSortedByPricePagination(String order, Pageable pageable) {
        if(pageable ==null){
            return null;
        }  
        Sort.Direction direction= Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){
            direction= Sort.Direction.DESC;
        }
               //pagerequest l'implementation de pageable 
        //pageable(pagenumber,sizepage)
        Pageable sortedPageable=PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(direction,"pricePerNight")
        );
        return (roomrep.findAll(sortedPageable));
    }
    @Override
    public Page<Room> getAllRoomPagination(Pageable pageable) {

       return roomrep.findAll(pageable);
    }}
