/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.javalego.demo.test;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import com.javalego.demo.ejb.ERPServices;

import junit.framework.TestCase;

public class JpaTest extends TestCase
{

	public void test() throws Exception
	{

		final Properties p = new Properties();

		// Configurar datasource con propiedades
		// p.put("mysql", "new://Resource?type=DataSource");
		// p.put("movieDatabase.JdbcDriver", "com.mysql.jdbc.Driver");
		// p.put("movieDatabase.JdbcUrl", "jdbc:mysql://localhost:3306/javalego?createDatabaseIfNotExist=true");
		// p.put("movieDatabase.Username", "root");

		// User un fichero de configuración donde podremos incluir cualquier configuración (datasource, ...) de open ejb
		// container.
		p.put("openejb.configuration", "src/main/resources/META-INF/openejb.xml");

//		p.put("openejb.deployments.classpath.filter.descriptors", "true");
//		p.put("openejb.exclude-include.order", "include-exclude"); // Defines the processing order
//		p.put("openejb.deployments.classpath.include", ".*javalego.*"); // Include nothing
//		p.put("openejb.descriptors.output", "true");

		// p.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.openejb.client.LocalInitialContextFactory");

		EJBContainer container = EJBContainer.createEJBContainer(p);
		final Context context = container.getContext();

		try
		{
			/** javalego-demo-javaee es el nombre del proyecto para localizar al servicio ejb */
			ERPServices movies = (ERPServices) context.lookup("java:global/javalego-demo/ERPServices");
			movies.populateDatabase();
		}
		finally
		{
			container.close();
		}
	}

}
