/*
 * Copyright (C) 2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.discovery.marathon

import akka.discovery.SimpleServiceDiscovery.ResolvedTarget
import org.scalatest.{ Matchers, WordSpec }
import spray.json._
import scala.io.Source

class MarathonApiSimpleServiceDiscoverySpec extends WordSpec with Matchers {
  "targets" should {
    "calculate the correct list of resolved targets" in {
      val data = resourceAsString("apps.json")

      val appList = JsonFormat.appListFormat.read(data.parseJson)

      MarathonApiSimpleServiceDiscovery.targets(appList, "akka-mgmt-http") shouldBe List(
          ResolvedTarget("192.168.65.60", Some(23236)), ResolvedTarget("192.168.65.111", Some(6850)))
    }
  }

  private def resourceAsString(name: String): String =
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(name)).mkString
}
