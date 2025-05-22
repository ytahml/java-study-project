package top.ytazwc.sort;

import java.util.Arrays;

/**
 * @author 花木凋零成兰
 * @title MergeSort
 * @date 2025-01-12 13:54
 * @package top.ytazwc.sort
 * @description 归并排序
 */
public class MergeSort {

    public static int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容

        if (sourceArray.length < 2) {
            return sourceArray;
        }
        int middle = (int) Math.floor(sourceArray.length / 2);

        int[] left = Arrays.copyOfRange(sourceArray, 0, middle);
        int[] right = Arrays.copyOfRange(sourceArray, middle, sourceArray.length);

        return  merge(sort(left), sort(right));
    }

    protected static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[0] <= right[0]) {
                result[i++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }

        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }

        return result;
    }

}
