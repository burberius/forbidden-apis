/*
 * (C) Copyright Uwe Schindler (Generics Policeman) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.thetaphi.forbiddenapis.maven;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.util.List;

/**
 * Mojo to check if no project generated class files (compile scope) contain calls to forbidden APIs
 * from the project classpath and a list of API signatures (either inline or as pointer to files or bundled signatures).
 * At least one signature must be given, using any of the corresponding optional parameters.
 * <p>
 * This Mojo exists since version 1.2, replacing the old <code>forbiddenapis:forbiddenapis</code> goal.
 * <em>In most cases its enough to rename the goal on update, the older v1.0 properties are still available.</em>
 * <p>
 * Since version 2.0 this Mojo defaults to run in the {@code 'verify'} lifecycle phase, before it was done in
 * {@code 'process-classes'} phase, which caused problems for some users (especially debugging tests).
 * @since 1.2
 */
@Mojo(name = "check", threadSafe = true, requiresProject = true, requiresDependencyResolution = ResolutionScope.COMPILE, defaultPhase = LifecyclePhase.VERIFY)
public final class CheckMojo extends AbstractCheckMojo {

  /**
   * Injected compile classpath.
   */
  @Parameter(defaultValue = "${project.compileClasspathElements}", readonly = true, required = true)
  private List<String> classpathElements;

  /**
   * Directory with the class files to check.
   */
  @Parameter(required = false, defaultValue = "${project.build.outputDirectory}")
  private File classesDirectory;
  
  @Override
  protected List<String> getClassPathElements() {
    return this.classpathElements;
  }
  
  @Override
  protected File getClassesDirectory() {
    return this.classesDirectory;
  }
  
}