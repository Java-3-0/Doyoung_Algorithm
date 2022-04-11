// 28428KB, 272ms

package bj2110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200000;
	static final int MAX_POS = (int) 1e9;

	static int N, C;
	static int[] houses;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, C 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		// 집 위치 배열 메모리 할당
		houses = new int[N];

		// 집 위치 입력
		for (int i = 0; i < N; i++) {
			houses[i] = Integer.parseInt(br.readLine());
		}

		// 집 위치 오름차순 정렬
		Arrays.sort(houses);

		// 가능한 최대 거리 계산
		int answer = getMaxDist();
		
		// 출력
		System.out.println(answer);

	} // end main

	/** 공유기 사이의 가능한 최대 거리를 계산해서 리턴 */
	public static int getMaxDist() {
		int left = 0;
		int right = MAX_POS;
		
		while (left < right) {
			int mid = (left + right + 1) / 2;
			
			if (isPossible(mid)) {
				left = mid;
			}
			else {
				right = mid - 1;
			}
		}
		
		return left;
	}

	/** dist 거리로 배치하는 것이 가능한지 여부를 리턴 */
	public static boolean isPossible(int dist) {
		// 맨 앞 집에 배치
		int prev = houses[0];
		int cnt = 1;

		// 거리가 dist 이상이 될 때마다 새로 배치
		for (int i = 1; i < N; i++) {
			int now = houses[i];
			int diff = now - prev;
			if (diff >= dist) {
				prev = now;
				cnt++;
			}
		}

		// C개 이상의 공유기 배치가 가능하면 성공, 아니면 실패
		if (cnt >= C) {
			return true;
		} else {
			return false;
		}

	}

}