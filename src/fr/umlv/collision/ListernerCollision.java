package fr.umlv.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.space.service.ServiceBomb;
import fr.umlv.space.service.ServiceHero;

public class ListernerCollision implements ContactListener{

	@Override
	public void beginContact(Contact c) {
		
	}

	@Override
	public void endContact(Contact arg0) {
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
	}
}
