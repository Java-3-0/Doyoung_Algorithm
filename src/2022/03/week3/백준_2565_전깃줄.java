// 11868KB, 80ms

package bj2565;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final int MAX_POS = 500;
	static final int INF = 987654321;
	static final int CACHE_EMPTY = -1;

	static int N;
	static Line[] lines;
	static int[][] cache = new int[MAX_N + 1][MAX_POS + 1];

	static class Line implements Comparable<Line> {
		int from;
		int to;

		public Line(int from, int to) {
			super();
			this.from = from;
			this.to = to;
		}

		@Override
		public String toString() {
			return "Line [from=" + from + ", to=" + to + "]";
		}

		@Override
		public int compareTo(Line l) {
			return this.from - l.from;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 캐시 초기화
		initCache();

		// 전깃줄 개수 입력
		N = Integer.parseInt(br.readLine());

		// 전깃줄 정보 입력
		lines = new Line[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			lines[i] = new Line(from, to);
		}

		// 전기줄을 시작 위치 번호 오름차순으로 정렬
		Arrays.sort(lines);

		// 정답 계산
		int answer = getMinCuts(0, 0);

		// 출력
		System.out.println(answer);
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** 전기줄 번호와 마지막으로 연결된 전깃줄의 끝 위치가 주어졌을 때, 가능한 최소 커팅 수를 리턴 */
	public static int getMinCuts(int lineIdx, int lastConnected) {
		// base case : 마지막 전기줄까지 모두 처리한 경우
		if (lineIdx == N) {
			return 0;
		}

		// 이미 캐시에 저장되어 있는 경우
		if (cache[lineIdx][lastConnected] != CACHE_EMPTY) {
			return cache[lineIdx][lastConnected];
		}

		int to = lines[lineIdx].to;

		// 이 선을 자르는 경우
		int ret = 1 + getMinCuts(lineIdx + 1, lastConnected);

		// 이 선을 자르지 않는 경우
		if (to > lastConnected) {
			ret = Math.min(ret, getMinCuts(lineIdx + 1, to));
		}

		return cache[lineIdx][lastConnected] = ret;
	}

}
