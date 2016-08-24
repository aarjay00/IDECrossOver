package log;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.intellij.openapi.application.ApplicationManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        String bucketName="cnu-2016";
        String fileName="rjain/IDE_logs/"+getUserDetail()+"/"+getFileName();
        Properties properties = System.getProperties();
        File fileLog = new File(System.getProperty("user.dir")+"/.IDECrossOverLogs/log");
        Long epoch = System.currentTimeMillis()/1000L;
        File fileUpload = new File(System.getProperty("user.dir")+"/.IDECrossOverLogs/upload"+epoch.toString());
        try {
            Files.copy(fileLog.toPath(), fileUpload.toPath());
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("cant copy");
            return false;
        }
        System.out.println("copied!!");
        try {
            ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
                @Override
                public void run() {
                    AWSCredentials credentials = new BasicAWSCredentials(accesskeyID,secretAccessKey);
                    AmazonS3 s3client = new AmazonS3Client(credentials);
//                    s3client.putObject(new PutObjectRequest(bucketName, fileName, fileUpload));
                    fileUpload.delete();
                }
            });
//            s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
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
        return epoch.toString()+"-log";
    }
}