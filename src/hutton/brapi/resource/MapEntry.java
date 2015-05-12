package hutton.brapi.resource;

/**
 * Created by gs40939 on 28/04/2015.
 */
public class MapEntry
{
	private int markerId;
	private String markerName;
	private String location;
	private String chromosome;

	public int getMarkerId()
	{
		return markerId;
	}

	public void setMarkerId(int markerId)
	{
		this.markerId = markerId;
	}

	public String getMarkerName()
	{
		return markerName;
	}

	public void setMarkerName(String markerName)
	{
		this.markerName = markerName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getChromosome()
	{
		return chromosome;
	}

	public void setChromosome(String chromosome)
	{
		this.chromosome = chromosome;
	}

	@Override
	public String toString()
	{
		return "MapEntry{" +
			"markerId=" + markerId +
			", markerName='" + markerName + '\'' +
			", location='" + location + '\'' +
			", chromosome='" + chromosome + '\'' +
			'}';
	}
}
