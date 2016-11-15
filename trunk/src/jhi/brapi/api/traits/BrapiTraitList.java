package jhi.brapi.api.traits;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

public class BrapiTraitList
{
	private List<BrapiTrait> traits;

	public BrapiTraitList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public BrapiTraitList(List<BrapiTrait> traits)
	{
		this.traits = traits;
	}

	// JsonValue annotation specifies that the value of traits should be used (i.e. it won't be wrapped in a Traits JSON
	// object)
	@JsonValue
	public List<BrapiTrait> getTraits()
		{ return traits; }

	public void setTraits(List<BrapiTrait> traits)
		{ this.traits = traits; }
}
