// 11856KB, 80ms

package bj15728;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N, K;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 메모리 할당
		int[] sharedCards = new int[N];
		int[] myCards = new int[N];
		
		// 공유 카드 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			sharedCards[i] = Integer.parseInt(st.nextToken());
		}
		
		// 내 카드 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			myCards[i] = Integer.parseInt(st.nextToken());
		}
		
		// 공유카드에서 가장 작은 수와 가장 수를 찾는다.
		int minShared = sharedCards[0];
		int maxShared = sharedCards[0];
		for (int i = 0; i < N; i++) {
			int card = sharedCards[i];
			if (card < minShared) {
				minShared = card;
			}
			if (maxShared < card) {
				maxShared = card;
			}
		}
		
		// 우리 팀이 가능한 최대 점수를 계산
		int answer = getMaxScore(myCards, minShared, maxShared);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 내 카드들, 가장 작은 공유 카드, 가장 큰 공유 카드가 주어졌을 때 가능한 최대 점수를 리턴 */
	public static int getMaxScore (int[] myCards, int minShared, int maxShared) {
		// 견제 수가 내 카드 수보다 많으면 아무 카드도 쓸 수 없다.
		if (N <= K) {
			return 0;
		}
		
		// 각 카드 별로 가능한 최대 점수를 저장
		List<Integer> scores = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			int card = myCards[i];
			if (card < 0) {
				scores.add(card * minShared);
			}
			else {
				scores.add(card * maxShared);
			}
		}
		
		// 가능한 점수들 중 K + 1번째로 높은 점수가 우리 팀이 가능한 최대 점수이다.
		Collections.sort(scores, Collections.reverseOrder());
		int ret = scores.get(K);
		
		return ret;
	}

}