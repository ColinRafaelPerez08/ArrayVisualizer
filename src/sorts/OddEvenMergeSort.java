package sorts;

import templates.Sort;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * This version of Odd-Even Merge Sort was taken from here, written by H.W. Lang:
 * http://www.inf.fh-flensburg.de/lang/algorithmen/sortieren/networks/oemen.htm
 */

final public class OddEvenMergeSort extends Sort {
    public OddEvenMergeSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Odd-Even Merge");
        this.setRunAllID("Batcher's Odd-Even Merge Sort");
        this.setReportSortID("Odd-Even Mergesort");
        this.setCategory("Concurrent Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }
    
    private void oddEvenMergeCompare(int[] array, int i, int j, double sleep) {
        Delays.sleep(sleep);
        if (Reads.compare(array[i], array[j]) > 0)
            Writes.swap(array, i, j, sleep, true, false);
    }
    
    /** lo is the starting position and
     *  n is the length of the piece to be merged,
     *  r is the distance of the elements to be compared
     */
    private void oddEvenMerge(int[] array, int lo, int n, int r, double sleep) {
        int m = r * 2;
        if (m < n) {
            this.oddEvenMerge(array, lo, n, m, sleep);      // even subsequence
            this.oddEvenMerge(array, lo+r, n, m, sleep);    // odd subsequence
            
            for (int i = lo + r; i + r < lo + n; i += m) {
                Highlights.markArray(1, i);
                Highlights.markArray(2, i + r);
                this.oddEvenMergeCompare(array, i, i + r, sleep);
            }
        }
        else {
            Highlights.markArray(1, lo + r);
            Highlights.markArray(2, lo);
            this.oddEvenMergeCompare(array, lo, lo+r, sleep);
        }
    }
    
    private void oddEvenMergeSort(int[] array, int lo, int n, double sleep) {
		if (n > 1) {
			int m = n / 2;
			this.oddEvenMergeSort(array, lo, m, sleep);
			this.oddEvenMergeSort(array, lo + m, m, sleep);
			this.oddEvenMerge(array, lo, n, 1, sleep);
		}
	}

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        this.oddEvenMergeSort(array, 0, currentLength, 1);
    }
}