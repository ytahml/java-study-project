package top.ytazwc.poi.others;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 版权：(C) 版权所有 2000-2019 上海天好电子商务股份有限公司苏州分公司
 * <简述>
 * <详细描述> POI 多线程多sheet导出数据
 * @author lichaojie
 * @version V1.0
 * @since <a href="https://blog.csdn.net/IT_private/article/details/117770995">POI 多线程多sheet导出数据</a>
 */
@Getter
public class ThreadExcelUtils {

    /**
     * 定义每个 sheet 最多数量 5w条
     */
    static final Integer EXCEL_MAX_CNT = 50000;

    /**
     * 工作薄
     */
    private HSSFWorkbook wb;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 路径
     */
    private String filePath;
    /**
     * 标题名称
     * -- GETTER --
     *  获取 标题名称
     *
     *
     * -- SETTER --
     *  设置 标题名称
     *
     @return headers 标题名称
      * @param headers 标题名称

     */
    @Setter
    private String[] headers;
    /**
     * 字段名称
     */
    private String[] fields;

    /**
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param headers  文件头
     * @param fields   字段属性
     */
    ThreadExcelUtils(String fileName, String filePath,
                     String[] headers, String[] fields) {
        this.wb = new HSSFWorkbook();
        this.fileName = fileName;
        this.filePath = filePath;
        this.headers = headers;
        this.fields = fields;
    }

