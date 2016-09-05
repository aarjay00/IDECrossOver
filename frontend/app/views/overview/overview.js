app_module  = angular.module('workpatternApp')


app_module.controller('OverviewController', function ($scope,CookieService,ActivityService,ActivityParser,GraphService) {

    var OC = this;
    OC.message  = "In Overview";

    OC.getUserSummary = function () {

        ActivityService.getUserSummary().then(function (activity_summary) {
            GraphService.MonthPlot(activity_summary)
        })
    }
    OC.getUserSummary()
    $scope.$on('user:updated',OC.getUserSummary)
})