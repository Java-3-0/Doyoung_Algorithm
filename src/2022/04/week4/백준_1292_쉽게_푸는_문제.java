// 11496KB, 76ms

package bj1292;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int[] seq = new int[1000 + 1];
		int[] psums = new int[1000 + 1];
		int cnt = 0;
		for (int i = 1, num = 1, sum = 0; i <= 1000; i++) {
			seq[i] = num;
			sum += num;
			psums[i] = sum;
			cnt++;

			if (cnt == num) {
				cnt = 0;
				num++;
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());

		int answer = psums[B] - psums[A - 1];

		System.out.println(answer);

	} // end main

}