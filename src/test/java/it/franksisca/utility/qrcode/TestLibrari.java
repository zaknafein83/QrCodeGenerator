package it.franksisca.utility.qrcode;

import java.io.IOException;
import java.net.URISyntaxException;

import it.dstech.mogliemiglia.GestioneMoglieMiglia;

public class TestLibrari {

	public static void main(String[] args) throws URISyntaxException, IOException {
		GestioneMoglieMiglia g = new GestioneMoglieMiglia();
		System.out.println(g.getListaAzioniMarito());
	}
}
