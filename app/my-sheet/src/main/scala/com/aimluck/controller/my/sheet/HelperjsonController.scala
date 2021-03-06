/*
 * This file is part of official Aipo App.
 * Copyright (C) 2011-2011 Aimluck,Inc.
 * http://www.aipo.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.aimluck.controller.my.sheet

import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import org.dotme.liquidtpl.controller.AbstractJsonController
import dispatch.json.JsValue
import sjson.json.JsonSerialization._
import org.dotme.liquidtpl.Constants
import com.aimluck.service.SheetService
import com.aimluck.model.SheetColumn
import dispatch.json.Js
import java.util.logging.Logger

class HelperjsonController extends AbstractJsonController {
  val logger = Logger.getLogger(classOf[HelperjsonController].getName)
  @throws(classOf[Exception])
  override def getJson: JsValue = {
    val mode = request.getParameter(Constants.KEY_MODE)
    try {
      mode match {
        case "defineForm" => {
          val index: Int = request.getParameter("index").toInt
          val dataType: String = request.getParameter("dataType")
          val column: SheetColumn = SheetService.createNewColumn
          JsValue(SheetService.dataTypeDefineFormTag(index, column).toString)
        }
        case "defineForm.body" => {
          val index: Int = request.getParameter("index").toInt
          val dataType: String = request.getParameter("dataType")
          val column: SheetColumn = SheetService.createNewColumn
          try {
            SheetService.setColumnParameters(index, column, request);
          } catch {
            case e: Exception =>
              logger.warning(e.getMessage)
              logger.warning(e.getStackTraceString)
          }
          JsValue(SheetService.dataTypeDefineFormTagMap(index, column).apply(dataType).toString)
        }
        case _ => JsValue("")
      }
    } catch {
      case e: Exception =>
        e.printStackTrace
        JsValue("")
    }
  }
}
