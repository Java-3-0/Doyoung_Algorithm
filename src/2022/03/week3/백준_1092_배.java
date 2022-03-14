// 15604KB, 160ms

package bj1092;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_M = 10000;
	static final int MAX_WEIGHT = 1000000;

	static int N, M;
	static Integer[] cranes;
	static int[] counts;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 크레인 입력
		N = Integer.parseInt(br.readLine());
		cranes = new Integer[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			cranes[i] = Integer.parseInt(st.nextToken());
		}

		// 크레인 내림차순 정렬
		Arrays.sort(cranes, Collections.reverseOrder());

		// 박스 입력
		counts = new int[N];
		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		boxLoop: for (int b = 0; b < M; b++) {
			int boxWeight = Integer.parseInt(st.nextToken());
			
			// 크레인 중 가장 높은 무게 제한보다 더 높은 무게의 박스가 있으면 실패
			if (boxWeight > cranes[0]) {
				System.out.println("-1");
				return;
			}

			// 각 크레인이 담당해야 하는 박스의 수를 센다.
			for (int c = 1; c < N; c++) {
				if (boxWeight > cranes[c]) {
					counts[c - 1]++;
					continue boxLoop;
				}
			}
			counts[N - 1]++;
		}

		// 정답 계산
		int answer = getMinTime();

		// 출력
		System.out.println(answer);
	}

	/** 박스들을 모두 옮기는 데 드는 최소 시간을 리턴 */
	public static int getMinTime() {
		int time = 0;
		int moved = 0;

		// 모든 박스를 옮길 때까지 반복
		while (moved < M) {
			craneLoop: for (int c = 0; c < N; c++) {
				countLoop: for (int k = c; k < N; k++) {
					// 자신이 옮겨야 하는 박스를 옮기고, 다 옮겼다면 다음 크레인이 옮겨야 할 박스를 대신 옮긴다.
					if (counts[k] > 0) {
						counts[k]--;
						moved++;
						continue craneLoop;
					}
				}
			}
			time++;
		}

		return time;
	}
}
