package experimental;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterExample {
    private final RateLimiter rateLimiter;

    public RateLimiterExample(double permitsPerSecond) {
        // Crea un limitador que permite un número específico de permisos por segundo
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    public void performAction() {
        // Espera hasta que se pueda obtener un permiso
        rateLimiter.acquire();
        // Acción a realizar
        System.out.println("Acción realizada a las " + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        RateLimiterExample example = new RateLimiterExample(2.0); // 2 acciones por segundo

        // Simula múltiples acciones
        for (int i = 0; i < 10; i++) {
            new Thread(example::performAction).start();
        }
    }
}