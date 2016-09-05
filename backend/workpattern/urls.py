from django.conf.urls import include,url


from . import views

from rest_framework import routers
from views import *

router = routers.DefaultRouter()

router.register(r'activity',views.ActivityViewSet)
router.register(r'user',views.UserViewSet)

urlpatterns =[
    url('^',include(router.urls)),
    url(r'docs/',include('rest_framework_swagger.urls')),
    # url(r'^$', views.index, name='index'),
]