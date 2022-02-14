package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookitService {
    @Autowired
    Repository repo;


    public String getTodaysDate() {

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (date.format(formatter));
    }


    public void generateTimeslots(int numOfDaysFromToday) {

        int num = 0;
        int employee = 0;
        LocalDate today = LocalDate.now();
        String startTime;
        String endTime;

        String[] startTimeArray = {"08:00:00", "09:00:00", "10:00:00", "11:00:00", "14:00:00", "15:00:00", "16:00:00"};


        for (int i = 0; i < numOfDaysFromToday; i++) {
            for (int j = 0; j < ThreadLocalRandom.current().nextInt(10, 15); j++) {
                employee = ThreadLocalRandom.current().nextInt(1, 4);
                String date = today.plusDays(i).toString();
                startTime = startTimeArray[ThreadLocalRandom.current().nextInt(1, startTimeArray.length)];
                endTime = startTime.substring(0, 2) + ":45:00";
                repo.newTimeslot(employee, date, startTime, endTime);
                num += 1;
            }
        }
        System.out.println(num + " timeslots generated!");

    }

    public ArrayList<Timeslot> hideDuplicateTimeslots(ArrayList<Timeslot> timeslots) {

        ArrayList<Timeslot> noDuplicates = new ArrayList<>();
        if (timeslots.size()==0) return noDuplicates;

        boolean isDuplicate;

        noDuplicates.add(timeslots.get(0));

        for (Timeslot timeslot : timeslots) {
            isDuplicate = false;
            for (Timeslot noDuplicate : noDuplicates) {
                if (timeslot.getStartTime().equals(noDuplicate.getStartTime())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                noDuplicates.add(timeslot);
            }
        }
        return noDuplicates;
    }

}
