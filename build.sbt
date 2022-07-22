name         := "behaviorsearch"
organization := "bsearch"
isSnapshot   := true

resolvers += "netlogo" at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"

libraryDependencies ++= Seq(
  "jfree"              % "jfreechart"      % "1.0.13"
, "jfree"              % "jcommon"         % "1.0.16"
, "args4j"             % "args4j"          % "2.0.12"
, "com.novocode"       % "junit-interface" % "0.11" % "test"
, "org.jogamp.jogl"    %  "jogl-all"       % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0-rc-20210111/jar/jogl-all.jar"
, "org.jogamp.gluegen" %  "gluegen-rt"     % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0-rc-20210111/jar/gluegen-rt.jar"
)

libraryDependencies ++= {
  if (description.value.contains("subproject of NetLogo"))
    Seq()
  else
    Seq("org.nlogo" % "netlogo" % "6.2.2"
      exclude("org.jogamp.jogl",    "jogl-all")
      exclude("org.jogamp.gluegen", "gluegen-rt")
    )
}

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "." + artifact.extension
}

Compile / javacOptions      ++= List("-g", "-deprecation", "-target", "1.8", "-source", "1.8")
Compile / javaSource         := baseDirectory.value / "src"
Compile / resourceDirectory  := baseDirectory.value / "src"

Compile / unmanagedJars += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))
  .put(AttributeKey[Boolean]("jdkLibrary"), true)

unmanagedResources / includeFilter := "*.fxml"
unmanagedSources   / excludeFilter := "*test*"

Test / javacOptions ++= List("-g", "-deprecation", "-target", "1.8", "-source", "1.8")
Test / javaSource    := baseDirectory.value / "src"
Test / testOptions   := Seq(Tests.Argument(TestFrameworks.JUnit, "-a"))

Test / unmanagedSources / excludeFilter := HiddenFileFilter
Test / unmanagedSources / includeFilter := "*Test.java"

fork in run  := true
fork in Test := true

crossPaths := false
