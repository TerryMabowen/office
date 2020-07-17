package com.mbw.office.demo.biz.jalian.file;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.util.io.FileUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-17 10:18
 */
@Service
public class ReadTxtService {
    public File getTxtFile(String path) {
        return FileUtil.getFile(path);
    }

    public List<String> readTxt(File txt) throws IOException {
        if (txt != null) {
            List<String> lineList = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(txt));
            String line = "";
            while (StrUtil.isNotBlank((line = bufferedReader.readLine()))) {
                lineList.add(line.trim());
            }
            bufferedReader.close();

            return lineList;
        }

        return Collections.emptyList();
    }

    public void parseLine(List<String> lineList) {
        if (CollUtil.isNotEmpty(lineList)) {
            for (String line : lineList) {
                System.out.println(line);
            }
        }
    }

}
