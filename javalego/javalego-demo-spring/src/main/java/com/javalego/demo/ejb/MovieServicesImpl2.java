package com.javalego.demo.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;

/**
 * Session Bean implementation class MovieS
 */
@Stateless
@LocalBean
public class MovieServicesImpl2 implements MoviesServices
{
	@EJB
	private DataProvider movies;

	/**
	 * Default constructor.
	 */
	public MovieServicesImpl2()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMovies(Entity<Long>[] list) throws Exception
	{
		for (Entity<Long> movie : list)
		{
			movies.save(movie);
			System.out.println(movie.toString());
			//throw new MyException();
			// movies.save(movie);
		}
	}

}
