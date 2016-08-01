package jhi.brapi.server;

import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

import java.io.*;

/**
 * @author Sebastian Raubach
 */
public class Files extends ServerResource
{
	private String filename;

	@Override
	protected void doInit() throws ResourceException
	{
		super.doInit();

		// Get the filename from the url parameter
		filename = (String) getRequestAttributes().get("filename");
	}

	@Get("tsv")
	public Representation getTsv()
	{
		return getFile(MediaType.TEXT_TSV);
	}

	@Get("png")
	public Representation getPng()
	{
		return getFile(MediaType.IMAGE_PNG);
	}

	/**
	 * Returns the file with the url parameter filename in the given {@link MediaType}.
	 *
	 * @param mediaType The {@link MediaType}
	 * @return The file with the url parameter filename in the given {@link MediaType}.
	 */
	private Representation getFile(MediaType mediaType)
	{
		File file = new File(System.getProperty("java.io.tmpdir"), filename);

		if (file.exists())
		{
			FileRepresentation fileRepresentation = new FileRepresentation(file, mediaType);

			// Set the filename and the size
			Disposition disp = new Disposition(Disposition.TYPE_ATTACHMENT);
			disp.setFilename(file.getName());
			disp.setSize(file.length());
			fileRepresentation.setDisposition(disp);

			return fileRepresentation;
		}
		else
		{
			// If the file doesn't exist, return a 404
			throw new ResourceException(404);
		}
	}
}
