// 22016KB, 224ms

package bj1912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int[] seq = new int[N + 1];
		int[] psums = new int[N + 1];
		psums[0] = 0;

		int sum = 0;
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			int num = Integer.parseInt(st.nextToken());
			seq[i] = num;
			sum += num;
			psums[i] = sum;
		}

		int answer = Integer.MIN_VALUE;
		int minPsum = 0;

		// psums[i] - psums[j]가 최소가 되는 (j < i)인 것을 찾아야 한다.
		// 현재까지 나온 psum 중 최소인 것을 저장해 두고, 앞으로 만나게 되는 psum마다 그걸 뺀 차이를 계산
		// 이 차이 중 최대값이 구하려는 답이 된다.
		for (int i = 1; i <= N; i++) {
			int here = psums[i];

			// 이전까지 만난 minPsum과의 차이 계산
			int diff = here - minPsum;
			if (diff > answer) {
				answer = diff;
			}
			
			// 필요하다면 minPsum 값 갱신
			if (here < minPsum) {
				minPsum = here;
			}
		}

		System.out.println(answer);
	}
}