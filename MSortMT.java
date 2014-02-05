import java.util.Random;
import java.util.Arrays;
import java.lang.Thread;

public class MSortMT  {
	static class Work extends Thread {
		int i;
		int n;
		String arr[];
		Random rand;
		boolean inter;
		Work(String[] array, int ii, int nn, boolean it) {
			n = nn;
			i = ii;
			itner = it;
			arr = array;
			rand = new Random(arr.length/n);
		}
		private int idx(int x) {
			if (inter)
				return x*n + i;
			else
				return n*i + x;
		}
		private void BubbleSort(String[] array)
		{
			int j;
			boolean flag = true;
			String temp;

			while (flag)
			{
				flag= false;
				for(j=0; j < array.length/n - 1; j++)
				{
					int ci = idx(j), ni = idx(j+1);
					if (array[ci].compareTo(array[ni]) < 0)
					{
						temp = array[ci];
						array[ci] = array[ni];
						array[ni] = temp;
						flag = true;
					}
				}
			}
		}
		private void ShuffleArray(String[] array)
		{
			int index;
			String temp;
			for (int j = array.length/n - 1; j > 0; j--)
			{
				index = idx(rand.nextInt(j + 1));
				temp = array[index];
				array[index] = array[idx(j)];
				array[idx(j)] = temp;
			}
		}
		public void run() {
			for(int j = 0; j < 10; j++) {
				BubbleSort(arr);
				ShuffleArray(arr);
			}
		}
	}
	public static void main( String arg[] ) {
		int nt = 4;
		if (arg.length > 0)
			nt = Integer.parseInt(arg[0]);
		int n = 10000 * nt;
		String arr[] = new String[n];
		Random rand = new Random(n);
		Work w[] = new Work[nt];
		for (int i = 0; i < n; i++)
			arr[i] = "" + rand.nextInt(n);
		for (int t = 0; t < nt; t++) {
			w[t] = new Work(arr, t, nt, arg.length < 2);
		}
		System.gc();
		System.out.println("start " + nt);
		try {
		long tim = System.currentTimeMillis();
		for (int t = 0; t < nt; t++)
			w[t].start();
		for (int t = 0; t < nt; t++)
			w[t].join();
		long dur = (long) (System.currentTimeMillis()-tim);
		System.out.println("time "+dur);
		} catch(Throwable o) {}
	}
}
