// 22952KB, 264ms

package baek2491;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 문제에서 요구하는 최대 길이 계산
		int answer = maxLengthAscOrDesc(seq);
		
		// 출력
		System.out.println(answer);
	}

	/** 수열에서 오름차순 또는 내림차순인 부분의 최대 길이를 리턴 */
	public static int maxLengthAscOrDesc(int[] arr) {
		int ret = 0;

		int countAsc = 1;
		int countDesc = 1;

		int prev = arr[0];
		for (int i = 1; i < arr.length; i++) {
			int num = arr[i];
			if (prev == num) {
				countAsc++;
				countDesc++;
			}

			else if (prev < num) {
				countAsc++;
				ret = Math.max(ret, countDesc);
				countDesc = 1;
			}

			else {
				countDesc++;
				ret = Math.max(ret, countAsc);
				countAsc = 1;
			}

			prev = num;
		}

		// 수열의 맨 끝에서 쌓인 count로 ret 한 번 더 갱신
		ret = Math.max(ret, countAsc);
		ret = Math.max(ret, countDesc);

		return ret;
	}
}
