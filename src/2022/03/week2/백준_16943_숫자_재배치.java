// 30076KB, 464ms

package bj16943;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int numberLength;
	static int[] permu;
	static boolean[] isUsed;
	static int A, B;
	static String strA;
	static List<Integer> listOfC = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// A, B 입력
		st = new StringTokenizer(br.readLine(), " ");
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());

		strA = String.valueOf(A);
		numberLength = String.valueOf(A).length();

		// 메모리 할당
		permu = new int[numberLength];
		isUsed = new boolean[numberLength];

		// 모든 순열 완전탐색하면서 가능한 모든 C를 만들어서 리스트에 넣는다.
		permutation(0);

		// 오름차순 정렬
		Collections.sort(listOfC);

		// B보다 작은 C중 가장 큰 값을 찾는다.
		int answer = -1;
		for (int num : listOfC) {
			if (num < B) {
				answer = num;
			}
		}

		System.out.println(answer);
	}

	public static void permutation(int idx) {
		// 순열이 완성된 경우
		if (idx == numberLength) {
			int C = 0;
			for (int i = 0; i < numberLength; i++) {
				C *= 10;
				C += (strA.charAt(permu[i]) - '0');
			}

			listOfC.add(C);
			return;
		}

		// 순열이 완성 전인 경우, 순열을 만들어 본다.
		for (int digit = 0; digit < numberLength; digit++) {
			if (isUsed[digit]) {
				continue;
			}

			if (strA.charAt(digit) == '0' && idx == 0) {
				continue;
			}

			isUsed[digit] = true;
			permu[idx] = digit;
			permutation(idx + 1);

			isUsed[digit] = false;
		}
	}

}