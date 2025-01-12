package top.ytazwc.sort;

/**
 * @author 花木凋零成兰
 * @title SelectionSort
 * @date 2025-01-12 13:39
 * @package top.ytazwc.sort
 * @description 选择排序
 */
public class SelectionSort {

    public static int[] sort(int[] sourceArray) throws Exception {

        // 总共要经过 N-1 轮比较
        for (int i = 0; i < sourceArray.length - 1; i++) {
            int min = i;

            // 每轮需要比较的次数 N-i
            for (int j = i + 1; j < sourceArray.length; j++) {
                if (sourceArray[j] < sourceArray[min]) {
                    // 记录目前能找到的最小值元素的下标
                    min = j;
                }
            }

            // 将找到的最小值和i位置所在的值进行交换
            if (i != min) {
                int tmp = sourceArray[i];
                sourceArray[i] = sourceArray[min];
                sourceArray[min] = tmp;
            }

        }
        return sourceArray;
    }
}