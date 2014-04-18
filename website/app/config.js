require.config({
	baseUrl: '',
	deps: ['angular', 'app'],
	paths: {
		
		'app.guestitem' : 'guest/guestitem/guestitem',
		'app.guestitem.guestitemServer' : 'guest/guestitem/guestitemServer',
		'app.guest' : 'guest/guest',
		'app.guest.guestServer' : 'guest/guestServer',
		'app.create' : 'create/create',
		'app.create.createServer' : 'create/createServer',
		'app.login' : 'login/login',
		'app.login.new' : 'login/newLogin/newLogin',
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
		'lodash': 'bower_components/lodash/dist/lodash',
		'angular-http-auth': 'bower_components/angular-http-auth/src/http-auth-interceptor',
		'angular-file-upload': 'bower_components/angular-file-upload/angular-file-upload'
	},
	shim: {
		'angular' : {
			exports: 'angular'
		},
		'lodash': {
            exports: '_'
        },
		'angular-file-upload': ['angular'],
		'angular-http-auth': ['angular'],
		'ui.router': ['angular'],
        'ui.bootstrap': ['angular']
    }
});