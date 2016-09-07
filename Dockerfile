FROM python:2.7
RUN git clone -b django_backend https://github.com/aarjay00/IDECrossOver.git
WORKDIR IDECrossOver/backend
RUN pip install -r requirements.txt
CMD git pull && python manage.py runserver 0.0.0.0:8000
