package ch.hslu.oop.SW03;

public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();

        demo.loopsCounting();
    }

    public void loopsCounting() {
        // best
        for (int i = 0; i < 11; i++) {
            System.out.println(i);
        }

        int j = 0;
        do {
            System.out.println(j);
        } while(j++ < 10);

        // worst
        int k = -1;
        while (k++ < 10) {
            System.out.println(k);
        }
    }

    public void whileFloatIncrease() {
        float i = 0.9f;
        int d = 0;
        while (i < 1.0f) {
            i += 0.000025f;
            d++;
        }

        System.out.println(i + " after " + d + " spins");

        float k = 0.9f;
        for (int j = 0; j < 4000; j++) {
            k += 0.000025f;
        }

        System.out.println(k + " after 4000 spins");
    }

    public void printBox(int height, int width) {
        // You can optimize this in lots of ways like
        // only using two loops and a few ifs,
        // building the art before printing it, etc.
        for (int i = 0; i < height; i++) {
            if (i == 0 || i == height - 1) {
                for (int j = 0; j < width; j++) {
                    System.out.print("#");
                }
            } else {
                System.out.print("#");
                for (int j = 0; j < width-2; j++) {
                    System.out.print(" ");
                }
                System.out.print("#");
            }

            System.out.println();
        }
    }

    public int max(int a, int b) {
        // return Math.max(a, b);

        if (a > b)
            return a;

        return b;
    }

    public int min(int a, int b) {
        // return Math.min(a, b);

        if (a < b)
            return a;

        return b;
    }

    public int max(int a, int... ints) {
        int max = a;
        for (int b : ints) {
            max = this.max(max, b);
        }

        return max;
    }
}
