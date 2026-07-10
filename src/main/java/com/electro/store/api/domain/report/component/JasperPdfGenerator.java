package com.electro.store.api.domain.report.component;

import com.electro.store.api.domain.report.exception.ReportGenerationException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.core.io.ClassPathResource;

@Component
@Slf4j
public class JasperPdfGenerator {

    private final ConcurrentMap<String, JasperReport> compiledTemplates = new ConcurrentHashMap<>();

    public byte[] generate(String templatePath, Map<String, Object> parameters) {
        try {
            JasperReport template = compiledTemplates.computeIfAbsent(templatePath, this::compile);
            JasperPrint filledReport = JasperFillManager.fillReport(template, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(filledReport);
        } catch (JRException e) {
            log.error("Error generating PDF from template '{}'", templatePath, e);
            throw new ReportGenerationException(templatePath, e);
        }
    }

    private JasperReport compile(String templatePath) {
        log.info("Compiling Jasper template '{}' (cached after first use)", templatePath);
        try (InputStream templateStream = new ClassPathResource(templatePath).getInputStream()) {
            return JasperCompileManager.compileReport(templateStream);
        } catch (IOException | JRException e) {
            throw new ReportGenerationException(templatePath, e);
        }
    }
}
