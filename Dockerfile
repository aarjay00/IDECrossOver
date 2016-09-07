FROM python:2.7
COPY backend/ ./backend/
WORKDIR backend
RUN pip install --upgrade -r requirements.txt
CMD python manage.py runserver 0.0.0.0:8000

