// 34488KB, 696ms

package bj18111;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static final long MAX_HEIGHT = 256;
	public static final long MAX_SIZE = 500;
	public static int N;
	public static int M;
	public static long B;
	public static long[][] ground;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());

		ground = new long[N][M];

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < M; x++) {
				ground[y][x] = Long.parseLong(st.nextToken());
			}
		}

		long minTime = Long.MAX_VALUE;
		long resultHeight = -1;
		for (int height = 0; height <= MAX_HEIGHT; height++) {
			long t = timeNeeded(height);
			if (t <= minTime) {
				minTime = t;
				resultHeight = height;
			}
		}

		System.out.println(minTime + " " + resultHeight);
	}

	/** 모든 땅의 높이를 height로 만들기 위해 필요한 시간을 리턴, 만드는 것이 불가능하면 Long.MAX_VALUE를 리턴 */
	public static long timeNeeded(long height) {
		long removes = countRemoves(height);
		long puts = countPuts(height);

		// 놓는 개수가 (가방에 있던 개수 + 가방에 넣은 개수)보다 많아야 가능
		if (puts <= removes + B) {
			return 2 * removes + puts;
		}

		return Long.MAX_VALUE;
	}

	/** 모든 땅의 높이를 height로 만들기 위해 놓아야 하는 블록의 수를 리턴 */
	public static long countPuts(long height) {
		long ret = 0;
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				long here = ground[y][x];
				if (here < height) {
					ret += (height - here);
				}
			}
		}

		return ret;
	}

	/** 모든 땅의 높이를 height로 만들기 위해 제거해야 하는 블록의 수를 리턴 */
	public static long countRemoves(long height) {
		long ret = 0;
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				long here = ground[y][x];
				if (here > height) {
					ret += (here - height);
				}
			}
		}

		return ret;
	}
}
