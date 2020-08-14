package com.mbw.office.common.lang.excel.handle;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-01-15 14:17
 */
@Component
public class ExportMatchHandlerListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(MatchExportType.class);
        ExportHandlerContext exportHandlerContext = event.getApplicationContext().getBean(ExportHandlerContext.class);
        beans.forEach((modelType, bean) -> {
            MatchExportType matchExportType = bean.getClass().getAnnotation(MatchExportType.class);
            exportHandlerContext.putExportHandler(matchExportType.value().getValue(), (AbstractExportHandler)bean);
        });
    }
}
