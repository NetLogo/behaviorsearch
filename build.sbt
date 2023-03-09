name         := "behaviorsearch"
organization := "bsearch"
isSnapshot   := true

val netLogoVersion = settingKey[String]("active version of NetLogo")

netLogoVersion := "6.3.0"

resolvers += "netlogo" at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"

libraryDependencies ++= Seq(
  "jfree"              % "jfreechart"      % "1.0.13"
, "jfree"              % "jcommon"         % "1.0.16"
, "args4j"             % "args4j"          % "2.0.12"
, "com.novocode"       % "junit-interface" % "0.11" % "test"
, "org.jogamp.jogl"    %  "jogl-all"       % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0/jar/jogl-all.jar"
, "org.jogamp.gluegen" %  "gluegen-rt"     % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0/jar/gluegen-rt.jar"
)

libraryDependencies ++= {
  if (description.value.contains("subproject of NetLogo"))
    Seq()
  else
    Seq("org.nlogo" % "netlogo" % netLogoVersion.value
      exclude("org.jogamp.jogl",    "jogl-all")
      exclude("org.jogamp.gluegen", "gluegen-rt")
    )
}

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "." + artifact.extension
}

Compile / javacOptions      ++= List("-g", "-deprecation", "--release", "11")
Compile / javaSource         := baseDirectory.value / "src"
Compile / resourceDirectory  := baseDirectory.value / "src"

Compile / unmanagedJars += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))
  .put(AttributeKey[Boolean]("jdkLibrary"), true)

unmanagedResources / includeFilter := "*.fxml"
unmanagedSources   / excludeFilter := "*test*"

Test / javacOptions ++= List("-g", "-deprecation", "--release", "11")
Test / javaSource    := baseDirectory.value / "src"
Test / testOptions   := Seq(Tests.Argument(TestFrameworks.JUnit, "-a"))

Test / unmanagedSources / excludeFilter := HiddenFileFilter
Test / unmanagedSources / includeFilter := "*Test.java"

run / fork := true
Test / fork := true

crossPaths := false
isSnapshot := true

// Add JavaFX dependencies
val javaFXVersion = "16"
libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % javaFXVersion classifier osName)
}
