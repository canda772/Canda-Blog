package com.site.blog.my.core.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.kernel.pdf.canvas.parser.listener.RegexBasedLocationExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PdfSwitchUtil {

    /**
     * word转PDF，需要 openOffice服务开启
     * (windows直接傻瓜式安装启动服务即可，linux则需要单独在应用所在服务器安装)
     *
     * @param srcPath          word文件路径 含文件名
     * @param desPath          pdf保存路径，含文件名
     * @param officeServerPath openOffice服务连接路径
     * @throws IOException
     */
    // 将word格式的文件转换为pdf格式
    public static Boolean Word2Pdf(String srcPath, String desPath, String officeServerPath) throws Exception {
        // 源文件目录
        File inputFile = new File(srcPath);
        if (!inputFile.exists()) {
            return false;
        }
        // 输出文件目录
        File outputFile = new File(desPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }

        Process p = null;
        OpenOfficeConnection connection = null;
        try {
            // window 使用  调用openoffice服务线程     本机C盘！！！
            //String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard\"";
            //Linux使用
            //   String command = "/opt/openoffice4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard\"";
            p = Runtime.getRuntime().exec(officeServerPath);
            // 连接openoffice服务
            connection = new SocketOpenOfficeConnection(
                    "127.0.0.1", 8100);
            connection.connect();
            // 转换word到pdf
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("WORD转换PDF异常！！！！");
        } finally {
            // 关闭连接
            connection.disconnect();
            // 关闭进程
            p.destroy();
        }
        return true;
    }

    /**
     * 测试方法
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String officeServerPath = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard\"";

        String srcPath = "D:/pdf/测试.doc";
        String desPath = "D:/pdf/测试.pdf";
        PdfSwitchUtil.Word2Pdf(srcPath, desPath,officeServerPath);
    }

    /**
     * 关键字定位(测试用)
     * @throws Exception
     */
    public static void findPosition(String inputPdfPath,String outputPath,String keyWord) throws Exception {
//        String sourceFolder2 = "/Users/huluwa/Desktop/hello.pdf";
//        String output = "/Users/huluwa/Desktop/test2.pdf";
        PdfReader reader = new PdfReader(inputPdfPath);
        PdfDocument pdfDocument = new PdfDocument(reader, new PdfWriter(outputPath));
        PdfPage lastPage = pdfDocument.getLastPage();
        RegexBasedLocationExtractionStrategy strategy = new RegexBasedLocationExtractionStrategy(keyWord);
        PdfCanvasProcessor canvasProcessor = new PdfCanvasProcessor(strategy);
        canvasProcessor.processPageContent(lastPage);
        Collection<IPdfTextLocation> resultantLocations = strategy.getResultantLocations();
        PdfCanvas pdfCanvas = new PdfCanvas(lastPage);
        pdfCanvas.setLineWidth(0.5f);
        List<IPdfTextLocation> sets = new ArrayList();
        for (IPdfTextLocation location : resultantLocations) {
            Rectangle rectangle = location.getRectangle();
            pdfCanvas.rectangle(rectangle);
            pdfCanvas.setStrokeColor(ColorConstants.RED);
            pdfCanvas.stroke();
            System.out.println(rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getLeft() + "," +
                    rectangle.getRight() + "," + rectangle.getTop() + "," + rectangle.getBottom() + "," +
                    rectangle.getWidth() + "," + rectangle.getHeight());
            System.out.println(location.getText());
            sets.add(location);
        }
        Collections.sort(sets, new Comparator<IPdfTextLocation>() {

            public int compare(IPdfTextLocation o1, IPdfTextLocation o2) {
                return o1.getRectangle().getY() - o2.getRectangle().getY() > 0 ? 1 : o1.getRectangle().getY() - o2.getRectangle().getY() == 0 ? 0 : -1;
            }
        });
        System.out.println(sets.get(0).getRectangle().getY());
        pdfDocument.close();
    }
}
