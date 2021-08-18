package com.site.blog.my.core.util;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoiForWordUtil {
    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     * @param inputUrl 模板存放地址
     * @param outputUrl 新文档存放地址
     * @param textMap 需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     * @return 成功返回true,失败返回false
     */
    public static boolean changWord(String inputUrl, String outputUrl,
                                    Map<String, String> textMap, List<String[]> tableList, Map<String,String> diyMap) {

        //模板转换默认成功
        boolean changeFlag = true;
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //解析替换文本段落对象
            PoiForWordUtil.changeText(document, textMap,diyMap);
            //解析替换表格对象
            PoiForWordUtil.changeTable(document, textMap, tableList,diyMap);

            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }

        return changeFlag;

    }

    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, String> textMap,Map<String,String> diyMap){
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        foreachPara(paragraphs,textMap,diyMap);
    }

    /**
     * 替换表格对象方法
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     */
    public static void changeTable(XWPFDocument document, Map<String, String> textMap,
                                   List<String[]> tableList,Map<String,String> diyMap){
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且不循环表头
            XWPFTable table = tables.get(i);
            if(table.getRows().size()>1){
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
//                if(checkText(table.getText())){
                if(checkTabelText(table.getText())){
                    List<XWPFTableRow> rows = table.getRows();
                    eachTable(rows,textMap,diyMap);
                    //遍历表格,并替换模板
//                    List list = new ArrayList();
//                    list.add(tableList);
//                    insertTable(table, list);
                }else{
//                  System.out.println("插入"+table.getText());
                    insertTable(table, tableList);
                }
            }
        }
    }

    /**
     * 把表头中的关键字去掉 ${key}
     * @param rows 表格行对象
     */
    public static void eachTable(List<XWPFTableRow> rows, Map<String, String> textMap,Map<String,String> diyMap){
        //遍历table中的行数
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            //遍历行中的单元格
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if(checkText(cell.getText())){
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    foreachPara(paragraphs,textMap,diyMap);
                }
            }
        }
    }

    /**
     * 段落文本内容分段并替换
     * @param paragraphs 文本段落
     * @param textMap 替换的内容
     */
    public static void foreachPara(List<XWPFParagraph> paragraphs,Map<String, String> textMap,Map<String,String> diyMap){
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getText();
            //担保人信息动态处理
            if (text.contains("${dbLocation}")){
                dbParamCheck(paragraph,diyMap);
            }

            textMap.putAll(diyMap);
//            for(String key:textMap.keySet()){
//                key = "${"+key+"}";
//                遍历获取段落中所有的runs
//                if(text.contains(key)){
            if(text.contains("${")){
                Matcher matcher;
                //段落分段
                List<XWPFRun> runs = paragraph.getRuns();
                for (Integer i = 0; i < runs.size(); i++) {
                    String text0 = runs.get(i).getText(runs.get(i).getTextPosition());
                    // 有时候还会把${ 分成 $ 和 {  或者空格$
                    if (text0 != null && text0.contains("$")) {
                        //用num记录$与}之间相差的位置值
                        int num = 0;
                        int j = i + 1;
                        for (; j < runs.size(); j++) {
                            String text1 = runs.get(j).getText(runs.get(j).getTextPosition());
                            if (text1 != null && text1.contains("}")) {
                                num = j - i;
                                break;
                            }
                        }
                        if (num != 0) {
                            //num!=0说明找到了${}配对，需要替换
                            StringBuilder newText = new StringBuilder();
                            for (int s = i; s <= i + num; s++) {
                                String text2 = runs.get(s).getText(runs.get(s).getTextPosition());
                                if(text2!=null){
                                    newText.append(text2);
                                }
                                runs.get(s).setText("", 0);
                            }
                            runs.get(i).setText(newText.toString(), 0);
                            //重新定义遍历位置，跳过设置为null的位置
                            i = j + 1;
                        }
                    }
                }
                // 匹配传入信息集合与模板
                for (int i = 0; i < runs.size(); i++) {
                    XWPFRun run = runs.get(i);
                    String runText = run.toString().trim();
                    matcher = matcher(runText);
                    if (matcher.find()) {
                        while ((matcher = matcher(runText)).find()) {
                            //去除前后空格  ${XXX} 可能被分割成 ${空格XXX空格}
                            runText = matcher.replaceFirst(String.valueOf(textMap.get(matcher.group(1).trim())));
                        }
                        //设置替换值
                        //if (StringUtils.isNotEmpty(runText) && null != runText) {
                        if (!runText.contains("$") && null != runText) {
                            run.setText(runText, 0);
                        } else {
                            run.setText("null", 0);
                        }
                    }
                }
            }
//            }
            //没有匹配到的埋点默认为null
//            List<XWPFRun> runs = paragraph.getRuns();
//            if(text.contains("$")){
//                for(XWPFRun run:runs){
//                    if(checkText(run.toString())){
//                        run.setText("null", 0);
//                    }
//                }
//            }
        }
    }
    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private static Matcher matcher(String str){
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 担保人信息动态生成
     * @param paragraph
     * @param diyMap
     */
    private static void dbParamCheck(XWPFParagraph paragraph,Map<String,String> diyMap){
        String text = paragraph.getText();
        StringBuilder sb = new StringBuilder();

        List<XWPFRun> dbRuns = paragraph.getRuns();
        XWPFRun dbRun = dbRuns.get(0);
        if (diyMap.size()!=0){
            List<String> list = new ArrayList();
            for(String key:diyMap.keySet()){
                //判断担保map中编号数
                String splitKey = String.valueOf(key.charAt(key.length() - 1));
                list.add(splitKey);
            }
            //担保map编号数乱序，需全部取出重新从小到大排序
            Set set = new HashSet();
            List<String> newList = new  ArrayList();
            for (String cd:list) {
                if(set.add(cd)){
                    newList.add(cd);
                }
            }
            Collections.sort(newList);
            //text追加
            for(String value:newList){
                StringBuilder sbName = new StringBuilder("丙方");
                StringBuilder sbIdCard = new StringBuilder("居民身份证/注册号:");
                sbName.append(value).append("(连带保证人):").append(diyMap.get("conName"+value)).append("\r\n");
                sbIdCard.append(diyMap.get("conIdCard"+value)).append("\r\n");
                sb.append(sbName).append(sbIdCard);
            }
            dbRun.setText(sb.toString(),0);
        }else {
            //担保生成位置清空
            dbRun.setText("",0);
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public static void insertTable(XWPFTable table, List<String[]> tableList){
        //表格预处理
        int tableNum = table.getRows().size();
        if (tableNum<=tableList.size()){
            for(int i = 1;i<tableList.size();i++){
                table.removeRow(1);
            }
        }else{
            for (int i=1;i<tableNum;i++){
                table.removeRow(1);
            }
        }
        //创建行,根据需要插入的数据添加新行，不处理表头
        for(int i = table.getRows().size()-1; i < tableList.size(); i++){
            table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        for(int i = 1; i < rows.size(); i++){
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for(int j = 0; j < cells.size(); j++){
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i-1)[j]);
            }
        }
    }

    /**
     * 判断文本中时候包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkText(String text){
        boolean check  =  false;
//        if (text.indexOf("$")==0 && text.charAt(text.length()-1)=='}'){
//            check = true;
//        }
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;

    }

    /**
     * 判断表格文本中时候包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkTabelText(String text){
        boolean check  =  false;
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;

    }

    //pdf更改后缀doc
    public static String pdfSwichDocPath(String pdfPath){
        String docPath = pdfPath.split("\\.")[0].concat(".doc");
        return docPath;
    }

    /**
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, String> textMap){
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            String key = "${"+textSet.getKey()+"}";
            if((key.equals(value))){
                value = textSet.getValue();
                return value;
            }
        }
        if(checkText(value)){
            value = "";
        }
        return value;
    }


    public static void main(String[] args) {
        //模板文件地址
        // String inputUrl = "D:/pdf/测试.docx";
        String inputUrl = "/020有担保.docx";
        //新生产的模板文件
        // String inputUrl = "D:/pdf/out.docx";
        String outputUrl = "/020有担保Temp.docx";

        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("name","老王2");

        Map<String, String> diyMap = new HashMap<String, String>();
        diyMap.put("conIdCard1","老白身份证号");
        diyMap.put("conName1","老白");
        diyMap.put("conIdCard2","老绿身份证号");
        diyMap.put("conName2","老绿");
        diyMap.put("conIdCard3","老红身份证号");
        diyMap.put("conName3","老红");

        List<String[]> testList = new ArrayList<String[]>();
        //表格有数据 则删掉原表 新增插入，若没数据，则表格字段匹配替换
        testList.add(new String[]{"表格测试","","",""});
        testList.add(new String[]{"单元格2行1格","单元格2行2格","单元格2行3格",""});
//        testList.add(new String[]{"单元格3行1格","单元格3行2格","单元格3行3格","单元格3行4格"});
        //测试合同生成
        PoiForWordUtil.changWord(inputUrl, outputUrl, testMap, testList,diyMap);

/**
 * POI获取doc里面的元素示例
 *
 *             //解析docx模板并获取document对象
 *             XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
 *             //获取整个文本对象
 *             List<XWPFParagraph> allParagraph = document.getParagraphs();
 *             //获取整个表格对象
 *             List<XWPFTable> allTable = document.getTables();
 *             //获取图片对象
 *             XWPFPictureData pic = document.getPictureDataByID("PICId");
 */

    }
}
