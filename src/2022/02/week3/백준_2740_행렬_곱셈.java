// 14184KB, 136ms

package bj2740;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 행렬 A 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] matrixA = new int[N][M];
		for (int y = 0 ; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < M; x++) {
				matrixA[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 행렬 B 입력
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[][] matrixB = new int[M][K];
		for (int y = 0 ; y < M; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < K; x++) {
				matrixB[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int[][] answer = multiply(matrixA, matrixB);
		
		System.out.print(toString(answer));
	}
	
	public static String toString(int[][] matrix) {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				sb.append(matrix[y][x]).append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static int[][] multiply(int[][] matrixA, int[][] matrixB) {
		int heightA = matrixA.length;
		int widthA = matrixA[0].length;
		
		int heightB = matrixB.length;
		int widthB = matrixB[0].length;
		
		int[][] ret = new int[heightA][widthB];
		
		for (int y = 0; y < heightA; y++) {
			for (int x = 0; x < widthB; x++) {
				for (int i = 0; i < widthA; i++) {
					ret[y][x] += matrixA[y][i] * matrixB[i][x];
				}
			}
		}
		
		return ret;
	}
}