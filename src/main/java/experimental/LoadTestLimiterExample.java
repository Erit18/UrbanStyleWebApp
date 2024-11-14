package experimental;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadTestLimiterExample {
    private static final int NUMBER_OF_USERS = 100; // Número de usuarios simulados

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de hilos

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            final int userId = i;
            executor.submit(() -> {
                // Simula una acción de usuario
                simulateUserAction(userId);
            });
        }

        executor.shutdown();
    }

    private static void simulateUserAction(int userId) {
        // Simula una acción, como una solicitud a un servicio
        System.out.println("Usuario " + userId + " realizando una acción a las " + System.currentTimeMillis());
        try {
            Thread.sleep((long) (Math.random() * 1000)); // Simula un tiempo de respuesta aleatorio
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}