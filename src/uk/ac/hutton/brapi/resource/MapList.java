package uk.ac.hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 04/05/2015.
 */
public class MapList
{
	private List<Map> maps;

	public List<Map> getMaps()
	{
		return maps;
	}

	public void setMaps(List<Map> maps)
	{
		this.maps = maps;
	}

	@Override
	public String toString()
	{
		return "MapList{" +
			"maps=" + maps +
			'}';
	}
}
