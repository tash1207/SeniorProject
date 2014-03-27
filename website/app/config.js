require.config({
	baseUrl: '',
	deps: ['angular', 'app'],
	paths: {
	
		'app.user' : 'user/user',
		'app.user.userServer' : 'user/userServer',
		'app.user.new' : 'user/newUser/newUser',
		'app.item' : 'collections/items/item',
		'app.collection' : 'collections/collection',
		'app.item.new' : 'collections/items/newItem/newItem',
		'app.collection.new' : 'collections/new/newCollection',
		'app.item.itemServer' : 'collections/items/itemServer',
		'app.collection.collectionServer' : 'collections/collectionServer',
		
		'angular': 'bower_components/angular/angular',
		'ui.bootstrap': 'bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'ui.router': 'bower_components/angular-ui-router/release/angular-ui-router',
	},
	shim: {
		'angular' : {
			exports: 'angular'
		},
		'ui.router': ['angular'],
        'ui.bootstrap': ['angular']
    }
});