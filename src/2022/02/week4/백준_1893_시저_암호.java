// 173756KB, 2048ms

package bj1893;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 테스트케이스 수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		// 테스트케이스 수만큼 반복
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 입력
			String alphabets = br.readLine();
			String plainText = br.readLine();
			String cypherText = br.readLine();

			// 가능한 시프트 방법들 계산
			List<Integer> possibleShifts = getPossibleShifts(alphabets, plainText, cypherText);
			// 그 방법들의 수 계산
			int solutionCount = possibleShifts.size();

			// 가능한 방법이 0개인지, 1개인지, 여러 개인지에 따라 형식에 맞게 출력 스트링빌더에 저장
			switch (solutionCount) {
			case 0:
				sb.append("no solution").append("\n");
				break;
			case 1:
				sb.append("unique: ");
				for (int shift : possibleShifts) {
					sb.append(shift).append(" ");
				}
				sb.append("\n");
				break;
			default:
				sb.append("ambiguous: ");
				for (int shift : possibleShifts) {
					sb.append(shift).append(" ");
				}
				sb.append("\n");

			} // end switch

		} // end for testCase

		// 출력
		System.out.print(sb.toString());
		
	} // end main

	/** 원문이 한 번만 등장하게 하는 시프트 횟수들을 리스트에 담아서 리턴 */
	public static List<Integer> getPossibleShifts(String alphabets, String plainText, String cypherText) {
		List<Integer> ret = new ArrayList<>();

		// 파라미터로 받은 String들을 char[]로 변환, 길이 계산
		char[] arrA = alphabets.toCharArray();
		char[] arrP = plainText.toCharArray();
		char[] arrC = cypherText.toCharArray();
		int lenP = arrP.length;
		int lenC = arrC.length;
		int lenA = alphabets.length();

		// 알파벳을 시프트해야 하므로 맵 생성
		Map<Character, Integer> alphaToIdx = new HashMap<>();
		Map<Integer, Character> idxToAlpha = new HashMap<>();
		for (int idx = 0; idx < lenA; idx++) {
			char alpha = arrA[idx];

			alphaToIdx.put(alpha, idx);
			idxToAlpha.put(idx, alpha);
		}

		char[] shiftedArrC = new char[lenC];
		for (int shift = 0; shift <= lenA - 1; shift++) {
			// 암호문을 시프트한다.
			for (int i = 0; i < lenC; i++) {
				int shiftedIdx = (lenA + alphaToIdx.get(arrC[i]) - shift) % lenA;
				shiftedArrC[i] = idxToAlpha.get(shiftedIdx);
			}

			// 시프트된 암호문에서 원문을 찾은 위치들을 담은 리스트를 구한다.
			List<Integer> found = searchByKMP(shiftedArrC, arrP);
			
			// 그 리스트의 길이가 1이라면 원문이 한번만 등장한 것이므로 결과값 리스트에 추가
			if (found.size() == 1) {
				ret.add(shift);
			}
		}

		return ret;
	}

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블 생성 */
	public static int[] getPrefixTable(char[] pattern) {
		int len = pattern.length;
		int p[] = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && pattern[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (pattern[i] == pattern[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}

	/** KMP 알고리즘을 이용하여 text에서 pattern을 찾아 찾은 위치들을 리스트 형태로 리턴 */
	public static List<Integer> searchByKMP(char[] text, char[] pattern) {
		List<Integer> ret = new ArrayList<>();

		int[] p = getPrefixTable(pattern);

		int lenT = text.length;
		int lenP = pattern.length;
		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					int foundIdx = i - j;
					ret.add(foundIdx);
					j = p[j];
				} else {
					j++;
				}
			}
		}

		return ret;
	}
}