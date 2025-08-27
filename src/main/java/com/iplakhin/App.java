package com.iplakhin;

import com.iplakhin.analyzer.Analyzer;
import com.iplakhin.analyzer.entities.AnalyzedWorkout;
import com.iplakhin.parser.processor.FileProcessor;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void notmain(String[] args) {
        Scanner initScanner = new Scanner(System.in);
        System.out.println("Input 0 if you need to get data or 1 if you want to upload data.");

        String answer = initScanner.nextLine();

        if (answer.equals("0")) {
            Instant startDate, endDate;

            System.out.println("Input start date in format '2025-01-01T00:00:00.000Z'");
            startDate = Instant.parse(initScanner.nextLine());
            System.out.println("Input end date in format '2025-01-01T00:00:00.000Z'");
            endDate = Instant.parse(initScanner.nextLine());

            getAnalyzedData(startDate, endDate);

        } else if (answer.equals("1")) {
            uploadData();
        }


    }

    public static void getAnalyzedData(Instant startDate, Instant endDate) {
        AnalyzedWorkout workout = Analyzer.analyzeWorkouts(startDate, endDate);

        System.out.println("For the period from " + startDate + " to " + endDate + " the following data was obtained:");
        System.out.println("Total time of all workouts is " + workout.getDuration() + " seconds.");
        System.out.println("Time spent in zone 1 is " + workout.getZone1() + " sec, which is " + workout.getZone1Percent() + "%.");
        System.out.println("Time spent in zone 2 is " + workout.getZone2() + " sec, which is " + workout.getZone2Percent() + "%.");
        System.out.println("Time spent in zone 3 is " + workout.getZone3() + " sec, which is " + workout.getZone3Percent() + "%.");
        System.out.println("Time spent in zone 4 is " + workout.getZone4() + " sec, which is " + workout.getZone4Percent() + "%.");
        System.out.println("Time spent in zone 5 is " + workout.getZone5() + " sec, which is " + workout.getZone5Percent() + "%.");
    }

    public static void uploadData() {
        List<String> reportList = new LinkedList<>();

        try {
            reportList = FileProcessor.processFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!reportList.isEmpty()) {
            System.out.println(reportList.toArray().toString());
        } else {
            System.out.println("reportList is empty");
        }


    }
}
