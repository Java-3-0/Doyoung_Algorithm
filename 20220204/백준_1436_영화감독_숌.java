// 영화감독 숌 문제
package baek1436;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public final static int MAX_N = 10000;
	/** 종말의 수를 오름차순으로 담을 Set */
	public static Set<Integer> doomNumbers = new TreeSet<Integer>();
	
	public static void main(String[] args) {
		// 종말의 수 생성
		generateDoomNumbers();
		Integer[] doomNumbersArray = doomNumbers.toArray(new Integer[doomNumbers.size()]);
		
		// 입력
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();		
		sc.close();

		// n번째 종말의 수 출력
		int answer = doomNumbersArray[N-1];
		System.out.println(answer);
	}
	
	/** 최대 7자리 수까지 가능한 모든 종말의 수를 만들어서 doomNumbers에 넣는다 */
	public static void generateDoomNumbers() {
		for (int i = 0; i < 9999; i++)
		{
			int doom = i * 1000 + 666;
			doomNumbers.add(doom);
			for (int shift = 1; shift <= 4; shift++)
			{
				int firstDigit = doom / 1000000;
				int otherDigits = doom % 1000000;
				doom = otherDigits * 10 + firstDigit;
				doomNumbers.add(doom);
			}
		}
	}
}
