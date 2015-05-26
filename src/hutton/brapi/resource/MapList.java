package hutton.brapi.resource;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * Created by gs40939 on 04/05/2015.
 */
public class MapList
{
	private List<Map> maps;

	public MapList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public MapList(List<Map> maps)
	{
		this.maps = maps;
	}

	// JsonValue annotation specifies that the value of maps should be used (i.e. it won't be wrapped in a Maps object)
	@JsonValue
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
