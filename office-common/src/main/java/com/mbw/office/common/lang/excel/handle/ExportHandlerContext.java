package com.mbw.office.common.lang.excel.handle;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-01-15 14:15
 */
@Component
public class ExportHandlerContext {
    private final Map<String, AbstractExportHandler> exportHandlerMap = new HashMap<>();

    public AbstractExportHandler getExportHandler(String modelType) {
        return exportHandlerMap.get(modelType);
    }

    public void putExportHandler(String modelType, AbstractExportHandler exportHandler) {
        exportHandlerMap.put(modelType, exportHandler);
    }
}
