package top.ytazwc.sort;

/**
 * @author 花木凋零成兰
 * @title ShellSort
 * @date 2025-01-12 13:42
 * @package top.ytazwc.sort
 * @description 希尔排序
 */
public class ShellSort {

    public static int[] sort(int[] sourceArray) throws Exception {
        int length = sourceArray.length;

        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = sourceArray[i];
                int j = i - step;
                while (j >= 0 && sourceArray[j] > temp) {
                    sourceArray[j + step] = sourceArray[j];
                    j -= step;
                }
                sourceArray[j + step] = temp;
            }
        }

        return sourceArray;
    }
}
