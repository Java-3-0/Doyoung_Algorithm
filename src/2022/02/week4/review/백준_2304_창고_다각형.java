// 12040KB, 88ms

package bj2304;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_L = 1000;
	
	/** 기둥 객체 */
	static class Pillar implements Comparable<Pillar> {
		/** 기둥의 왼쪽 면 좌표 */
		int left;
		/** 기둥의 높이 */
		int height;

		/** 기둥의 왼쪽 면 좌표와 높이를 입력받아 기둥 객체를 생성하는 생성자 */
		public Pillar(int left, int height) {
			super();
			this.left = left;
			this.height = height;
		}

		/** 기둥을 왼쪽 면 좌표의 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Pillar p) {
			return this.left - p.left;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		// 입력
		int N = Integer.parseInt(br.readLine());
		Pillar[] pillars = new Pillar[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int L = Integer.parseInt(st.nextToken());
			int H = Integer.parseInt(st.nextToken());
			pillars[i] = new Pillar(L, H);
		}

		// 창고 다각형 최대 넓이 계산
		int answer = getMaxArea(pillars);

		// 출력
		System.out.println(answer);
	}

	public static int getMaxArea(Pillar[] pillars) {
		int len = pillars.length;

		// 기둥을 왼쪽 면의 좌표 오름차순으로 정렬
		Arrays.sort(pillars);

		// 모든 기둥 중 가장 높은 기둥을 찾음
		Pillar tallestPillar = pillars[0];
		int tallestIdx = 0;
		for (int i = 0; i < len; i++) {
			Pillar p = pillars[i];
			if (tallestPillar.height < p.height) {
				tallestPillar = p;
				tallestIdx = i;
			}
		}

		int ret = 0;

		// 왼쪽 창고 다각형의 넓이
		int localMaxHeight = pillars[0].height;
		int prevIdx = pillars[0].left;
		for (int i = 1; i < tallestIdx; i++) {
			Pillar p = pillars[i];
			
			ret += localMaxHeight * (p.left - prevIdx);
			prevIdx = p.left;
			
			if (localMaxHeight < p.height) {
				localMaxHeight = p.height;
			}
		}
		ret += (tallestPillar.left - prevIdx) * localMaxHeight;
		
		// 중간 창고 다각형의 넓이
		ret += tallestPillar.height;

		// 오른쪽 창고 다각형의 넓이
		localMaxHeight = pillars[len - 1].height;
		prevIdx = pillars[len - 1].left + 1;
		for (int i = len - 2; i > tallestIdx; i--) {
			Pillar p = pillars[i];

			ret += localMaxHeight * (prevIdx - (p.left + 1));
			prevIdx = p.left + 1;
			
			if (localMaxHeight < p.height) {
				localMaxHeight = p.height;
			}
		}
		ret += (prevIdx - (tallestPillar.left + 1)) * localMaxHeight;
		
		return ret;
	}

}