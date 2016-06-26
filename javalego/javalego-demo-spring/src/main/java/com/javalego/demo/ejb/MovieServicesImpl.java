package com.javalego.demo.ejb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;

/**
 * Session Bean implementation class MovieS
 */
@Service
public class MovieServicesImpl /*implements MoviesServices*/
{
	@Autowired
	private DataProvider movies;

	/**
	 * Default constructor.
	 */
	public MovieServicesImpl()
	{
		// TODO Auto-generated constructor stub
	}

	//@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addMovies(Entity<Long>[] list) throws Exception
	{
		for (Entity<Long> movie : list)
		{
			movies.save(movie);
			System.out.println(movie.toString());
			//throw new Exception("error");
			// movies.save(movie);
		}
	}

}
