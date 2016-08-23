package log;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.util.Properties;


/**
 * Created by aarjay on 23/08/16.
 */
public class S3Client {

    private static String accesskeyID = "AKIAJPLXTB7ME7WDDYBA";
    private static String secretAccessKey = "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";

    private static S3Client s3Client = null;

    public static S3Client getInstance(){
        if(s3Client == null){
            s3Client = new S3Client();
        }
        return s3Client;
    }

    public Boolean uploadLogToS3(){
        AWSCredentials credentials = new BasicAWSCredentials(accesskeyID,secretAccessKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String bucketName="cnu-2016";
        String fileName="rjain/IDE_logs/"+getUserDetail()+"/"+getFileName();
        Properties properties = System.getProperties();
        File file = new File(System.getProperty("user.dir")+"/.IDECrossOverLogs/log");
        try {
            s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    private String getUserDetail(){
        String userName= System.getProperty("user.name");
        String userHome = System.getProperty("user.home");
        String user="X"+userName+userHome+"X";
        user=user.replace('/','-');
        user=user.replace('\\','-');
        return user;
    }
    private String getFileName() {
        Long epoch = System.currentTimeMillis() / 1000L;
        return epoch.toString()+"-Log";
    }
}