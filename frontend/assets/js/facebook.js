/* ============================================================
 * File: facebook.js
 * Configure facebook application
 * ============================================================ */

angular.module('app').config(function(socialProvider){
  socialProvider.setFbKey({appId: "1585654564782253", apiVersion: "v2.8"});
});

angular.module('app')

  .config([
    'FacebookProvider',
    function(FacebookProvider) {
     var myAppId = '1585654564782253';
     
     // You can set appId with setApp method
     // FacebookProvider.setAppId('myAppId');
     
     /**
      * After setting appId you need to initialize the module.
      * You can pass the appId on the init method as a shortcut too.
      */
     FacebookProvider.init(myAppId);
     
    }
  ])


  /**
   * Just for debugging purposes.
   * Shows objects in a pretty way
   */
  .directive('debug', function() {
		return {
			restrict:	'E',
			scope: {
				expression: '=val'
			},
			template:	'<pre>{{debug(expression)}}</pre>',
			link:	function(scope) {
				// pretty-prints
				scope.debug = function(exp) {
					return angular.toJson(exp, true);
				};
			}
		}
	});