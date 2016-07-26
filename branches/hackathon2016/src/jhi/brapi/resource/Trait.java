package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class Trait
{
	private String traitId;
	private String name;
	private String format;
	private String unit;
	private String method;
	private String defaultValue;
	private String minimum;
	private String maximum;
	private List<String> categories;
	private String isVisible;
	private String realPosition;

	public String getTraitId()
	{
		return traitId;
	}

	public void setTraitId(String traitId)
	{
		this.traitId = traitId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public String getMinimum()
	{
		return minimum;
	}

	public void setMinimum(String minimum)
	{
		this.minimum = minimum;
	}

	public String getMaximum()
	{
		return maximum;
	}

	public void setMaximum(String maximum)
	{
		this.maximum = maximum;
	}

	public List<String> getCategories()
	{
		return categories;
	}

	public void setCategories(List<String> categories)
	{
		this.categories = categories;
	}

	public String getIsVisible()
	{
		return isVisible;
	}

	public void setIsVisible(String isVisible)
	{
		this.isVisible = isVisible;
	}

	public String getRealPosition()
	{
		return realPosition;
	}

	public void setRealPosition(String realPosition)
	{
		this.realPosition = realPosition;
	}
}
