import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.Comparator;
import java.util.Iterator;

import java.util.Collections;


class Day1 {
    public static void main(String[] args) {
        try{
            int result = Utils.readFile("../inputs/1.txt").stream().map(Day1::partTwo).map(Day1::partOne).reduce(0, (a, b) -> {
                int sum = a + b;
                System.out.println("Adding: " + a + " + " + b + " = " + sum);
                return sum;
            });
            System.out.println("-------");
            System.out.println("Result = " + result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String partTwo(String line){
        System.out.println("-------");
        System.out.println("Parsing: " + line);

        String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        Map<Integer, String> indices = new HashMap<>();

        for(int i = 0; i < numbers.length; i++){
            int index = line.indexOf(numbers[i]);
            if(index != -1){
                indices.put(index, numbers[i]);
            }
            while(index != -1){
                index = line.indexOf(numbers[i], index + 1);
                if(index != -1){
                    indices.put(index, numbers[i]);
                }
            }
        }

        if(indices.size() == 0){
            return line;
        }

        int lineLength = line.length();

        List<Integer> sortedIndices = indices.keySet().stream().sorted().collect(Collectors.toList());
        List<Integer> highIndices = sortedIndices.stream().filter(i -> i > (lineLength / 2)).sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        List<Integer> lowIndices = sortedIndices.stream().filter(i -> i <= (lineLength / 2)).collect(Collectors.toList());

        List<Integer> priorityIndices = new ArrayList<>();

        int lowIndex = 0;
        int highIndex = 0;

        while(lowIndex < lowIndices.size() && highIndex < highIndices.size()){
            int low = lowIndices.get(lowIndex);
            int high = highIndices.get(highIndex);
            int highDelta = lineLength - high - indices.get(high).length();
            if(low <= highDelta){
                priorityIndices.add(low);
                lowIndex++;
            }else{
                priorityIndices.add(high);
                highIndex++;
            }
        }

        if(lowIndex < lowIndices.size()){
            priorityIndices.addAll(lowIndices.subList(lowIndex, lowIndices.size()));
        }
        if(highIndex < highIndices.size()){
            priorityIndices.addAll(highIndices.subList(highIndex, highIndices.size()));
        }

        System.out.println("order of indices to replace: " + priorityIndices);

        for(int i = 0; i < priorityIndices.size(); i++){
            Integer index = priorityIndices.get(i);
            String number = indices.get(index);
            String digit = digits[Arrays.asList(numbers).indexOf(number)];

            if(line.indexOf(number) == -1){
                continue;
            }

            int indexDecrease = 0;

            for(int j = 0; j < i && j != i; j++){
                Integer prevIndex = priorityIndices.get(j);
                String prevNumber = indices.get(prevIndex);
                if(prevIndex < index){
                    indexDecrease += (prevNumber.length() - 1);
                }
            }
            
            int start = index - indexDecrease;
            line = line.substring(0, start) +  line.substring(start).replaceFirst(number, digit);
        }

        System.out.println("To: " + line);
        return line;
    }

    private static int partOne(String line){
        int left = 0;
        int right = line.length() - 1;
        Integer first = null;
        Integer second = null;

        while(left <= right && (first == null || second == null)){
            char leftChar = line.charAt(left);
            char rightChar = line.charAt(right);
            try{
                first = Integer.parseInt(String.valueOf(leftChar));
            }catch(Exception e){
                left++;
            }
            try{
                second = Integer.parseInt(String.valueOf(rightChar));
            }catch(Exception e){
                right--;
            }
        }
        if(first != null && second != null){
            System.out.println("Values: " + ((first * 10) + second));
            return (first * 10) + second;
        }else if(first != null){
            System.out.println("Values: " + ((first * 10) + first));
            return (first * 10) + first;
        }else if(second != null){
            System.out.println("Values: " + ((second * 10) + second));
            return (second * 10) + second;
        }
        throw new RuntimeException("No values found");
    }
}
