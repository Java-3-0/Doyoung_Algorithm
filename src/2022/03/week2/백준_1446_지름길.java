// 11652KB, 80ms

package bj1446;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 12;
	static final int MAX_D = 10000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 98765432;

	static Path[] paths;
	static int[] cache = new int[MAX_D + 1];
	static int N, D;

	static class Path implements Comparable<Path> {
		int from;
		int to;
		int weight;

		public Path(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Path p) {
			return this.from - p.from;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// N, D 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		// 지름길 입력
		paths = new Path[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			paths[i] = new Path(from, to, weight);
		}

		// 지름길을 시작 위치 순으로 정렬
		Arrays.sort(paths);

		// 최단 거리 계산
		int answer = getMinDistance(0);

		// 출력
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	/** startPos 위치부터 고속도로의 끝까지 가능한 최단 거리를 리턴 */
	public static int getMinDistance(int startPos) {
		// base case 1: 고속도로 범위를 초과한 경우
		if (startPos > D) {
			return INF;
		}

		// base case 2: 더 이상 탈 수 있는 지름길이 없는 경우
		int lastPath = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (paths[i].from <= D) {
				lastPath = paths[i].from;
				break;
			}
		}
		if (startPos > lastPath) {
			return D - startPos;
		}

		// 캐시에 이미 저장된 값이 있는 경우
		if (cache[startPos] != CACHE_EMPTY) {
			return cache[startPos];
		}

		// 지름길을 타지 않는 경우
		int ret = D - startPos;

		// 지름길을 타는 경우들에 대해 재귀적으로 수행
		for (Path p : paths) {
			if (p.from < startPos) {
				continue;
			}

			// 지름길이 시작되는 위치까지 가는 거리 + 지름길의 길이 + 재귀호출로 구한 나머지 부분의 길이
			int dist = (p.from - startPos) + p.weight + getMinDistance(p.to);

			// 최단 거리 갱신
			ret = dist < ret ? dist : ret;
		}

		return cache[startPos] = ret;
	}

}