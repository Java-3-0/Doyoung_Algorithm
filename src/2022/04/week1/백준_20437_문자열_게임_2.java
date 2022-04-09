// 25232KB, 268ms

package bj20437;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static final int ALPHABETS = 26;
	static final int INF = 987654321;
	static final int FAIL = -1;

	static List<Integer>[] positions = new ArrayList[ALPHABETS];
	static int K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// positions 메모리 할당
		for (int i = 0; i < positions.length; i++) {
			positions[i] = new ArrayList<Integer>();
		}

		final int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			// positions 초기화
			initPositions();

			char[] text = br.readLine().toCharArray();
			int len = text.length;

			for (int i = 0; i < len; i++) {
				char c = text[i];
				positions[c - 'a'].add(i);
			}

			K = Integer.parseInt(br.readLine());

			int answer3 = three();
			int answer4 = four();

			if (answer3 == FAIL) {
				sb.append(answer3).append("\n");
			} else {
				sb.append(answer3).append(" ").append(answer4).append("\n");
			}
			
		} // end for tc
		
		System.out.print(sb.toString());

	} // end main

	public static void printPositions() {
		for (int i = 0; i < positions.length; i++) {
			System.out.println(positions[i].toString());
		}
	}

	public static void initPositions() {
		for (int i = 0; i < positions.length; i++) {
			positions[i].clear();
		}
	}

	public static int three() {
		int ret = INF;

		for (List<Integer> pos : positions) {
			if (pos.size() < K) {
				continue;
			}

			for (int rightIdx = K - 1; rightIdx < pos.size(); rightIdx++) {
				int leftIdx = rightIdx - (K - 1);
				int right = pos.get(rightIdx);
				int left = pos.get(leftIdx);

				ret = Math.min(ret, right - left + 1);
			}

		}

		if (ret == INF) {
			return FAIL;
		} else {
			return ret;
		}

	}

	public static int four() {
		int ret = FAIL;

		for (List<Integer> pos : positions) {
			if (pos.size() < K) {
				continue;
			}

			for (int rightIdx = K - 1; rightIdx < pos.size(); rightIdx++) {
				int leftIdx = rightIdx - (K - 1);
				int right = pos.get(rightIdx);
				int left = pos.get(leftIdx);

				ret = Math.max(ret, right - left + 1);
			}

		}

		return ret;
	}

}