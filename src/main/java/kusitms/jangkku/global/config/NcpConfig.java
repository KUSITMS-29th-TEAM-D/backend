package kusitms.jangkku.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NcpConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.endpoint}")
    private String endPoint;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public BucketCrossOriginConfiguration bucketCrossOriginConfiguration(AmazonS3 amazonS3) {
        configureS3Cors(amazonS3);
        return new BucketCrossOriginConfiguration();
    }

    public void configureS3Cors(AmazonS3 s3) {
        List<CORSRule.AllowedMethods> methodRule = new ArrayList<>();
        methodRule.add(CORSRule.AllowedMethods.PUT);
        methodRule.add(CORSRule.AllowedMethods.GET);
        methodRule.add(CORSRule.AllowedMethods.POST);
        CORSRule rule = new CORSRule().withId("CORSRule")
                .withAllowedMethods(methodRule)
                .withAllowedHeaders(List.of("*"))
                .withAllowedOrigins(List.of("*"))
                .withMaxAgeSeconds(3000);

        List<CORSRule> rules = new ArrayList<>();
        rules.add(rule);

        BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();
        configuration.setRules(rules);

        s3.setBucketCrossOriginConfiguration(bucketName, configuration);
    }
}