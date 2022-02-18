package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public ArrayList<Timeslot> prepareTimeslotArrayForPresentationOnWeb(String date) throws ParseException {
        // First - get ArrayList on correct day
        ArrayList<Timeslot> preparedList = repo.getEmptyTimeslotsOnDate(date);

        // Second - remove duplicates
        preparedList = hideDuplicateTimeslots(preparedList);

        // Third - Parse time from HH:MM:SS to HH:MM
        preparedList = parseTimeslotTimesToHHMM(preparedList);

        return preparedList;
    }

    public void generateTimeslots(int numOfDaysFromToday) throws ParseException {
        int num = 0;
        int employee = 0;
        LocalDate today = LocalDate.now();
        String startTime;
        String endTime;

        String[] startTimeArray = {"08:00:00", "08:30:00",  "08:45:00","09:00:00", "09:30:00", "10:00:00", "10:45:00", "11:00:00", "14:00:00", "14:30:00", "15:00:00", "15:45:00", "16:00:00"};
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < numOfDaysFromToday; i++) {
            for (int j = 0; j < ThreadLocalRandom.current().nextInt(10, 15); j++) {
                employee = ThreadLocalRandom.current().nextInt(1, 4);
                String date = today.plusDays(i).toString();
                startTime = startTimeArray[ThreadLocalRandom.current().nextInt(1, startTimeArray.length)];
                Date d = df.parse(startTime);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE, 45);
                endTime = df.format(cal.getTime());

//                endTime = startTime.substring(0, 2) + ":45:00";
                repo.newTimeslot(employee, date, startTime, endTime);
                num += 1;
            }
        }
        System.out.println(num + " timeslots generated!");
    }

    public String parseTimeToHHMM(String time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
//        cal.add(Calendar.MINUTE, 10);
        return df.format(cal.getTime());
    }

    public ArrayList<Timeslot> parseTimeslotTimesToHHMM(ArrayList<Timeslot> timeslots) throws ParseException {
        ArrayList<Timeslot> parsedTimes;
        parsedTimes = (ArrayList)timeslots.clone();
        String oldStartTime;
        String oldEndTime;

        for (Timeslot parsedTime : parsedTimes) {
            oldStartTime = parsedTime.getStartTime();
            System.out.println(oldStartTime);
            parsedTime.setStartTime(parseTimeToHHMM(oldStartTime));
            System.out.println(parsedTime.getStartTime());
            oldEndTime = parsedTime.getEndTime();
            parsedTime.setEndTime(parseTimeToHHMM(oldEndTime));       }

        return parsedTimes;

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
