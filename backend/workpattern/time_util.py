# coding: utf-8

import datetime
from datetime import  timedelta

def get_week_range_old(week_num):
    end = datetime.date.today()
    start =  end - timedelta(days = end.isocalendar()[2]-1)
    for i in range(week_num):
        end = start - timedelta(days=1)
        start = end - timedelta(days = end.isocalendar()[2]-1)
    end += timedelta(days=1)
    return datetime.datetime.combine(start,datetime.datetime.min.time()),datetime.datetime.combine(end,datetime.datetime.min.time())


def get_week_range(week_num):

    d = datetime.datetime.today()

    start_week = d - timedelta(days=d.weekday())
    start_week = start_week - timedelta(hours = start_week.hour , minutes = start_week.minute , seconds = start_week.second , microseconds = start_week.microsecond)

    end_week = start_week + timedelta(days=7) - timedelta(seconds=1)

    start_week = start_week - timedelta(days = 7*week_num)

    end_week = end_week - timedelta(days=7 * week_num)

    return start_week,end_week

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