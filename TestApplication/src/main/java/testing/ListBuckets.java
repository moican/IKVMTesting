package testing;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.crt.S3CrtHttpConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import static com.amazonaws.services.s3.internal.Constants.MB;

public class ListBuckets {
    public CompletableFuture listBuckets(String accessId, String secret, String host, int port) throws URISyntaxException {
        S3CrtHttpConfiguration httpConf = S3CrtHttpConfiguration.builder()
                .trustAllCertificatesEnabled(true)
                .build();

        AwsCredentialsProvider creds = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessId, secret));

        S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder()
                .credentialsProvider(creds)
                .checksumValidationEnabled(false)
                .targetThroughputInGbps(1.0)
                .region(Region.EU_CENTRAL_2)
                .endpointOverride(new URI("http://" + host + ":" + port))
                .httpConfiguration(httpConf)
                .maxConcurrency(20)
                .minimumPartSizeInBytes(8L * MB)
                .build();

        return s3AsyncClient.listBuckets();
    }
}
