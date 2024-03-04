package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkinglot = new ParkingLot();
        parkinglot.setName(name);
        parkinglot.setAddress(address);
        return parkingLotRepository1.save(parkinglot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        spot.setId(parkingLotId);
        spot.setNumberOfWheels(numberOfWheels);
        spot.setPricePerHour(pricePerHour);

        if (numberOfWheels == 2) {
            spot.setSpotType(SpotType.TWO_WHEELER);
        } else if (numberOfWheels == 4) {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        } else {
            spot.setSpotType(SpotType.OTHERS);
        }

        return spotRepository1.save(spot);
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).orElseThrow(() -> new RuntimeException("Spot not found"));

        if (spot.getParkingLot().getId() != parkingLotId) {
            throw new RuntimeException("Spot does not belong to the specified parking lot");
        }

        spot.setPricePerHour(pricePerHour);
        return spotRepository1.save(spot);
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
