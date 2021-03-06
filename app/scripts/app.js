'use strict';

// Declare app level module which depends on filters, and services
var App = angular.module('app', ['ngSanitize', 'ngResource', 'ui.router']).config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function($stateProvider, $locationProvider, $urlRouterProvider) {
	$locationProvider.hashPrefix('!');
	$urlRouterProvider.otherwise("/");

	$stateProvider.state('index', {
		abstract: true,
		views: {
			"navView@": {
				templateUrl: "partials/nav.html"
			}
		}
	}).state('main', {
		parent: 'index',
		url: "/", // root route
		views: {
			"@": {
				templateUrl: "partials/main.html",
				controller: 'MainCtrl'
			}
		}
	}).state('about', {
		parent: 'index',
		url: "/about",
		views: {
			"@": {
				templateUrl: "partials/about.html",
				controller: 'AboutCtrl'
			}
		}
	});

	// Without server side support html5 must be disabled.
	return $locationProvider.html5Mode(false);
}]);