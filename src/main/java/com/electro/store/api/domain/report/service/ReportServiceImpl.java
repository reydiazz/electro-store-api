package com.electro.store.api.domain.report.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ReportServiceImpl implements ReportService{

    @Override
    public byte[] generarReporte(String nombreReporte, List<?> datos, Map<String, Object> parametros) throws Exception {

        InputStream reporte = new ClassPathResource("reports/" + nombreReporte + ".jrxml").getInputStream();
        JasperReport jasperReport = JasperReportCompileManager.compileReport(reporte);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(datos);
        parametros.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
        return JasperExportManage.exportReportToPdf(jasperPrint);

    }

}
