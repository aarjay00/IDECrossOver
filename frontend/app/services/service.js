app_module  = angular.module('workpatternApp')




app_module.service('CookieService', function ($cookieStore,$cookies) {

    this.getValue =function(key){
        return $cookies.get(key);
    }
    this.setValue=function(key,value){
        $cookies.put(key,value);
    }
    this.deleteValue=function(key){
        console.log("DELETING >>>>>>>");
        $cookies.remove(key);
    }
    this.setObject=function(key,object)
    {
        $cookies.putObject(key,object);
    };
    this.getObject=function (key) {
        return $cookies.getObject(key);
    };
});


app_module.service('UserService',function ($q,$http,CookieService) {



    this.getUserList = function () {
        var http_get_request = 'http://localhost:8000/workpattern/user'
        var defer = $q.defer();
        $http.get(http_get_request)
            .success(function (response) {
                defer.resolve(response);
            });
        return defer.promise;
    }

});


app_module.service('ActivityService',function ($q,$http,CookieService,DateService) {



    var user_id = 35
    CookieService.setValue('user_id',user_id)
    var AS =  this

    AS.getUserSummary = function () {
        var http_get_request = 'http://localhost:8000/workpattern/activity/user_month_activity/?'
        var user_id = CookieService.getValue('user_id')
        var defer = $q.defer();
        http_get_request = http_get_request + "user_id="+user_id

        $http.get(http_get_request)
            .success(function (response) {
                defer.resolve(response);
            });
        return defer.promise;
    }
    AS.getWeekSummary = function () {

        var date = CookieService.getObject('week_picker_date')

        date = new Date(date.toString())

        var date_repr = date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()

        console.log("aaaa"+date_repr)
        var http_get_request='http://localhost:8000/workpattern/activity/user_week_activity/?'
        var user_id = CookieService.getValue('user_id')
        var defer = $q.defer();

        http_get_request = http_get_request + "user_id="+user_id+"&&date="+date_repr
        $http.get(http_get_request)
            .success(function (response) {
                defer.resolve(response);
            });
        return defer.promise;
    }
    AS.getDaySummary = function (date) {
        var http_get_request = 'http://localhost:8000/workpattern/activity/user_day_activity/?'
        var user_id=CookieService.getValue('user_id')
        var date = CookieService.getObject('date_picker_date')
        var defer = $q.defer();

        date = new Date(date.toString())

        var date_repr = date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()

        http_get_request = http_get_request + "user_id="+user_id+"&&date="+date_repr
        $http.get(http_get_request)
            .success(function (response) {
                defer.resolve(response);
            });
        return defer.promise;
    }
})


