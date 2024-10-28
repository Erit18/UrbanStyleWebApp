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
}
