from os import walk
from os import path


def get_all_files(file_path):
    files=[]
    for(dirpath,dirnames,filenames) in walk(file_path):
        files.extend([path.join(file_path,filename) for filename in filenames])
    return files

def get_all_dir(dir_path):
    dirs=[]
    for(dir_path,dir_names,file_name) in walk(dir_path):
        dirs.extend([path.join(dir_path,dir_name) for dir_name in dir_names])
    return dirs