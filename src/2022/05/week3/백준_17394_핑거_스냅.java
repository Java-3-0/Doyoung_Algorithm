// 299204KB, 708ms

package bj17394;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) (1e6 * 2);

	static boolean[] isPrime = new boolean[MAX_N + 1];
	static boolean[] isVisited = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 소수 여부 미리 계산
		precalcPrimes();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// N, A, B 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());

			// 도달 가능한 최소 횟수 계산
			int answer = getMinOperations(N, A, B);

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 소수 여부를 미리 계산해서 isPrime[] 배열에 저장해 둔다 */
	public static void precalcPrimes() {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		int sqrt = (int) Math.floor(Math.sqrt(MAX_N));
		for (int div = 2; div <= sqrt; div++) {
			if (!isPrime[div]) {
				continue;
			}

			for (int num = div + div; num <= MAX_N; num += div) {
				isPrime[num] = false;
			}
		}
	}

	/** N을 [A, B] 범위 내의 소수로 만드는 최수 연산 수를 리턴 */
	public static int getMinOperations(int N, int A, int B) {
		Queue<Integer> q = new ArrayDeque<>();
		Arrays.fill(isVisited, false);
		
		// 시작 정점 처리
		isVisited[N] = true;
		q.offer(N);

		// bfs 수행
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				// 현 위치 처리
				int now = q.poll();
				if (A <= now && now <= B && isPrime[now]) {
					return depth;
				}

				// 다음 위치들을 큐에 넣는다
				int next = now / 2;
				if (isInRange(next) && !isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}

				next = now / 3;
				if (isInRange(next) && !isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}

				next = now + 1;
				if (isInRange(next) && !isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}

				next = now - 1;
				if (isInRange(next) && !isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}
			}

			depth++;
		}

		return -1;
	}

	/** x가 0 ~ MAX_N 사이 범위 내의 수인지 여부를 리턴 */
	public static boolean isInRange(int x) {
		if (0 <= x && x <= MAX_N) {
			return true;
		}
		return false;
	}
}