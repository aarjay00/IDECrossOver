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


class ActivityViewSet(mixins.ListModelMixin,
                      mixins.RetrieveModelMixin,
                      viewsets.GenericViewSet):

    serializer_class =  ActivitySerializer
    queryset = Activity.objects.all()

    @list_route(methods=['GET'])
    def user_week_activity(self,request):

        if "user_id" not in request.GET or 'week_num' not in request.GET:
            return Response(status = status.HTTP_400_BAD_REQUEST)

        user_id = request.GET['user_id']
        week_num  = int(request.GET['week_num'])

        user = User.objects.get(user_id = user_id)
        start_date,end_date = time_util.get_week_range(week_num)

        activity_list = Activity.objects.filter(user_id_id = user, time_begin__gte=start_date, time_end__lte=end_date)


        data = json.loads(serializers.serialize('json',activity_list))

        return JsonResponse(data,safe=False)

    @list_route(methods=['GET'])
    def user_day_activity(self, request):

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
            data['day' + str(day)] = json.loads(serializers.serialize('json', activity_list))

        return JsonResponse(data,safe=False)