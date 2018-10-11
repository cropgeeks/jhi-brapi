package jhi.brapi.api.files;

import java.io.*;

import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

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

	/**
	 * Returns the file with the url parameter filename in the given {@link MediaType}.
	 *
	 * @param mediaType The {@link MediaType}
	 * @return The file with the url parameter filename in the given {@link MediaType}.
	 */
	private Representation getFile(MediaType mediaType)
	{
		File file = new File(System.getProperty("java.io.tmpdir"), filename);
		System.out.println(file.getAbsolutePath());

		if (file.exists())
		{
			FileRepresentation fileRepresentation = new FileRepresentation(file, mediaType);

			// Set the filename and the size
			Disposition disp = new Disposition(Disposition.TYPE_ATTACHMENT);
			disp.setFilename(file.getName());
			disp.setSize(file.length());
			fileRepresentation.setDisposition(disp);
			fileRepresentation.setAutoDeleting(true);

			return fileRepresentation;
		}
		else
		{
			// If the file doesn't exist, return a 404
			throw new ResourceException(404);
		}
	}
}
