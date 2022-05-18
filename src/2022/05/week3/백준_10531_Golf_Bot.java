// 421228KB, 1232ms

package bj10531;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	/** 복소수 클래스 */
	static class Complex {
		double r;
		double i;

		public Complex(double r, double i) {
			super();
			this.r = r;
			this.i = i;
		}

		public static Complex add(Complex c1, Complex c2) {
			return new Complex(c1.r + c2.r, c1.i + c2.i);
		}

		public static Complex subtract(Complex c1, Complex c2) {
			return new Complex(c1.r - c2.r, c1.i - c2.i);
		}

		public static Complex multiply(Complex c1, Complex c2) {
			return new Complex(c1.r * c2.r - c1.i * c2.i, c1.r * c2.i + c1.i * c2.r);
		}

		public static Complex divide(Complex c1, Complex c2) {
			return new Complex((c1.r * c2.r + c1.i * c2.i) / (c2.r * c2.r + c2.i * c2.i),
					(c1.i * c2.r - c1.r * c2.i) / (c2.r * c2.r + c2.i * c2.i));
		}

		public Complex add(Complex other) {
			return add(this, other);
		}

		public Complex subtract(Complex other) {
			return subtract(this, other);
		}

		public Complex multiply(Complex other) {
			return multiply(this, other);
		}

		public Complex divide(Complex other) {
			return divide(this, other);
		}

		@Override
		public String toString() {
			return "(" + r + ") + (" + i + ")i";
		}

	}

	static final int MAX_N = 200000;

	/** 다항식 f를 고속 퓨리에 변환한 결과를 리턴 */
	public static Complex[] fft(Complex[] f, boolean isReverse) {
		int n = f.length;

        // 스왑하기
		for (int i = 1, j = 0; i < n; i++) {
			int bit = n / 2;
			while (j >= bit) {
				j -= bit;
				bit /= 2;
			}
			j += bit;
			if (i < j) {
				swap(f, i, j);
			}
		}

        // 재귀 대신 for문으로 풀어서 메모리 아끼기
		for (int len = 2; len <= n; len <<= 1) {
			double angle = 2.0 * Math.PI / len;
			if (isReverse) {
				angle = -angle;
			}

			Complex wLen = new Complex(Math.cos(angle), Math.sin(angle));
			for (int i = 0; i < n; i += len) {
				Complex w = new Complex(1.0, 0.0);
				for (int j = 0; j < len / 2; j++) {
					Complex u = f[i + j];
					Complex v = f[i + j + len / 2].multiply(w);
					f[i + j] = u.add(v);
					f[i + j + len / 2] = u.subtract(v);
					w = w.multiply(wLen);
				}
			}
		}
		
        // 역변환이면 n으로 나눠주기
		if (isReverse) {
			for (int i = 0; i < n; i++) {
				f[i] = f[i].divide(new Complex((double)n, 0.0));
			}
		}
		
		return f;
	}

    /** arr[a]와 arr[b]를 스왑한다 */
	private static void swap(Complex[] arr, int a, int b) {
		Complex tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	/** 두 다항식을 곱한 결과를 리턴 */
	public static Complex[] multiply(Complex[] f, Complex[] g) {
		int len1 = f.length;
		int len2 = g.length;

		// 2의 제곱수 중 f * g를 담을 수 있는 최소 크기를 구한다
		int n = 1;
		while (n < len1 && n < len2) {
			n *= 2;
		}
		n *= 2;

		// 두 배열 길이를 확장한다
		Complex[] resizedF = new Complex[n];
		for (int i = 0; i < n; i++) {
			if (i < len1) {
				resizedF[i] = f[i];
			} else {
				resizedF[i] = new Complex(0.0, 0.0);
			}
		}
		Complex[] resizedG = new Complex[n];
		for (int i = 0; i < n; i++) {
			if (i < len2) {
				resizedG[i] = g[i];
			} else {
				resizedG[i] = new Complex(0.0, 0.0);
			}
		}

		// 각각의 다항식을 FFT 변환한다
		Complex[] fftF = fft(resizedF, false);
		Complex[] fftG = fft(resizedG, false);

		// FFT한 값들끼리 곱한다
		Complex[] h = new Complex[n];
		for (int i = 0; i < n; i++) {
			h[i] = fftF[i].multiply(fftG[i]);
		}

		// 얻은 결과를 FFT 역변환한다
		Complex[] ret = fft(h, true);
		
		for (int i = 0; i < n; ++i) {
			ret[i].r = Math.round(ret[i].r);
			ret[i].i = Math.round(ret[i].i);
		}

		return ret;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 다항식 계수 입력
		Complex[] f = new Complex[MAX_N + 1];
		Complex[] g = new Complex[MAX_N + 1];
		for (int i = 0; i <= MAX_N; i++) {
			f[i] = new Complex(0.0, 0.0);
			g[i] = new Complex(0.0, 0.0);
		}
		f[0].r = 1.0;
		g[0].r = 1.0;
		for (int i = 0; i < N; i++) {
			int num = Integer.parseInt(br.readLine());
			f[num].r = 1.0;
			g[num].r = 1.0;
		}

		// 다항식을 곱한다
		Complex[] res = multiply(f, g);
		
		// M 입력
		int M = Integer.parseInt(br.readLine());

		// 가능한 구멍 개수를 센다 (제곱한 다항식의 계수가 0이 아니면 가능)
		int answer = 0;
		for (int i = 0; i < M; i++) {
			int hole = Integer.parseInt(br.readLine());

			if (res[hole].r > 0.0) {
				answer++;
			}
		}
		
		// 결과 출력
		System.out.println(answer);

	} // end main

}