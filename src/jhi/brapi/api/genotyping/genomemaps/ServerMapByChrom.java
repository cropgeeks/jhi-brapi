package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

public class ServerMapByChrom extends BaseBrapiServerResource<BrapiMapMetaData>
{
	private MapDAO mapDAO = new MapDAO();

	private String id;
	private String chrom;

	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
		this.chrom = (String)getRequestAttributes().get("chrom");
	}

	@Get("json")
	public BrapiMapMetaData getJson()
	{
		BrapiMapMetaData mapDetail = mapDAO.getByIdAndChromosome(Integer.parseInt(id), chrom);

		if (mapDetail != null)
			return mapDetail;

		throw new ResourceException(404);
	}
}