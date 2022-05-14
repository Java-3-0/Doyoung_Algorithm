// 12876KB, 88ms

package bj13075;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final long MOD = (long) 1e9;
	static final Matrix IDENTITY = new Matrix(new long[][] { { 1L, 0L }, { 0L, 1L } });

	static class Matrix {
		long[][] arr;

		public Matrix(long[][] arr) {
			super();
			this.arr = arr;
		}

		/** 이 행렬의 exponent 제곱을 계산한다 */
		public Matrix pow(long exponent) {
			// base case : 0승
			if (exponent == 0L)
				return IDENTITY;

			// (exponent / 2)승 한 것을 저장해 둔다
			Matrix half = pow(exponent / 2L);

			// 짝수이면 이것을 두번 곱하면 되고
			if (exponent % 2L == 0L) {
				return half.multiply(half);
			}

			// 홀수이면 이것을 두번 곱한 것에 추가로 원래의 matrix를 곱하면 된다.
			else {
				return this.multiply(half).multiply(half);
			}
		}

		/** 이 행렬과 m 행렬을 곱한 행렬을 리턴한다 */
		public Matrix multiply(Matrix m) {
			int height1 = this.getHeight();
			int width1 = this.getWidth();

			int height2 = m.getHeight();
			if (width1 != height2) {
				return null;
			}
			int width2 = m.getWidth();

			long[][] ret = new long[height1][width2];
			for (int i = 0; i < ret.length; i++) {
				Arrays.fill(ret[i], 0L);
			}

			for (int y = 0; y < height1; y++) {
				for (int x = 0; x < width2; x++) {
					for (int i = 0; i < width1; i++) {
						ret[y][x] += (this.get(y, i) * m.get(i, x));
						ret[y][x] %= MOD;
					}
				}
			}

			return new Matrix(ret);
		}

		/** 행렬에서 (r, c)위치의 값을 리턴한다 */
		public long get(int r, int c) {
			return this.arr[r][c];
		}

		/** 행렬의 세로 길이를 리턴한다 */
		public int getHeight() {
			return this.arr.length;
		}

		/** 행렬의 가로 길이를 리턴한다 */
		public int getWidth() {
			return this.arr[0].length;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 제곱 할 횟수 입력
			long N = Long.parseLong(br.readLine());

			// N번째 피보나치 수 계산
			long answer = fibo(N);

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	}

	/** n번째 피보나치 수를 리턴하는 함수 */
	public static long fibo(long n) {
		// 행렬 제곱을 이용해서 n번째 피보나치 수를 구한다
		Matrix start = new Matrix(new long[][] { { 1L, 1L }, { 1L, 0L } });
		Matrix result = start.pow(n);

		return result.get(0, 1);
	}

}