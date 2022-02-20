// 11544KB, 76ms

package bj1474;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");

		// 단어 개수, 최종 문자열 길이 입력
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 단어 입력
		String[] words = new String[N];
		int sumLength = 0;
		for (int i = 0; i < N; i++) {
			String word = br.readLine();
			words[i] = word;
			sumLength += word.length();
		}

		// 부족한 길이 만큼이 언더바의 개수이다.
		int totalUnderbars = M - sumLength;

		// 각 단어 앞에 들어가는 언더바 개수를 담을 배열
		int[] underbars = new int[N];

		// 첫 단어 앞에는 0개, 나머지 단어들 앞에는 똑같이 나눠서 넣는다.
		int quotient = totalUnderbars / (N - 1);
		int remainder = totalUnderbars % (N - 1);

		Arrays.fill(underbars, 1, N, quotient);

		// 부족한 만큼, 앞에서부터 소문자로 시작하는 단어 앞마다 하나씩 더 넣는다.
		for (int i = 1; i < N; i++) {
			if (remainder == 0) {
				break;
			}
			
			char c = words[i].charAt(0);
			if ('a' <= c && c <= 'z') {
				underbars[i]++;
				remainder--;
			}
		}
		
		// 그래도 부족하면, 뒤에서부터 대문자로 시작하는 단어 앞마다 하나씩 더 넣는다.
		for (int i = N - 1; i > 0; i--) {
			if (remainder == 0) {
				break;
			}
			
			char c = words[i].charAt(0);
			if ('A' <= c && c <= 'Z') {
				underbars[i]++;
				remainder--;
			}
		}
		
		// 출력을 담을 스트링빌더 선언
		StringBuilder sb = new StringBuilder();
		
		// 첫 단어 추가
		sb.append(words[0]);
		
		// 이후 N - 1개의 언더바 묶음들과 N - 1개 단어들 추가
		for (int i = 1; i < N; i++) {
			for (int num = 0; num < underbars[i]; num++) {
				sb.append("_");
			}
			sb.append(words[i]);
		}

		// 출력
		System.out.print(sb.toString());
	}
}