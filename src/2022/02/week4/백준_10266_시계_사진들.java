// 70296KB, 660ms

package bj10266;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_TIME = 360000;
	static final int MAX_N = 200000;
	
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 시계 바늘 수 입력
		N = Integer.parseInt(br.readLine());

		// 시계 1 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] clock1 = new int[N];
		for (int i = 0; i < N; i++) {
			clock1[i] = Integer.parseInt(st.nextToken());
		}

		// 시계 2 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] clock2 = new int[N];
		for (int i = 0; i < N; i++) {
			clock2[i] = Integer.parseInt(st.nextToken());
		}

		// 시계 바늘을 순서대로 정렬
		Arrays.sort(clock1);
		Arrays.sort(clock2);

		// 바늘과 바늘 사이의 각도들을 계산
		int[] diff1 = new int[N];
		int[] diff2 = new int[N];

		for (int i = 0; i < N; i++) {
			diff1[i] = (MAX_TIME + clock1[(i + 1) % N] - clock1[i]) % MAX_TIME;
			diff2[i] = (MAX_TIME + clock2[(i + 1) % N] - clock2[i]) % MAX_TIME;
		}

		// 시계1에서 구한 간격들과 시계2에서 구한 간격들이 같으면서 원형으로 위치만 다른 것임을 판단하기 위해
		// 원형인 것을 시계1의 길이를 2배로 하는 것으로 표현해서 시계 2를 찾음.
		int[] diff1Repeat = new int[diff1.length * 2];
		for (int i = 0; i < diff1Repeat.length; i++) {
			diff1Repeat[i] = diff1[i % diff1.length];
		}
		
		// diff1을 길이를 2배로 늘린 것에서 diff2가 찾아진다면, 원형으로 봤을 때 둘은 같은 문자열이라는 것
		boolean canFind = searchKMP(diff1Repeat, diff2);

		// 찾았다면 성공, 못 찾았다면 실패
		if (canFind) {
			System.out.println("possible");
		} else {
			System.out.println("impossible");
		}

	} // end main

	/** 부분 일치 테이블을 생성해서 리턴하는 함수 */
	public static int[] getPrefixTable(int[] arr) {
		int len = arr.length;
		int[] p = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && arr[i] != arr[j]) {
				j = p[j - 1];
			}

			if (arr[i] == arr[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}

	// KMP알고리즘을 이용하여 탐색해 보고, arr 배열에서 pattern 배열을 찾을 수 있으면 true, 아니면 false를 리턴 */
	public static boolean searchKMP(int[] arr, int[] pattern) {
		int lenArr = arr.length;
		int lenP = pattern.length;

		int[] p = getPrefixTable(pattern);

		for (int i = 0, j = 0; i < lenArr; i++) {
			while (j > 0 && arr[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (arr[i] == pattern[j]) {
				if (j == lenP - 1) {
					j = p[j];
					return true;
				} else {
					j++;
				}
			}
		}

		return false;
	}
}