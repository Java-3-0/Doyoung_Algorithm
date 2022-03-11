import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 16;
	static final int ALPHABETS = 26;
	static final int INF = 987654321;

	static char[] targetWord;
	static int N;
	static int[] prices = new int[MAX_N];
	static int[][] alphabetCounts = new int[MAX_N][ALPHABETS];
	static int[] targetCounts = new int[ALPHABETS];
	
	static boolean[] isUsed = new boolean[MAX_N];
	static int[] combi = new int[MAX_N];
	static int answer = INF;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력을 받아서 각 알파벳 카운트 계산
		targetWord = br.readLine().toCharArray();
		N = Integer.parseInt(br.readLine());
		targetCounts = getAlphabetCount(targetWord);

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			prices[i] = Integer.parseInt(st.nextToken());
			alphabetCounts[i] = getAlphabetCount(st.nextToken().toCharArray());
		}

		// 모든 조합 완전탐색
		combination(0, 0);
		
		// 정답 출력
		System.out.println(answer == INF ? -1 : answer);
		
	} // end main

	public static void combination(int hereIdx, int cnt) {
		if (hereIdx == N) {
			int[] totalCnt = new int[ALPHABETS];
			
			// 조합에 사용된 책들의 알파벳 개수 합 계산
			for (int i = 0; i < cnt; i++) {
				for (int alpha = 0; alpha < ALPHABETS; alpha++) {
					totalCnt[alpha] += alphabetCounts[combi[i]][alpha];
				}
			}
			
			// 각 알파벳의 개수가 목적 개수보다 적다면 실패
			for (int alpha = 0; alpha < ALPHABETS; alpha++) {
				if (totalCnt[alpha] < targetCounts[alpha]) {
					return;
				}
			}
			
			// 성공한 경우, 가격 합 계산
			int totalPrice = 0;
			for (int i = 0; i < cnt; i++) {
				totalPrice += prices[combi[i]];
			}
			
			// 최소 가격 갱신
			answer = Math.min(answer, totalPrice);
			return; 
		}
		
		combination(hereIdx + 1, cnt);
		
		combi[cnt] = hereIdx;
		combination(hereIdx + 1, cnt + 1);
	}

	/** char형 배열 arr에 들은 각 알파벳의 수를 세어서 배열로 리턴 */
	public static int[] getAlphabetCount(char[] arr) {
		int[] ret = new int[ALPHABETS];

		for (char c : arr) {
			ret[c - 'A']++;
		}

		return ret;
	}
}