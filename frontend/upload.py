from os import walk,path
import boto3

def get_all_files(file_path):
    files=[]
    for(dirpath,dirnames,filenames) in walk(file_path):
        files.extend([path.join(dirpath,filename) for filename in filenames])
    return files


file_list = get_all_files('frontend')

file_list = filter(lambda x : ".idea" not in x , file_list)

bucket   = s3.Bucket('cnu-2k16')

for file in file_list:
    bucket.upload_file(file,'aarjay/'+file)