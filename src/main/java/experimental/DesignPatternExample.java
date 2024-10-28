package experimental;

public class DesignPatternExample {
    private static DesignPatternExample instance;
    private String data;
    
    private DesignPatternExample() {
        data = "Singleton Pattern Example";
    }
    
    public static DesignPatternExample getInstance() {
        if (instance == null) {
            instance = new DesignPatternExample();
        }
        return instance;
    }
    
    public void executeComplexTask() {
        for (int i = 0; i < 100; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 100; j++) {
                builder.append("Task_").append(i).append("_").append(j);
            }
        }
    }
}
