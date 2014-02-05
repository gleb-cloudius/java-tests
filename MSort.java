import java.util.Random;
import java.util.Arrays;

public class MSort  {
	static int n = 1000000;
	static String arr[] = new String[n];
	static Random rand = new Random(n);
	private static void ShuffleArray(String[] array)
	{
		int index;
	        String temp;
		for (int i = array.length - 1; i > 0; i--)
		{
			index = rand.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}
	public static void main( String arg[] ) {
		for (int i = 0; i < n; i++)
			arr[i] = "" + rand.nextInt(n);
		System.gc();
		System.out.println("start");
		long tim = System.currentTimeMillis();
		for(int i = 0; i < 100; i++) {
			Arrays.sort(arr);
			ShuffleArray(arr);
		}
		long dur = (long) (System.currentTimeMillis()-tim);
		System.out.println("time "+dur);
	}
}
