/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.bootstrap.http

import javax.inject.{Inject, Singleton}
import com.typesafe.config.Config
import play.api.Configuration
import play.api.libs.ws.WSClient
import uk.gov.hmrc.http._
import uk.gov.hmrc.http.hooks.HttpHook
import uk.gov.hmrc.play.audit.http.HttpAuditing
import uk.gov.hmrc.play.audit.http.connector.AuditConnector
import uk.gov.hmrc.play.bootstrap.config.AppName
import uk.gov.hmrc.play.http.ws._

trait HttpClient extends HttpGet with HttpPut with HttpPost with HttpDelete with HttpPatch

@Singleton
class DefaultHttpClient @Inject()(
  config: Configuration,
  override val auditConnector: AuditConnector,
  override val wsClient: WSClient)
    extends HttpClient
    with WSHttp
    with HttpAuditing {
  override lazy val configuration: Option[Config] = Option(config.underlying)

  override val appName: String = new AppName {
    override def configuration: Configuration = config
  }.appName

  override val hooks: Seq[HttpHook] = Seq(AuditingHook)
}
