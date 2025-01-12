package top.ytazwc.sort;

/**
 * @author 花木凋零成兰
 * @title BubbleSort
 * @date 2025-01-12 13:35
 * @package top.ytazwc.sort
 * @description 冒泡排序
 */
public class BubbleSort {

    public static int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容

        for (int i = 1; i < sourceArray.length; i++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;

            for (int j = 0; j < sourceArray.length - i; j++) {
                if (sourceArray[j] > sourceArray[j + 1]) {
                    int tmp = sourceArray[j];
                    sourceArray[j] = sourceArray[j + 1];
                    sourceArray[j + 1] = tmp;

                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }
        return sourceArray;
    }
}
