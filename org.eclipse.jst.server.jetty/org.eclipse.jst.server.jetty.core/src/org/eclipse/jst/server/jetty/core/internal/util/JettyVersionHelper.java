package org.eclipse.jst.server.jetty.core.internal.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.server.jetty.core.internal.JettyConstants;
import org.eclipse.jst.server.jetty.core.internal.JettyServer;
import org.eclipse.jst.server.jetty.core.internal.JettyServerBehaviour;

public class JettyVersionHelper implements JettyConstants {

	public static IStatus checkJettyVersion(IPath installPath) {
		// Search start.jar
		IPath startJarPath = installPath.append(START_JAR);
		File jarFile = null;
		jarFile = startJarPath.toFile();
		// If jar is not at expected location, try alternate location
		if (!jarFile.exists()) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	/**
	 * Gets the base directory for this server. This directory is used as the
	 * "base" property for the server.
	 * 
	 * @param ts
	 *            JettyServer from which to derive the base directory directory.
	 *            Only used to get the temp directory if needed.
	 * @return path to base directory
	 */
	public static IPath getStandardBaseDirectory(JettyServer ts) {
		if (ts.isTestEnvironment()) {
			String baseDir = ts.getInstanceDirectory();
			// If test mode and no instance directory specified, use temporary
			// directory
			if (baseDir == null) {
				JettyServerBehaviour tsb = (JettyServerBehaviour) ts
						.getServer().loadAdapter(JettyServerBehaviour.class,
								null);
				return tsb.getTempDirectory();
			}
			IPath path = new Path(baseDir);
			if (!path.isAbsolute()) {
				IPath rootPath = ResourcesPlugin.getWorkspace().getRoot()
						.getLocation();
				path = rootPath.append(path);
			}
			// Return specified instance directory
			return path;
		}
		// Return runtime path
		return ts.getServer().getRuntime().getLocation();
	}

	/**
	 * Gets the startup VM arguments for the Jetty server.
	 * 
	 * @param installPath
	 *            installation path for the server
	 * @param instancePath
	 *            instance path for the server
	 * @param deployPath
	 *            deploy path for the server
	 * @param isTestEnv
	 *            test environment flag
	 * @return array of strings containing VM arguments
	 */
	public static String[] getJettyVMArguments(IPath installPath,
			IPath instancePath, IPath deployPath, String endorsedDirs,
			boolean isTestEnv) {
		List list = new ArrayList();
		if (isTestEnv) {
			list.add("-Djetty.home=\"" + instancePath.toOSString() + "\"");
			list.add("-DSTART=\"" + instancePath.toOSString()
					+ "/start.config\"");
			list.add("-Dinstall.jetty.home=\"" + installPath.toOSString() + "\"");
		} else
			list.add("-Djetty.home=\"" + installPath.toOSString() + "\"");
		// if (isTestEnv)
		// list.add("-Djetty.base=\"" + instancePath.toOSString() + "\"");
		// else
		// list.add("-Djetty.base=\"" + installPath.toOSString() + "\"");
		// list.add("-Djetty.home=\"" + installPath.toOSString() + "\"");
		// Include a system property for the configurable deploy location
//		list.add("-Dwtp.deploy=\"" + deployPath.toOSString() + "\"");
//		list.add("-Djava.endorsed.dirs=\"" + endorsedDirs + "\"");

		list.add("-DVERBOSE");
		// list.add("-Djetty.port=8081");
		// list.add("-Djetty.port=8081");
		list.add("-DSTOP.PORT=8082");
		list.add("-DSTOP.KEY=secret");

		String[] s = new String[list.size()];
		list.toArray(s);
		return s;
	}

	public static String[] getJettyProgramArguments(IPath configPath,
			boolean debug, boolean starting) {
		List list = new ArrayList();

		if (starting) {
			//list.add(configPath.toOSString() + "/etc/jetty.xml");
			// list.add(configPath.toOSString() + "/etc/jetty-deploy.xml");
		} else
			list.add("--stop");

		String[] temp = new String[list.size()];
		list.toArray(temp);
		return temp;
	}

}
