require.config({
	baseUrl: '',
	deps: ['angular', 'app'],
	paths: {
	
		'app.login' : 'login/login',
		'app.login.loginServer' : 'login/loginServer',
		'app.user' : 'user/user',
		'app.user.userServer' : 'user/userServer',
		'app.user.new' : 'user/newUser/newUser',
		'app.item' : 'user/collections/items/item',
		'app.collection' : 'user/collections/collection',
		'app.item.new' : 'user/collections/items/newItem/newItem',
		'app.collection.new' : 'user/collections/new/newCollection',
		'app.item.itemServer' : 'user/collections/items/itemServer',
		'app.collection.collectionServer' : 'user/collections/collectionServer',
		
		'angular': 'bower_components/angular/angular',
		'ui.bootstrap': 'bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'ui.router': 'bower_components/angular-ui-router/release/angular-ui-router',
		'lodash': 'bower_components/lodash/dist/lodash'
	},
	shim: {
		'angular' : {
			exports: 'angular'
		},
		'lodash': {
            exports: '_'
        },
		'ui.router': ['angular'],
        'ui.bootstrap': ['angular']
    }
});