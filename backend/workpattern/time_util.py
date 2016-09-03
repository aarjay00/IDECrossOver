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