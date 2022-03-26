// 47436KB, 364ms

package bj2876;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int GRADES = 5;
	
	static int N;
	static int[][] cache = new int[MAX_N + 1][GRADES + 1];
	static int[][] chairs = new int[MAX_N + 1][2];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 캐시 초기화
		initCache();
		
		// 입력
		N = Integer.parseInt(br.readLine());
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			chairs[i][0] = Integer.parseInt(st.nextToken());
			chairs[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int[] answer = solve();
		
		System.out.println(answer[0] + " " + answer[1]);
		
	} // end main
	
	public static int[] solve() {
		int[] ret = new int[2];
		
		for (int grade = 1; grade <= GRADES; grade++) {
			int maxStudent = 0;
			for (int i = 1; i <= N; i++) {
				int students = getMaxStudents(i, grade);
				if (maxStudent < students) {
					maxStudent = students;
				}
			}
			
			if (ret[0] < maxStudent) {
				ret[0] = maxStudent;
				ret[1] = grade;
			}
			
		}
		
		return ret;
	}
	
	public static int getMaxStudents(int startIdx, int grade) {
		if (startIdx > N) {
			return 0;
		}
		
		if (cache[startIdx][grade] != CACHE_EMPTY) {
			return cache[startIdx][grade];
		}
		
		if (hasGrade(startIdx, grade)) {
			return cache[startIdx][grade] = 1 + getMaxStudents(startIdx + 1, grade);
		}
		else {
			return cache[startIdx][grade] = 0;
		}
	}
	
	public static boolean hasGrade(int idx, int grade) {
		if (chairs[idx][0] == grade || chairs[idx][1] == grade) {
			return true;
		}
		return false;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}