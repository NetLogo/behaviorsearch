name := "behaviorsearch"

organization := "bsearch"

val netLogoVersion = settingKey[String]("active version of NetLogo")

netLogoVersion := "6.2.0-d27b502"

resolvers += "netlogo"         at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"
resolvers += "netlogoheadless" at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"

libraryDependencies ++= Seq(
  "jfree"                  %  "jfreechart"               % "1.0.13"
, "jfree"                  %  "jcommon"                  % "1.0.16"
, "args4j"                 %  "args4j"                   % "2.0.12"
, "org.picocontainer"      %  "picocontainer"            % "2.13.6"
, "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
, "org.ow2.asm"            %  "asm-all"                  % "5.0.4"
, "org.parboiled"          %% "parboiled"                % "2.1.3"
, "com.novocode"           %  "junit-interface"          % "0.11" % "test"
)

libraryDependencies ++= {
  val version = netLogoVersion.value
  if (description.value.contains("subproject of NetLogo"))
    Seq()
  else
    Seq(
      ("org.nlogo" % "netlogo" % version).intransitive
    , "org.nlogo" % "netlogoheadless" % version
    )
}

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    artifact.name + "." + artifact.extension
}

javacOptions in Compile ++= List("-g", "-deprecation", "-target", "1.8", "-source", "1.8")

javacOptions in Test ++= List("-g", "-deprecation", "-target", "1.8", "-source", "1.8")

javaSource in Compile := baseDirectory.value / "src"

resourceDirectory in Compile := baseDirectory.value / "src"

includeFilter in unmanagedResources := "*.fxml"

excludeFilter in Compile in unmanagedSources := "*test*"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))
  .put(AttributeKey[Boolean]("jdkLibrary"), true)

javaSource in Test := baseDirectory.value / "src"

excludeFilter in Test in unmanagedSources := HiddenFileFilter

includeFilter in Test in unmanagedSources := "*Test.java"

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a"))

fork in run := true

fork in Test := true

crossPaths := false
isSnapshot := true
