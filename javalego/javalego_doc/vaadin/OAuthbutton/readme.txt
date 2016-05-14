CREAR COMPONENTE Y SERVICIO DE LOGIN para los diferentes redes sociales

MVP

COMPONENTES EN VAADIN

https://vaadin.com/directory#addon/oauth-popup-add-on:vaadin

https://vaadin.com/directory#addon/oauth-buttons:vaadin

Librería Suscribe:
https://github.com/fernandezpablo85/scribe-java/

	private void auth() {
		
		OAuthPopupButton ob = new TwitterButton("31ssXGMU4WW6KPxWwT6IMQ",
				"FR3wJmGyGAdpQMxB3vMreED2UnsHVb6nPF16f1RrtU");

		ob.addOAuthListener(new OAuthListener() {
		  @Override
		  public void authSuccessful(String accessToken, String accessTokenSecret) {
		    Notification.show("Authorized");
		    // TODO: do something with the access token
		  }

		  @Override
		  public void authDenied(String reason) {
		    Notification.show("Authorization denied");
		  }
		});

		setContent(ob);		
	}