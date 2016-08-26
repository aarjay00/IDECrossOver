package log;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.intellij.openapi.application.ApplicationManager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Properties;


/**
 * Created by aarjay on 23/08/16.
 */
public class S3Client {

    private static String accesskeyID = "AKIAIC3W6WEJ7IOFUA5A";
    private static String secretAccessKey = "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";

    private static S3Client s3Client = null;

    public static S3Client getInstance(){
        if(s3Client == null){
            s3Client = new S3Client();
        }
        return s3Client;
    }

    public Boolean uploadLogToS3(){

        String bucketName="cnu-idelogs";
        String fileName=getUserDetail()+"/"+getFileName();
//        Properties properties = System.getProperties();
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
                    s3client.putObject(new PutObjectRequest(bucketName, fileName, fileUpload));
                    if(!fileUpload.delete()){
                        //Handle upload file not being deleted
                    }
                    IDELogger.getInstance().deleteLogs();
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
        String macAddress = getMacAddress();
        String user="X"+userName+macAddress+"X";
        user=user.replace('/','-');
        user=user.replace('\\','-');
        return user;
    }
    private String getFileName() {
        Long epoch = System.currentTimeMillis() / 1000L;
        return epoch.toString()+"-log";
    }
    private String getMacAddress(){
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return "";
    }
}