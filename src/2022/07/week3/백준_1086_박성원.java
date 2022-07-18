// 38300KB, 900ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    static final int MAX_N = 15, MAX_K = 100, MAX_LEN = 50;
    static final int MAX_BITMASK = (1 << MAX_N) - 1;
    static final int CACHE_EMPTY = -1;

    static int N, K;

    static int targetBitMask;
    static String[] seq = new String[MAX_N];
    static int[] remainders = new int[MAX_N];
    static int[] lengths = new int[MAX_N];
    static int[] powers = new int[MAX_LEN + 1];

    static long[][] cache = new long[MAX_K + 1][MAX_BITMASK + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        // 입력
        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            seq[i] = br.readLine();
        }

        K = Integer.parseInt(br.readLine());
        BigInteger bigIntK = new BigInteger(String.valueOf(K));
        for (int i = 0; i < N; i++) {
            remainders[i] = new BigInteger(seq[i]).remainder(bigIntK).intValue();
            lengths[i] = seq[i].length();
        }

        targetBitMask = (1 << N) - 1;

        // 캐시 초기화
        initCache();

        // 10의 제곱의 mod K들 초기화
        initPowers();

        // 분모 계산
        long denom = getFactorial(N);

        // 분자 계산
        long numer = countCorrectPermu(0, 0);

        // 0 처리
        if (numer == 0) {
            sb.append(0).append("/").append(1).append("\n");
        }

        // 기약분수로 나타내기
        else {
            long gcd = getGCD(denom, numer);
            denom /= gcd;
            numer /= gcd;
            sb.append(numer).append("/").append(denom).append("\n");
        }

        System.out.print(sb.toString());

    }

    public static void initPowers() {
        powers[0] = 1;
        for (int i = 1; i <= MAX_LEN; i++) {
            powers[i] = (powers[i - 1] * 10) % K;
        }
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }

    public static long countCorrectPermu(int rem, int used) {
        if (used == targetBitMask) {
            if (rem == 0) {
                return 1L;
            } else {
                return 0L;
            }
        }

        if (cache[rem][used] != CACHE_EMPTY) {
            return cache[rem][used];
        }

        long ret = 0L;

        for (int pick = 0; pick < N; pick++) {
            if ((used & (1 << pick)) == 0) {
                int nextRem = (rem * powers[lengths[pick]] + remainders[pick]) % K;
                int nextUsed = used | (1 << pick);
                ret += countCorrectPermu(nextRem, nextUsed);
            }
        }

        return cache[rem][used] = ret;
    }

    public static long getFactorial(long num) {
        long ret = 1L;
        for (long n = 1; n <= num; n++) {
            ret *= n;
        }

        return ret;
    }

    public static long getGCD(long a, long b) {
        if (a == 0) {
            return b;
        }

        return getGCD(b % a, a);
    }
}
