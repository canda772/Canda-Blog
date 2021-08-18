package com.site.blog.my.core.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

public class FileOptUtils {
    private static final String SUFFIX = "pdf,doc,docx,xls,xlsx,ppt,pps,pptx";

    public FileOptUtils() {
    }

    public static boolean upload(InputStream inputStream, String path) throws IllegalAccessException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        boolean var5;
        try {
            in = new BufferedInputStream(inputStream);
            out = new BufferedOutputStream(new FileOutputStream(new File(path)));
            byte[] b = new byte[1024];
            int a =-1;
            while((a=in.read(b)) != -1) {
                out.write(b, 0, a);
            }

            in.close();
            out.close();
            var5 = true;
            return var5;
        } catch (Exception var19) {
            var5 = false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return var5;
    }

    public static boolean upload2(XWPFDocument doc, String uploadDir, String originalFilename) throws IllegalAccessException {
        String path = null;
        BufferedOutputStream out = null;

        boolean var6;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File((String)path)));
            doc.write(out);
            out.close();
            boolean var5 = true;
            return var5;
        } catch (Exception var16) {
            var6 = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return var6;
    }

    public static void mkDir(File file) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            mkDir(file.getParentFile());
            file.mkdir();
        }

    }

    private static boolean isShow(String suffix) {
        String[] sArry = "pdf,doc,docx,xls,xlsx,ppt,pps,pptx".split(",");
        String[] var2 = sArry;
        int var3 = sArry.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            if (suffix.toLowerCase().endsWith(s)) {
                return true;
            }
        }

        return false;
    }

    public static String uploadFileWithoutSwf(String filedir, File sourceFile, String sourceName) {
        String strOutFileName = "";

        try {
            String newName = createFileName(sourceName);
            String path = null;
            if (isFileDir((String)path)) {
                File file = new File(path + newName);
                boolean bflag = copy(sourceFile, file);
                if (bflag) {
                    strOutFileName = filedir + newName;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return strOutFileName;
    }

    public static boolean copy(File file_in, File file_out) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        boolean var5;
        try {
            in = new BufferedInputStream(new FileInputStream(file_in));
            out = new BufferedOutputStream(new FileOutputStream(file_out));

            int c;
            while((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            out.close();
            var5 = true;
            return var5;
        } catch (Exception var19) {
            var5 = false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return var5;
    }

    public static boolean delFile(String pathname) throws Exception {
        if (pathname != null && !pathname.equals("")) {
            File file = new File(pathname);
            return file.exists() && file.isFile() ? file.delete() : false;
        } else {
            return false;
        }
    }

    public static boolean delDir(String file_path) throws Exception {
        File dirFile = new File(file_path);
        if (dirFile.exists() && dirFile.isDirectory()) {
            boolean flag = true;
            File[] files = dirFile.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if (files[i].isFile()) {
                    flag = delFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                } else {
                    flag = delDir(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                return dirFile.delete();
            }
        } else {
            return false;
        }
    }

    public static boolean delDirNocurrent(String file_path) throws Exception {
        File dirFile = new File(file_path);
        if (dirFile.exists() && dirFile.isDirectory()) {
            boolean flag = true;
            File[] files = dirFile.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if (files[i].isFile()) {
                    flag = delFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                } else {
                    flag = delDir(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    public static boolean delDirectory(String file_path) {
        File path = new File(file_path);
        String separator = File.separator;
        if (path.isDirectory()) {
            String[] list1 = path.list();

            for(int i = 0; i < list1.length; ++i) {
                File f = new File(file_path + separator + list1[i]);
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    delDirectory(f.getPath());
                }
            }

            return path.delete();
        } else {
            return false;
        }
    }

    public static boolean delFiles(String filedir) {
        File path = new File(filedir);
        String separator = File.separator;
        if (path.isDirectory()) {
            String[] list1 = path.list();

            for(int i = 0; i < list1.length; ++i) {
                File f = new File(filedir + separator + list1[i]);
                if (f.isFile()) {
                    f.delete();
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isFileDir(String pathname) throws Exception {
        if (pathname != null && !pathname.equals("")) {
            File dirFile = new File(pathname);
            return !dirFile.exists() ? dirFile.mkdirs() : true;
        } else {
            return false;
        }
    }

    public static boolean creatDirs(String rootDir, String subDir) {
        File aFile = new File(rootDir);
        if (aFile.exists()) {
            File aSubFile = new File(rootDir + subDir);
            return !aSubFile.exists() ? aSubFile.mkdirs() : true;
        } else {
            return false;
        }
    }

    public static long getFileSize(String file_path) {
        if (file_path != null && file_path.length() != 0) {
            File myFile = new File(file_path);
            return myFile.exists() && myFile.isFile() ? myFile.length() : -1L;
        } else {
            return -1L;
        }
    }

    public static String createFileName(String oldName) throws Exception {
        StringBuffer outName = new StringBuffer();
        String[] defaultChars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random r = new Random();
        int length = r.nextInt(5) + 5;

        int pos;
        for(pos = 0; pos < length; ++pos) {
            outName.append(defaultChars[r.nextInt(defaultChars.length)]);
        }

        outName.append(System.currentTimeMillis());
        if (oldName != null && !"".equals(oldName)) {
            pos = oldName.lastIndexOf(".");
            outName.append(oldName.substring(pos));
            return outName.toString();
        } else {
            return outName.toString();
        }
    }

    public static List<String> fileSearch(String file_path, String str_search, int fileviewType) {
        if (file_path != null && file_path.length() != 0) {
            List<String> searchResult = new ArrayList();
            File path = new File(file_path);
            String separator = File.separator;
            if (path.isDirectory()) {
                String[] list1 = path.list();
                int i;
                File f;
                if (list1 != null) {
                    for(i = 0; i < list1.length; ++i) {
                        f = new File(file_path + separator + list1[i]);
                        if (f.isDirectory()) {
                            List<String> tempResult = fileSearch(f.getPath(), str_search, fileviewType);
                            if (!tempResult.isEmpty()) {
                                for(int j = 0; j < tempResult.size(); ++j) {
                                    searchResult.add(tempResult.get(j));
                                }
                            }
                        }
                    }
                }

                if (list1 != null) {
                    for(i = 0; i < list1.length; ++i) {
                        f = new File(file_path + separator + list1[i]);
                        if (f.isFile() && list1[i].toLowerCase().indexOf(str_search.toLowerCase()) > -1) {
                            if (fileviewType == 0) {
                                searchResult.add(f.getName());
                            } else {
                                searchResult.add(f.getPath());
                            }
                        }
                    }
                }
            }

            return searchResult;
        } else {
            return null;
        }
    }

    public static String fileToString(String fileName) throws IOException {
        String lineContent = null;
        StringBuffer str = new StringBuffer();

        try {
            File f = new File(fileName);
            if (f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                BufferedReader br = new BufferedReader(read);
                new String();
                lineContent = br.readLine().trim();

                do {
                    str.append(lineContent.trim() + "\n");
                } while((lineContent = br.readLine()) != null);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return str.toString();
    }

    public static boolean stringToFile(String fileName, String content) throws IOException {
        try {
            File f = new File(fileName);
            if (f.exists()) {
                OutputStream out = new FileOutputStream(fileName, false);
                OutputStreamWriter fwout = new OutputStreamWriter(out, "utf-8");
                BufferedWriter bw = new BufferedWriter(fwout);
                bw.write(content);
                bw.close();
                fwout.close();
                out.close();
                bw = null;
                fwout = null;
                out = null;
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        } else {
            try {
                FileInputStream stream = new FileInputStream(f);
                ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
                byte[] b = new byte[1000];

                int n;
                while((n = stream.read(b)) != -1) {
                    out.write(b, 0, n);
                }

                stream.close();
                out.close();
                return out.toByteArray();
            } catch (IOException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;

        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return file;
    }

    public static boolean isPic(String name) {
        boolean bRslt = false;
        if (StringUtils.isEmpty(name)) {
            return false;
        } else {
            String pos = name.substring(name.lastIndexOf(".") + 1);
            if (!StringUtils.isEmpty(pos)) {
                String[] pics = new String[]{"jpg", "jpeg", "gif", "png", "bmp"};
                String[] var4 = pics;
                int var5 = pics.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String s = var4[var6];
                    if (pos.trim().equals(s)) {
                        bRslt = true;
                        break;
                    }
                }
            }

            return bRslt;
        }
    }

    public static boolean isDoc(String name) {
        boolean bRslt = false;
        if (StringUtils.isEmpty(name)) {
            return false;
        } else {
            String pos = name.substring(name.lastIndexOf(".") + 1);
            if (!StringUtils.isEmpty(pos)) {
                String[] pics = new String[]{"docx", "doc"};
                String[] var4 = pics;
                int var5 = pics.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String s = var4[var6];
                    if (pos.trim().equals(s)) {
                        bRslt = true;
                        break;
                    }
                }
            }

            return bRslt;
        }
    }

    public static boolean createFile(String filePathAndName, String fileContent) {
        boolean bFlag = false;

        try {
            File filePath = new File(filePathAndName);
            if (!filePath.exists()) {
                filePath.createNewFile();
            }

            FileWriter writer = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(writer);
            pw.println(fileContent);
            writer.close();
            bFlag = true;
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return bFlag;
    }

    public static File getFile(String filedir, String filename) throws Exception {
        File file = null;
        if (isFileDir(filedir)) {
            file = new File(filedir + "/" + filename);
            if (!file.exists()) {
                file.createNewFile();
            }
        }

        return file;
    }

    public static double getDirSize(File file) {
        if (!file.exists()) {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0D;
        } else if (!file.isDirectory()) {
            double size = (double)file.length() / 1024.0D / 1024.0D;
            return size;
        } else {
            File[] children = file.listFiles();
            double size = 0.0D;
            File[] var4 = children;
            int var5 = children.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File f = var4[var6];
                size += getDirSize(f);
            }

            return size;
        }
    }

    public static Map<String, Object> downLoadTemplate(String path) throws Exception {
        File file = new File(path);
        Map<String, Object> resultMap = new HashMap();
        if (!file.exists()) {
            resultMap.put("flag", false);
            return resultMap;
        } else {
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            ByteArrayOutputStream bas = null;
            BufferedInputStream fis = null;

            Map var7;
            try {
                bas = new ByteArrayOutputStream(1000);
                fis = new BufferedInputStream(new FileInputStream(path));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                bas.write(buffer);
                resultMap.put("flag", true);
                resultMap.put("DownloadFileContent", bas.toByteArray());
                resultMap.put("DownloadFileName", fileName);
                var7 =  resultMap;
            } catch (Exception var11) {
                throw new Exception(var11);
            } finally {
                if (null != fis) {
                    fis.close();
                }

                if (null != bas) {
                    bas.close();
                }

            }

            return var7;
        }
    }
}
