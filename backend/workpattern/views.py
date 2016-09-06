from django.shortcuts import render

# Create your views here.


from django.http import HttpResponse
from rest_framework import status
from rest_framework import viewsets
from rest_framework import mixins
from models import *
from serializers import *
from django.core import serializers
from django.http import JsonResponse

from rest_framework.decorators import  list_route

from rest_framework.response import Response
import json

import time_util

import util

from datetime import timedelta
import datetime

class ActivityViewSet(mixins.ListModelMixin,
                      mixins.RetrieveModelMixin,
                      viewsets.GenericViewSet):

    serializer_class =  ActivitySerializer
    queryset = Activity.objects.all()


    @list_route(methods=['GET'])
    def user_day_activity(self,request):

        print "here"
        print request.GET
        if "user_id" not in request.GET or 'date' not in request.GET:
            return Response(status = status.HTTP_400_BAD_REQUEST)

        user_id = request.GET['user_id']
        date  = request.GET['date']

        user  = User.objects.get(user_id=user_id)

        print "aaa",date


        start_date = time_util.get_date_object(date)
        end_date = start_date + timedelta(days=1) - timedelta(seconds=1)
        activity_list = Activity.objects.filter(user_id_id=user, time_begin__gte=start_date, time_end__lte=end_date)

        data = json.loads(serializers.serialize('json', activity_list))

        data = [{'category':d['fields']['category'],'time_begin':d['fields']['time_begin'],'time_end':d['fields']['time_end']} for d in data]

        for d in data:
            s  = datetime.datetime.strptime(d['time_begin'],'%Y-%m-%dT%H:%M:%S') - start_date
            e = datetime.datetime.strptime(d['time_end'], '%Y-%m-%dT%H:%M:%S') - start_date
            d['time_begin']=s.total_seconds()*1.0/3600
            d['time_end']=e.total_seconds()*1.0/3600

        data = sorted(data , key = lambda x : x['time_begin'])

        return JsonResponse(data, safe=False)




    @list_route(methods=['GET'])
    def user_week_activity(self,request):

        if "user_id" not in request.GET or 'date' not in request.GET:
            return Response(status = status.HTTP_400_BAD_REQUEST)

        user_id = request.GET['user_id']
        date  = time_util.get_date_object(request.GET['date'])

        print "aaa",date

        user = User.objects.get(user_id = user_id)
        week_day = date.weekday()

        print "request at",week_day

        data = {}
        for day_num in range(7):
            day= date + timedelta(days=day_num - date.weekday())
            start_date, end_date = time_util.get_day_range_2(day)
            print start_date, end_date
            activity_list = Activity.objects.filter(user_id_id=user, time_begin__gte=start_date, time_end__lte=end_date)
            data['day' + str(day.weekday())] = util.get_percentage(activity_list)


        return JsonResponse(data, safe=False)


    @list_route(methods=['GET'])
    def user_month_activity(self,request):

        if "user_id" not in request.GET:
            return Response(status = status.HTTP_400_BAD_REQUEST)

        month_data={}
        for week_num in range(0,4):
            user_id = request.GET['user_id']

            user = User.objects.get(user_id = user_id)
            start_date,end_date = time_util.get_week_range(week_num)
            print start_date,end_date

            activity_list = Activity.objects.filter(user_id_id = user, time_begin__gte=start_date, time_end__lte=end_date)

            data = util.get_percentage(activity_list)
            month_data['week'+str(week_num+1)] = data
        return JsonResponse(month_data,safe=False)

    @list_route(methods=['GET'])
    def user_day_activity_old(self, request):

        if "user_id" not in request.GET or 'day_num' not in request.GET:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        user_id = request.GET['user_id']
        day_num = int(request.GET['day_num'])

        user = User.objects.get(user_id=user_id)
        start_date, end_date = time_util.get_week_range(day_num)

        activity_list = Activity.objects.filter(user_id_id=user, time_begin__gte=start_date, time_end__lte=end_date)

        data = json.loads(serializers.serialize('json', activity_list))

        return JsonResponse(data, safe=False)

    @list_route(methods=['GET'])
    def user_current_week_activity(self, request):

        if "user_id" not in request.GET:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        user_id = request.GET['user_id']

        user = User.objects.get(user_id = user_id)
        day_num = time_util.today()
        day_num = day_num.isocalendar()[2]


        data={}
        for day in reversed(range(day_num)):

            start_date, end_date = time_util.get_day_range(day)
            print start_date,end_date
            activity_list = Activity.objects.filter(user_id_id=user, time_begin__gte=start_date, time_end__lte=end_date)
            data['day' + str(day+1)] = util.get_percentage(activity_list)

        return JsonResponse(data,safe=False)




class UserViewSet(mixins.ListModelMixin,
                      viewsets.GenericViewSet):
    serializer_class = UserSerializer
    queryset = User.objects.all()