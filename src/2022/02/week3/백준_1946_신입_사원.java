// 308628KB, 2392ms

package bj1946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	/** 지원자를 나타내는 객체 */
	static class Applicant implements Comparable<Applicant> {
		/** 서류 등수 */
		int document;
		/** 면접 등수를 */
		int interview;

		/** 서류 등수, 면접 등수를 입력받아 지원자 객체를 생성하는 생성자 */
		public Applicant(int document, int interview) {
			super();
			this.document = document;
			this.interview = interview;
		}

		/** 서류 등수 오름차순 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Applicant app) {
			return this.document - app.document;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			// 지원자 수 입력
			int N = Integer.parseInt(br.readLine());
			
			// 각 지원자 정보를 입력받아 객체로 만들고 배열에 저장
			Applicant[] applicants = new Applicant[N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int document = Integer.parseInt(st.nextToken());
				int interview = Integer.parseInt(st.nextToken());
				applicants[i] = new Applicant(document, interview);
			}
			
			// 지원자들을 서류 등수 오름차순으로 정렬
			Arrays.sort(applicants);
			
			// 0번 참가자는 무조건 선발
			int count = 1;
			
			// 1번 참가자부터는 이전 참가자들보다 서류 등수가 크니까, 면접 등수는 이전까지의 가장 작은 등수보다 더 작아야만 선발
			int minInterviewRank = applicants[0].interview;
			for (int i = 1; i < N; i++) {
				Applicant app = applicants[i];
				
				if (app.interview < minInterviewRank) {
					count++;
					minInterviewRank = app.interview;
				}
			}
			
			// 정답을 출력에 추가
			sb.append(count).append("\n");
		} // end for testCase
		
		// 출력
		System.out.println(sb.toString());
	} // end main
}