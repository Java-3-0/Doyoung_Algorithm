// 11560KB, 76ms

package bj10830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
	public static final long MOD = 1000L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 행렬 크기, 제곱 할 횟수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		long B = Long.parseLong(st.nextToken());
		
		// 행렬 입력
		long[][] matrix = new long[N][N];
		for (int y = 0 ; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				matrix[y][x] = Long.parseLong(st.nextToken());
			}
		}
		
		long[][] answer = power(matrix, B);
		
		System.out.print(toString(answer));
	}
	
	public static long[][] power(long[][] matrix, long exponent) {
		if (exponent == 1L) return matrix;
		
		if (exponent == 2L) return multiply(matrix, matrix);
		
		long[][] half = power(matrix, exponent / 2L);
				
		if (exponent % 2L == 0L) {
			return multiply(half, half);
		}
		
		else {
			return multiply(multiply(half, half), matrix);
		}
	}
	
	public static String toString(long[][] matrix) {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				sb.append(matrix[y][x] % MOD).append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static long[][] multiply(long[][] matrixA, long[][] matrixB) {
		int heightA = matrixA.length;
		int widthA = matrixA[0].length;
		
		int heightB = matrixB.length;
		int widthB = matrixB[0].length;
		
		long[][] ret = new long[heightA][widthB];
		
		for (int y = 0; y < heightA; y++) {
			for (int x = 0; x < widthB; x++) {
				for (int i = 0; i < widthA; i++) {
					ret[y][x] += matrixA[y][i] * matrixB[i][x];
				}
				
				ret[y][x] %= MOD;
			}
		}
		
		return ret;
	}
}