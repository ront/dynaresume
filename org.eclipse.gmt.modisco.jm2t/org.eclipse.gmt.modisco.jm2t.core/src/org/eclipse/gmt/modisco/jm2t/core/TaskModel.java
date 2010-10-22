/*******************************************************************************
 * Copyright (c) 2010 Angelo ZERR.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:      
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.gmt.modisco.jm2t.core;

import java.util.HashMap;
import java.util.Map;

/**
 * A task model represents a model that can be shared between multiple tasks in
 * a common workflow.
 * <p>
 * The task model contains information about the overall task flow and allows
 * tasks to store and retrieve data. Its usage allows multiple tasks to be
 * chained together and share data from the output of one task to the input of
 * another.
 * </p>
 * 
 * @since 1.0
 */
public class TaskModel {

	public static final String TASK_GENERATOR_TYPE = "generatorType";
	public static final String TASK_MODEL_CONVERTER_TYPE = "modelConverterType";
	public static final String TASK_GENERATOR_CONFIGURATION = "generatorConfiguration";

	private Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * Returns the object in the task model with the given id.
	 * <p>
	 * The id can be any of the predefined ids within TaskModel, or any other
	 * key to retrieve task-specific data.
	 * </p>
	 * 
	 * @param id
	 *            an id for the object
	 * @return the object with the given id, or <code>null</code> if no object
	 *         could be found with that id
	 */
	public Object getObject(String id) {
		try {
			return map.get(id);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Put an object into the task model with the given id.
	 * <p>
	 * The id can be any of the predefined ids within TaskModel, or any other
	 * key to store task-specific data.
	 * </p>
	 * 
	 * @param id
	 *            the id to associate the object with
	 * @param obj
	 *            an object, or <code>null</code> to reset (clear) the id
	 */
	public void putObject(String id, Object obj) {
		map.put(id, obj);
	}
}
