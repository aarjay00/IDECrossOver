'use strict';

// Declare app level module which depends on views, and components


var app_module = angular.module("workpatternApp",["ngRoute","ngCookies"]);


app_module.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/summary', {
      templateUrl: 'views/summary/summary.html',
      controller: 'SummaryController'
    }).
    when('/activity', {
      templateUrl: 'views/activity/activity.html',
      controller: 'ActivityController'
    }).
    otherwise({
      templateUrl: 'views/summary/summary.html',
      controller: 'SummaryController'
    });
  }]);