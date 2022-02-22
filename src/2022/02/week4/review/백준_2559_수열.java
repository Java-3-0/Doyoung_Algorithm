// 23344KB, 280ms

package baek2559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		int seq[] = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 부분합 계산
		int psums[] = getPsums(seq);

		// 구간합의 최대 계산
		int answer = Integer.MIN_VALUE;
		for (int i = K; i < psums.length; i++) {
			answer = Math.max(answer, psums[i] - psums[i - K]);
		}

		System.out.println(answer);
	}

	/** 부분합을 계산해서 배열로 리턴 */
	public static int[] getPsums(int[] arr) {
		int[] ret = new int[arr.length + 1];

		ret[0] = 0;
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			ret[i + 1] = sum;
		}

		return ret;
	}

}
