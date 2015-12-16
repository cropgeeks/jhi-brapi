package jhi.brapi.resource;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by gs40939 on 07/05/2015.
 */
public class GermplasmList
{
	private List<BrapiGermplasm> germplasm;

	public GermplasmList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public GermplasmList(List<BrapiGermplasm> germplasm)
	{
		this.germplasm = germplasm;
	}

	// JsonValue annotation specifies that the value of germplasm should be used (i.e. it won't be wrapped in a BrapiGermplasm JSON object)
	@JsonValue
	public List<BrapiGermplasm> getGermplasm()
	{
		return germplasm;
	}

	public void setGermplasm(List<BrapiGermplasm> germplasm)
	{
		this.germplasm = germplasm;
	}
}
