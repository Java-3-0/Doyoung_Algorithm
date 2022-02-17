// 42556KB, 288ms

package bj1914;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
	public static final BigInteger TWO = new BigInteger("2");
	public static final BigInteger ONE = new BigInteger("1");
	
	// 초기화
	public static StringBuilder sb = new StringBuilder(); // 하노이 이동 과정

	// 메인
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		// 하노이 수행 횟수
		System.out.println(hanoiCount(N).toString());

		// 하노이 수행 과정
		if (N <= 20) {
			hanoiProcess(N, 1, 3);
			System.out.println(sb.toString());
		}
	}

	public static BigInteger hanoiCount(int N) {
		if (N == 1)
			return ONE;

		return (hanoiCount(N - 1).multiply(TWO)).add(ONE);
	}

	// 하노이 수행하면서 이동 과정을 sb에 저장
	public static void hanoiProcess(int N, int start, int finish) {
		if (N == 1) {
			sb.append(start).append(" ").append(finish).append("\n");
		} else {
			int other = otherTower(start, finish);
			hanoiProcess(N - 1, start, other);
			hanoiProcess(1, start, finish);
			hanoiProcess(N - 1, other, finish);
		}
	}

	// tower1, tower2를 제외한 나머지 한 타워의 번호 리턴
	public static int otherTower(int tower1, int tower2) {
		switch (tower1 + tower2) {
		case 3:
			return 3;
		case 4:
			return 2;
		case 5:
			return 1;
		default:
			return -1;
		}
	}
}