package com.mycompany.aplicativowebintegrador.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MantenimientoService {
    private static final Logger logger = LoggerFactory.getLogger(MantenimientoService.class);
    
    private static final String TOMCAT_HOME = "C:\\Users\\USUARIO\\Downloads\\apache-tomcat-11.0.1";
    private static final String BACKUP_DIR = TOMCAT_HOME + "\\backups\\";
    private static final String MYSQLDUMP_PATH = "C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysqldump.exe";
    
    public void realizarBackup() {
        try {
            File backupDirectory = new File(BACKUP_DIR);
            if (!backupDirectory.exists()) {
                backupDirectory.mkdirs();
                logger.info("Directorio de backups creado en: {}", BACKUP_DIR);
            }
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String backupFile = BACKUP_DIR + "backup_" + timestamp + ".sql";
            
            ProcessBuilder pb = new ProcessBuilder(
                MYSQLDUMP_PATH,
                "-h", "localhost",           // Host
                "-u", "root",               // Usuario
                "-perits321123",           // Tu contraseña
                "--add-drop-table",
                "--databases",
                "urbanstyledb"             // Nombre correcto de la base de datos
            );
            
            logger.info("Iniciando backup de urbanstyledb...");
            logger.info("Comando: {}", String.join(" ", pb.command()));
            
            if (!verificarBaseDatos()) {
                logger.error("No se puede acceder a la base de datos urbanstyledb");
                return;
            }
            
            pb.redirectOutput(new File(backupFile));
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            
            Process process = pb.start();
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("Backup creado exitosamente en: {}", backupFile);
                long fileSize = new File(backupFile).length();
                logger.info("Tamaño del archivo de backup: {} bytes", fileSize);
            } else {
                logger.error("Error al crear backup. Código de salida: {}", exitCode);
            }
            
        } catch (Exception e) {
            logger.error("Error durante el backup: " + e.getMessage(), e);
        }
    }
    
    private boolean verificarBaseDatos() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                MYSQLDUMP_PATH,
                "-h", "localhost",
                "-u", "root",
                "-perits321123",
                "--databases",
                "urbanstyledb",            // Nombre correcto de la base de datos
                "--no-data",
                "--no-create-info"
            );
            
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            logger.error("Error al verificar la base de datos: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verificarMySQLDump() {
        File mysqldump = new File(MYSQLDUMP_PATH);
        boolean exists = mysqldump.exists();
        logger.info("mysqldump existe en {}: {}", MYSQLDUMP_PATH, exists);
        return exists;
    }
    
    public String getBackupLocation() {
        return BACKUP_DIR;
    }
} 