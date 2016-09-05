
from rest_framework import serializers

from models import Activity
from models import  User

class ActivitySerializer(serializers.ModelSerializer):

    class Meta:
        model = Activity
        fields = '__all__'
        fields=('activity_id','user_id','time_begin','time_end','category')
        depth = 1

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('user_id','user_name')
