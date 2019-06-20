package com.example.administrator.rolemanage.okhttp.V2;


import com.example.administrator.rolemanage.BuildConfig;
import com.example.administrator.rolemanage.base.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by curry on 2018/5/14.
 */

public class HttpsManager {

    public static void buildSSLSocketFactory(OkHttpClient.Builder builder) {
        try {
            if (BuildConfig.DEBUG) {
                getSSLSocketFactory(builder, MyApplication.instance.getAssets().open("service-atpiao.crt"));
            } else {
                getSSLSocketFactory(builder, MyApplication.instance.getAssets().open("public-cert.crt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 载入证书
     */
    public static void getSSLSocketFactory(OkHttpClient.Builder builder, InputStream... certificates) {
        try {
            //用我们的证书创建一个keystore
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = "server" + Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //创建一个trustmanager，只信任我们创建的keystore
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (sslSocketFactory != null) {
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
