import java.util.Random;

public class Hello  {
	static class OW {
		Object o;
		OW(Object w) {
			this.o = w;
		}
	}
	static Random rand;
	Object ss;
	void stam() {
		ss = new OW(1);
	}
	Object get() {
		return ss;
	}
	public static void main( String arg[] ) {
		rand = new Random();
		String s[];
		Hello h = new Hello();
//		OW o[] = new OW[1000];
		while(true) {
			h.stam();
			s = new String[1000];
			for(int i=0; i < 10000; i++)
				s[rand.nextInt(1000)] = "" + i;//h.get();
//				o[i] = new OW(new OW("" + i));
			s = null;
		}
	}
}
