// 106788KB, 680ms

package bj14722;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int TYPES = 3;
	static final int[] dy = {0, 1};
	static final int[] dx = {1, 0};
	
	static int N;
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[][][] cache = new int[MAX_N + 1][MAX_N + 1][TYPES];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		initCache();
		
		N = Integer.parseInt(br.readLine());
		
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		int answer = getMaxDrinks(1, 1, 2);
		
		System.out.println(answer);
		
	} // end main
	
	public static int getMaxDrinks(int startY, int startX, int prevMilk) {
		if (!isInRange(startY, startX)) {
			 return 0;
		}
		
		if (cache[startY][startX][prevMilk] != CACHE_EMPTY) {
			return cache[startY][startX][prevMilk];
		}
		
		int nowMilk = grid[startY][startX];
		
		int ret = 0;
		for (int dir = 0; dir < dy.length; dir++) {
			int nextY = startY + dy[dir];
			int nextX = startX + dx[dir];
			ret = Math.max(ret, getMaxDrinks(nextY, nextX, prevMilk));
			
			if (nowMilk == (prevMilk + 1) % TYPES) {
				ret = Math.max(ret, 1 + getMaxDrinks(nextY, nextX, nowMilk));
			}
		}
		
		return cache[startY][startX][prevMilk] = ret;
	}
	
	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= N) {
			return true;
		}
		return false;
	}
	
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

}