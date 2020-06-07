package DataApp.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class sslutil {

    public static SSLContext createSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,
                    new X509TrustManager[] { new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException {}
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException {}
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
                    } }, new SecureRandom());
            // HttpsURLConnection
            // .setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    public static HostnameVerifier createHostNameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {return true;}
        };
    }

}
