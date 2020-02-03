package jhi.brapi.api.core.serverinfo;

import java.util.*;

public class BrapiServerInfo
{
	private String contactEmail;
	private String documentationURL;
	private String location;
	private String organizationName;
	private String organizationURL;
	private String serverDescription;
	private String serverName;
	private List<BrapiCall> calls;

	public String getContactEmail()
		{ return contactEmail; }

	public void setContactEmail(String contactEmail)
		{ this.contactEmail = contactEmail; }

	public String getDocumentationURL()
		{ return documentationURL; }

	public void setDocumentationURL(String documentationURL)
		{ this.documentationURL = documentationURL; }

	public String getLocation()
		{ return location; }

	public void setLocation(String location)
		{ this.location = location; }

	public String getOrganizationName()
		{ return organizationName; }

	public void setOrganizationName(String organizationName)
		{ this.organizationName = organizationName; }

	public String getOrganizationURL()
		{ return organizationURL; }

	public void setOrganizationURL(String organizationURL)
		{ this.organizationURL = organizationURL; }

	public String getServerDescription()
		{ return serverDescription; }

	public void setServerDescription(String serverDescription)
		{ this.serverDescription = serverDescription; }

	public String getServerName()
		{ return serverName; }

	public void setServerName(String serverName)
		{ this.serverName = serverName; }

	public List<BrapiCall> getCalls()
		{ return calls; }

	public void setCalls(List<BrapiCall> calls)
		{ this.calls = calls; }
}
