// 11556KB, 76ms

package baek2628;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		// 종이와 자를 점선들 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int width = Integer.parseInt(st.nextToken());
		int height = Integer.parseInt(st.nextToken());
		boolean[] isCutX = new boolean[width + 1];
		boolean[] isCutY = new boolean[height + 1];

		int numCuts = Integer.parseInt(br.readLine());
		for (int i = 0; i < numCuts; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int cutType = Integer.parseInt(st.nextToken());
			int cutIdx = Integer.parseInt(st.nextToken());

			if (cutType == 0) {
				isCutY[cutIdx] = true;
			} else {
				isCutX[cutIdx] = true;
			}
		}

		// x가 잘린 곳을 만나기 전까지의 길이를 세고, 잘린 곳을 만나면 지금까지 센 만큼을 최대값과 비교
		int maxWidth = 0;
		int accumWidth = 0;
		for (int i = 1; i < isCutX.length; i++) {
			boolean b = isCutX[i];
			accumWidth++;
			if (maxWidth < accumWidth) {
				maxWidth = accumWidth;
			}

			if (b) {
				accumWidth = 0;
			}
		}

		// y에 대해서도 똑같이 수행
		int maxHeight = 0;
		int accumHeight = 0;
		for (int i = 1; i < isCutY.length; i++) {
			boolean b = isCutY[i];
			accumHeight++;
			if (maxHeight < accumHeight) {
				maxHeight = accumHeight;
			}

			if (b) {
				accumHeight = 0;
			}
		}

		int answer = maxWidth * maxHeight;
		System.out.println(answer);
	}

}
