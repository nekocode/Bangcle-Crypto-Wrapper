/*
 * Copyright 2018 nekocode (nekocode.cn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.bangcle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class BangclePlugin implements Plugin<Project> {
    static final String GEN_PATH = "generated/source/bangcle/";


    @Override
    void apply(Project project) {
        project.getExtensions().create('bangcle', BangcleDsl)

        project.getTasks()['preBuild']
                .dependsOn(project.tasks.create('bangcleGenFiles', GenFilesTask))


        final File keysSourceDir = new File(project.getBuildDir(), GEN_PATH)
        def android = project.getExtensions().getByName("android")

        if (android instanceof LibraryExtension) {
            ((LibraryExtension) android).getSourceSets()
                    .getByName("main").getJava().srcDirs += keysSourceDir

        } else if (android instanceof AppExtension) {
            ((AppExtension) android).getSourceSets()
                    .getByName("main").getJava().srcDirs += keysSourceDir
        }
    }
}
