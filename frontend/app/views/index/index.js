app_module  = angular.module('workpatternApp')


app_module.controller('CalendarPickController',function ($rootScope,$location,DateService,CookieService) {

    var CPC = this

    CPC.week_start = 'a'
    CPC.week_end  = 'b'
    CPC.date = 'c'

    CPC.calendar_format= 'week'

    CPC.date_picker_date = new Date()
    CPC.week_picker_date = new Date()

    CookieService.setObject('week_picker_date',CPC.week_picker_date)
    CookieService.setObject('date_picker_date', CPC.date_picker_date)


    CPC.nextCalendar = function () {
        if(CPC.calendar_format=='week') {
            CPC.week_picker_date.setHours(CPC.week_picker_date.getHours() + 7 * 24)
            CPC.date_picker_date=CPC.week_picker_date
            CookieService.setObject('week_picker_date', CPC.week_picker_date)
        }
        else{
            CPC.date_picker_date.setHours(CPC.date_picker_date.getHours() + 24)
            CookieService.setObject('date_picker_date', CPC.date_picker_date)

        }
        CPC.updateWeekRange()
    }

    CPC.prevCalendar = function () {
        if(CPC.calendar_format=='week'){
            CPC.week_picker_date.setHours(CPC.week_picker_date.getHours() - 7*24)
            CPC.date_picker_date=CPC.week_picker_date
            CookieService.setObject('week_picker_date',CPC.week_picker_date)
        }
        else{
            CPC.date_picker_date.setHours(CPC.date_picker_date.getHours() - 24)
            CookieService.setObject('date_picker_date', CPC.date_picker_date)

        }
        CPC.updateWeekRange()
    }

    CPC.updateWeekRange = function () {
        if(CPC.calendar_format=='week') {
            var l = DateService.getWeekRange(CPC.week_picker_date)
            CPC.week_start = l[0]
            CPC.week_end = l[1]
            $rootScope.$broadcast('week:updated')
        }
        else {
            CPC.date = DateService.getDateRepresentation(CPC.date_picker_date)
            $rootScope.$broadcast('day:updated')
        }
    }

    CPC.display_picker = function () {
        if(CPC.calendar_format=='week'){
            return CPC.week_start+" - "+CPC.week_end
        }
        else if(CPC.calendar_format=='day'){
            return CPC.date
        }
    }
    CPC.updateWeekRange()

    $rootScope.$on('$locationChangeSuccess',function (event,next,current) {
        console.log($location.path())
        if($location.path()=='/summary'){
            CPC.calendar_format='week'
        }
        else if($location.path()=='/activity'){
            CPC.calendar_format='day'
        }
        CPC.updateWeekRange()
    })

})


app_module = angular.module("workpatternApp")

app_module.controller('UserController',function ($rootScope,UserService,CookieService) {

    var UC = this
    UC.message = "Got here"
    UC.user_list = [1,2,3,4]

    UC.change_user = function (user) {
        console.log(user)
        CookieService.setValue('user_id',user.user_id)
        $rootScope.$broadcast('user:updated')
    }

    UC.getUserList = function () {
        UserService.getUserList().then(function (user_list) {
            UC.user_list = user_list
        })
    }
    UC.getUserList()
});