package com.iplakhin.parser;

import org.w3c.dom.*;
import com.iplakhin.parser.processor.entities.Workout;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.*;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Set;


public class Parser {
    private static HashMap<Integer, Integer> convertToHashMap (NodeList nodeList) {
        HashMap<Integer, Integer> heartRateValues = new HashMap<Integer, Integer>();

        if (nodeList == null) {
            return heartRateValues;
        }

        for (int i=0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            Integer key, value;
            if (node.getTextContent() != null) { //
                try {
                    key = Integer.parseInt(node.getTextContent());
                } catch (DOMException e) {
                    System.out.println("Не удалось скастить значение пульса в int");
                    throw new RuntimeException(e);
                }
            } else {
                continue;
            }

            if (heartRateValues.containsKey(key)) {
                value = heartRateValues.get(key);
                heartRateValues.put(key, ++value);
            } else {
                heartRateValues.put(key, 1);
            }
        }
        return heartRateValues;
    }

    private static int countTotalTimeWithValues (HashMap<Integer, Integer> heartRateValues) {
        int totalTimeWithValues = 0;
        Set<Integer> keySet = heartRateValues.keySet();

        for (Integer key: keySet) {
            totalTimeWithValues = totalTimeWithValues + heartRateValues.get(key);
        }
        return totalTimeWithValues;
    }

    private static String extractIdByTag (Document document, XPathFactory xpathFactory) {
        final String EXPRESSION = "//*[local-name()='Id']";
        String workoutId = null;

        XPath xPath = xpathFactory.newXPath();
        XPathExpression expr;

        try {
            expr = xPath.compile(EXPRESSION);
            workoutId = (String) expr.evaluate(document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            System.out.println("Tag <Id> is not found in document or is empty");
            throw new RuntimeException(e);
        }

        return workoutId;
    }

    private static Integer extractTotalTimeByTag (Document document, XPathFactory xpathFactory) {
        final String EXPRESSION = "//*[local-name()='TotalTimeSeconds']";
        Integer totalTime = 0;

        XPath xPath = xpathFactory.newXPath();
        XPathExpression expr;

        try {
            expr = xPath.compile(EXPRESSION);
            double idDouble = Double.parseDouble(expr.evaluate(document, XPathConstants.NUMBER).toString());
            totalTime = (int) idDouble;
        } catch (XPathExpressionException e) {
            System.out.println("Tag <TotalTimeSeconds> is not found in document or is empty");
            throw new RuntimeException(e);
        }
        return totalTime;
    }

    private static HashMap<Integer, Integer> extractHeartRateValues (Document document, XPathFactory xpathFactory) {
        final String EXPRESSION = "//*[local-name()='Trackpoint']/*[local-name()='HeartRateBpm']/*[local-name()='Value']";
        NodeList heartRateNodeList = null;

        XPath xPath = xpathFactory.newXPath();
        XPathExpression expr;

        try {
            expr = xPath.compile(EXPRESSION);
            heartRateNodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            System.out.println("Some problem with parsing HeartRate values");
            throw new RuntimeException(e);
        }

        return convertToHashMap(heartRateNodeList);
    }

    private static Instant convertStringToDate (String workoutId) {
        Instant dateWorkoutId = null;
        try {
            dateWorkoutId = Instant.parse(workoutId);
        } catch (DateTimeParseException e) {
            System.out.println("Error while parsing workoutId to data");
            e.printStackTrace();
        }
        return dateWorkoutId;
    }

    public static Workout parseFile (File file) throws Exception {
        Instant workoutId;
        int totalTime;
        int totalTimeWithValues;
        NodeList heartRateNodeList = null;
        HashMap<Integer, Integer> heartRateValues = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        XPathFactory xpathFactory = XPathFactory.newInstance();

        workoutId = Parser.convertStringToDate(extractIdByTag(document, xpathFactory));
        totalTime = extractTotalTimeByTag(document, xpathFactory);
        heartRateValues = extractHeartRateValues(document, xpathFactory);

        // идет по хешмапу, складывает все значения
        totalTimeWithValues = countTotalTimeWithValues(heartRateValues);

        Workout workout = new Workout(workoutId, totalTime, totalTimeWithValues, heartRateValues);

        System.out.println(workout);
        return workout;
    }

}


