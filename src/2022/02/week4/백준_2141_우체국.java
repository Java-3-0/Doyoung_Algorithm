// 47568KB, 600ms

package bj2141;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static class Town implements Comparable<Town> {
		/** 마을의 위치 */
		long position;
		/** 거주 인구 수 */
		long people;

		/** 위치와 사람 수를 받아서 마을 객체를 생성하는 생성자 */
		public Town(long position, long people) {
			super();
			this.position = position;
			this.people = people;
		}

		/** 위치 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Town t) {
			if (this.position < t.position) {
				return -1;
			} else if (this.position == t.position) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 마을 개수 입력
		int N = Integer.parseInt(br.readLine());
		// 각 마을을 나타낼 배열
		Town[] towns = new Town[N];
		
		// 마을 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long position = Long.parseLong(st.nextToken());
			long people = Long.parseLong(st.nextToken());
			towns[i] = new Town(position, people);
		}
		
		// 위치 오름차순으로 정렬
		Arrays.sort(towns);

		// median 위치에 설치해야 하니 median을 찾는다.
		long answer = findMedian(towns);

		// 찾은 답 출력
		System.out.println(answer);
	}

	/** 마을의 위치들과 사람 수 정보가 주어졌을 때, 중간값이 되는 위치를 찾는다 */
	public static long findMedian(Town[] towns) {
		int len = towns.length;

		// 전체 사람 수를 셈
		long sumPeople = 0L;
		for (int i = 0; i < len; i++) {
			sumPeople += towns[i].people;
		}

		// 사람이 한 명도 없는 경우 맨 왼쪽 위치를 리턴
		if (sumPeople == 0L) {
			return -100000000L;
		}

		// 전체의 과반수가 되는 사람 수
		long halfPeople = sumPeople / 2 + sumPeople % 2;

		// 왼쪽부터 세다가 과반수를 딱 넘기는 타이밍이 중간값의 인덱스이므로 리턴
		long sumLeft = 0L;
		for (int idx = 0; idx < len; idx++) {
			sumLeft += towns[idx].people;
			if (sumLeft >= halfPeople) {
				return towns[idx].position;
			}

		}

		// 여기까진 올 수 없다.
		return -1;
	}
}