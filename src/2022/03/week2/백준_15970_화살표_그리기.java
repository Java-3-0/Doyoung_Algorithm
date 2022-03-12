// 13968KB, 136ms

package bj15970;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 5000;
	static final int INF = Integer.MAX_VALUE;

	static int N;
	static List<Integer>[] positions;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 메모리 할당
		positions = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			positions[i] = new ArrayList<Integer>();
		}

		// 점들의 좌표와 색깔 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int pos = Integer.parseInt(st.nextToken());
			int color = Integer.parseInt(st.nextToken());

			positions[color - 1].add(pos);
		}

		// 각 색상에 대해서 처리
		int answer = 0;
		for (int color = 0; color < N; color++) {
			int size = positions[color].size();
			if (size != 0) {
				// 정렬
				Collections.sort(positions[color]);

				// 가장 가까운 위치와의 거리를 구해서 answer에 더한다.
				for (int i = 0; i < size; i++) {
					int minDist = INF;
					if (0 <= i - 1) {
						int dist = Math.abs(positions[color].get(i) - positions[color].get(i - 1));
						minDist = Math.min(minDist, dist);
					}

					if (i + 1 < size) {
						int dist = Math.abs(positions[color].get(i + 1) - positions[color].get(i));
						minDist = Math.min(minDist, dist);
					}

					answer += minDist;
				}
			}
		}

		// 출력
		System.out.println(answer);

	}

}