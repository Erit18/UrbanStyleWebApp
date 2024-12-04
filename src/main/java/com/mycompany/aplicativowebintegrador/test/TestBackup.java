package com.mycompany.aplicativowebintegrador.test;

import com.mycompany.aplicativowebintegrador.service.MantenimientoService;

public class TestBackup {
    public static void main(String[] args) {
        MantenimientoService service = new MantenimientoService();
        System.out.println("Ubicación de backups: " + service.getBackupLocation());
        
        if (service.verificarMySQLDump()) {
            System.out.println("mysqldump encontrado, procediendo con el backup de UrbanStyle...");
            service.realizarBackup();
        } else {
            System.out.println("ERROR: mysqldump no encontrado en la ruta especificada.");
            System.out.println("Por favor, verifica la ruta correcta de MySQL Workbench en tu sistema.");
        }
    }
} 