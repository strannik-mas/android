package su.strannik.testthread;

import android.util.Log;

public class Demo {
    private static final String TAG = "Demo";
    public static void main(String[] args) {
        Log.i(TAG, "main: threadMain");
        System.out.println("Main thread id is: " + Thread.currentThread().getId());
    }
}
