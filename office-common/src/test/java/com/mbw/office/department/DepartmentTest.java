package com.mbw.office.department;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.io.FileUtil;
import com.mbw.office.common.util.json.JacksonUtil;
import com.mbw.office.department.pojo.Department;
import com.mbw.office.department.pojo.DtDepartment;
import com.mbw.office.department.pojo.OaDepartment;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-15 14:08
 */
@Slf4j
public class DepartmentTest {
    private String fileDir = "/Users/apple_22/Desktop/F100/虚拟银行系统/data/钉钉和OA部门数据比对/";

    @Test
    public void f1() {
        List<OaDepartment> oaDepartments = listOaDepartment();
//        System.out.println("oa department: " + oaDepartments);

        List<DtDepartment> dtDepartments = listDtDepartment();
//        System.out.println("dt department: " + dtDepartments);

//        compare(dtDepartments, oaDepartments);

        List<Department> noEqualCodeDepartments = getNoEqualCodeDepartments(dtDepartments, oaDepartments);
        ExportParams params = new ExportParams("部门编码不一致", "部门编码不一致");
        String fileName = "部门编码不一致的钉钉和OA部门表_" + System.currentTimeMillis() + ".xlsx";
        Workbook workbook = ExcelExportUtil.exportExcel(params, Department.class, noEqualCodeDepartments);
        exportExcel(workbook, fileDir + "export/", fileName);

        List<Department> noCodeDepartments = getNoCodeDepartments(dtDepartments, oaDepartments);
        ExportParams params2 = new ExportParams("没有部门编码", "没有部门编码");
        String fileName2 = "钉钉中未配置编码的部门表_" + System.currentTimeMillis() + ".xlsx";
        Workbook workbook2 = ExcelExportUtil.exportExcel(params2, Department.class, noCodeDepartments);
        exportExcel(workbook2, fileDir + "export/", fileName2);
    }

    private List<Department> compare(List<DtDepartment> dtDepartments, List<OaDepartment> oaDepartments) {
        List<Department> departments = new ArrayList<>();
        List<Department> noCodeDepartments = new ArrayList<>();
        List<Department> noEqualCodeDepartments = new ArrayList<>();
        if (CollUtil.isNotEmpty(dtDepartments) && CollUtil.isNotEmpty(oaDepartments)) {
            Map<String, OaDepartment> map = oaDepartments.stream().collect(Collectors.toMap(OaDepartment::getOaDepartmentCode, code -> code,  (code1, code2) -> code1));
            Map<String, OaDepartment> map2 = oaDepartments.stream().collect(Collectors.toMap(OaDepartment::getOaDepartmentName, name -> name,  (name1, name2) -> name1));
            for (DtDepartment dtDepartment : dtDepartments) {
                if (StrUtil.isNotBlank(dtDepartment.getOaDepartmentCode())) {
                    OaDepartment oaDepartment = map.get(dtDepartment.getOaDepartmentCode());
                    if (oaDepartment != null) {
                        Department dept = new Department();
                        dept.setOaDepartmentCode(oaDepartment.getOaDepartmentCode());
                        dept.setOaDepartmentName(oaDepartment.getOaDepartmentName());

                        departments.add(dept);
                    } else {
                        Department dept = new Department();
                        dept.setDtDepartmentCode(dtDepartment.getOaDepartmentCode());
                        dept.setDtDepartmentName(dtDepartment.getDtDepartmentName());

                        OaDepartment oaDepartment1 = map2.get(dtDepartment.getDtDepartmentName());
                        if (oaDepartment1 != null) {
                            dept.setOaDepartmentCode(oaDepartment1.getOaDepartmentCode());
                            dept.setOaDepartmentName(oaDepartment1.getOaDepartmentName());
                        }

                        noEqualCodeDepartments.add(dept);
                    }
                } else {
                    Department dept = new Department();
                    dept.setDtDepartmentName(dtDepartment.getDtDepartmentName());

                    OaDepartment oaDepartment = map2.get(dtDepartment.getDtDepartmentName());
                    if (oaDepartment != null) {
                        dept.setOaDepartmentCode(oaDepartment.getOaDepartmentCode());
                        dept.setOaDepartmentName(oaDepartment.getOaDepartmentName());
                    }

                    noCodeDepartments.add(dept);
                }
            }
        }

        log.info("钉钉与OA部门共有{}个编码保持一致", departments.size());
        log.info("钉钉没有配置OA部门编码的有{}个，分别是: {}", noCodeDepartments.size(), JacksonUtil.beanToJson(noCodeDepartments));
        log.info("钉钉配置的部门编码在OA系统中未查找到对应的部门，共有{}个这样的数据。分别是：{}", noEqualCodeDepartments.size(),  JacksonUtil.beanToJson(noEqualCodeDepartments));

        return departments;
    }

