// 11836KB, 84ms

package bj1671;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int NOT_MATCHED = -1;

	static int N;
	static List<Integer>[] adjList;

	static Shark[] sharks;
	static int[][] isMatchedTo;
	static boolean[] isChecked;

	static class Shark implements Comparable<Shark> {
		int size;
		int speed;
		int intelligence;

		public Shark(int size, int speed, int intelligence) {
			super();
			this.size = size;
			this.speed = speed;
			this.intelligence = intelligence;
		}

		/** 상어들을 능력치 내림차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Shark s) {
			if (this.size == s.size) {
				if (this.speed == s.speed) {
					return -(this.intelligence - s.intelligence);
				}
				return -(this.speed - s.speed);
			}
			return -(this.size - s.size);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 상어 마리수 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		adjList = new ArrayList[N];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		sharks = new Shark[N];
		isMatchedTo = new int[N][2]; // 2마리까지 먹을 수 있으니 size 2를 가지는 2차원 배열
		isChecked = new boolean[N];

		// 상어 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int size = Integer.parseInt(st.nextToken());
			int speed = Integer.parseInt(st.nextToken());
			int intelligence = Integer.parseInt(st.nextToken());
			sharks[i] = new Shark(size, speed, intelligence);
		}

		// 상어들 정렬
		Arrays.sort(sharks);
		
		// 간선 정보 생성 (먹히는 쪽 -> 먹는 쪽 방향)
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				Shark sharkBig = sharks[i];
				Shark sharkSmall = sharks[j];
				if (sharkBig.size >= sharkSmall.size && sharkBig.speed >= sharkSmall.speed && sharkBig.intelligence >= sharkSmall.intelligence) {
					adjList[j].add(i);
				}
			}
		}

		// 최소 생존 수 = 전체 - 최대 먹히는 수
		int answer = N - getMaxMatches();

		// 출력
		System.out.println(answer);

	} // end main

	/** 최대 매칭 수를 리턴 */
	public static int getMaxMatches() {
		for (int i = 0; i < isMatchedTo.length; i++) {
			Arrays.fill(isMatchedTo[i], NOT_MATCHED);
		}

		int ret = 0;
		for (int i = 0; i < N; i++) {
			Arrays.fill(isChecked, false);
			if (tryMatch(i)) {
				ret++;
			}
		}

		return ret;
	}

	/** i번 인덱스의 매칭을 시도하고 성공 시 true, 실패 시 false를 리턴 */
	public static boolean tryMatch(int x) {
		for (int y : adjList[x]) {
			if (isChecked[y]) {
				continue;
			}
			isChecked[y] = true;

			for (int k = 0; k < 2; k++) {
				int prevMatchX = isMatchedTo[y][k];
				if (prevMatchX == NOT_MATCHED || tryMatch(prevMatchX)) {
					isMatchedTo[y][k] = x;
					return true;
				}
			}
		}

		return false;
	}
}