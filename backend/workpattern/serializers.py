
from rest_framework import serializers

from models import Activity

class ActivitySerializer(serializers.ModelSerializer):

    class Meta:
        model = Activity
        fields = '__all__'
        fields=('activity_id','user_id','time_begin','time_end','category')
        depth = 1
