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
package com.javalego.demo.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.javalego.data.DataProviderException;
import com.javalego.data.jpa.JpaProvider;
import com.javalego.demo.entities.Department;
import com.javalego.demo.entities.Employee;

@Stateful
public class ERPServices
{
	@EJB
	private JpaProvider dataProvider;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void populateDatabase() throws DataProviderException
	{
		// Create Department Entity
		Department department = new Department();
		department.setName("Development");

		// Store Department.
		dataProvider.save(department);

		// Create Employee1 Entity
		Employee employee1 = new Employee();
		employee1.setEname("Satish");
		employee1.setSalary(45000.0);
		employee1.setDeg("Technical Writer");
		employee1.setDepartment(department);

		// Create Employee2 Entity
		Employee employee2 = new Employee();
		employee2.setEname("Krishna");
		employee2.setSalary(45000.0);
		employee2.setDeg("Technical Writer");
		employee2.setDepartment(department);

		// Create Employee3 Entity
		Employee employee3 = new Employee();
		employee3.setEname("Masthanvali");
		employee3.setSalary(50000.0);
		employee3.setDeg("Technical Writer");
		employee3.setDepartment(department);

		// Store Employees
		dataProvider.save(employee1);
		dataProvider.save(employee2);
		dataProvider.save(employee3);
	}

}
