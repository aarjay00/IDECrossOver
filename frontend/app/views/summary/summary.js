app_module  = angular.module('workpatternApp')


app_module.controller('SummaryController', function ($scope,ActivityService,GraphService,CookieService,DateService) {

    var SC = this;
    SC.message  = "In Summary";


    SC.displayWeekSummary = function () {

        var week_picker_date  = CookieService.getObject('week_picker_date')

        ActivityService.getWeekSummary().then(function (activity_summary) {
            GraphService.WeekPlot(activity_summary)
        })
    }

   SC.displayDate = function () {
       var date  = DateService.getDate()
       return date.toString()
   }

   SC.displayWeekSummary()
   $scope.$on('week:updated', function () {
       SC.displayWeekSummary()
   })

   $scope.$on('user:updated',SC.displayWeekSummary)


})