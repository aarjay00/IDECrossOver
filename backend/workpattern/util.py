from models import *





def get_percentage(activity_list):

    category_set=['U','E','D','X']
    category_list=[activity.category for activity in activity_list]


    category_sum = {}
    for category in category_set:
        category_sum[category] = 0

    for category in category_list:
        category_sum[category] += 1

    for category in category_sum.keys():
        try:
            category_sum[category] = (category_sum[category]*100.0)/len(category_list)
        except:
            continue
    return category_sum

