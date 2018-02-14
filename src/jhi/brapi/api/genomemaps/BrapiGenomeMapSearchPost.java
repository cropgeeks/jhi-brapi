package jhi.brapi.api.genomemaps;

import jhi.brapi.api.*;

public class BrapiGenomeMapSearchPost extends BasicPost
{
	private String type;
	private String species;

	public BrapiGenomeMapSearchPost()
	{
	}

	public BrapiGenomeMapSearchPost(String type, String species)
	{
		this.type = type;
		this.species = species;
	}

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }

	public String getSpecies()
		{ return species; }

	public void setSpecies(String species)
		{ this.species = species; }
}
