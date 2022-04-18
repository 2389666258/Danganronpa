package Danganronpa;

import java.io.*;
import java.util.*;

class Danganronpa {

    /* ----- variable and method ----- */

    static ArrayList<String> stm = new ArrayList<>();
    static ArrayList<Integer> si = new ArrayList<>();
    static MaxHeap heap1 = new MaxHeap();
    static MinHeap heap2 = new MinHeap();
    static int n, I, p;

    public static int counterStatements(){
        return heap1.getMax();
    }

    public static void alterInfluenceGauge(int k){
        if (I < k)
            I = I + 1 - si.size();
        else
            I = I + 1;
    }


    /* ----- Danganronpa main ----- */

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();


        /* --- read n, I and m(s) --- */

        n = in.nextInt();
        I = in.nextInt();
        p = 0;

        int[] m = new int[n];
        long sumM = 0;
        for (int i = 0; i < n; i++) {
            m[i] = in.nextInt();
            sumM += m[i];
        }


        /* --- read operations and op1's stm --- */

        boolean IPositive = true;
        int maxA = 0;
        String s;
        for (int i = 0; i < n; i++) {
            int op = in.nextInt();
            if (op == 1) {          // Operation 1
                s = in.next();
                stm.add(s);
                si.add(s.length());

//                // heap for middle statements
//                int x = s.length();
//                if (heap1.size() == 0 || x <= heap1.getMax()) {
//                    heap1.push(x);
//                } else {
//                    heap2.push(x);
//                }
//                if (heap1.size() > heap2.size()) {
//                    heap2.push(heap1.getMax());
//                    heap1.pop();
//                } else if (heap1.size() < heap2.size() + 1) {
//                    heap1.push(heap2.getMin());
//                    heap2.pop();
//                }

                // heap for middle statements
                int x = s.length();
                if (heap1.size() == 0 || x <= heap1.getMax()) {
                    heap1.push(x);
                } else {
                    heap2.push(x);
                }
                if (heap1.size() < heap2.size()) {
                    heap1.push(heap2.getMin());
                    heap2.pop();
                } else if (heap1.size() > heap2.size() + 1) {
                    heap2.push(heap1.getMax());
                    heap1.pop();
                }

            } else if (op == 2) {   // Operation 2
                s = in.next();
                int q = 0;
                for (String value : stm) {
                    if (value.equals(s)) {
                        q++;
                    }
                }
                p += q;

                // mid in heap1
                int mid = counterStatements();

                // alter I by mid
                alterInfluenceGauge(mid);

                IPositive &= (I >= 0);
                maxA = Math.max(maxA, mid);

                out.println(mid);

            } else                  // Operation 3
                out.println(p);
        } // Operations

        // judge whether failed or Qi Fei
        if (!IPositive) {
            out.println("Fail");
        } else {
            if (p == 0 || maxA == 0) {
                out.println("Qi Fei");
            } else {
                if (sumM >= 0) {
                    out.println("Qi Fei");
                } else {
                    out.println("Fail");
                }
            }
        }

        // This should be at the last of main()
        out.close();
    }
}



// Don't make any change in the following code
class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}

/**
 * MaxHeap & MinHeap class may help you find the median
 * To correctly use them, you should first instantiate the object
 * and then use the public methods in it.
 */

class MinHeap{
    private static final int MAXSIZE = 10010;
    private int[] a = new int[MAXSIZE];
    private void swap(int x,int y){
        a[x] = a[x]^a[y];
        a[y] = a[y]^a[x];
        a[x] = a[x]^a[y];
    }
    private void up(){
        int p = a[0];
        while(p > 1){
            if (a[p] < a[p/2]){
                swap(p,p/2);
                p = p / 2;
            }
            else
                break;
        }
    }

    public void push(int k){
        a[++a[0]] = k;
        up();
    }
    public void pop(){
        int s = 2, t = 1;
        a[1] = a[a[0]--];
        while (s <= a[0]){
            if (s <= a[0] - 1 && a[s+1] < a[s])
                ++s;
            if (a[s] < a[t]){
                swap(s, t);
                t = s;
                s*=2;
            }
            else
                break;
        }
    }
    public int getMin(){
        return a[1];
    }
    public int size(){
        return a[0];
    }
}

class MaxHeap{
    private static final int MAXSIZE = 10010;
    private int[] a = new int[MAXSIZE];
    private void swap(int x,int y){
        a[x] = a[x]^a[y];
        a[y] = a[y]^a[x];
        a[x] = a[x]^a[y];
    }
    private void up(){
        int p = a[0];
        while(p > 1){
            if (a[p] > a[p/2]){
                swap(p,p/2);
                p = p / 2;
            }
            else
                break;
        }
    }

    public void push(int k){
        a[++a[0]] = k;
        up();
    }
    public void pop(){
        int s = 2, t = 1;
        a[1] = a[a[0]--];
        while (s <= a[0]){
            if (s <= a[0] - 1 && a[s+1] > a[s])
                ++s;
            if (a[s] > a[t]){
                swap(s,t);
                t = s;
                s*=2;
            }
            else
                break;
        }
    }
    public int getMax(){
        return a[1];
    }
    public int size(){
        return a[0];
    }
}