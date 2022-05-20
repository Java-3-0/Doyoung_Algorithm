// 108556KB, 2296ms

#include <iostream>
#include <cstring>
#include <vector>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const int MAX_N = 1000, MAX_K = 1000, MAX_PRICE = 1000;
const int MAX_SIZE = MAX_K * MAX_PRICE + 1;

vector<ll> power(const vector<ll>& fll, int exponent);
void fft(vector<cpx> &f, bool isReverse);
vector<ll> square(const vector<ll>& f);
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll);

int main(void)
{
    // N, K 입력
    int N, K;
    scanf("%d", &N);
    scanf("%d", &K);

    // 보석 가치마다 항의 차수를 1을 준 다항식을 만든다.
    vector<ll> f(MAX_PRICE + 1, 0);
    int val;
    for (int i = 0; i < N; i++) {
        scanf("%d", &val);
        f[val]++;
    }

    // 다항식의 K제곱 계산
    vector<ll> product = power(f, K);

    // 계수가 양수인 항의 차수들을 출력
    for (int i = 0; i < product.size(); i++) {
        if (product[i] > 0) {
            printf("%d ", i);
        }
    }

    return 0;
}

/** 다항식 fll의 exponent 승을 구해서 리턴한다 */
vector<ll> power(const vector<ll>& fll, int exponent) {
    if (exponent == 1) {
        return fll;
    }

    // 분할 정복으로 exp / 2승을 먼저 구한다
    vector<ll> half = power(fll, exponent / 2);

    // 구한 값을 제곱한다
    vector<ll> ret = square(half);

    // 1000원짜리를 1000개 훔친 것보다 더 고차항은 의미가 없으므로 리사이징 해준다
    if (ret.size() > MAX_SIZE) {
        ret.resize(MAX_SIZE);
    }

    // 홀수승인 경우 원본 fll를 한 번 더 곱해준다
    if (exponent % 2 != 0) {
        ret = multiply(ret, fll);

        // 1000원짜리를 1000개 훔친 것보다 더 고차항은 의미가 없으므로 리사이징 해준다
        if (ret.size() > MAX_SIZE) {
            ret.resize(MAX_SIZE);
        }
    }

    return ret;
}

/** 다항식 f를 고속 퓨리에 변환한 결과를 f에 저장 */
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

/** 다항식 fll의 제곱을 계산해서 리턴 */
vector<ll> square(const vector<ll>& fll) {
    vector<cpx> f(fll.begin(), fll.end());

    int size = f.size();

    // 2의 제곱수 중 f * f를 담을 수 있는 최소 크기를 구한다
    int n = 1;
    while (n < size + size) {
        n <<= 1;
    }

    // 그 크기로 배열 길이를 늘린다
    f.resize(n);

    // 다항식을 FFT 변환한다
    fft(f, false);

    // FFT한 값들끼리 곱한다
    for (int i = 0; i < n; i++) {
        f[i] *= f[i];
    }

    // 얻은 결과를 FFT 역변환한다
    fft(f, true);

    // 가능한지 불가능한지만 필요하니까 0, 1로만 나타내서 리턴한다
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = round(f[i].real()) > 0 ? 1 : 0;
    }
    return ret;
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

    // 가능한지 불가능한지만 필요하니까 0, 1로만 나타내서 리턴한다
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = round(f[i].real()) > 0 ? 1 : 0;
    }
    return ret;
}
