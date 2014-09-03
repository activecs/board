function changeLocale(loc) {
			$.get( window.location.pathname, { "lang": loc }, function() {
				window.location.reload(true);
			} );
}