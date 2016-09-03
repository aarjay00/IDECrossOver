import boto3
import os

def downloadLogs(dir_path):
    s3 = boto3.resource('s3')
    # for bucket in s3.buckets.all():
    #     print(bucket.name)

    bucket = s3.Bucket('cnu-idelogs')
    for object in bucket.objects.all():
        print object,object.key
        key=object.key
        file_path=dir_path+key
        print file_path
        if not os.path.exists(os.path.dirname(file_path)):
            os.makedirs(os.path.dirname(file_path))
            print "made folder"
        if not os.path.exists(file_path):
            print "downloading file"
            with open(file_path,'w') as file_fd:
                # bucket.download_file(key,"Logs/"+key)
                bucket.download_fileobj(key,file_fd)