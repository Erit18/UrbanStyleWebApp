package experimental;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.google.common.util.concurrent.RateLimiter;

public class FilePerformanceWithLimiter {
    private static final String FILE_PATH = "testfile.txt";
    private static final int FILE_SIZE_MB = 10; // Tamaño del archivo en MB
    private static final double WRITE_PERMITS_PER_SECOND = 5.0; // Permisos de escritura por segundo
    private static final double READ_PERMITS_PER_SECOND = 5.0; // Permisos de lectura por segundo

    public static void main(String[] args) {
        writeFile();
        readFile();
    }

    private static void writeFile() {
        byte[] data = new byte[FILE_SIZE_MB * 1024 * 1024]; // Convertir MB a bytes
        new Random().nextBytes(data); // Llenar el archivo con datos aleatorios

        RateLimiter rateLimiter = RateLimiter.create(WRITE_PERMITS_PER_SECOND); // Limitador de escritura

        long startTime = System.currentTimeMillis();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(FILE_PATH))) {
            for (byte b : data) {
                rateLimiter.acquire(); // Espera hasta que se pueda obtener un permiso
                os.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de escritura: " + (endTime - startTime) + " ms");
    }

    private static void readFile() {
        RateLimiter rateLimiter = RateLimiter.create(READ_PERMITS_PER_SECOND); // Limitador de lectura

        long startTime = System.currentTimeMillis();
        try (InputStream is = new BufferedInputStream(new FileInputStream(FILE_PATH))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                rateLimiter.acquire(); // Espera hasta que se pueda obtener un permiso
                // Usar bytesRead para procesar el buffer
                // Aquí puedes almacenar o imprimir los bytes leídos
                System.out.write(buffer, 0, bytesRead); // Ejemplo de uso
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de lectura: " + (endTime - startTime) + " ms");
    }
}