package de.tim.facharbeit.structure.streets;

public class StreetOutOfBoundsException extends Exception {

	private static final long serialVersionUID = 1L;

	public StreetOutOfBoundsException(String type) {
		super(type);
	}
}
