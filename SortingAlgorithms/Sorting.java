import java.util.List;
import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * Your implementation of various sorting algorithms.
 *
 * @author Noah Statton
 * @version 1.0
 *
 * Resources:
 * https://stackoverflow.com/questions/1306727/way-to-get-number-of-digits-in-an-int
 * https://www.geeksforgeeks.org/java-util-random-nextint-java/
 *
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments to insertionSort cannot be null");
        }
        for (int n = 1; n <= arr.length - 1; n++) {
            int i = n;
            while (i != 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = temp;
                i--;
            }
        }

    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("one of teh arguments is null");
        }
        boolean swapsmade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        int tempendIndex = endIndex;
        int tempstartIndex = startIndex;
        while (swapsmade) {
            swapsmade = false;
            for (int i = startIndex; i < tempendIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsmade = true;
                    endIndex = i;
                }
            }
            tempendIndex = endIndex;
            if (swapsmade) {
                swapsmade = false;
                for (int i = endIndex; i > tempstartIndex; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapsmade = true;
                        startIndex = i;
                    }
                }
                tempstartIndex = startIndex;
            }

        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("one of the arguments is null");
        }
        if (arr.length == 1) {
            return;
        }
        int midIdx = arr.length / 2;
        T[] left = subArray(0, midIdx - 1, midIdx, arr);
        T[] right = subArray(midIdx, arr.length - 1, arr.length - left.length, arr);
        mergeSort(left, comparator);
        mergeSort(right, comparator);


        int i = 0;
        int j = 0;
        while (i < left.length  && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }

    }

    /**
     *
     * @param start start index of array
     * @param end end index for array
     * @param length length
     * @param arr array from which subarray is devised
     * @param <T> generric type of array
     * @return a subarray
     */
    private static <T> T[] subArray(int start, int end, int length, T[] arr) {
        T[] subArr = (T[]) new Object[length];
        for (int i = 0; i < length; i++) {
            subArr[i] = arr[start];
            start++;
        }
        return subArr;
    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("one of the arguments is null");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);

    }

    /**
     *
     * @param arr array to be sorted
     * @param comparator object for comparing
     * @param rand object for getting random value
     * @param start start index of array
     * @param end last index of array
     * @param <T> generic type to be passed in
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (start < end - 1) {
            //works?
            int pivotIdx = rand.nextInt(end - start) + start;
            T pivotVal = arr[pivotIdx];
            T temp = arr[start];
            arr[start] = arr[pivotIdx];
            arr[pivotIdx] = temp;
            pivotIdx = start;
            int i = start + 1;
            int j = end;
            while (i <= j) {
                while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                    i++;
                }
                while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                    j--;
                }
                if (i <= j) {
                    T temp2 = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp2;
                    i++;
                    j--;
                }
            }
            T temp3 = arr[pivotIdx];
            arr[pivotIdx] = arr[j];
            arr[j] = temp3;
            pivotIdx = j;
            quickSortHelper(arr, comparator, rand, start, pivotIdx - 1);
            quickSortHelper(arr, comparator, rand, pivotIdx + 1, end);
        }

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        //passthrough to find int with most digits
        int largest = 1;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > largest) {
                largest = arr[i];
            }
        }
        //find number of digits in largest
        int k = numplaces(Math.abs(largest));

        //sorting time
        int currentPower = 1;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 1; i <= k; i++) {
            //adding to the buckets
            for (int j = 0; j < arr.length; j++) {
                int digit = ((arr[j] / currentPower) % 10);
                if (buckets[digit + 9] == null) {
                    buckets[digit + 9] = new LinkedList<Integer>();
                    buckets[digit + 9].add(arr[j]);
                } else {
                    buckets[digit + 9].add(arr[j]);
                }
            }

            //removing from buckets
            int index = 0;
            for (LinkedList bucket: buckets) {
                while (bucket != null && !bucket.isEmpty()) {
                    arr[index] = (int) bucket.removeFirst();
                    index++;
                }
            }
            currentPower *= 10;
        }

    }

    /**
     *
     * @param n number to have places determined
     * @return the number of places
     */
    private static int numplaces(int n) {
        if (n < 100000) {
            // 5 or less
            if (n < 100) {
                // 1 or 2
                if (n < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                // 3 or 4 or 5
                if (n < 1000) {
                    return 3;
                } else {
                    // 4 or 5
                    if (n < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            // 6 or more
            if (n < 10000000) {
                // 6 or 7
                if (n < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                // 8 to 10
                if (n < 100000000) {
                    return 8;
                } else {
                    // 9 or 10
                    if (n < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }


    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] returnList = new int[data.size()];
        for (int i = 0; i < returnList.length; i++) {
            returnList[i] = heap.remove();
        }
        return returnList;
    }
}
