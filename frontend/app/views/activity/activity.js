app_module  = angular.module('workpatternApp')


app_module.controller('ActivityController', function ($scope,ActivityService,CookieService,GraphService) {

    var AC = this
    AC.message  = "In Activity"

    AC.understand_percentage = 70
    AC.edit_percentage = 5
    AC.debug_percentage = 20
    AC.other_percentage = 5

    AC.displayDaySummary  = function () {
        var date_picker_date = CookieService.getObject('date_picker_date')

        ActivityService.getDaySummary().then(function (activity_summary) {
            console.log(activity_summary)
            AC.displayActivityPercentage(activity_summary)
            GraphService.DayPlot(activity_summary)
        })
    }
    AC.displayActivityPercentage = function (activity_summary) {
        var activity = {'U': 0, 'E': 0, 'D': 0, 'X': 0}
        var total = 0
        for (idx in activity_summary) {
            activity[activity_summary[idx]['category']] += 1
            total += 1
        }
        if(total==0) total=1
        AC.understand_percentage = Math.round( activity['U']*100 / total)
        AC.edit_percentage = Math.round(activity['E']*100 / total)
        AC.debug_percentage = Math.round(activity['D']*100 / total)
        AC.other_percentage = Math.round(activity['X']*100 / total)
    }
    AC.displayDaySummary()
    $scope.$on('day:updated', function () {
        AC.displayDaySummary()
    })
    $scope.$on('user:updated',AC.displayDaySummary)

})