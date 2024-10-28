package experimental;

import java.util.*;

public class DataStructureExample {
    private List<String> stringList;
    private Map<Integer, String> dataMap;
    private Set<Double> numberSet;
    
    public DataStructureExample() {
        stringList = new ArrayList<>();
        dataMap = new HashMap<>();
        numberSet = new HashSet<>();
    }
    
    public void processData() {
        for (int i = 0; i < 1000; i++) {
            stringList.add("Item_" + i);
            dataMap.put(i, "Value_" + i);
            numberSet.add(Math.random() * 100);
        }
    }
    
    public void complexOperations() {
        Queue<Integer> queue = new LinkedList<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        Deque<Double> deque = new ArrayDeque<>();
        
        for (int i = 0; i < 500; i++) {
            queue.offer(i);
            priorityQueue.offer("Priority_" + (1000 - i));
            deque.addFirst(Math.random());
        }
        
        TreeMap<String, List<Integer>> treeMap = new TreeMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                values.add((int)(Math.random() * 100));
            }
            treeMap.put(String.valueOf(c), values);
        }
    }
    
    public void stringManipulation() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result.append("String_").append(i).append("_");
            if (i % 100 == 0) {
                result.append(System.lineSeparator());
            }
        }
        
        String[] parts = result.toString().split("_");
        Set<String> uniqueParts = new LinkedHashSet<>(Arrays.asList(parts));
    }
}
