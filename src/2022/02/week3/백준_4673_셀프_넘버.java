// 11328KB, 76ms

package bj4673;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		
		boolean[] isSelfNumber = new boolean[10000 + 100];
		Arrays.fill(isSelfNumber, true);
		
		for (int num = 1; num <= 10000; num++) {		
			isSelfNumber[d(num)] = false;
		}
		
		for (int num = 1; num <= 10000; num++) {
			if (isSelfNumber[num]) {
				sb.append(num).append("\n");
			}
		}
		
		System.out.print(sb.toString());
	}

	public static int d(int n) {
		return n + digitSum(n);
	}
	
	public static int digitSum(int n) {
		int ret = 0;
		while (n > 0) {
			ret += n % 10;
			n /= 10;
		}
		
		return ret;
	}
}
