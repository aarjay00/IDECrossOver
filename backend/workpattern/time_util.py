# coding: utf-8

import datetime
from datetime import  timedelta

def get_week_range(week_num):
    end = datetime.date.today()
    start =  end - timedelta(days = end.isocalendar()[2]-1)
    for i in range(week_num):
        end = start - timedelta(days=1)
        start = end - timedelta(days = end.isocalendar()[2]-1)
    end += timedelta(days=1)
    return datetime.datetime.combine(start,datetime.datetime.min.time()),datetime.datetime.combine(end,datetime.datetime.min.time())

def get_day_range(day_num):
    end  = datetime.date.today()+timedelta(days=1)
    start  = end - timedelta(days=1)

    start -= timedelta(days = day_num)
    end -= timedelta(days=day_num)

    return datetime.datetime.combine(start,datetime.datetime.min.time()),datetime.datetime.combine(end,datetime.datetime.min.time())

def today():
    return datetime.date.today()

def get_date_object(date_string):

    date_string = date_string.split('-')

    date  = datetime.datetime(year=int(date_string[0]),month=int(date_string[1])+1,day=int(date_string[2]))

    return date

def get_day_range_2(day):

    start  =  day
    end = day + timedelta(days=1) - timedelta(seconds=1)

    return start,end