/** 
* DATA STRUCTURES: FINAL PROJECT
* @author E Wilber
* @version 1.0
* @since 1.0
*  
* OS: Windows11
* IDE: Eclipse
*  
* Copyright : This is my own original work 
* based on specifications issued by our instructor
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or
* unmodified, nor used generative AI as a final draft. 
* I have not given other fellow student(s) access to my program.
*/

//SORTING METHOD (Heap Sort Algorithm)
//sorts hard coded “initial” list of tasks that I would want to do everyday
//and not have to constantly add one by one
// (tasks added by the Add button go straight into the PriorityQueue instead of this)
package util;

import model.Task;
import java.util.Comparator;

public class HeapSort {

    public static void sort(Task[] arr, Comparator<Task> comp) {
        int n = arr.length;
//build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, comp);
        }
//One by one extract element from heap
        for (int end = n - 1; end > 0; end--) {
//moving current root to end
            swap(arr, 0, end);       
// calling max heapify on the reduced heap
            heapify(arr, end, 0, comp);     
        }
    }
    private static void heapify(Task[] arr, int heapSize, int rootIndex, Comparator<Task> comp) {
// initializing largest as root
        int largest = rootIndex;   
// left = 2*i + 1
        int left  = 2 * rootIndex + 1;   
// right = 2*i + 2
        int right = 2 * rootIndex + 2;     
//if left child is larger than root
        if (left < heapSize && comp.compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }
// if right child is larger than largest so far
        if (right < heapSize && comp.compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }
//if largest is not root
        if (largest != rootIndex) {
            swap(arr, rootIndex, largest);
// recursively heapify the affected sub tree
            heapify(arr, heapSize, largest, comp);
        }
    }
    private static void swap(Task[] arr, int i, int j) {
        Task temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
