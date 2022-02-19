// 11916KB, 80ms

package bj11444;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

/*
 * 0 1 행렬을 n제곱 한 것에 0 을 곱한 것의 윗칸이 n번째 피보나치 수
 * 1 1		          1
 * 
 */

public class Main {
	public static final BigInteger MOD = new BigInteger("1000000007");
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger ONE = new BigInteger("1");
	public static final BigInteger TWO = new BigInteger("2");
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 제곱 할 횟수 입력
		String line = br.readLine();
		BigInteger N = new BigInteger(line);
		
		BigInteger answer = fibo(N);
		
		System.out.println(answer.toString());
	}
	
	/** n번째 피보나치 수를 리턴하는 함수 */
	public static BigInteger fibo(BigInteger N) {
		// base case : fibo(0) = 0, fibo(1) = 1
		if (N.equals(ZERO)) return ZERO;
		if (N.equals(ONE)) return ONE;
		
		// 행렬 제곱을 이용해서 n번째 피보나치 수를 구한다
		BigInteger[][] matrix = {{ZERO, ONE}, {ONE, ONE}};
		BigInteger[][] fiboStart = {{ZERO}, {ONE}};
		BigInteger[][] fibos = multiply(power(matrix, N), fiboStart);
		
		return fibos[0][0];
	}
	
	/** 행렬의 n제곱을 분할 정복을 통해 O(log n)으로 수행하는 함수 */
	public static BigInteger[][] power(BigInteger[][] matrix, BigInteger exponent) {
		// base case : 0승, 1승 하는 경우
		if (exponent.equals(ONE)) return matrix;
		if (exponent.equals(TWO)) return multiply(matrix, matrix);
		
		// (n / 2)승 한 것을 저장해 둔다
		BigInteger[][] half = power(matrix, exponent.divide(TWO));
		
		// 짝수이면 이것을 두번 곱하면 되고
		if (exponent.remainder(TWO).equals(ZERO)) {
			return multiply(half, half);
		}
		
		// 홀수이면 이것을 두번 곱한 것에 추가로 원래의 matrix를 곱하면 된다.
		else {
			return multiply(multiply(half, half), matrix);
		}
	}

	
	/** 행렬의 곱셈을 수행하는 함수 */
	public static BigInteger[][] multiply(BigInteger[][] matrixA, BigInteger[][] matrixB) {
		int heightA = matrixA.length;
		int widthA = matrixA[0].length;
		
		int heightB = matrixB.length;
		int widthB = matrixB[0].length;
		
		BigInteger[][] ret = new BigInteger[heightA][widthB];
		for (int i = 0; i < ret.length; i++) {
			Arrays.fill(ret[i], ZERO);
		}
		
		for (int y = 0; y < heightA; y++) {
			for (int x = 0; x < widthB; x++) {
				for (int i = 0; i < widthA; i++) {
					ret[y][x] = ret[y][x].add(matrixA[y][i].multiply(matrixB[i][x]));
				}
				
				ret[y][x] = ret[y][x].remainder(MOD);
			}
		}
		
		return ret;
	}
	
	/** 디버깅을 돕기 위해 행렬의 상태를 스트링으로 리턴해주는 함수 */
	public static String toString(BigInteger[][] matrix) {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				sb.append(matrix[y][x].remainder(MOD)).append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}