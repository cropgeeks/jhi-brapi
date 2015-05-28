package hutton.brapi.resource;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class TraitList
{
	private List<Trait> traits;

	public TraitList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public TraitList(List<Trait> traits)
	{
		this.traits = traits;
	}

	// JsonValue annotation specifies that the value of traits should be used (i.e. it won't be wrapped in a Traits JSON
	// object)
	@JsonValue
	public List<Trait> getTraits()
	{
		return traits;
	}

	public void setTraits(List<Trait> traits)
	{
		this.traits = traits;
	}
}
