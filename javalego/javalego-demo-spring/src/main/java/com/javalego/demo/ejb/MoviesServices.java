package com.javalego.demo.ejb;

import com.javalego.entity.Entity;

public interface MoviesServices
{

	void addMovies(Entity<Long>[] list) throws Exception;

}