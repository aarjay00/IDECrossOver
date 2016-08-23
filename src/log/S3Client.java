package log;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.Properties;


/**
 * Created by aarjay on 23/08/16.
 */
public class S3Client {

    private static String accesskeyID="AKIAJPLXTB7ME7WDDYBA";
    private static String secretAccessKey="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";

    public S3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accesskeyID,secretAccessKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);

        String bucketName="cnu-2016";
        String fileName="rjain/IDE_logs/"+getUserDetail()+"/log";
        Properties properties = System.getProperties();
        File file = new File(System.getProperty("user.dir")+"/.IDECrossOverLogs/log");
        s3client.putObject(new PutObjectRequest(bucketName,fileName,file));
        for(Bucket bucket : s3client.listBuckets())
        {
            System.out.println(bucket.getName());
        }
    }
    private String getUserDetail(){
        String userName= System.getProperty("user.name");
        String userHome = System.getProperty("user.home");
        String user="X"+userName+userHome+"X";
        user=user.replace('/','-');
        user=user.replace('\\','-');
        return user;
    }
}
