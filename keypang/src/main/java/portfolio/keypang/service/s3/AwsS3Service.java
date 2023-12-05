package portfolio.keypang.service.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.common.properties.AwsProperties;
import portfolio.keypang.exception.ImageFIleRoadFailedException;
import portfolio.keypang.service.s3.utils.FileNameUtils;


@Slf4j
@Service
@EnableConfigurationProperties(AwsProperties.class)
@RequiredArgsConstructor
public class AwsS3Service {

  private final AwsProperties awsProperties;
  private AmazonS3 s3Client;

  @PostConstruct
  private void setS3Client() {
    log.info("S3 connect");
    AWSCredentials credentials = new BasicAWSCredentials(
        awsProperties.getAccessKey(),
        awsProperties.getSecretKey());

    s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(awsProperties.getRegionStatic())
        .build();
  }

  public String uploadItemImage(MultipartFile file) {
   return upload(file, awsProperties.getBucket(), awsProperties.getFolder());
  }

  public String upload(MultipartFile file, String bucket, String folder) {
    log.info("bucket name = {}", bucket);
    String fileName = file.getOriginalFilename();
    log.info("originalFileName = {}", fileName);
    String convertedFileName = FileNameUtils.fileNameConvert(fileName);
    log.info("convertedFileName = {}", convertedFileName);
    String fullFileName = folder + "/" + convertedFileName;
    try {
      String mimeType = new Tika().detect(file.getInputStream());
      ObjectMetadata metadata = new ObjectMetadata();

      FileNameUtils.checkImageType(mimeType);
      metadata.setContentType(mimeType);
      s3Client.putObject(
          new PutObjectRequest(bucket, fullFileName, file.getInputStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new ImageFIleRoadFailedException();
    }
    return s3Client.getUrl(bucket, convertedFileName).toString();
  }

  public void delete(String bucket, String key) {
    s3Client.deleteObject(bucket, key);
  }

}
