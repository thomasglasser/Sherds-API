package dev.thomasglasser.sherdsapi.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*

abstract class GeneratePackageInfos extends DefaultTask {
    @InputDirectory
    @Optional
    abstract DirectoryProperty getSrcDir()

    @InputFiles
    @Optional
    abstract ConfigurableFileCollection getCommonJavaFiles()

    @OutputDirectory
    abstract DirectoryProperty getOutDir()

    @TaskAction
    void generate() {
        File out = outDir.asFile.get()

        Set<File> searchDirs = [] as Set
        File src = srcDir.asFile.orNull
        if (src != null && src.exists()) {
            searchDirs.add(src)
        }
        commonJavaFiles.each { File f ->
            if (f.exists() && f.isDirectory()) {
                searchDirs.add(f)
            }
        }

        Set<String> packages = [] as Set
        searchDirs.each { File dir ->
            dir.eachFileRecurse { File file ->
                if (file.isDirectory()) {
                    def javaFiles = file.listFiles({ File f -> f.name.endsWith('.java') && f.name != 'package-info.java' } as FileFilter)
                    if (javaFiles != null && javaFiles.length > 0) {
                        String relPath = dir.toPath().relativize(file.toPath()).toString().replace('\\', '/')
                        packages.add(relPath)
                    }
                }
            }
        }

        packages.each { String relPath ->
            boolean staticExists = searchDirs.any { File dir ->
                new File(dir, relPath + '/package-info.java').exists()
            }
            if (!staticExists) {
                String pkgName = relPath.replace('/', '.')
                File pkgInfo = new File(out, relPath + '/package-info.java')
                pkgInfo.parentFile.mkdirs()
                pkgInfo.text = """@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
package ${pkgName};

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
"""
            }
        }
    }
}
