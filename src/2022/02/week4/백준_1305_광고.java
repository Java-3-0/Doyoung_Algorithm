// 26480KB, 204ms

package bj1305;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		int len = Integer.parseInt(br.readLine());
		String line = br.readLine();
		
		// KMP 알고리즘을 이용하여 각 위치까지의 부분 문자열에서 접두사 == 접미사가 되는 최대 길이를 담는 테이블을 계산
		int[] prefixTable = getPrefixTable(line);
		
		// 접두사 == 접미사가 되는 최대 길이만큼이 겹치는 부분
		int repeatLen = prefixTable[prefixTable.length - 1];
		
		// 문자열 전체 길이에서 겹치는 부분만큼을 뺀 길이가 실제로 반복되는 문자열의 길이
		int answer = len - repeatLen;
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** KMP 알고리즘을 이용, 각 부분 문자열에서 접두사 = 접미사가 되는 길이를 배열로 리턴 */
	public static int[] getPrefixTable(String s) {
		char[] arr = s.toCharArray();
		int len = arr.length;
		int[] p = new int[len];
		p[0] = 0;
		
		// 문자열 끝까지 반복
		for (int i = 1, j = 0; i < len; i++) {
			// j가 0이 되거나 문자가 일치할 때까지 j값 갱신
			while (j > 0 && arr[i] != arr[j]) {
				j = p[j - 1];
			}
			
			// 문자가 일치하면 p[i]는 j + 1이 되고
			if (arr[i] == arr[j]) {
				p[i] = ++j;
			} 
			// 문자가 일치하지 않으면 p[i] = 0이 된다.
			else {
				p[i] = 0;
			}
		}
		
		return p;
	}
}