    public static void main(String[] args) throws Exception {

        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        // 造数据
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name1", "张三" + i);
            map.put("name2", "李四" + i);
            map.put("name3", "王五" + i);
            lists.add(map);
        }
        String[] header = {"姓名1", "姓名2", "姓名3"};
        String[] fileNames = {"name1", "name2", "name3"};
        String filePath = "E:\\open";
        String filename = "测试表.xls";
        ThreadExcelUtils utils = new ThreadExcelUtils(filename, filePath,
                header, fileNames);
        System.out.println("开始执行导出.......");
        long start = System.currentTimeMillis();
        utils.exportExcelToFilePath(lists);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000 + "秒");

    }

    /**
     * @param list 数据
     */
    void exportExcelToFilePath(List<Map<String, Object>> list)
            throws Exception {
        // 每个Excel文件条数
        int excelSize = EXCEL_MAX_CNT;
        // 查询结果总条数
        int totalCount = list.size();
        // 总sheet页个数
        int pageCount;
        // 是否整页数
        int numPage = totalCount % excelSize;
        if (numPage > 0) {
            pageCount = totalCount / excelSize + 1;
        } else {
            pageCount = totalCount / excelSize;
        }
        // 创建线程池 多sheet多线程写入 线程数 为sheet页的总数量
        int threadNumber = pageCount;
        if (threadNumber == 0) {
            threadNumber = 1;
        }
        //定义线程的名字
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("gjjExcel-pool-%d").build();
        //核心线程数，最大线程数，线程空闲时间，时间单位，任务队列容量，线程工厂
        ExecutorService threadPool = new ThreadPoolExecutor(threadNumber, threadNumber,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        // 创建栅栏 等待任务完成
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
        // 循环遍历投递任务
        for (int i = 1; i <= pageCount; i++) {
            ThreadExcel threadExcel = new ThreadExcel(list, i, pageCount,
                    numPage, this);
            threadExcel.setCountDownLatch(countDownLatch);
            threadPool.execute(threadExcel);
        }
        //阻塞主线程，等所有的子线程执行完成
        countDownLatch.await();
        //下载到本地
//        Workbook wb = getWb();
//        File file = new File(filePath);
//        FileOutputStream fout = new FileOutputStream(new File(file, fileName));
//        try {
//            wb.write(fout);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 立即销毁线程池
        threadPool.shutdownNow();
    }

    /**
     * JavaBean转Map
     *
     * @param obj Object
     * @return Map<String, Object>
     */
    private static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean
                    .getPropertyDescriptors(obj);
            for (PropertyDescriptor descriptor : descriptors) {
                String name = descriptor.getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj,
                            name));
                }
            }
        } catch (Exception e) {
            System.err.println("bean 转Map出错");
            e.printStackTrace();
        }
        return params;
    }


    /***
     * 线程写入sheet
     */
    @Getter
    private static class ThreadExcel implements Runnable {

        /**
         * 数据
         * -- SETTER --
         *  设置 数据
         *
         *
         * -- GETTER --
         *  获取 数据
         *
         @param list 数据
          * @return list 数据

         */
        @Setter
        private List<Map<String, Object>> list;
        /**
         * 当前sheet页码
         * -- SETTER --
         *  设置 当前sheet页码
         *
         *
         * -- GETTER --
         *  获取 当前sheet页码
         *
         @param sheetNumber 当前sheet页码
          * @return sheetNumber 当前sheet页码

         */
        @Setter
        private Integer sheetNumber;
        /**
         * 总数据
         * -- SETTER --
         *  设置 总数据
         *
         *
         * -- GETTER --
         *  获取 总数据
         *
         @param totalSheetCount 总数据
          * @return totalSheetCount 总数据

         */
        @Setter
        private Integer totalSheetCount;
        /**
         * 是否整页数
         * -- SETTER --
         *  设置 是否整页数
         *
         *
         * -- GETTER --
         *  获取 是否整页数
         *
         @param numPage 是否整页数
          * @return numPage 是否整页数

         */
        @Setter
        private int numPage;
        /**
         * excel sheet长度
         * -- SETTER --
         *  设置 excel sheet长度
         *
         *
         * -- GETTER --
         *  获取 excel sheet长度
         *
         @param excelSize excel sheet长度
          * @return excelSize excel sheet长度

         */
        @Setter
        private Integer excelSize;
        /**
         * 工具类
         * -- SETTER --
         *  设置 工具类
         *
         *
         * -- GETTER --
         *  获取 工具类
         *
         @param threadExcelUtils 工具类
          * @return threadExcelUtils 工具类

         */
        @Setter
        private ThreadExcelUtils threadExcelUtils;
        /**
         * 栅栏对象 计数器
         * -- GETTER --
         *  获取 栅栏对象
         *
         * @return countDownLatch 栅栏对象

         */
        private CountDownLatch countDownLatch;

        /**
         * @param list            总数据
         * @param sheetNumber     当前sheet页
         * @param totalSheetCount 总sheet页
         * @param numPage         是否整数
         */
        ThreadExcel(List<Map<String, Object>> list, Integer sheetNumber,
                    Integer totalSheetCount, Integer numPage,
                    ThreadExcelUtils threadExcelUtils) {
            this.list = list;// 总数据
            this.sheetNumber = sheetNumber;// 当前sheet页
            this.totalSheetCount = totalSheetCount;// 总sheet页
            this.numPage = numPage;// 是否整除
            this.excelSize = ThreadExcelUtils.EXCEL_MAX_CNT;// 没个sheet最大数量
            this.threadExcelUtils = threadExcelUtils;// 当前线程对象
        }

        @Override
        public void run() {
            //链式编程
            System.out.println(Thread.currentThread().getName() + "---执行中---");
            List<Map<String, Object>> sheetList = null;
            if (totalSheetCount > 1) {
                // 是否整除
                if (numPage == 0) {
                    sheetList = list.subList((sheetNumber - 1) * excelSize,
                            excelSize * sheetNumber);
                } else {
                    if (sheetNumber.equals(totalSheetCount)) {
                        sheetList = list.subList((sheetNumber - 1) * excelSize,
                                list.size());
                    } else {
                        sheetList = list.subList((sheetNumber - 1) * excelSize,
                                excelSize * (sheetNumber));
                    }
                }
            } else {
                sheetList = list;
            }
            // 开始写入数据
            createWorkBook(sheetList);
            if (this.countDownLatch != null) {
                //计数器减1
                this.countDownLatch.countDown();
            }
        }

        /**
         * 设置表头
         *
         * @param wb  工作薄
         * @param row 行
         */
        public void setTitle(HSSFWorkbook wb, HSSFRow row) {
            //设置标题行高
            row.setHeight((short) 750);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HorizontalAlignment.CENTER);
            // 设置背景色
            style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 14);
            //粗体显示
            font.setBold(true);
            style.setFont(font);
            //下边框
            style.setBorderBottom(BorderStyle.THIN);
            //左边框
            style.setBorderLeft(BorderStyle.THIN);
            //右边框
            style.setBorderRight(BorderStyle.THIN);
            //上边框
            style.setBorderTop(BorderStyle.THIN);
            String[] header = threadExcelUtils.getHeaders();
            // 设置标题
            for (int i = 0; i < header.length; i++) {
                row.createCell(i).setCellValue(header[i]);
                row.getCell(i).setCellStyle(style);
            }
        }

        /***
         * 写出数据
         */
        private void createWorkBook(List<Map<String, Object>> sheetList) {
            HSSFSheet sheet = null;
            HSSFRow row = null;
            synchronized (ThreadExcelUtils.class) {
                HSSFWorkbook wb = threadExcelUtils.getWb();
                sheet = wb.createSheet("sheet" + this.sheetNumber);
                // 默认列宽
                sheet.setDefaultColumnWidth(20);
                row = sheet.createRow(0);
                // 设置标题
                setTitle(wb, row);
            }
            String[] fields = threadExcelUtils.getFields();

            // 开始写入数据
            if (sheetList != null && sheetList.size() > 0) {
                int dataLength = sheetList.size();
                for (int i = 0; i < dataLength; i++) {
                    Row row1 = sheet.createRow(i + 1);
                    Object obj = sheetList.get(i);
                    Map<String, Object> map = (obj != null) ? (Map<String, Object>) obj : beanToMap(obj);
                    int length = fields.length;
                    for (int j = 0; j < length; j++) {
                        String key = fields[j];
                        Object value = map.get(key);
                        if (value != null) {
                            // 不晓得 此处为啥有线程安全问题
                            synchronized (ThreadExcelUtils.class) {
                                if (value instanceof Date) {
                                    SimpleDateFormat sdf = new SimpleDateFormat(
                                            "yyyy-MM-dd");
                                    String format = sdf.format(value);
                                    row1.createCell(j).setCellValue(format);
                                } else {
                                    try {
                                        row1.createCell(j).setCellValue(
                                                value.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * 设置 栅栏对象
         *
         * @param countDownLatch 栅栏对象
         */
        void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
    }
}



