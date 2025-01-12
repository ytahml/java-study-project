package top.ytazwc.sort;

/**
 * @author 花木凋零成兰
 * @title sourceArray
 * @date 2025-01-12 13:56
 * @package top.ytazwc.sort
 * @description 堆排序
 */
public class HeapSort {

    public static int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容

        int len = sourceArray.length;

        buildMaxHeap(sourceArray, len);

        for (int i = len - 1; i > 0; i--) {
            swap(sourceArray, 0, i);
            len--;
            heapify(sourceArray, 0, len);
        }
        return sourceArray;
    }

    private static void buildMaxHeap(int[] arr, int len) {
        for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    private static void heapify(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
