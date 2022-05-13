// 11888KB, 88ms

package bj10827;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		BigDecimal a = new BigDecimal(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		
		BigDecimal answer = pow(a, b);
		
		System.out.println(answer.toPlainString());
		
	} // end main
	
	public static BigDecimal pow(BigDecimal base, int power) {
		if (power == 0) {
			return new BigDecimal(1.0);
		}
		
		BigDecimal half = pow(base, power / 2);
		
		BigDecimal ret = half.multiply(half);
		if (power % 2 != 0) {
			ret = ret.multiply(base);
		}
		
		return ret;
	}
}