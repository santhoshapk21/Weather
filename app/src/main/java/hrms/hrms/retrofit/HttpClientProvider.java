package hrms.hrms.retrofit;

import android.app.Activity;

import com.hris365.R;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.tls.HandshakeCertificates;

public class HttpClientProvider {
    public static OkHttpClient getOkHttpClient(Activity context) {
        try {
            // Load your certificate
            InputStream caInput = context.getResources().openRawResource(R.raw.api_skillwill_live);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate ca = (X509Certificate) certificateFactory.generateCertificate(caInput);
            caInput.close();

            // Create a handshake certificates object
            HandshakeCertificates certificates = new HandshakeCertificates.Builder()
                    .addTrustedCertificate(ca)
                    .build();
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add("api.skillwill.live", "sha256/fhkdaXUp6LrIfego/FaYhcMo8dEq34Mb0jGP/WeQO24=")
                    .build();
            // Build the OkHttpClient
            return new OkHttpClient.Builder()
                    .certificatePinner(certificatePinner)
                    .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient getOkHttpClient2(Activity context) {
        try {
            // Load your certificate
            InputStream caInput = context.getResources().openRawResource(R.raw.api2_skillwill_live);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate ca = (X509Certificate) certificateFactory.generateCertificate(caInput);
            caInput.close();

            // Create a handshake certificates object
            HandshakeCertificates certificates = new HandshakeCertificates.Builder()
                    .addTrustedCertificate(ca)
                    .build();
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add("api2.skillwill.live", "sha256/mgGOZnev2cnHFIHaGaUSlqi0JE5NrTdWJQpfekm62OU=")
                    .build();
            // Build the OkHttpClient
            return new OkHttpClient.Builder()
                    .certificatePinner(certificatePinner)
                    .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}