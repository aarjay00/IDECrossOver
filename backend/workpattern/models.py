from __future__ import unicode_literals

from django.db import models

# Create your models here.


class User(models.Model):

    user_id = models.AutoField(primary_key=True)
    user_name = models.CharField(max_length=256,unique=True)


class Log(models.Model):

    log_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey('User')
    time_begin = models.DateTimeField()
    time_end = models.DateTimeField()
    content_raw = models.TextField()
    content_basic_repr = models.TextField()


class Activity(models.Model):

    activity_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey('User')
    time_begin = models.DateTimeField()
    time_end = models.DateTimeField()
    category  = models.CharField(max_length=256)
    log_id = models.ForeignKey('Log')