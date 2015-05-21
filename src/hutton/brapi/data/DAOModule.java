package hutton.brapi.data;

import com.google.inject.*;

/**
 * Binds concrete implementations to their interfaces for dependency injection in the server code. We're using Google
 * Guice for dependency injection (this is a test to see if it's worthwhile using).
 */
public class DAOModule extends AbstractModule
{
	/**
	 * The method where dependency injection bindings are set up. Here we're telling the code that wherever we see
	 * @Inject GermplasmDAO, the class GermplasmDAOImpl should be proviced by Guice.
	 */
	@Override
	protected void configure()
	{
		bind(GermplasmDAO.class).to(GermplasmDAOImpl.class);
		bind(MapDAO.class).to(MapDAOImpl.class);
		bind(MarkerProfileDAO.class).to(MarkerProfileDAOImpl.class);
		bind(TraitDAO.class).to(TraitDAOImpl.class);
	}
}
