app_module  = angular.module('workpatternApp')


app_module.controller('ActivityController', function ($scope,ActivityService,CookieService,GraphService) {

    var AC = this
    AC.message  = "In Activity"

    AC.displayDaySummary  = function () {
        var date_picker_date = CookieService.getObject('date_picker_date')

        ActivityService.getDaySummary().then(function (activity_summary) {
            console.log(activity_summary)
            GraphService.DayPlot(activity_summary)
        })
    }

    AC.displayDaySummary()
    $scope.$on('day:updated', function () {
        AC.displayDaySummary()
    })
    $scope.$on('user:updated',AC.displayDaySummary)

})