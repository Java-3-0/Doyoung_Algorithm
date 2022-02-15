// 11500KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	// 재료 개수
	public static int N;
	// 재료의 신맛 배열
	public static long[] sour;
	// 재료의 쓴맛 배열
	public static long[] bitter;
	// 신맛과 쓴맛의 차이 최소값를 저장할 변수
	public static long minDiff = Long.MAX_VALUE;

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());

		sour = new long[N]; 
		bitter = new long[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			sour[i] = Long.parseLong(st.nextToken());
			bitter[i] = Long.parseLong(st.nextToken());
		}

		// 모든 부분집합을 만들어 보면서 minDiff 값 갱신
		powerSet();

		// 출력
		System.out.println(minDiff);
	}
	
	/** 외부에서 호출할 때 시작 인덱스 초기값, 카운트 초기값, 덧셈의 항등원, 곱셈의 항등원을 입력해야 하면 불편하니까 만들어 둔 오버로딩 함수 */
	public static void powerSet() {
		powerSet(0, 0, 1L, 0L);
	}

	/** 재료의 모든 부분집합을 만들어 보면서 minDiff 값 갱신 */
	public static void powerSet(int hereIdx, int cnt, long sourAccum, long bitterAccum) {
		// 재료의 부분집합이 완성된 경우
		if (hereIdx == N) {
			// 물은 음식이 아니라고 본다
			if (cnt == 0)
				return;

			// 신맛과 쓴맛의 차이 계산
			long diff = Long.MAX_VALUE;
			if (sourAccum <= bitterAccum) {
				diff = bitterAccum - sourAccum;
			} else {
				diff = sourAccum - bitterAccum;
			}

			// 차이의 최소값 갱신
			if (diff < minDiff) {
				minDiff = diff;
			}

			return;
		}

		// 넣는 경우
		powerSet(hereIdx + 1, cnt + 1, sourAccum * sour[hereIdx], bitterAccum + bitter[hereIdx]);

		// 안 넣는 경우
		powerSet(hereIdx + 1, cnt, sourAccum, bitterAccum);

	}
}