app_module.service('GraphService',function (CookieService) {


    this.color_map = {'U':'rgb(34, 170, 228,.9)','E':'rgba(76,175,80,.9)','D':'rgba(237,80,83,.9)','X':'rgb(82,97,107,.9)'};

    this.getActivitySummationWeekTrace = function (activity_summation,week_name) {

        var category_list = ['U','E','D','X']
        var traces = []

        for(idx in category_list){
            var trace = {
                x: [activity_summation[category_list[idx]]],
                y: [week_name],
                orientation: 'h',
                marker: {
                    color: this.color_map[category_list[idx]],
                    width: 1
                },
                type: 'bar'
            };
            traces = traces.concat(trace)
        }
        return traces
    }


    this.getActivitySummationDayTrace = function (activity_summation,day_name) {

        if(activity_summation == undefined){
            return []
        }

        var category_list = ['U','E','D','X']
        var traces = []

        for(idx in category_list){
            var trace = {
                y: [activity_summation[category_list[idx]]],
                x: [day_name],
                orientation: 'v',
                marker: {
                    color: this.color_map[category_list[idx]],
                    width: 1
                },
                type: 'bar'
            };
            traces = traces.concat(trace)
        }
        return traces
    }

    this.MonthPlot = function (activity_summation_list) {
        var traces=[]
        console.log(activity_summation_list)
        week_name = ['week1','week2','week3','week4']
        for(idx in week_name) {
            console.log(week_name[idx])
            data = this.getActivitySummationWeekTrace(activity_summation_list[week_name[idx]],week_name[idx])
            traces = traces.concat(data)
        }

        var layout = {
            title: 'User Work Pattern Summary',
            barmode: 'stack',
            xaxis:
            {
                fixedrange: true
            },
            yaxis:{fixedrange: true},
            width:500,
            height:300,
            hovermode:'none',
            showlegend: false

        };

        Plotly.newPlot('month_graph', traces, layout, {staticPlot: true});
    }

    this.WeekPlot = function (activity_summation_list) {
        var traces = []

        var day_idx = ['day1','day2','day3','day4','day5','day6','day7']

        var day_name = ['Mon','Tue','Wed','Thur','Fri','Sat','Sun']


        for(idx in day_name){
            data = this.getActivitySummationDayTrace(activity_summation_list[day_idx[idx]],day_name[idx])
            traces = traces.concat(data)
        }

        var layout = {
            title: 'User Work Pattern Summary',
            barmode: 'stack',
            xaxis:
            {
                fixedrange: true
            },
            yaxis:{fixedrange: true,range:[0,100]},
            width:500,
            height:300,
            hovermode:'none',
            showlegend: false

        };
        Plotly.newPlot('week_graph', traces, layout, {staticPlot: true});
    }

    this.DayPlot = function (activity_list) {
        var traces=[]
        console.log(activity_list)

        var prev_activity_time = 0.0

        for( idx in activity_list){

            var activity  = activity_list[idx]

            var activity_time_begin = activity['time_begin']
            var activity_time_end = activity['time_end']
            var activity_category = activity['category']


            var trace_empty = {
                y: ['Timeline'],
                x: [activity_time_begin-prev_activity_time],
                orientation: 'h',
                marker: {
                    color: 'white',
                    width: 1
                },
                type: 'bar'
            };
            var trace_activity = {
                y: ['Timeline'],
                x: [activity_time_end-activity_time_begin],
                orientation: 'h',
                marker: {
                    color: this.color_map[activity_category],
                    width: 1
                },
                type: 'bar'
            };
            traces = traces.concat(trace_empty)
            traces = traces.concat(trace_activity)
            prev_activity_time = activity_time_end
            console.log(traces.length)
        }
        var trace_empty = {
            y: ['Timeline'],
            x: [24.0-prev_activity_time],
            orientation: 'h',
            marker: {
                color: 'white',
                width: 1
            },
            type: 'bar'
        };
        traces = traces.concat(trace_empty)
        var layout = {
            title: 'Timeline',
            barmode: 'stack',
            xaxis:
            {
                fixedrange: true,range:[0.0,24.0]
            },
            yaxis:{fixedrange: true},
            width:1500,
            height:300,
            hovermode:'none',
            showlegend: false

        };
        Plotly.newPlot('day_graph', traces, layout, {staticPlot: true});
    }
})




app_module.service('DateService',function (CookieService) {

    day_list=['Mon','Tue','Wed','Thur','Fri','Sat','Sun']

    month_list= ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']


    this.getDate = function () {
            return new Date()
    }
    this.subtractDayFromDate = function (day_num,date) {

        date.setDay(date.getHours() - day_num*24)
        return date
    }
    this.getWeekRange = function(date) {
        var day = date.getDay()

        var week_start = new Date(date.getTime()) ;
        var week_end  = new Date(date.getTime());
        week_start.setHours(week_start.getHours()-(day-1)*24)
        week_end.setHours(week_end.getHours()+(7-day)*24)

        var week_start_repr = this.getDateRepresentation(week_start)
        var week_end_repr = this.getDateRepresentation(week_end)

        return [week_start_repr,week_end_repr]

    }
    this.getDateRepresentation = function (date) {

        var date_repr = month_list[date.getMonth()] +  " " + date.getDate() + ", " + date.getFullYear();
        return date_repr
    }

})


app_module.service('ActivityParser',function () {

    this.parseActivity = function (activity) {
        var category  =  activity['fields']['category']
        var time_begin = activity['fields']['time_begin']
        var time_end = activity['fields']['time_end']

        return [category,time_begin,time_end]
    }

    this.getSummaryOnActivityList = function (activity_list) {

        activity_summation={}
        for(activity_idx in activity_list){
            var category = activity_list[activity_idx]['fields']['category']
            if(!(category in activity_summation)){
                activity_summation[category]=0
            }
            activity_summation[category]+=1
        }
        return activity_summation
    }

})