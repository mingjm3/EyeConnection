package com.eyeconnection.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AvailableDatesRepository;
import com.eyeconnection.server.entity.AvailableDates;

@Service
public class DoctorService {
    private final AvailableDatesRepository availableDatesRepository;

    public DoctorService(AvailableDatesRepository availabledDatesRepository) {
        this.availableDatesRepository = availabledDatesRepository;
    }

    public void updateAvailableDates(Long doctorSysId, LocalDateTime[] newAvailabeDates) throws Exception {
        ArrayList<AvailableDates> newAvailableDatesRecords = new ArrayList<>();
        for(LocalDateTime date : newAvailabeDates) {
            newAvailableDatesRecords.add(new AvailableDates(doctorSysId, date));
        }
        availableDatesRepository.deleteByDoctorSysId(doctorSysId);
        availableDatesRepository.saveAllAndFlush(newAvailableDatesRecords);
    }
}
