package com.electro.store.api.domain.report.service;

import java.util.List;
import java.util.Map;

public interface ReportService {

    byte[] generarReporte(String nombreReporte, List<?> datos, Map<String, Object> parametros) throws Exception;

}
