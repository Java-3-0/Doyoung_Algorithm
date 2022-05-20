// 8616KB, 48ms

#include <iostream>
#include <string>
#include <cstring>
#include <vector>
#include <map>
#include <stack>
#include <queue>
#include <algorithm>
#include <climits>
#include <cfloat>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const int MAX_N = 50000;
const int MAX_POS= 60000; // 위치는 -30000 ~ 30000 대신 30000을 더해서 0 ~ 60000으로 사용한다

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll);

int main(void)
{
    // 각 줄을 나타낼 세 벡터 선언
    vector<ll> f(MAX_POS + 1);
    vector<ll> g(MAX_POS + 1);
    vector<ll> h(MAX_POS + 1);

    // f 벡터 입력
    int n, x;
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        f[x + 30000]++;
    }

    // g 벡터 입력
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        g[x + 30000]++;
    }

    // h 벡터 입력
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &x);
        h[x + 30000]++;
    }

    // b - a = c - b를 정리하면 a + c = 2 * b 이다
    // 다항식 f와 h를 곱해서 계수가 양수인 항들이 가능한 a + c의 값들이다
    // 다항식 f와 h의 곱을 구한다
    vector<ll> product = multiply(f, h);

    int answer = 0;
    for (int b = 0; b <= MAX_POS; b++) {
        // 가능한 b값들에 대해서 product[2 * b]의 합을 구한다
        if (g[b] > 0) {
            if (2 * b < product.size() && product[2 * b] > 0) {
                answer += (g[b] * product[2 * b]);
            }
        }
    }

    // 정답 출력
    printf("%d\n", answer);
}

/** 다항식 f를 고속 푸리에 변환한 결과를 f에 저장 */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // 스왑하기
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // 재귀 대신 for문으로 풀어서 메모리 아끼기
    for (int len = 2; len <= n; len <<= 1) {
        int halfLen = (len >> 1);

        double angle = 2 * PI / len;
        if (isReverse) {
            angle *= -1;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1, 0);
            for (int j = 0; j < halfLen; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + halfLen] * w;
                f[i + j] = u + v;
                f[i + j + halfLen] = u - v;
                w *= wLen;
            }
        }
    }

    // 역변환이면 n으로 나눠주기
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** 다항식 fll와 gll의 곱을 계산해서 리턴 */
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = f.size();
    int size2 = g.size();

    // 2의 제곱수 중 f * g를 담을 수 있는 최소 크기를 구한다
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // 그 크기로 배열 길이를 늘린다
    f.resize(n);
    g.resize(n);

    // 각각의 다항식을 FFT 변환한다
    fft(f, false);
    fft(g, false);

    // FFT한 값들끼리 곱한다
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
    }

    // 얻은 결과를 FFT 역변환한다
    fft(f, true);

    // 정수로 반올림해서 리턴
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) round(f[i].real());
    }
    return ret;
}
