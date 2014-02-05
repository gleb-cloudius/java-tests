import java.util.HashMap;
import java.util.Random;
import java.awt.Dimension;

public class FSTGCMark {

    static class UseLessWrapper {
        Object wrapped;
        UseLessWrapper(Object wrapped) {
            this.wrapped = wrapped;
        }
    }

    static HashMap map = new HashMap();
    static int hmFillRange = 5000000;//1000000 * 30; //
    static int mutatingRange = 200000;//2000000; //
    static int operationStep = 1000;

    int operCount;
    int milliDelayCount[] = new int[100];
    int hundredMilliDelayCount[] = new int[100];
    int secondDelayCount[] = new int[100];
    Random rand = new Random(1000);

    int stepCount = 0;
    public void operateStep() {
        stepCount++;

        if ( stepCount%100 == 0 ) {
            // enforce some tenuring
            for ( int i = 0; i < operationStep; i++) {
                int key = (int) (rand.nextDouble() * mutatingRange)+mutatingRange;
                map.put(key,  new UseLessWrapper(new UseLessWrapper(""+stepCount)));
            }
        }
        if ( stepCount%200 == 199 ) {
            // enforce some tenuring
            for ( int i = 0; i < operationStep; i++) {
                int key = (int) (rand.nextDouble() * mutatingRange)+mutatingRange*2;
                map.put(key,  new UseLessWrapper(new UseLessWrapper("a"+stepCount)));
            }
        }
if ( stepCount%400 == 299 ) {
            // enforce some tenuring
            for ( int i = 0; i < operationStep; i++) {
                int key = (int) (rand.nextDouble() * mutatingRange)+mutatingRange*3;
                map.put(key,  new UseLessWrapper(new UseLessWrapper("a"+stepCount)));
            }
        }
        if ( stepCount%1000 == 999 ) {
            // enforce some tenuring
            for ( int i = 0; i < operationStep; i++) {
                int key = (int) (rand.nextDouble() * hmFillRange);
                map.put(key,  new UseLessWrapper(new UseLessWrapper("a"+stepCount)));
            }
        }
        for ( int i = 0; i < operationStep/2; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange);
            map.put(key, new UseLessWrapper(new Dimension(key,key)));
        }
        for ( int i = 0; i < operationStep/8; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange);
            map.put(key, new UseLessWrapper(new UseLessWrapper(new UseLessWrapper(new UseLessWrapper(new UseLessWrapper("pok"+i))))));
        }
        for ( int i = 0; i < operationStep/16; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange);
            map.put(key, new UseLessWrapper(new int[50]));
        }
        for ( int i = 0; i < operationStep/32; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange);
            map.put(key, ""+new UseLessWrapper(new int[100]));
        }
        for ( int i = 0; i < operationStep/32; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange);
            Object[] wrapped = new Object[100];
            for (int j = 0; j < wrapped.length; j++) {
                wrapped[j] = ""+j;
            }
            map.put(key, new UseLessWrapper(wrapped));
        }
         for ( int i = 0; i < operationStep/64; i++) {
            int key = (int) (rand.nextDouble() * mutatingRange /64);
            map.put(key, new UseLessWrapper(new int[1000]));
        }
        for ( int i = 0; i < 4; i++) {
            int key = (int) (rand.nextDouble() * 16);
            map.put(key, new UseLessWrapper(new byte[1000000]));
        }
    }

    public void fillMap() {
        for ( int i = 0; i < hmFillRange; i++) {
            map.put(i, new UseLessWrapper(new UseLessWrapper(""+i)));
        }
    }

    public void run() {
        fillMap();
        System.gc();
        System.out.println("static alloc " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000 / 1000 + "mb");
        long time = System.currentTimeMillis();
        int count = 0;
        while ( (System.currentTimeMillis()-time) < runtime) {
            count++;
            long tim = System.currentTimeMillis();
            operateStep();
            int dur = (int) (System.currentTimeMillis()-tim);
            if ( dur < 100 )
                milliDelayCount[dur]++;
            else if ( dur < 10*100 )
                hundredMilliDelayCount[dur/100]++;
            else {
                secondDelayCount[dur/1000]++;
            }
        }
        System.out.println("Iterations "+count);
    }

    public void dumpResult() {
         for (int i = 0; i < milliDelayCount.length; i++) {
            int i1 = milliDelayCount[i];
	    milliDelayCount[i] = 0;
            if ( i1 > 0 ) {
                System.out.println("["+i+"]\t"+i1);
            }
        }
        for (int i = 0; i < hundredMilliDelayCount.length; i++) {
            int i1 = hundredMilliDelayCount[i];
	    hundredMilliDelayCount[i] = 0;
            if ( i1 > 0 ) {
                System.out.println("["+i*100+"]\t"+i1);
            }
        }
        for (int i = 0; i < secondDelayCount.length; i++) {
            int i1 = secondDelayCount[i];
	    secondDelayCount[i] = 0;
            if ( i1 > 0 ) {
                System.out.println("["+i*1000+"]\t"+i1);
            }
        }
    }
    int runtime = 60000 * 5;
    public static void main( String arg[] ) {
        FSTGCMark fstgcMark = new FSTGCMark();
	for (int i = 0; i < 2; i++) {
        fstgcMark.run();
        fstgcMark.dumpResult();
	}
    }
}
