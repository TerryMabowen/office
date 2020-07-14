* Apache POI 中常用的类
```
    HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
    XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
    HWPF － 提供读写Microsoft Word DOC97格式档案的功能。
    XWPF － 提供读写Microsoft Word DOC2003格式档案的功能。
    HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
    HDGF － 提供读Microsoft Visio格式档案的功能。
    HPBF － 提供读Microsoft Publisher格式档案的功能。
    HSMF － 提供读Microsoft Outlook格式档案的功能。
```

* Excel中的工作簿、工作表、行、单元格中的关系:
```
    一个Excel文件对应于一个workbook(HSSFWorkbook)，
    一个workbook可以有多个sheet（HSSFSheet）组成，
    一个sheet是由多个row（HSSFRow）组成，
    一个row是由多个cell（HSSFCell）组成
```