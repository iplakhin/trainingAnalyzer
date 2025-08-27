package com.iplakhin.parser.processor;

import com.iplakhin.db.ProcessedWorkoutDAO;
import com.iplakhin.entities.ProcessedWorkout;
import com.iplakhin.parser.Parser;
import com.iplakhin.parser.processor.entities.Workout;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileProcessor {
    public static List<String> processFiles() {
        List<String> reportList = new LinkedList<>();
        Queue<Workout> workoutQueue = new LinkedList<>();

        File inputDir = new File("inputfiles");

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.out.println("Folder 'inputfiles' does not exist");
            return reportList;
        }

        File[] tcxFiles = null;
        try {
            tcxFiles = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".tcx"));
        } catch (SecurityException e) {
            System.out.println("Read access to directory is denied");
            e.printStackTrace();
        }

        if (tcxFiles == null || tcxFiles.length == 0) {
            System.out.println("No tcx files in directory");
            return reportList;
        }

        Queue<File> fileQueue = new LinkedList<>();
        fileQueue.addAll(Arrays.asList(tcxFiles));


        while (!fileQueue.isEmpty()) {
            Workout workout = new Workout();
            File currentFile = fileQueue.poll();

            try {
                workout = Parser.parseFile(currentFile);
                workoutQueue.add(workout);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!workout.isEmpty()) {
                reportList.add(currentFile.getName() + ": success");
            } else {
                reportList.add(currentFile.getName() + ": failed");
            }
        }


        Queue<ProcessedWorkout> processedWorkoutQueue = WorkoutProcessor.processWorkouts(workoutQueue);
        ProcessedWorkoutDAO.writeWorkouts(processedWorkoutQueue);

        return reportList;
    }
}
