Github
======================

##Merge
Per il merge di una branch:

1) Eseguire un Commit
2) Aprire Git Branches in: xxx in basso a destra nella finestra di IntelljIdea
3) posizionarsi su Local Branches
4) selezionare Master
5) premere Checkout per switchare sul master
6) posizionarsi su Remote Branches
7) selezionare quella che si vuole 'consolidare'
8) premere Merge Selected into Current
9) eseguire SUBITO un UPDATE Project (selezionando merge incoming changes...)

##Gitignore
- Covers JetBrains IDEs: IntelliJ, RubyMine, PhpStorm, AppCode, PyCharm, CLion, Android Studio and WebStorm
- Reference: https://intellij-support.jetbrains.com/hc/en-us/articles/206544839


####User-specific stuff
    .idea/**/workspace.xml
    .idea/**/tasks.xml
    .idea/**/usage.statistics.xml
    .idea/**/dictionaries
    .idea/**/shelf

####Generated files
    .idea/**/contentModel.xml

####Cache
    .slcache/

####Target
    target/

####Sensitive or high-churn files
    .idea/**/dataSources/
    .idea/**/dataSources.ids
    .idea/**/dataSources.local.xml
    .idea/**/sqlDataSources.xml
    .idea/**/dynamic.xml
    .idea/**/uiDesigner.xml
    .idea/**/dbnavigator.xml

####Gradle
    .idea/**/gradle.xml
    .idea/**/libraries

####Gradle and Maven with auto-import
- When using Gradle or Maven with auto-import, you should exclude module files, since they will be recreated, and may cause churn.  Uncomment if using auto-import.
    
    
    .idea/modules.xml
    .idea/*.iml
    .idea/modules

####CMake
    cmake-build-*/

####Mongo Explorer plugin
    .idea/**/mongoSettings.xml

####File-based project format
    *.iws

####IntelliJ
    out/

####mpeltonen/sbt-idea plugin
    .idea_modules/

####JIRA plugin
    atlassian-ide-plugin.xml

####Cursive Clojure plugin
    .idea/replstate.xml

####Crashlytics plugin (for Android Studio and IntelliJ)
    com_crashlytics_export_strings.xml
    crashlytics.properties
    crashlytics-build.properties
    fabric.properties

####Editor-based Rest Client
    .idea/httpRequests

####Android studio 3.1+ serialized cache file
    .idea/caches/build_file_checksums.ser