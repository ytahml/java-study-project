package top.ytazwc.sort;

/**
 * @author 花木凋零成兰
 * @title InsertSort
 * @date 2025-01-12 13:40
 * @package top.ytazwc.sort
 * @description 插入排序
 */
public class InsertSort {

    public static int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容

        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < sourceArray.length; i++) {

            // 记录要插入的数据
            int tmp = sourceArray[i];

            // 从已经排序的序列最右边的开始比较，找到比其小的数
            int j = i;
            while (j > 0 && tmp < sourceArray[j - 1]) {
                sourceArray[j] = sourceArray[j - 1];
                j--;
            }

            // 存在比其小的数，插入
            if (j != i) {
                sourceArray[j] = tmp;
            }

        }
        return sourceArray;
    }
}
