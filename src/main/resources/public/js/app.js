var app = angular.module('lucene-demo', []);

app.filter('trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);

app.controller('BaseController', function($http, $scope){
    $scope.request = {};
    $scope.queryTypes = [];
    function reset() {
        $scope.total = 0;
        $scope.request.index = 0;
        $scope.documents = [];
    }

    $scope.search = function(clear) {
        if(clear) {
            reset();
        }
        if($scope.request.term) {
            $http.get("/search", {params : $scope.request}).then(
                function(res){
                    var pager = res.data;
                    $scope.total = pager.total;
                    $scope.request.index = pager.currentIndex;
                    $scope.documents = $scope.documents.concat(pager.documents);
                });
        }
    }

    $scope.detail = function(doc) {
        window.open(doc.path, '_blank');
    }

    function init() {
        reset();
        $http.get("/list-types").then(
            function(res){
                $scope.queryTypes = res.data;
                $scope.request.type = $scope.queryTypes[0];
            });
    };
    init();
});