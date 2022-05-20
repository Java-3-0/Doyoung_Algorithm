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
    // N, K �Է�
    int N, K;
    scanf("%d", &N);
    scanf("%d", &K);

    // ���� ��ġ���� ���� ������ 1�� �� ���׽��� �����.
    vector<ll> f(MAX_PRICE + 1, 0);
    int val;
    for (int i = 0; i < N; i++) {
        scanf("%d", &val);
        f[val]++;
    }

    // ���׽��� K���� ���
    vector<ll> product = power(f, K);

    // ����� ����� ���� �������� ���
    for (int i = 0; i < product.size(); i++) {
        if (product[i] > 0) {
            printf("%d ", i);
        }
    }

    return 0;
}

/** ���׽� fll�� exponent ���� ���ؼ� �����Ѵ� */
vector<ll> power(const vector<ll>& fll, int exponent) {
    if (exponent == 1) {
        return fll;
    }

    // ���� �������� exp / 2���� ���� ���Ѵ�
    vector<ll> half = power(fll, exponent / 2);

    // ���� ���� �����Ѵ�
    vector<ll> ret = square(half);

    // 1000��¥���� 1000�� ��ģ �ͺ��� �� �������� �ǹ̰� �����Ƿ� ������¡ ���ش�
    if (ret.size() > MAX_SIZE) {
        ret.resize(MAX_SIZE);
    }

    // Ȧ������ ��� ���� fll�� �� �� �� �����ش�
    if (exponent % 2 != 0) {
        ret = multiply(ret, fll);

        // 1000��¥���� 1000�� ��ģ �ͺ��� �� �������� �ǹ̰� �����Ƿ� ������¡ ���ش�
        if (ret.size() > MAX_SIZE) {
            ret.resize(MAX_SIZE);
        }
    }

    return ret;
}

/** ���׽� f�� ��� ǻ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
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

    // ����ȯ�̸� n���� �����ֱ�
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** ���׽� fll�� ������ ����ؼ� ���� */
vector<ll> square(const vector<ll>& fll) {
    vector<cpx> f(fll.begin(), fll.end());

    int size = f.size();

    // 2�� ������ �� f * f�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size + size) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);

    // ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= f[i];
    }

    // ���� ����� FFT ����ȯ�Ѵ�
    fft(f, true);

    // �������� �Ұ��������� �ʿ��ϴϱ� 0, 1�θ� ��Ÿ���� �����Ѵ�
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = round(f[i].real()) > 0 ? 1 : 0;
    }
    return ret;
}

/** ���׽� fll�� gll�� ���� ����ؼ� ���� */
vector<ll> multiply(const vector<ll>& fll, const vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = f.size();
    int size2 = g.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);
    g.resize(n);

    // ������ ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);
    fft(g, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
    }

    // ���� ����� FFT ����ȯ�Ѵ�
    fft(f, true);

    // �������� �Ұ��������� �ʿ��ϴϱ� 0, 1�θ� ��Ÿ���� �����Ѵ�
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = round(f[i].real()) > 0 ? 1 : 0;
    }
    return ret;
}
