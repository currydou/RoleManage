/**
 * Copyright © All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.base.api;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @since Jdk1.6
 * 
 * @Description 加载https请求证书
 */
public class SSLSocketFactoryEx extends SSLSocketFactory {

	private static final String SSL_PASSWORD = "";
	private static final String SSL_NAME = "client.cer";

	private SSLContext mSslContext = SSLContext.getInstance("TLS");

	private SSLSocketFactoryEx(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(truststore);

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
		kmf.init(truststore, SSL_PASSWORD.toCharArray());
		mSslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
                               boolean autoClose) throws IOException, UnknownHostException {
		injectHostname(socket, host);
		return mSslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return mSslContext.getSocketFactory().createSocket();
	}

	private void injectHostname(Socket socket, String host) {
		try {
			Field field = InetAddress.class.getDeclaredField("hostName");
			field.setAccessible(true);
			field.set(socket.getInetAddress(), host);
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public static SSLSocketFactory getSSLSocketFactory(Context context)
			throws IOException, CertificateException, KeyStoreException,
            NoSuchProviderException, NoSuchAlgorithmException,
            KeyManagementException, UnrecoverableKeyException {
		// 把咱的证书库作为信任证书库
		AssetManager am = context.getAssets();
		InputStream ins = am.open(SSL_NAME);
		try {
			// 创建一个证书库，并将证书导入证书库
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate ca = cf.generateCertificate(ins);

			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(null, SSL_PASSWORD.toCharArray());
			keystore.setCertificateEntry("ca", ca);

			return new SSLSocketFactoryEx(keystore);
		} finally {
			ins.close();
		}
	}

}
