package com.example.hotelversion2.Business.Servicesimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.Models.Room;
import com.example.hotelversion2.DAO.Repository.RoomRepository;
@Service
public class Roomserviceimp implements RoomServices {
    private final RoomRepository roomrep;
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
    public void deleteRoomById(Long id) {
        roomrep.deleteById(id);
        
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
    
 
}