    private List<Department> getNoEqualCodeDepartments(List<DtDepartment> dtDepartments, List<OaDepartment> oaDepartments) {
        List<Department> noEqualCodeDepartments = new ArrayList<>();
        if (CollUtil.isNotEmpty(dtDepartments) && CollUtil.isNotEmpty(oaDepartments)) {
            Map<String, OaDepartment> map = oaDepartments.stream().collect(Collectors.toMap(OaDepartment::getOaDepartmentCode, code -> code,  (code1, code2) -> code1));
            Map<String, OaDepartment> map2 = oaDepartments.stream().collect(Collectors.toMap(OaDepartment::getOaDepartmentName, name -> name,  (name1, name2) -> name1));
            for (DtDepartment dtDepartment : dtDepartments) {
                if (StrUtil.isNotBlank(dtDepartment.getOaDepartmentCode())) {
                    OaDepartment oaDepartment = map.get(dtDepartment.getOaDepartmentCode());
                    if (oaDepartment == null) {
                        Department dept = new Department();
                        dept.setDtDepartmentCode(dtDepartment.getOaDepartmentCode());
                        dept.setDtDepartmentName(dtDepartment.getDtDepartmentName());

                        OaDepartment oaDepartment1 = map2.get(dtDepartment.getDtDepartmentName());
                        if (oaDepartment1 != null) {
                            dept.setOaDepartmentCode(oaDepartment1.getOaDepartmentCode());
                            dept.setOaDepartmentName(oaDepartment1.getOaDepartmentName());

                            noEqualCodeDepartments.add(dept);
                        }
                    }
                }
            }
        }

        log.info("钉钉配置的部门编码在OA系统中未查找到对应的部门，共有{}个这样的数据。分别是：{}", noEqualCodeDepartments.size(),  JacksonUtil.beanToJson(noEqualCodeDepartments));

        return noEqualCodeDepartments;
    }

    private List<Department> getNoCodeDepartments(List<DtDepartment> dtDepartments, List<OaDepartment> oaDepartments) {
        List<Department> noCodeDepartments = new ArrayList<>();
        if (CollUtil.isNotEmpty(dtDepartments) && CollUtil.isNotEmpty(oaDepartments)) {
            Map<String, OaDepartment> map = oaDepartments.stream().collect(Collectors.toMap(OaDepartment::getOaDepartmentName, name -> name,  (name1, name2) -> name1));
            for (DtDepartment dtDepartment : dtDepartments) {
                if (StrUtil.isBlank(dtDepartment.getOaDepartmentCode())) {
                    Department dept = new Department();
                    dept.setDtDepartmentName(dtDepartment.getDtDepartmentName());

                    OaDepartment oaDepartment = map.get(dtDepartment.getDtDepartmentName());
                    if (oaDepartment != null) {
                        dept.setOaDepartmentCode(oaDepartment.getOaDepartmentCode());
                        dept.setOaDepartmentName(oaDepartment.getOaDepartmentName());

                        noCodeDepartments.add(dept);
                    }
                }
            }
        }

        log.info("钉钉没有配置OA部门编码的有{}个，分别是: {}", noCodeDepartments.size(), JacksonUtil.beanToJson(noCodeDepartments));

        return noCodeDepartments;
    }

    private List<DtDepartment> listDtDepartment() {
        String fileName = "钉钉部门.xlsx";
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            throw new ServiceException(fileDir + fileName + " 文件未找到");
        }

        ImportParams params = new ImportParams();
        List<DtDepartment> dtDepartments = ExcelImportUtil.importExcel(file, DtDepartment.class, params);
        if (CollUtil.isNotEmpty(dtDepartments)) {
            return dtDepartments;
        }

        return Collections.emptyList();
    }

    private List<OaDepartment> listOaDepartment() {
        String fileName = "OA部门信息.xlsx";
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            throw new ServiceException(fileDir + fileName + " 文件未找到");
        }

        ImportParams params = new ImportParams();
        List<OaDepartment> oaDepartments = ExcelImportUtil.importExcel(file, OaDepartment.class, params);
        if (CollUtil.isNotEmpty(oaDepartments)) {
            return oaDepartments;
        }
        return Collections.emptyList();
    }

    private void exportExcel(Workbook workbook, String fileDir, String fileName) {
        FileUtil.createFile(fileDir, fileName);

        OutputStream os = null;
        try {
            File file = new File(fileDir, fileName);
            os = new FileOutputStream(file);

            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
