// 52968KB, 392ms

package bj23253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		boolean flag = true;
		for (int i = 0; i < M; i++) {
			int size = Integer.parseInt(br.readLine());

			st = new StringTokenizer(br.readLine(), " ");
			int[] arr = new int[size];
			for (int k = 0; k < size; k++) {
				arr[k] = Integer.parseInt(st.nextToken());
			}
			
			if (!isDescending(arr)) {
				flag = false;
			}
		}
		
		if (flag) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}

	} // end main

	public static boolean isDescending(int[] arr) {
		int len = arr.length;
		for (int i = 1; i < len; i++) {
			if (arr[i - 1] < arr[i]) {
				return false;
			}
		}

		return true;

	}

}