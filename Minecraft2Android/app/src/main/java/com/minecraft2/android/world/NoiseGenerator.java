package com.minecraft2.android.world;

public class NoiseGenerator {

    private final int[] perm = new int[512];
    private static final int[] P = {
        151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,
        69,142,8,99,37,240,21,10,23,190,6,148,247,120,234,75,0,26,197,62,94,
        252,219,203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,171,
        168,68,175,74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,
        211,133,230,220,105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,
        216,80,73,209,76,132,187,208,89,18,169,200,196,135,130,116,188,159,86,
        164,100,109,198,173,186,3,64,52,217,226,250,124,123,5,202,38,121,118,
        114,160,217,60,97,237,20,252,121,105,220,234,52,165,90,108,43,150,73,
        150,223,251,18,141,10,14,244,98,112,134,231,56,140,92,233,202,197,25,
        154,109,176,29,127,189,63,174,209,178,128,105,198,80,125,47,239,130,
        140,48,139,161,70,183,80,75,211,53,25,6,133,17,72,101,111,149,180,
        71,15,3,170,2,210,136,12,143,95,178,93,147,225,247,81,45,207,248,77,
        208,148,111,167,231,183,61,177,69,97,139,144,145,241,23,113,100,86,85,
        182,145,208,212,177,138,98,250,76,14,21,164,188,157,249,67,118,25,38,
        23,150,210,252,37,124,48,135,29,64,197,245,113,43,171,94,221,55,165,184
    };

    public NoiseGenerator(long seed) {
        int[] p = P.clone();
        for (int i = p.length - 1; i > 0; i--) {
            int j = (int)(seed % (i + 1));
            if (j < 0) j += i + 1;
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }
        for (int i = 0; i < 512; i++) perm[i] = p[i & 255];
    }

    public double noise(float x, float y) {
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        x -= Math.floor(x);
        y -= Math.floor(y);
        double u = fade(x), v = fade(y);
        int a = perm[X] + Y, aa = perm[a], ab = perm[a + 1];
        int b = perm[X + 1] + Y, ba = perm[b], bb = perm[b + 1];
        return lerp(v, lerp(u, grad(perm[aa], x, y), grad(perm[ba], x-1, y)),
                       lerp(u, grad(perm[ab], x, y-1), grad(perm[bb], x-1, y-1)));
    }

    public double octaveNoise(float x, float y, int octaves, float persistence) {
        double total = 0, freq = 1, amp = 1, maxVal = 0;
        for (int i = 0; i < octaves; i++) {
            total += noise(x * freq, y * freq) * amp;
            maxVal += amp;
            amp *= persistence;
            freq *= 2;
        }
        return total / maxVal;
    }

    private double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
    private double lerp(double t, double a, double b) { return a + t * (b - a); }
    private double grad(int hash, double x, double y) {
        int h = hash & 3;
        double u = h < 2 ? x : y;
        double v = h < 2 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}
