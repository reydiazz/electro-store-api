package com.electro.store.api.domain.backup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BackupService {

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    /**
     * Genera un backup de la base de datos SQL Server.
     * Como SQL Server corre en Docker, el backup se guarda en /var/opt/mssql/backup/
     * dentro del contenedor y luego se lee como bytes para enviarlo al cliente.
     */
    public BackupResult generarBackup() throws Exception {
        String dbName = extraerNombreBaseDatos(datasourceUrl);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = dbName + "_backup_" + timestamp + ".bak";

        String backupPath = "/var/opt/mssql/backup/" + fileName;

        jdbcTemplate.execute("EXEC xp_create_subdir '/var/opt/mssql/backup/'");

        String sql = "BACKUP DATABASE [" + dbName + "] TO DISK = N'" + backupPath
                + "' WITH FORMAT, INIT, NAME = N'ElectroStore Backup'";
        jdbcTemplate.execute(sql);

        byte[] fileBytes = leerArchivoDesdeServidor(backupPath);

        try {
            jdbcTemplate.execute("EXEC xp_cmdshell 'del \"" + backupPath + "\"'");
        } catch (Exception ignored) {
        }

        return new BackupResult(fileName, fileBytes);
    }

    private byte[] leerArchivoDesdeServidor(String rutaArchivo) {
        return jdbcTemplate.queryForObject(
                "SELECT BulkColumn FROM OPENROWSET(BULK N'" + rutaArchivo + "', SINGLE_BLOB) AS x",
                byte[].class
        );
    }

    private String extraerNombreBaseDatos(String url) {
        String[] parts = url.split(";");
        for (String part : parts) {
            if (part.trim().toLowerCase().startsWith("databasename=")) {
                return part.trim().substring("databaseName=".length());
            }
        }
        return "electro_db";
    }

    public record BackupResult(String fileName, byte[] fileBytes) {}
}
