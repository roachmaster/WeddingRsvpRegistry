package com.leonardo.rocha.wedding.data;

import com.leonardo.rocha.wedding.service.GuestDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestRepositoryTest {
    @Autowired
    private GuestRepository guestRepository;

    @Before
    public void setUp() throws Exception {
        this.guestRepository.deleteAll();
        Guest guest1 = new Guest("Alice", 3);
        Guest guest2 = new Guest("Bob", 2);
        //save guest, verify has ID value after save
        assertNull(guest1.getId());
        assertNull(guest2.getId());//null before save
        this.guestRepository.save(guest1);
        this.guestRepository.save(guest2);
        assertNotNull(guest1.getId());
        assertNotNull(guest2.getId());
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        Guest guestA = guestRepository.findByName("Bob");
        assertNotNull(guestA);
        assertEquals(new Integer(2), guestA.getMaxGuest());
        /*Get all products, list should only have two*/
        Iterable<Guest> guests = guestRepository.findAll();
        long count = GuestDao.getNumberOfGuests(guests);
        assertEquals(count, 2);
        this.guestRepository.deleteAll();
    }
}