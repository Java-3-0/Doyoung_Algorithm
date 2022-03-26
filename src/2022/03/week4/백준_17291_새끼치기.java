// 11492KB, 76ms

package bj17291;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 20;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static int[] alives = new int[MAX_N + 1];
	static int[] borns = new int[MAX_N + 1];
	static int[] dies = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		initCaches();

		N = Integer.parseInt(br.readLine());
		
		int answer = aliveAt(N);
		
		System.out.println(answer);
		
	} // end main
	
	public static int aliveAt(int year) {
		if (year == 1) {
			return 1;
		}
		
		if (alives[year] != CACHE_EMPTY) {
			return alives[year];
		}
		
		return alives[year] = aliveAt(year - 1) + bornAt(year) - dieAt(year);
	}
	
	public static int bornAt(int year) {
		if (year <= 0) {
			return 0;
		}
		
		if (year == 1) {
			return 1;
		}
		
		if (borns[year] != CACHE_EMPTY) {
			return borns[year];
		}
		
		return borns[year] = aliveAt(year - 1);
	}
	
	public static int dieAt(int year) {
		if (year <= 1) {
			 return 0;
		}
		
		if (dies[year] != CACHE_EMPTY) {
			return dies[year];
		}
		
		if (year % 2 == 0) {
			return dies[year] = bornAt(year - 3) + bornAt(year - 4);
		}
		
		else {
			return dies[year] = 0;
		}
	}
	
	public static void initCaches() {
		Arrays.fill(alives, CACHE_EMPTY);
		Arrays.fill(borns, CACHE_EMPTY);
		Arrays.fill(dies, CACHE_EMPTY);
	}

}